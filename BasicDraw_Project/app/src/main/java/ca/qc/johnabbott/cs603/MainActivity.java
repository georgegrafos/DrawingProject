package ca.qc.johnabbott.cs603;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.qc.johnabbott.cs603.Tasks.AsyncSavePic;

public class MainActivity extends Activity {

    public static String token;
    public static Context context;
    private DrawingView drawing;
    private Dialog current;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawing = (DrawingView)this.findViewById(R.id.drawing_view);
        context = this.getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_tools:
                showToolsDialog();
                return true;
            case R.id.menu_menu:
                showMenuDialog();
                return true;
            case R.id.menu_save:
                //prompt user for the name of picture
                setPictureNameDialog(getApplicationContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // display messages related to the login process
    public static void displayMessage(String message){
        Spannable centeredText = new SpannableString(message);
        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, message.length() - 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        Toast.makeText(context, centeredText, Toast.LENGTH_SHORT).show();
        return;
    }

    // start drawing activity on successful login
    public static void startDrawingListActivity(String token){
        MainActivity.token = token;
        Intent intent = new Intent(MainActivity.context, DrawingList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("token", token);
        MainActivity.context.startActivity(intent);
    }

    private void showToolsDialog() {
        new ToolSettingsDialog(this, drawing.getToolBox());
    }

    private void showMenuDialog() {
        current = new Dialog(this);
        current.setContentView(R.layout.dialog_menu);
        current.setTitle("Menu");
        current.setCanceledOnTouchOutside(true);

        Button buttonErase = (Button) current.findViewById(R.id.buttonErase);
        Button buttonUndo = (Button) current.findViewById(R.id.buttonUndo);

        //disable undo option if picture is empty
        if(drawing.getPicture().numShapes() == 0)
            buttonUndo.setEnabled(false);
        else
            buttonUndo.setEnabled(true);

        buttonErase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawing.erase();
                drawing.invalidate();

                current.dismiss();
            }
        });

        buttonUndo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawing.getPicture().undo();
                drawing.invalidate();

                current.dismiss();
            }
        });

        current.show();
    }

    private void setPictureNameDialog(final Context context){
        alert = new AlertDialog.Builder(this);

        alert.setTitle("Picture Name");
        alert.setMessage("Please enter a name: ");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = input.getText().toString();
                if(name.isEmpty())
                    dialog.dismiss();
                else{
                    if(drawing.getPicture().numShapes() > 0) {
                        //save picture
                        AsyncSavePic save = new AsyncSavePic(drawing.getPicture().convertToJSON(name).toString(), token);
                        save.execute();
                    }
                    else
                        Toast.makeText(context, "Picture is empty, cannot save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.dismiss();
            }
        });

        alert.show();
    }


}

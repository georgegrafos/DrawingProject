package ca.qc.johnabbott.cs603;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
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

public class MainActivity extends Activity {

    private DrawingView drawing;
    private Dialog current;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawing = (DrawingView)this.findViewById(R.id.drawing_view);
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
                        //get JSON of picture
                        Log.d("JSON", drawing.getPicture().convertToJSON(name).toString());
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

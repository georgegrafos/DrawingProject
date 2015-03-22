package ca.qc.johnabbott.cs603;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private DrawingView drawing;
    private Dialog current;

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
            case R.id.menu_create_account:
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
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


}

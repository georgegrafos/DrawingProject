package ca.qc.johnabbott.cs603;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.qc.johnabbott.cs603.Tasks.AsyncDeletePic;
import ca.qc.johnabbott.cs603.Tasks.AsyncDrawingList;
import ca.qc.johnabbott.cs603.Tasks.AsyncLogout;
import ca.qc.johnabbott.cs603.Tasks.AsyncViewPic;


public class DrawingList extends Activity {
    private ListView drawingView;
    private Dialog current;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<Integer> identifiers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_list);
        MainActivity.context = getApplicationContext();

        this.identifiers = new ArrayList<Integer>();
        this.drawingView = (ListView) findViewById(android.R.id.list);

        AsyncDrawingList drawingListTask = new AsyncDrawingList(MainActivity.token, this);
        drawingListTask.execute();

        // user presses and holds on list item
        this.drawingView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showMenuDialog(position);
                return true;
            }
        });

        // user clicks on list item to view it
        this.drawingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewPicture(position);
            }
        });
    }

    private void deletePicture(int position){
        AsyncDeletePic delete = new AsyncDeletePic(MainActivity.token, this.identifiers.get(position), this, position);
        delete.execute();
    }

    private void viewPicture(int position){
        AsyncViewPic viewPic = new AsyncViewPic(MainActivity.token, this.identifiers.get(position), this);
        viewPic.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_drawing_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case R.id.action_draw:
                Intent intent = new Intent(DrawingList.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.list_logout:
                AsyncLogout logoutTask = new AsyncLogout();
                logoutTask.execute();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createListView(String response) {
        try {
            // get JSONArray from AsyncTask
            JSONArray json = new JSONArray(response);

            // add all picture names to ArrayList
            List<String> items = new ArrayList<String>();
            for(int i = 0; i < json.length(); i++) {
                JSONObject json_data = json.getJSONObject(i);
                String name = json_data.getString("name");
                Integer id = json_data.getInt("id");
                items.add(name);
                this.identifiers.add(id);
            }
            // populate ListView
            arrayAdapter = new ArrayAdapter<String>(MainActivity.context, R.layout.custom_layout, items);
            this.drawingView.setAdapter(arrayAdapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void updateList(int removeIndex){
        arrayAdapter.remove(arrayAdapter.getItem(removeIndex));
        arrayAdapter.notifyDataSetChanged();
    }

    public static void displayMessage(String message){
        Spannable centeredText = new SpannableString(message);
        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, message.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        Toast.makeText(MainActivity.context, centeredText, Toast.LENGTH_SHORT).show();
        return;
    }

    private void showMenuDialog(final int position) {
        current = new Dialog(this);
        current.setContentView(R.layout.dialog_delete_pic);
        current.setTitle(arrayAdapter.getItem(position).toString());
        current.setCanceledOnTouchOutside(true);

        Button buttonDelete = (Button) current.findViewById(R.id.buttonDelete);

        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletePicture(position);
                current.dismiss();
            }
        });

        current.show();
    }

    public void startPictureViewerActivity(String response){
        Intent intent = new Intent(this, PictureView.class);
        intent.putExtra("json", response);
        startActivity(intent);
    }

    public static void redirectToLogin() {
        Intent intent = new Intent(MainActivity.context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.context.startActivity(intent);
    }
}

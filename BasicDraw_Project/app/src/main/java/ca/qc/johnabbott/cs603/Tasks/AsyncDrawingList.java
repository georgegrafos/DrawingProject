package ca.qc.johnabbott.cs603.Tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import ca.qc.johnabbott.cs603.DrawingList;

/**
 * Created by George on 15-04-26.
 */
public class AsyncDrawingList extends AsyncTask<String, Void, String> {
    private String token;

    public AsyncDrawingList(String token) {
        this.token = token;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            // Create the GET request
            url = new URL("http://linux2-cs.johnabbott.qc.ca/~cs506_f14_10/androidApp/API/get_picts.php?token=" + this.token);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setDoInput(true);
            con.setChunkedStreamingMode(0);

            // communication was successful?
            if (con.getResponseCode() == 200) {
                // retrieve the output from the request
                Scanner scanner = new Scanner(con.getInputStream());
                String response = scanner.hasNext() ? scanner.next() : "";

                System.out.println(response);
                // handle the output cases
                if(response.equalsIgnoreCase("empty")) {
                    return "You have no drawings!";
                }else{
                    return response;
                }
            }else{
                return "Connection could not be established, check your network connection";
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            // Something went wrong with getting the input stream, probably a network error
            return "Connection could not be established, check your network connection";
        }
        return null;
    }

    // gets called once doInBackground is completed
    // parameter "result" is the result of the return in doInBackground
    @Override
    protected void onPostExecute(String result) {
        if(result == "empty") {
            DrawingList.displayMessage(result);
        }else{
            DrawingList drawingList = new DrawingList();
            drawingList.createListView(result);
        }
    }
}

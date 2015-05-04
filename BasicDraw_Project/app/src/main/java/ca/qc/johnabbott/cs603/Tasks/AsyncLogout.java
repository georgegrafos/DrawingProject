package ca.qc.johnabbott.cs603.Tasks;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import ca.qc.johnabbott.cs603.DrawingList;
import ca.qc.johnabbott.cs603.LoginActivity;
import ca.qc.johnabbott.cs603.MainActivity;

/**
 * Created by George on 15-05-04.
 */
public class AsyncLogout extends AsyncTask<String, Void, String> {
    private boolean success = false;

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        URL url;
        try {
            // Create the GET request
            url = new URL("http://linux2-cs.johnabbott.qc.ca/~cs506_f14_10/androidApp/API/logout.php?token=" + MainActivity.token);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setDoInput(true);
            con.setChunkedStreamingMode(0);

            // communication was successful?
            if (con.getResponseCode() == 200) {
                this.success = true;
                return "Logout successful";
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
        if(this.success == true)
            DrawingList.redirectToLogin();
        else
            DrawingList.displayMessage(result);
    }
}

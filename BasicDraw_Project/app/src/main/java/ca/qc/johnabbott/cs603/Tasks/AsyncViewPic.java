package ca.qc.johnabbott.cs603.Tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import ca.qc.johnabbott.cs603.DrawingList;

/**
 * Created by AlexGenio on 15-05-01.
 */
public class AsyncViewPic extends AsyncTask<String, Void, String> {
    private String token;
    private Integer picID;
    private DrawingList caller;
    private Boolean success;

    public AsyncViewPic(String token, Integer picID, DrawingList caller) {
        this.caller = caller;
        this.token = token;
        this.picID = picID;
        this.success = false;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            // Create the GET request
            url = new URL("http://linux2-cs.johnabbott.qc.ca/~cs506_f14_10/androidApp/API/get_pict.php?token=" + this.token + "&picture_id=" + this.picID.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setDoInput(true);
            con.setChunkedStreamingMode(0);

            // communication was successful?
            if (con.getResponseCode() == 200) {
                // retrieve the output from the request
                Scanner scanner = new Scanner(con.getInputStream()).useDelimiter("\\A");
                String response = scanner.hasNext() ? scanner.next() : "";

                // handle the output cases
                if (response.equalsIgnoreCase("error-1")) {
                    return "Blank Picture ID";
                } else if (response.equalsIgnoreCase("invalid/expired token")) {
                    return "Expired Token";
                } else if (response.equalsIgnoreCase("empty")) {
                    return "Empty";
                } else if (response.equalsIgnoreCase("invalid picture ID")) {
                    return "Invalid Picture ID";
                } else {
                    this.success = true;
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
        if(this.success)
            caller.startPictureViewerActivity(result);
        else
            DrawingList.displayMessage(result);
        return;
    }
}

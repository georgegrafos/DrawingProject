package ca.qc.johnabbott.cs603.Tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import ca.qc.johnabbott.cs603.LoginActivity;

/**
 * Created by AlexGenio on 15-04-18.
 */
public class AsyncLogin extends AsyncTask<String, Void, String> {
    private String username;
    private String password;
    private Boolean success;

    public AsyncLogin(String username, String password){
        this.username = username;
        this.password = password;
        this.success = false;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        URL url;
        try {
            // Create the GET request
            url = new URL("http://linux2-cs.johnabbott.qc.ca/~cs506_f14_10/androidApp/API/login.php?username=" + this.username + "&password=" + this.password);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setDoInput(true);
            con.setChunkedStreamingMode(0);

            // communication was successful?
            if (con.getResponseCode() == 200) {
                // retrieve the output from the request
                Scanner scanner = new Scanner(con.getInputStream());
                String response = scanner.hasNext() ? scanner.next() : "";

                // handle the output cases
                if(response.equalsIgnoreCase("error-1")) {
                    return "Blank fields";
                }
                else if (response.equalsIgnoreCase("error-2")) {
                    return "Invalid username or password";
                }
                else{
                    this.success = true;
                    return response.replace("token=", "");
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
        // Bring the user to the new activity on success
        if(this.success){
            LoginActivity.startDrawingListActivity(result, this.username);
        }else{
            // display error messages
            LoginActivity.displayMessage(result);
        }
        return;
    }
}

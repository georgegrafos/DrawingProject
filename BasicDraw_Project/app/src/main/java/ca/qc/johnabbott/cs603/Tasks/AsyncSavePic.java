package ca.qc.johnabbott.cs603.Tasks;

import android.os.AsyncTask;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import ca.qc.johnabbott.cs603.MainActivity;

/**
 * Created by AlexGenio on 15-04-28.
 */
public class AsyncSavePic extends AsyncTask<String, Void, String> {
    private String jSON;
    private String token;
    private Boolean success;

    public AsyncSavePic(String jSON, String token){
            this.jSON = jSON;
            this.token = token;
            this.success = false;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        HttpURLConnection con= null;
        // TODO Auto-generated method stub
        try {
            //Create connection
            url = new URL("http://linux2-cs.johnabbott.qc.ca/~cs506_f14_10/androidApp/API/add_pic.php?token=" + this.token.toString());
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            String urlParameters ="encoded_string=" + URLEncoder.encode(this.jSON, "UTF-8");
            con.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            con.setRequestProperty("Content-Language", "en-US");
            con.setUseCaches (false);
            con.setDoInput(true);
            con.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(con.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            if (con.getResponseCode() == 200) {
                // retrieve the output from the request
                Scanner scanner = new Scanner(con.getInputStream()).useDelimiter("\\A");
                String response = scanner.hasNext() ? scanner.next() : "";

                if(response.equalsIgnoreCase("error-1")) {
                    return "Blank Picture";
                }else if(response.equalsIgnoreCase("invalid/expired token")){
                    return "Expired Token";
                }else{
                    this.success = true;
                    return response.replace("picture_id=", "");
                }
            }else{
                return "Connection could not be established, check your network connection";
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
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
        if(this.success) {
            MainActivity.displayMessage("Picture saved!");
            MainActivity.startDrawingListActivity(this.token);
        }else
            MainActivity.displayMessage(result);
        return;
    }
}

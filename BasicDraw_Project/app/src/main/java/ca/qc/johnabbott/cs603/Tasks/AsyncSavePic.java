package ca.qc.johnabbott.cs603.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            // TODO Auto-generated method stub
            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://linux2-cs.johnabbott.qc.ca/~cs506_f14_10/androidApp/API/add_pic.php?token=" + this.token.toString());

                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("encoded_string", this.jSON));
                post.setEntity(new UrlEncodedFormEntity(pairs));

                post.setHeader("Accept", "application/json");
                post.setHeader("Content-type", "application/json");

                HttpResponse response = client.execute(post);

                if (response.getStatusLine().getStatusCode() == 200) {
                    Scanner scanner = new Scanner(response.getEntity().getContent());
                    String res = scanner.hasNext() ? scanner.next() : "";
                    Log.d("SDSDVSSDVSDVSVSV", res.toString());
                }else{
                    return "Connection could not be established, check your network connection";
                }

                /*// handle the output case
                if(response.equalsIgnoreCase("error-1")) {
                    return "Blank fields";
                }
                else if (response.equalsIgnoreCase("error-2")) {
                    return "Username already in use";
                }
                else if (response.equalsIgnoreCase("error-3")){
                    return "Email already in use";
                }
                else{
                    this.success = true;
                    return response.replace("created-account_id=", "");
                }*/
            }catch (UnsupportedEncodingException e){
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
            /*if(this.success){
                CreateAccountActivity.startDrawingListActivity(result, this.username);
            }else{
                // display error messages
                CreateAccountActivity.displayMessage(result);
            }*/
            return;
    }
}

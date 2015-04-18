package ca.qc.johnabbott.cs603;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Patterns;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import ca.qc.johnabbott.cs603.Tasks.AsyncLogin;


public class LoginActivity extends Activity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context = getApplicationContext();
    }

    public void startDrawingActivity(View view) {
        EditText user = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        String userName = user.getText().toString();
        String userPass = password.getText().toString();

        String errorMsg = "Invalid field(s): \n\n";
        Boolean isError = false;

        if(userName.matches("")) {
            errorMsg += "Username\n";
            isError = true;
        }
        if(userPass.matches("")) {
            errorMsg += "Password";
            isError = true;
        }

        if(isError){
            displayMessage(errorMsg);
        }else{
            // asynchronously verify the login
            AsyncLogin loginTask = new AsyncLogin(userName, userPass);
            loginTask.execute();
        }
    }

    public void startCreateAccountActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
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
    public static void goToDraw(String token, String username){
        // do something with the token
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        displayMessage("Welcome " + username + "!");
        context.startActivity(intent);
    }
}

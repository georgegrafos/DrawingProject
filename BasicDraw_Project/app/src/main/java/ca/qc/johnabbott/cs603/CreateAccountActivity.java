package ca.qc.johnabbott.cs603;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import ca.qc.johnabbott.cs603.Tasks.AsyncCreateAccount;

/**
 * Created by AlexGenio on 15-03-21.
 */
public class CreateAccountActivity extends Activity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        this.context = getApplicationContext();
    }

    public void createAccount(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        String userName = username.getText().toString();
        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();

        String errorMsg = "Invalid field(s): \n\n";
        Boolean isError = false;
        Pattern emailRegEx = Patterns.EMAIL_ADDRESS;


        if(userName.matches("")) {
            errorMsg += "Username\n";
            isError = true;
        }
        if(userEmail.matches("") || !emailRegEx.matcher(userEmail).matches()) {
            errorMsg += "Email\n";
            isError = true;
        }
        if(userPass.matches("")) {
            errorMsg += "Password";
            isError = true;
        }
        if(isError){
            displayMessage(errorMsg);
        }else{
            AsyncCreateAccount task = new AsyncCreateAccount(userName, userEmail, userPass);
            task.execute();
        }
    }

    public void startLogInActivity(View view) {
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public static void startDrawingListActivity(String accNum, String username) {
        // do something with the token
        Intent intent = new Intent(context, DrawingList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        displayMessage("Account number " + accNum + " created for " + username);
        context.startActivity(intent);
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

}

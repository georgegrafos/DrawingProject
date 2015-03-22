package ca.qc.johnabbott.cs603;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by AlexGenio on 15-03-21.
 */
public class CreateAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
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
            //center text within toast
            Spannable centeredText = new SpannableString(errorMsg);
            centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0, errorMsg.length() - 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            Toast.makeText(this, centeredText, Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(this, "Account has successfully been created!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
}

package ca.qc.johnabbott.cs603;

import android.app.Activity;
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


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void startDrawingActivity(View view) {
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();

        String errorMsg = "Invalid field(s): \n\n";
        Boolean isError = false;
        Pattern emailRegEx = Patterns.EMAIL_ADDRESS;

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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void startCreateAccountActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }
}

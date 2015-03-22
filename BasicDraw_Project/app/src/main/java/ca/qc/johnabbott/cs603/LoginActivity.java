package ca.qc.johnabbott.cs603;

import android.app.Activity;
import android.os.Bundle;
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

        Pattern emailRegEx = Patterns.EMAIL_ADDRESS;

        if(userEmail.matches("") && userPass.matches("")) {
            Toast.makeText(this, "You did not enter an email and password", Toast.LENGTH_SHORT).show();
            return;
        }else if(userEmail.matches("") || !emailRegEx.matcher(userEmail).matches()) {
            Toast.makeText(this, "You did not enter a proper email", Toast.LENGTH_SHORT).show();
            return;
        }else if(userPass.matches("")) {
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

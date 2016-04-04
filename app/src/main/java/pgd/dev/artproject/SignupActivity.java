package pgd.dev.artproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText edtName, edtEmail, edtPass;
    Button btnSignup;
    TextView txtLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtName = (EditText) findViewById(R.id.input_name);
        edtEmail = (EditText) findViewById(R.id.input_email);
        edtPass = (EditText) findViewById(R.id.input_password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        txtLogin = (TextView) findViewById(R.id.link_login);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        // TODO: Implement your own signup logic here.

        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        btnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnSignup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            edtName.setError("at least 3 characters");
            valid = false;
        } else {
            edtName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("enter a valid email address");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPass.setError(null);
        }

        return valid;
    }
}
package pgd.dev.artproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pgd.dev.artproject.Controller.User;
import pgd.dev.artproject.Controller.UserLocalStore;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText edtEmail, edtPass;
    Button btnLogin;
    TextView txtSignup;
    UserLocalStore userLocalStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.input_email);
        edtPass = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtSignup = (TextView) findViewById(R.id.link_signup);

        userLocalStore = new UserLocalStore(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }

        if (!authenticate(new User("rey", "rey"))) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        // TODO: Implement your own authentication logic here.

        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(false);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

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

    private boolean authenticate(User user) {
        /**
         * Jika Menggunakan Rest Untuk Login
         */
//        ServerUserRequests serverRequests = new ServerUserRequests(this.getActivity().getBaseContext());
//        serverRequests.fetchUserDetailInBackground(user, new UserCallback() {
//            @Override
//            public void done(User returnUser) {
//                if (returnUser==null){
//                    showErrorMessage();
//                }else{
//                    LogeUserInt(returnUser);
//                }
//            }
//        });

        /**
         * Sementara Langsung Login
         */
//        LogeUserInt(new User(etUserName.getText().toString(), etUserName.getText().toString(), "1234"));
        LogeUserInt(new User("IDREY", "Rey", "1234"));
        return true;
    }

    private void showErrorMessage() {
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(getApplicationContext());
        diaBuilder.setMessage("incorect user Details ");
        diaBuilder.setPositiveButton("OK", null);
        diaBuilder.show();
    }

    private void LogeUserInt(User returnUser) {
        userLocalStore.storeUserData(returnUser);
        userLocalStore.setUserLoggedIn(true);

//        Fragment fragment = new UserFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
//        ((MainActivity) (getActivity())).updateGenerateDrawer();
    }

}

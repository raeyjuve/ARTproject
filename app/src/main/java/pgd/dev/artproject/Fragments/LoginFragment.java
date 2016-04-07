package pgd.dev.artproject.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import pgd.dev.artproject.Controller.User;
import pgd.dev.artproject.Controller.UserLocalStore;
import pgd.dev.artproject.MainActivity;
import pgd.dev.artproject.Model.GenericResponse;
import pgd.dev.artproject.R;
import pgd.dev.artproject.RestClient.AuthenticationRestClient;
import pgd.dev.artproject.exception.GagalLoginException;

public class LoginFragment extends Fragment implements View.OnClickListener {
    Button bLogin;
    EditText etUserName, etPassword;
    TextView tvRegisterLink;
    UserLocalStore userLocalStore;
    String tag = "Login Frag";

    public LoginFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) view.findViewById(R.id.tvRegisterLink);

        bLogin = (Button) view.findViewById(R.id.bLogin);
        bLogin.setEnabled(true);
        bLogin.setOnClickListener(this);

        tvRegisterLink.setOnClickListener(this);
        userLocalStore = new UserLocalStore(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bLogin:
                String userId = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                authenticate(new User(userId, password));
                break;
        }
    }

    private void authenticate(final User user){
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

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        // onLoginSuccess();
                        // onLoginFailed();
                        /**
                         * Sementara Langsung Login
                         */
                        loginProcess(user);
//                        LogeUserInt(new User(etUserName.getText().toString(), etPassword.getText().toString(), "1234"));

                        progressDialog.dismiss();
                    }
                }, 1500);
    }

    private void showErrorMessage(){
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(getActivity());
        diaBuilder.setMessage("incorect user Details ");
        diaBuilder.setPositiveButton("OK", null);
        diaBuilder.show();
    }

    private void loginProcess(User user) {
        new AsyncTask<String, Void, GenericResponse>() {

            ProgressDialog progressDialog;
            String pesanError;

            @Override
            protected void onPreExecute() {
                bLogin.setEnabled(false);
                progressDialog = ProgressDialog.show(getActivity(), "Logging in", "Authentication...", true);
            }

            @Override
            protected GenericResponse doInBackground(String... params) {
                AuthenticationRestClient restClient = new AuthenticationRestClient();
                try {
                    GenericResponse hasil = restClient.login(params[0], params[1]);
                    Log.i(tag, hasil.toString());

                    if (hasil.isSuccess()) {
                        Log.i(tag, "Email : " + hasil.getData().get("email"));
                    } else {
                        Log.i(tag, "Error : " + hasil.getData().get("errormessage"));
                    }

                    return hasil;
                } catch (GagalLoginException err) {
                    Log.i(tag, "Koneksi ke server gagal");
                    pesanError = err.getMessage();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(GenericResponse genericResponse) {

                progressDialog.dismiss();
                bLogin.setEnabled(true);

                if (genericResponse == null) {
                    Toast.makeText(getActivity(), pesanError, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (genericResponse.isSuccess()) {
                    Map<String, Object> hData = genericResponse.getData();
                    LogeUserInt(new User(hData.get("email").toString(), hData.get("username").toString(), "1234"));
                } else {
                    Toast.makeText(getActivity(), genericResponse.getData().get("errormessage").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(user.getUserId(), user.getPassword());
    }

    private void LogeUserInt(User returnUser){
        userLocalStore.storeUserData(returnUser);
        userLocalStore.setUserLoggedIn(true);

        bLogin.setEnabled(true);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userDetails", 0);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString("userIdRec", returnUser.getUserId());
        spEditor.putString("usernameRec", returnUser.getName());

        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
        ((MainActivity) (getActivity())).updateGenerateDrawer();
    }
}

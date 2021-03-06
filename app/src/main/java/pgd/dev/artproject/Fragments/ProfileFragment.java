package pgd.dev.artproject.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pgd.dev.artproject.Controller.User;
import pgd.dev.artproject.Controller.UserLocalStore;
import pgd.dev.artproject.R;

/**
 * Created by Frog-Grammar on 24-Feb-16.
 */
public class ProfileFragment extends Fragment {
    UserLocalStore userLocalStore;
    TextView txtGetID, txtGetName;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        userLocalStore = new UserLocalStore(getContext());

        txtGetID = (TextView) rootView.findViewById(R.id.txtGetID);
        txtGetName = (TextView) rootView.findViewById(R.id.txtGetName);

        putShared();

        return rootView;
    }

    public void putShared() {
        User user = userLocalStore.getLoggedInUser();
        if (user != null) {
            txtGetID.setText(user.getUserId());
            txtGetName.setText(user.getName());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

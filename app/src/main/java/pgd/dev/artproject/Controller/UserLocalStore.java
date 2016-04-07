package pgd.dev.artproject.Controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by syaiful on 2/20/2016.
 */
public class UserLocalStore {
    public  static final String SP_NAME="userDetails";
    SharedPreferences userLocalDB;

    public UserLocalStore(Context context){
        userLocalDB = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDB.edit();
        spEditor.putString("userId", user.getUserId());
        spEditor.putString("name", user.getName());
        spEditor.putString("password", user.getPassword());
        spEditor.commit();
    }

    public User getLoggedInUser() {
        String userid = userLocalDB.getString("userId", "");
        String name = userLocalDB.getString("name", "");
        String password = userLocalDB.getString("password", "");

        User storeUser = new User(userid, name , password);
        return storeUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDB.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedid(){
        if (userLocalDB.getBoolean("loggedIn", false)==true){
            return true;
        }else{
            return false;
        }
    }

    public  void clearUsereData(){
        SharedPreferences.Editor spEditor = userLocalDB.edit();
        spEditor.clear();
        spEditor.commit();
    }
}

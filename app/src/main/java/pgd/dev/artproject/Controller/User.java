package pgd.dev.artproject.Controller;

/**
 * Created by syaiful on 2/20/2016.
 */
public class User {
    String userId;
    String name;
    String password;

    public User(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.name = "";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

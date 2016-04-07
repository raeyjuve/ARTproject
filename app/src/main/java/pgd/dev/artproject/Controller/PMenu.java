package pgd.dev.artproject.Controller;

/**
 * Created by syaiful on 3/24/2016.
 */
public class PMenu {
    int id;
    int no;
    String title;
    String classes;
    int auth;

    public PMenu(){
    }
    public PMenu(int id, int no, String title, String classes, int auth) {
        this.id = id;
        this.no = no;
        this.title = title;
        this.classes = classes;
        this.auth = auth;
    }

    public PMenu(int no, String title, String classes, int auth) {
        this.no = no;
        this.title = title;
        this.classes = classes;
        this.auth = auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }
}

package pgd.dev.artproject.Model;

/**
 * Created by Frog-Grammar on 04-Apr-16.
 */
public class NavDrawerItem {
    private int id;
    private String title;
    private String className;
    private int auth;

    public NavDrawerItem(boolean showNotify, String title, String className) {
        this.showNotify = showNotify;
        this.title = title;
        this.className = className;
    }

    public NavDrawerItem(int id, String title, String className, int auth) {
        this.id = id;
        this.title = title;
        this.className = className;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    private boolean showNotify;

    public NavDrawerItem() {
    }
}

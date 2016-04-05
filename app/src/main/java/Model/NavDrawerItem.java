package Model;

/**
 * Created by Frog-Grammar on 04-Apr-16.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String tag;

    public NavDrawerItem() {
    }

    public NavDrawerItem(boolean showNotify, String title, String tag) {
        this.showNotify = showNotify;
        this.title = title;
        this.tag = tag;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

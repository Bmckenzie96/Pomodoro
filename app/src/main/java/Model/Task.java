package Model;

/**
 * Created by Ben on 6/5/2016.
 */
public class Task {
    String mTitle;
    String mContent;
    public Task(String title, String content) {
        mTitle = title;
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}

package Model;

import com.backendless.BackendlessUser;

/**
 * Created by Ben on 6/15/2016.
 */
public class BackendlessTask {
    private String mTitle;
    private String mContent;
    private String mOwner;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public BackendlessTask(String title, String content, String owner) {
        mContent = content;
        mOwner = owner;
        mTitle = title;
    }

    public BackendlessTask() {}
}

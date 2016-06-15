package Model;

/**
 * Created by Ben on 6/5/2016.
 */
public class Task {
    String mTitle;
    String mContent;
    long rowId;
    private String OwnersId;
    private int isDirty = 0;

    public int getIsDirty() {
        return isDirty;
    }

    public void setIsDirty(int isDirty) {
        this.isDirty = isDirty;
    }

    public String getOwnersId() {
        return OwnersId;
    }

    public void setOwnersId(String ownersId) {
        OwnersId = ownersId;
    }

    public Task() {}

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public Task(String title, String content, String ownersId) {
        mTitle = title;
        mContent = content;
        OwnersId = ownersId;
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

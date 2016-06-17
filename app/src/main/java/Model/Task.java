package Model;

/**
 * Created by Ben on 6/5/2016.
 */
public class Task {
    private String title;
    private String content;
    private long rowId;
    private String objectId;
    private String ownerId;
    private int isDirty = 0; //1 means editing is not saved, 2 means the task was deleted and needs to be removed from backendless

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getIsDirty() {
        return isDirty;
    }

    public void setIsDirty(int isDirty) {
        this.isDirty = isDirty;
    }

    public Task() {}

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public Task(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

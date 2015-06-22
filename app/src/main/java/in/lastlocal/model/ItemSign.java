package in.lastlocal.model;

/**
 * Created by USER on 21-Jun-15.
 */
public class ItemSign {

    int id;
    String msg;
    String filename;

    String title;
    String desc;

    public ItemSign(int id, String msg) {
        this.id = id;
        this.msg = msg;
        this.filename = "";
        this.title = "";
        this.desc = "";
    }

    public ItemSign(String filename, String msg) {
        this.id = 0;
        this.msg = msg;
        this.filename = filename;
        this.title = "";
        this.desc = "";
    }

    public ItemSign(String filename, String title, String desc) {
        this.id = 0;
        this.msg = "";
        this.filename = filename;
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}




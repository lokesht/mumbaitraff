package in.lastlocal.model;

/**
 * Created by USER on 21-Jun-15.
 */
public class ItemSign {

    int id;
    String msg;

    public ItemSign(int id, String msg) {
        this.id = id;
        this.msg = msg;
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
}

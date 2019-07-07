package pwd.allen.util;

import pwd.allen.entity.User;

import java.util.List;

/**
 * @author 门那粒沙
 * @create 2019-07-07 14:34
 **/
public class OraclePage {
    private int start;
    private int end;
    private int total;
    private List<User> data;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}

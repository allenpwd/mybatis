package pwd.allen.entity;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("user")
/**
 * @author pwd
 * @create ${Year}-07-22 14:22
 **/
public class User {
    private Integer id;
    private String userName;
    private int age;
    private int status;
    private Date createAt;
    private Department dept;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", status=" + status +
                ", createAt=" + createAt +
                '}';
    }
}

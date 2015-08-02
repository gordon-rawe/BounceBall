package rawe.gordon.com.boucingballdemo;

/**
 * Created by gordon on 2015/8/1.
 */
public class User {
    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    String userName;
    String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

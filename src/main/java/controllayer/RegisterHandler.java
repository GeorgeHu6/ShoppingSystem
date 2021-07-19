package controllayer;

import modellayer.ModelToolClass;
import modellayer.User;
import org.bson.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterHandler {
    String username, password;
    public RegisterHandler(String username, String psw) {
        this.username = username;
        this.password = psw;
    }

    /* 注册Handler.register返回状态码
         0——注册成功
         1——用户名不符合要求
         2——密码不符合要求
         3——用户名已存在
     */
    public int register() {
        //用户名长度不小于5，不大于20
        if (!User.usernameValidation(this.username))
            return 1;
        if (!User.passwordValidation(this.password))
            return 2;
        if (User.userExisted(this.username))
            return 3;

        return 0;
    }


}

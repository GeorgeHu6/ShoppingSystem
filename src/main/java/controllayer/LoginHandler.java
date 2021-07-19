package controllayer;

import modellayer.User;

public class LoginHandler {
    String username, password;
    public LoginHandler(String username, String psw) {
        this.username = username;
        this.password = psw;
    }

    /* login返回状态码
        0——登录成功
        1——密码错误
        2——用户不存在
     */
    public int login() {
        if (!User.userExisted(this.username))
            return 2;
        User user = User.getInstance(this.username, this.password);
        if (user == null)
            return 1;

        return 0;
    }
}

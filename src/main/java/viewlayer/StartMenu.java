package viewlayer;

import controllayer.ControlToolClass;
import controllayer.LoginHandler;
import controllayer.RegisterHandler;
import modellayer.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class StartMenu {
    /* 返回值
     有登录态的User——页面生命周期正常结束，将程序控制权交到下一个事件循环
     无登录态的User——页面生命周期无效，需要将程序控制权重新交到本事件
     null——页面生命周期中断，程序控制权中断，需要退出系统
     */
    public User loginMenuPage(InputStream is) throws IOException, InterruptedException {
        int choice;
        boolean loopFlag = true;
        User loginStatus, registerStatus;
        String inputStr;
        String prompt = "请输入选择项的序号（输入q返回）：";
        Scanner scanner = new Scanner(is);

        while (loopFlag) {
            ViewToolClass.clearScreen();
            System.out.print(ViewToolClass.getPage("login_menu"));
            System.out.print(prompt);
            inputStr = scanner.nextLine();
            if (inputStr.equals("q"))
                return null;
            else if (ControlToolClass.intQualify(inputStr, 1, 3)) {
                choice = Integer.parseInt(inputStr);
                //登录
                if (choice == 1) {
                    ViewToolClass.clearScreen();
                    loginStatus = loginPage(is);
                    if (!loginStatus.isLogined()) {
                        System.out.println("登录失败，返回主菜单...");
                        Thread.sleep(1000);
                        return new User();
                    } else {
                        System.out.println("登录成功，正在跳转至用户主界面");
                        Thread.sleep(1000);
                        return loginStatus;
                    }

                }
                //注册
                else if (choice == 2) {
                    ViewToolClass.clearScreen();
                    registerStatus = registerPage(is);
                    if (!registerStatus.isLogined()) {
                        System.out.println("注册失败，返回主菜单...");
                        Thread.sleep(1000);
                        return new User();
                    } else {
                        System.out.println("注册成功，正在跳转至用户主界面");
                        Thread.sleep(1000);
                        return registerStatus;
                    }
                }
                //退出
                else if (choice == 3) {
                    return null;
                }
            } else {
                System.out.println("输入的格式有误！");
                Thread.sleep(1000);
            }
        }
        return new User();
    }

    /* 返回值
    有登录态的User——登录成功
    无登录态的User——登录失败
     */
    public User loginPage(InputStream is) throws IOException {
        String username, password;
        Scanner scanner = new Scanner(is);
        LoginHandler loginSession;

        System.out.print(ViewToolClass.getPage("login"));
        System.out.print("请输入用户名：");
        username = scanner.nextLine();
        System.out.print("请输入密码：");
        password = scanner.nextLine();

        loginSession = new LoginHandler(username, password);
        switch (loginSession.login()) {
            case 0:
                return User.getInstance(username, password);
            case 1:
            case 2:
                return new User();
        }

        return null;
    }

    /*返回值
        有登录态的User——注册成功，应进入下个页面
        无登录态的User——注册操作中断，应回到起始页面
     */
    public User registerPage(InputStream is) throws IOException {
        String username="", password="", phoneNumber="", email="";
        boolean passwordLoop = false, usernameLoop = true;
        boolean phoneNumberLoop=false, emailLoop=false;
        boolean allOK=false;
        User res=new User();
        Scanner scanner = new Scanner(is);
        RegisterHandler registerController;

        System.out.print(ViewToolClass.getPage("register"));

        while (usernameLoop) {
            System.out.print("请输入用户名（输入q返回）：");
            username = scanner.nextLine();
            if (username.equals("q")) {
                usernameLoop = false;
            } else if (!User.usernameValidation(username)) {
                System.out.println("用户名不符合要求（用户名长度应为5~20）！");
            } else if (User.userExisted(username)) {
                System.out.println("用户名已存在！");
            } else {
                usernameLoop = false;
                passwordLoop = true;
            }
        }

        while (passwordLoop) {
            System.out.print("请输入密码（输入q返回）：");
            password = scanner.nextLine();
            if (password.equals("q")) {
                passwordLoop = false;
            } else if (!User.passwordValidation(password)) {
                System.out.println("密码不符合要求（密码至少8位，必须包含大小写字母、数字和下划线四种字符）！");
            } else {
                passwordLoop = false;
                phoneNumberLoop = true;
            }
        }

        while (phoneNumberLoop) {
            System.out.print("请输入电话号码（输入q返回）：");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.equals("q")) {
                phoneNumberLoop = false;
            } else if (!User.phoneNumberValidation(phoneNumber)) {
                System.out.println("电话号码不符合要求（至少5位，只能含有数字）！");
            } else {
                phoneNumberLoop = false;
                emailLoop = true;
            }
        }

        while (emailLoop) {
            System.out.print("请输入邮箱地址（输入q返回）：");
            email = scanner.nextLine();
            if (email.equals("q")) {
                emailLoop = false;
            } else if (!User.emailAddressValidation(email)) {
                System.out.println("邮箱地址格式错误！");
            } else {
                emailLoop = false;
                allOK = true;
            }
        }

        if (allOK) {
            res = new User(false, username, phoneNumber, email);
            res.userRegister(password);
        }

        return res;
    }
}

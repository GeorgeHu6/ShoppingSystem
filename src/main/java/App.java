import modellayer.User;
import viewlayer.MainMenu;
import viewlayer.StartMenu;
import viewlayer.ViewToolClass;

import java.io.IOException;
import java.util.Scanner;

public class App {
    //App全局变量
    User currentUser;
    boolean systemRunning = false;
    boolean loginPageRunning = false;
    boolean mainMenuRunning = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        App app = new App();
        app.engineStart();
    }

    public void engineStart() throws IOException, InterruptedException {
        StartMenu loginMenuViewer = new StartMenu();
        MainMenu mainMenu;
        Scanner scanner = new Scanner(System.in);
        System.out.print(ViewToolClass.getPage("welcome"));

        Thread.sleep(1500);

        systemRunning = true;

        //系统总循环
        while (systemRunning) {
            loginPageRunning = true;
            //登录主选单事件循环
            while (loginPageRunning) {
                User statusCode;
                ViewToolClass.clearScreen();
                statusCode = loginMenuViewer.loginMenuPage(System.in);
                if (statusCode == null) {
                    mainMenuRunning = false;
                    loginPageRunning = false;
                    systemRunning = false;
                } else if (!statusCode.isLogined()) {
                    loginPageRunning = true;
                    mainMenuRunning = false;
                } else if (statusCode.isLogined()) {
                    this.currentUser = statusCode;
                    loginPageRunning = false;
                    mainMenuRunning = true;
                }
            }
            //用户主界面事件循环
            while (mainMenuRunning) {
                User statusCode;
                mainMenu = new MainMenu(this.currentUser);
                statusCode = mainMenu.mainMenuPage(System.in);
                //主界面发起系统中断
                if (statusCode == null) {
                    mainMenuRunning = false;
                    systemRunning = false;
                }
                //主界面返回失去登录态的User，即退出登录，返回上级菜单
                else if (!statusCode.isLogined()) {
                    this.currentUser = new User();
                    mainMenuRunning = false;
                }
                //主界面仍有登录态，继续主界面事件循环
                else if (statusCode.isLogined()) {
                    mainMenuRunning = true;
                }
            }
        }

        ViewToolClass.clearScreen();
        System.out.print(ViewToolClass.getPage("quit"));
        Thread.sleep(1500);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setSystemRunning(boolean systemRunning) {
        this.systemRunning = systemRunning;
    }

    public void setLoginPageRunning(boolean loginPageRunning) {
        this.loginPageRunning = loginPageRunning;
    }

    public void setMainMenuRunning(boolean mainMenuRunning) {
        this.mainMenuRunning = mainMenuRunning;
    }
}

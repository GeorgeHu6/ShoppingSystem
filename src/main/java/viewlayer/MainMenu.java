package viewlayer;


import controllayer.ControlToolClass;
import modellayer.Good;
import modellayer.GoodList;
import modellayer.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    /* 返回值
     有登录态的User——页面生命周期正常结束，将程序控制权交到下一个事件循环
     无登录态的User——页面生命周期无效，需要将程序控制权重新交到本事件
     null——页面生命周期中断，程序控制权中断，需要退出系统
    */


    User curUser;

    public MainMenu(User curUser) {
        this.curUser = curUser;
    }

    public User mainMenuPage(InputStream is) throws IOException, InterruptedException {
        int choice;
        User statusCode;
        boolean loopFlag = true;

        //客户主界面事件循环
        if (!this.curUser.isAdmin()) {
            while (loopFlag) {
                ViewToolClass.clearScreen();
                statusCode = customerMainMenu(is);
                //系统中断请求返回上级
                if (statusCode == null)
                    return null;
                    //页面生命周期结束，未失去登录态，返回上级
                else if (statusCode.isLogined()) {
                    return curUser;
                }
            }
        }
        //管理员主界面事件循环
        else {

        }
        return null;

    }

    /* 返回值
        null——中断系统
        有登录态的User——页面生命周期结束，程序控制权应交到下一个页面
        无登录态的User——页面重置，程序控制权应重新交到本页面
     */
    public User customerMainMenu(InputStream is) throws IOException, InterruptedException {
        boolean shoppingFlag, cartFlag, MeFlag;
        User statusCode;
        String inputStr;
        int choice;
        Scanner scanner = new Scanner(is);

        System.out.println(ViewToolClass.getPage("customer_main"));
        System.out.print("请输入选择项的序号（输入q退出系统）：");
        inputStr = scanner.nextLine();

        if (inputStr.equals("q")) {
            return null;
        } else if (ControlToolClass.intQualify(inputStr, 1, 3)) {
            choice = Integer.parseInt(inputStr);
            //控制权交给购物商品页
            if (choice == 1) {
                System.out.println("购物页进入");
                shoppingFlag = true;
                while (shoppingFlag) {
                    statusCode = shoppingPage(is);
                    if ()
                }
                return curUser;
            }
            //控制权交给购物车页
            else if (choice == 2) {

                return curUser;
            }
            //控制权交给个人页面
            else if (choice == 3) {

                return curUser;
            }
        } else {
            System.out.println("输入的格式有误！");
            Thread.sleep(1000);
        }
        return new User();
    }

    public User shoppingPage(InputStream is) throws InterruptedException {
        GoodList goodList = new GoodList();
        String inputStr;
        ArrayList<Good> tmp = goodList.getCurList();
        Scanner scanner = new Scanner(is);
        int choice;

        for (int i = 0; i < tmp.size(); i++) {
            System.out.println(" " + (i + 1) + " " + tmp.get(i).toCustomerString());
        }
        System.out.println("商品列表加载完成，请输入要操作的商品编号（输入q返回））：");
        inputStr = scanner.nextLine();
        if (inputStr.equals("q")) {
            return new User();
        } else if (ControlToolClass.intQualify(inputStr, 1, tmp.size())) {
            choice = Integer.parseInt(inputStr);
            System.out.println("您将把"+tmp.get(choice-1).getName()+"加入购物车，将要加入件数（输入q返回）：");
            inputStr = scanner.nextLine();
            while (!inputStr.equals("q")) {
                //输入数量符合条件（大于0且小于库存数）
                if (ControlToolClass.intQualify(inputStr, 1, tmp.get(choice-1).getQuantity().intValue())) {

                }
            }


        } else {
            System.out.println("输入的格式有误！");
            Thread.sleep(1000);
        }
        return new User();
    }


}

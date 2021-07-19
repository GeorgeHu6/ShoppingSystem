package viewlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ViewToolClass {
    public static void clearScreen() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }

    public static String getPage(String pageName) {
        String pageFoldPath = "src/main/resources/pagefiles/";
        String content;
        try {
            if (pageName.equals("welcome")) {
                content = new String(Files.readAllBytes(Paths.get(pageFoldPath, "Welcome.page")));
            } else if (pageName.equals("login_menu")) {
                content = new String(Files.readAllBytes(Paths.get(pageFoldPath, "LoginMenu.page")));
            } else if (pageName.equals("customer_main")) {
                content = new String(Files.readAllBytes(Paths.get(pageFoldPath, "CustomerMainMenu.page")));
            } else if (pageName.equals("login")) {
                content = new String(Files.readAllBytes(Paths.get(pageFoldPath, "Login.page")));
            } else if (pageName.equals("register")) {
                content = new String(Files.readAllBytes(Paths.get(pageFoldPath, "Register.page")));
            } else if (pageName.equals("quit")) {
                content = new String(Files.readAllBytes(Paths.get(pageFoldPath, "Quit.page")));
            } else if (pageName.equals("goodsthForcust")) {
                content = new String(Files.readAllBytes(Paths.get(pageFoldPath, "GoodsTableHeadforCustomer.page")));
            } else {
                content = "Page route index NOT FOUND.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            content = "IOException";
        }
        return content;
    }
}

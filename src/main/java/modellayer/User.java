package modellayer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class User {
    private boolean admin;
    boolean logined;
    String id, username;
    Long moneyPaid;
    Date registerDate;
    String phoneNumber;
    String email;
    Cart userCart;


    public User(boolean isAdmin, String username, String phoneNumber, String email) {
        this.admin = isAdmin;
        this.username = username;
        this.moneyPaid = 0L;
        this.registerDate = new Date();
        this.phoneNumber = phoneNumber;
        this.email = email;
        logined = false;
    }

    public User() {
    }

    public boolean initCart(String id) {
        this.id = id;
        this.userCart = new Cart(id);

        return true;
    }

    public boolean userRegister(String psw) {
        if (userExisted(this.username))
            return false;
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");
        Document userInfo = this.getDocument();

        userInfo.append("password", ModelToolClass.getSHA(psw));
        userInfo.append("isAdmin", this.admin);

        usersCollection.insertOne(userInfo);

        this.id = usersCollection.find(eq("username", username)).first().getObjectId("_id").toString();
        this.logined = true;

        this.userCart = new Cart(this.id);
        this.userCart.saveToDatabase();

        return true;
    }

    public static User getInstance(String username, String psw) {
        if (!userExisted(username))
            return null;
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");
        Document userInfo;
        User res;

        userInfo = usersCollection.find(eq("username", username)).first();

        if (userInfo.getString("password").equals(ModelToolClass.getSHA(psw))) {
            res = new User(userInfo.getBoolean("isAdmin"), username, userInfo.getString("phone_number"),
                    userInfo.getString("email"));
            res.logined = true;
            ObjectId objId = userInfo.getObjectId("_id");

            res.registerDate = objId.getDate();
            res.id = objId.toString();
            res.initCart(res.id);

            return res;
        } else {
            return null;
        }
    }

    public boolean isAdmin() {
        return admin;
    }

    //?????????????????????????????????????????????????????????????????????
    public static boolean userExisted(String username) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");
        boolean existed;

        existed = usersCollection.find(eq("username", username)).first() != null;

        client.close();

        return existed;
    }

    public static boolean userExisted(ObjectId id) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");

        return usersCollection.find(eq("_id", id)).first()!=null;
    }

    // ???????????????8???
    // ?????????????????????????????????????????????????????????????????????????????????
    public static boolean passwordValidation(String psw) {
        if (psw.length() <= 8)
            return false;
        boolean flag = true;
        Pattern pattern1 = Pattern.compile("\\p{Graph}");
        Matcher matcher = pattern1.matcher(psw);
        String afterReplace = matcher.replaceAll("");

        //???????????????????????????????????????????????????????????????????????????
        if (afterReplace.length() > 0)
            return false;

        matcher.reset();
        //???????????????????????????
        matcher.usePattern(Pattern.compile("\\p{Upper}"));
        flag &= matcher.find();
        //???????????????????????????
        matcher.usePattern(Pattern.compile("\\p{Lower}"));
        flag &= matcher.find();
        //?????????????????????
        matcher.usePattern(Pattern.compile("\\p{Digit}"));
        flag &= matcher.find();
        //???????????????????????????
        matcher.usePattern(Pattern.compile("\\p{Punct}"));
        flag &= matcher.find();

        return flag;
    }

    public static boolean usernameValidation(String username) {
        return username.length() >= 5 && username.length() <= 20;
    }

    //????????????username???moneyPaid???phoneNumber???email???Document
    public Document getDocument() {
        Document res = new Document();
        res.append("username", username).append("money_paid", moneyPaid);
        res.append("phone_number", phoneNumber).append("email", email);

        return res;
    }

    public static boolean phoneNumberValidation(String phoneNumber) {
        return phoneNumber.matches("[\\p{Digit}]{7,}");
    }

    public static boolean emailAddressValidation(String email) {
        String reg = "[A-Za-z0-9-._]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})";
        return email.matches(reg);
    }

    public boolean isLogined() {
        return logined;
    }

    public Cart getUserCart() {
        return userCart;
    }
}

package modellayer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.mongodb.client.model.Filters.eq;

public class Cart extends ArrayList<Good> {
    String userID;

    public Cart(List<Good> goodList, String userID) {
        this.addAll(goodList);
        this.userID = userID;
    }

    public boolean saveToDatabase() {
        boolean flag=true;

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");

        if (!User.userExisted(new ObjectId(userID)))
            return false;

        usersCollection.updateOne()
    }


}

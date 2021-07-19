import static com.mongodb.client.model.Filters.eq;

import com.mongodb.BasicDBList;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import modellayer.Cart;
import modellayer.GoodList;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");

        GoodList goodList = new GoodList();
        Cart cart = new Cart("60f454a73e8da52020c43aa2");

        cart.put(new ObjectId("60f534e5ef07553e065592fc"), 5L);
        System.out.println(cart.saveToDatabase());
    }
}

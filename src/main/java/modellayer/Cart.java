package modellayer;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public class Cart extends Hashtable<ObjectId, Long> {
    String userID;

    public Cart(String userID) {
        super();
        this.userID = userID;
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");
        Document userInfo;

        userInfo = usersCollection.find(eq("_id", new ObjectId(userID))).first();
        if (userInfo.get("cart") == null)
            return;

        for (Document doc : (ArrayList<Document>) userInfo.get("cart")) {
            for (Map.Entry<String, Object> i :doc.entrySet()) {
                this.put(new ObjectId(i.getKey()), (Long) i.getValue());
            }
        }
        //System.out.println(this);
    }

    public Cart() {
        super();
    }

    public boolean saveToDatabase() {
        long updateRes;
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> usersCollection = database.getCollection("Users");

        if (!User.userExisted(new ObjectId(userID)))
            return false;

        updateRes = usersCollection.updateOne(eq("_id", new ObjectId(userID)), eq("$set", new Document("cart", this.getGoodIDDBL()))).getMatchedCount();
        return updateRes == 1L;
    }

    public BasicDBList getGoodIDDBL() {
        BasicDBList res = new BasicDBList();

        for (Map.Entry<ObjectId, Long> i:this.entrySet()) {
            res.add(new BasicDBObject(i.getKey().toString(), i.getValue()));
        }

        return res;
    }

    public boolean addItem(Good good, long n) {
        ObjectId goodID = good.getId();
        Long value;

        if (this.containsKey(goodID)) {
            value = this.get(goodID);
            this.put(goodID, value+n);
        }
        else {
            this.put(goodID, n);
        }

        this.saveToDatabase();

        return true;
    }
}

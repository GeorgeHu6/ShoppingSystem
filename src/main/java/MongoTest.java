import static com.mongodb.client.model.Filters.eq;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoTest {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("ShoppingSystem");
        MongoCollection<Document> collection = mongoDatabase.getCollection("Goods");

//        FindIterable<Document> findIterable = collection.find();
//        MongoCursor<Document> mongoCursor = findIterable.iterator
//        while (mongoCursor.hasNext()) {
//            System.out.println(mongoCursor.next());
//        }
//        collection.insertOne(new Document()
//                .append("_id", new ObjectId())
//                .append("username", "GeorgeHu4"));


        Document result = collection.find(eq("quantity", 100)).first();
        System.out.println(result.toString());
        result = collection.find().filter(new Document("quantity", new Document("$gte", 100))).first();
        System.out.println(result.toString());

        mongoClient.close();
    }
}

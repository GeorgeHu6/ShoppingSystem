package modellayer;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoodList {
    FindIterable<Document> originFindResult;
    FindIterable<Document> curFindResult;
    Document curFilter;
    ArrayList<Good> curList;

    //默认构造函数获取全部商品
    public GoodList() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("ShoppingSystem");
        MongoCollection<Document> goodsCollection = database.getCollection("Goods");
        originFindResult = goodsCollection.find();

        curFilter = new Document();

        curList = new ArrayList<>();
        for (Document i : originFindResult) {
            curList.add(new Good(i));
        }
        client.close();
    }

    public GoodList filterByName(String goodName) {
        curFilter = curFilter.append("goodName", goodName);
        curFindResult = originFindResult.filter(curFilter);

        curList.clear();
        for (Document i: curFindResult) {
            curList.add(new Good(i));
        }
        return this;
    }

    public GoodList filterByProducer(String producer) {
        curFilter = curFilter.append("producer", producer);
        curFindResult = originFindResult.filter(curFilter);

        curList.clear();
        for (Document i: curFindResult) {
            curList.add(new Good(i));
        }
        return this;
    }

    public GoodList filterPurchasePriceMoreThan(double lowBound) {
        curFilter.append("purchase_price", new Document("$gte", lowBound));
        curFindResult = originFindResult.filter(curFilter);

        curList.clear();
        for (Document i: curFindResult) {
            curList.add(new Good(i));
        }
        return this;
    }


    public GoodList filterPurchasePriceEqualTo(double purchasePrice) {
        curFilter.append("purchase_price", purchasePrice);
        curFindResult = originFindResult.filter(curFilter);

        curList.clear();
        for (Document i: curFindResult) {
            curList.add(new Good(i));
        }
        return this;
    }

    public void clearFilter() {
        curFilter = new Document();
        curFindResult = originFindResult.filter(curFilter);

        curList.clear();
        for (Document i : originFindResult) {
            curList.add(new Good(i));
        }
    }

    public ArrayList<Good> getCurList() {
        return curList;
    }

    public void setCurFilter(Document curFilter) {
        this.curFilter = curFilter;
        curFindResult = originFindResult.filter(curFilter);

        for (Document i: curFindResult) {
            curList.add(new Good(i));
        }
    }
}

package modellayer;

import org.bson.Document;
import viewlayer.ViewToolClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Good {
    String id, name, producer;
    Long quantity;
    Date produceDate;
    String modeType;
    Double buyingPrice, purchasePrice;

    public Good(String name, String producer, long quantity, Date produceDate, String modeType, double buyingPrice, double purchasePrice) {
        this.name = name;
        this.producer = producer;
        this.quantity = quantity;
        this.produceDate = produceDate;
        this.modeType = modeType;
        this.buyingPrice = buyingPrice;
        this.purchasePrice = purchasePrice;
    }
    public Good() {
        this.name = "未上架商品";
    }

    public Good(Document doc) {
        this.name = doc.getString("name");
        this.quantity = doc.getLong("quantity");
        this.buyingPrice = doc.getDouble("buying_price");
        this.id = doc.getObjectId("_id").toString();
        this.producer = doc.getString("producer");
        this.purchasePrice = doc.getDouble("purchase_price");
        this.produceDate = doc.getDate("produce_date");
        this.modeType = doc.getString("model_type");
    }

    public static List<Good> getGoodsList() {
        return null;
    }

    public static String tableHeadOneForCustomer() {
        return ViewToolClass.getPage("goodsthForcust");
    }


    public String toCustomerString() {
        StringBuilder res = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        res.append(this.name).append("     ");
        res.append(this.modeType).append("     ");
        res.append(this.quantity).append("     ");
        res.append(this.purchasePrice).append("     ");
        res.append(this.producer).append("     ");
        res.append(format.format(this.produceDate));

        return res.toString();
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }
}

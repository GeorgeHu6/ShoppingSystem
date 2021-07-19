import modellayer.Good;
import modellayer.GoodList;
import modellayer.ModelToolClass;
import modellayer.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        Document document = new Document();
//        document = document.append("t", 1).append("t", 2);
//
//        System.out.println(document.toString());

        Hashtable<String, Long> hashtable = new Hashtable<>();
        hashtable.put("test1", 123L);
        System.out.println(hashtable.get(new String("test1")).toString());
        hashtable.put("test1", 234L);
        System.out.println(hashtable.get(new String("test1")).toString());

        /*
        Pattern pattern1 = Pattern.compile("\\p{Graph}");
        Matcher matcher = pattern1.matcher("a**-bc123*-\n");

        System.out.println(matcher.replaceAll("").length());

        matcher.usePattern(Pattern.compile("\\p{Alpha}"));
        matcher.reset();
        System.out.println(matcher.find());
         */

    }
}

package modellayer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelToolClass {

    //将originStr用SHA512加密，返回加密后的十六进制字符串
    public static String getSHA(String originStr) {
        StringBuffer result = new StringBuffer();
        MessageDigest message;
        byte[] out;
        try {
            message = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        message.update(originStr.getBytes(StandardCharsets.UTF_8));
        out = message.digest();

        for (byte b:out) {
            StringBuffer tmpHex = new StringBuffer();
            tmpHex.append(Integer.toHexString(b & 0xff));
            if (tmpHex.length() == 1)
                result.append("0");
            result.append(tmpHex);
        }

        return result.toString();
    }
}

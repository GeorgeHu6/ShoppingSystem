package controllayer;

public class ControlToolClass {
    //验证number的有效性：是否在[from, to]的范围内
    public static boolean intQualify (String number, int from, int to) {
        int max_length=0, tmp=0;
        int from_copy=from, to_copy=to;
        while (from_copy > 0) {
            tmp++;
            from_copy /= 10;
        }
        while (to_copy > 0) {
            max_length++;
            to_copy /= 10;
        }
        max_length = Math.max(tmp, max_length);

        if (number.length() == 0 || number.length() > max_length)
            return false;
        for (char i: number.toCharArray()) {
            if (!Character.isDigit(i))
                return false;
        }
        tmp = Integer.parseInt(number);

        return tmp >= from && tmp <= to;
    }
}

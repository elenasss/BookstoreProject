package bookstore.utils;

public class Helper {

    public static boolean isTextLengthValid(int count) {
        return count > 0;
    }

    public static boolean isPriceValid(double price) {
            return price > 0;
    }

    public static boolean isAvailabilityValid(int availability) {
            return availability > 0;
        }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}

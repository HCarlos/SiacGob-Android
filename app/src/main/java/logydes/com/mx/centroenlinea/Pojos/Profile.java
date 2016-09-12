package logydes.com.mx.centroenlinea.Pojos;

/**
 * Created by devch on 1/09/16.
 */
public class Profile {
    private static String FullName;
    private static String Domicilio;
    private static String NumCell;

    public Profile(String _fullName, String _Domicilio, String _NumCell) {
        FullName = _fullName;
        Domicilio = _Domicilio;
        NumCell = _NumCell;
    }

    public static String getFullName() {
        return FullName;
    }

    public static void setFullName(String _fullName) {
        FullName = _fullName;
    }

    public static String getDomicilio() {
        return Domicilio;
    }

    public static void setDomicilio(String domicilio) {
        Domicilio = domicilio;
    }

    public static String getNumCell() {
        return NumCell;
    }

    public static void setNumCell(String numCell) {
        NumCell = numCell;
    }
}

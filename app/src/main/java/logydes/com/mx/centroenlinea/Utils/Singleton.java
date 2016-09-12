package logydes.com.mx.centroenlinea.Utils;

import java.util.ArrayList;

import logydes.com.mx.centroenlinea.Pojos.Imagenes;

/**
 * Created by devch on 22/06/16.
 */

public class Singleton {
    private static Singleton ourInstance = new Singleton();
    public static Singleton getInstance() {
        return ourInstance;
    }

    private static int ID;
    private static int Modulo;
    private static int IdUser;
    private static int IdEmp;
    private static String Username;
    private static String Password;
    private static double Longitude;
    private static double Latitude;
    private static String Nombre;
    private static String Celular;
    private static String UUID;
    private static String Direccion;
    private static String FullName;
    private static String Domicilio;
    private static String NumCell;

    private static ArrayList<Imagenes> arrImagenes;

    private static boolean isCameraPresent;
    private static boolean isGPS;

    public Singleton() { }

    public Singleton(int _ID, int modulo, int idUser, String username, String _fullName, String _domicilio, String _numCell) {
        ID = _ID;
        Modulo = modulo;
        IdUser = idUser;
        Username = username;
        Latitude = 0.00;
        Longitude = 0.00;
        Nombre = "";
        Celular = "";
        isCameraPresent = false;
        isGPS = false;
        UUID = "";
        FullName = _fullName;
        Domicilio = _domicilio;
        NumCell = _numCell;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Singleton.ID = ID;
    }

    public static int getModulo() {
        return Modulo;
    }

    public static void setModulo(int modulo) {
        Modulo = modulo;
    }

    public static int getIdUser() {
        return IdUser;
    }

    public static void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public static int getIdEmp() {
        return IdEmp;
    }

    public static void setIdEmp(int idEmp) {
        IdEmp = idEmp;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static double getLongitude() {
        return Longitude;
    }

    public static void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public static double getLatitude() {
        return Latitude;
    }

    public static void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public static String getNombre() {
        return Nombre;
    }

    public static void setNombre(String nombre) {
        Nombre = nombre;
    }

    public static String getCelular() {
        return Celular;
    }

    public static void setCelular(String celular) {
        Celular = celular;
    }

    public static boolean isCameraPresent() {
        return isCameraPresent;
    }

    public static String getUUID() {
        return UUID;
    }

    public static String getDireccion() {
        return Direccion;
    }

    public static void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public static void setUUID(String UUID) {
        Singleton.UUID = UUID;
    }

    public static String getFullName() {
        return FullName;
    }

    public static void setFullName(String fullName) {
        FullName = fullName;
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

    public static void setIsCameraPresent(boolean isCameraPresent) {
        Singleton.isCameraPresent = isCameraPresent;
    }

    public static boolean isGPS() {
        return isGPS;
    }

    public static void setIsGPS(boolean isGPS) {
        Singleton.isGPS = isGPS;
    }

    public static ArrayList<Imagenes> getArrImagenes() {
        return arrImagenes;
    }

    public static void setArrImagenes(ArrayList<Imagenes> arrImagenes) {
        Singleton.arrImagenes = arrImagenes;
    }

    public static void reset() {
        setArrImagenes(null);
        ourInstance = new Singleton();
    }

}

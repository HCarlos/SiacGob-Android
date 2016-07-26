package logydes.com.mx.centroenlinea.Helper;

import android.util.Log;

import java.util.ArrayList;

import logydes.com.mx.centroenlinea.Pojos.Lista_Elementos;
import logydes.com.mx.centroenlinea.Pojos.Hijos;

/**
 * Created by devch on 22/06/16.
 */

public class Singleton {
    private static Singleton ourInstance = new Singleton();
    public static Singleton getInstance() {
        return ourInstance;
    }

    private static String pathPList;
    private static int Modulo;
    private static int IdUser;
    private static int IdEmp;
    private static int IdUserNivelAcceso;
    private static int Clave;
    private static int Param1;
    private static int RegistrosPorPagina;
    private static boolean IsDelete;
    private static String Empresa;
    private static String Username;
    private static String Password;
    private static String NombreCompletoUsuario;
    private static ArrayList<Hijos> rsHijos;
    private static int rsHijosSize;
    private static int IdAlu;
    private static ArrayList<Lista_Elementos> rsElementos;
    private static int rsElementosSize;

    public Singleton() { }

    public Singleton(int modulo, int idUser, String username) {
        Modulo = modulo;
        IdUser = idUser;
        Username = username;
    }

    public static String getPathPList() {
        return pathPList;
    }

    public static void setPathPList(String pathPList) {
        Singleton.pathPList = pathPList;
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

    public static int getIdUserNivelAcceso() {
        return IdUserNivelAcceso;
    }

    public static void setIdUserNivelAcceso(int idUserNivelAcceso) {
        IdUserNivelAcceso = idUserNivelAcceso;
    }

    public static int getClave() {
        return Clave;
    }

    public static void setClave(int clave) {
        Clave = clave;
    }

    public static int getParam1() {
        return Param1;
    }

    public static void setParam1(int param1) {
        Param1 = param1;
    }

    public static int getRegistrosPorPagina() {
        return RegistrosPorPagina;
    }

    public static void setRegistrosPorPagina(int registrosPorPagina) {
        RegistrosPorPagina = registrosPorPagina;
    }

    public static boolean isDelete() {
        return IsDelete;
    }

    public static void setIsDelete(boolean isDelete) {
        IsDelete = isDelete;
    }

    public static String getEmpresa() {
        return Empresa;
    }

    public static void setEmpresa(String empresa) {
        Empresa = empresa;
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

    public static String getNombreCompletoUsuario() {
        return NombreCompletoUsuario;
    }

    public static void setNombreCompletoUsuario(String nombreCompletoUsuario) {
        NombreCompletoUsuario = nombreCompletoUsuario;
    }

    public static ArrayList<Hijos> getRsHijos() {
        return rsHijos;
    }

    public static void setRsHijos(ArrayList<Hijos> rsHijos) {
        Singleton.rsHijos = rsHijos;
        Singleton.setRsHijosSize();
    }

    public static int getRsHijosSize() {
        return rsHijosSize;
    }

    private static void setRsHijosSize() {
        Singleton.rsHijosSize = rsHijos.size();
        Log.e("rsHijosSize", String.valueOf(rsHijosSize)  );
    }

    public static int getIdAlu() {
        return IdAlu;
    }

    public static void setIdAlu(int idAlu) {
        IdAlu = idAlu;
    }


    public static ArrayList<Lista_Elementos> getRsElementos() {
        return rsElementos;
    }

    public static void setRsElementos(ArrayList<Lista_Elementos> rsElementos) {
        Singleton.rsElementos = rsElementos;
        Singleton.setRsElementosSize();
    }

    public static int getRsElementosSize() {
        return rsElementosSize;
    }

    private static void setRsElementosSize() {
        Singleton.rsElementosSize = rsElementos.size();
        Log.e("rsElementosSize", String.valueOf(rsElementosSize)  );
    }



}

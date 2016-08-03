package logydes.com.mx.centroenlinea.Pojos;

/**
 * Created by devch on 3/08/16.
 */
public class Imagenes {
    
    private  int idmdenuncia;
    private  String denuncia;
    private  String idF;
    private  String imagen;
    private  String imagen_s;
    private  String imagen_mini;
    private  String nombre;
    private  String celular;
    private  int so_mobile;
    private  double latitud;
    private  double longitud;
    private  int modulo;
    private  String cmodulo;
    private  int megusta;
    private  int status_reparacion;
    private  String domicilio;
    private  String creado_el;
    private  String cfecha;

    public Imagenes(
                     int _idmdenuncia,
                     String _denuncia,
                     String _idF,
                     String _imagen,
                     String _nombre,
                     String _celular,
                     int _so_mobile,
                     double _latitud,
                     double _longitud,
                     int _modulo,
                     String _cmodulo,
                     int _megusta,
                     int _status_reparacion,
                     String _domicilio,
                     String _creado_el,
                     String _cfecha
            
    ) {
        idmdenuncia = _idmdenuncia;
        denuncia = _denuncia;
        idF = _idF;
        imagen = _imagen;
        nombre = _nombre;
        celular = _celular;
        so_mobile = _so_mobile;
        latitud = _latitud;
        longitud = _longitud;
        modulo = _modulo;
        cmodulo = _cmodulo;
        megusta = _megusta;
        status_reparacion = _status_reparacion;
        domicilio = _domicilio;
        creado_el = _creado_el;
        cfecha = _cfecha;

        String[] img = _imagen.split("\\.");

        imagen_s = img[0]+"-s."+img[1];
        imagen_mini = img[0]+"-mini."+img[1];

    }

    public  int getIdmdenuncia() {
        return idmdenuncia;
    }

    public  void setIdmdenuncia(int _idmdenuncia) {
        idmdenuncia = _idmdenuncia;
    }

    public  String getDenuncia() {
        return denuncia;
    }

    public  void setDenuncia(String _denuncia) {
        denuncia = _denuncia;
    }

    public  String getIdF() {
        return idF;
    }

    public  void setIdF(String _idF) {
        idF = _idF;
    }

    public  String getImagen() {
        return imagen;
    }

    public  void setImagen(String _imagen) {
        imagen = _imagen;

        String[] img = _imagen.split("\\.");

        imagen_s = img[0]+"-s."+img[1];
        imagen_mini = img[0]+"-mini."+img[1];

    }

    public  String getImagen_s() {
        return imagen_s;
    }

    public  String getImagen_mini() {
        return imagen_mini;
    }

    public  String getNombre() {
        return nombre;
    }

    public  void setNombre(String _nombre) {
        nombre = _nombre;
    }

    public  String getCelular() {
        return celular;
    }

    public  void setCelular(String _celular) {
        celular = _celular;
    }

    public  int getSo_mobile() {
        return so_mobile;
    }

    public  void setSo_mobile(int _so_mobile) {
        so_mobile = _so_mobile;
    }

    public  double getLatitud() {
        return latitud;
    }

    public  void setLatitud(double _latitud) {
        latitud = _latitud;
    }

    public  double getLongitud() {
        return longitud;
    }

    public  void setLongitud(double _longitud) {
        longitud = _longitud;
    }

    public  int getModulo() {
        return modulo;
    }

    public  void setModulo(int _modulo) {
        modulo = _modulo;
    }

    public  String getCmodulo() {
        return cmodulo;
    }

    public  void setCmodulo(String _cmodulo) {
        cmodulo = _cmodulo;
    }

    public  int getStatus_reparacion() {
        return status_reparacion;
    }

    public  void setStatus_reparacion(int _status_reparacion) {
        status_reparacion = _status_reparacion;
    }

    public  int getMegusta() {
        return megusta;
    }

    public  void setMegusta(int _megusta) {
        megusta = _megusta;
    }

    public  String getDomicilio() {
        return domicilio;
    }

    public  void setDomicilio(String _domicilio) {
        domicilio = _domicilio;
    }

    public  String getCreado_el() {
        return creado_el;
    }

    public  void setCreado_el(String _creado_el) {
        creado_el = _creado_el;
    }

    public  String getCfecha() {
        return cfecha;
    }

    public  void setCfecha(String _cfecha) {

        cfecha = _cfecha;
    }
}

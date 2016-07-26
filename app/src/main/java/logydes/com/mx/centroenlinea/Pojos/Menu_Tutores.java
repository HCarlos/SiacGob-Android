package logydes.com.mx.centroenlinea.Pojos;

/**
 * Created by devch on 24/06/16.
 */
public class Menu_Tutores {
    private int idmenu;
    private String menu;

    public Menu_Tutores(int _idmenu, String _menu) {
        idmenu = _idmenu;
        menu = _menu;
    }

    public int getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(int idmenu) {
        this.idmenu = idmenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}

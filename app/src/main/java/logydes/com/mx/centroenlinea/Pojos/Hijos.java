package logydes.com.mx.centroenlinea.Pojos;

/**
 * Created by devch on 23/06/16.
 */
public class Hijos {
    private int data;
    private String label;
    private String grupo;
    private String msg;

    public Hijos(int data, String label, String grupo, String msg) {
        this.data = data;
        this.label = label;
        this.grupo = grupo;
        this.msg = msg;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

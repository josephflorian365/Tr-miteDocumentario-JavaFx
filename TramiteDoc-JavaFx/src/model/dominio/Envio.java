
package model.dominio;


public class Envio {
private int id;
private int idDoc;
private String fecEnv;
private String idemplRec;
private String idAreaR;
private String estadoEnv;
private Documento documento;

    public Documento getDocumento() {
        return documento;
    }


    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String getIdAreaR() {
        return idAreaR;
    }

    public void setIdAreaR(String idAreaR) {
        this.idAreaR = idAreaR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }

    public String getFecEnv() {
        return fecEnv;
    }

    public void setFecEnv(String fecEnv) {
        this.fecEnv = fecEnv;
    }



    public String getEstadoEnv() {
        return estadoEnv;
    }

    public void setEstadoEnv(String estadoEnv) {
        this.estadoEnv = estadoEnv;
    }


    public String getIdemplRec() {
        return idemplRec;
    }

    public void setIdemplRec(String idemplRec) {
        this.idemplRec = idemplRec;
    }

}

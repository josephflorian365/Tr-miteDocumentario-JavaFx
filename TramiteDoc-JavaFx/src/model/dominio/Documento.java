

package model.dominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.database.DatabaseMySQL;


public class Documento {
    
    private int id;
    private String asunto;
    private String idTipoD;
    private int idExp;
    private String idEmpl;
    private String estado;


    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getIdTipoD() {
        return idTipoD;
    }

    public void setIdTipoD(String idTipoD) {
        this.idTipoD = idTipoD;
    }

    public int getIdExp() {
        return idExp;
    }

    public void setIdExp(int idExp) {
        this.idExp = idExp;
    }

    public String getIdEmpl() {
        return idEmpl;
    }

    public void setIdEmpl(String idEmpl) {
        this.idEmpl = idEmpl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "DOC-"+ id;
    }

    
    public static void cbDoc(ObservableList<Documento> lista) {
    Connection con;
    DatabaseMySQL cn = new DatabaseMySQL();
    PreparedStatement ps;
    ResultSet rs;


        String sql = "Select iddoc from documento";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Documento a = new Documento();
                a.setId(rs.getInt(1));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error en AreaDAO - cbDoc: " + e.getMessage());
        }
    
    }
    
}

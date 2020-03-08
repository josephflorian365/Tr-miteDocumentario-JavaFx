package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.database.DatabaseMySQL;
import model.dominio.Documento;

public class DocumentoDAO implements CRUD<Object> {

    Connection con;
    DatabaseMySQL cn = new DatabaseMySQL();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List Listar() {
        ObservableList lista = FXCollections.observableArrayList();
        String sql = "SELECT IDDOC, ASUDOC, tipo_documento.nomtipdoc, expediente.numexp, CONCAT(empleado.nomemp,\" \",empleado.apepatemp,\" \",empleado.apepatemp), ESTDOC \n"
                + "FROM documento\n"
                + "inner join tipo_documento on documento.idtipdoc = \n"
                + "tipo_documento.idtipdoc \n"
                + "inner join expediente on documento.idexp = expediente.idexp \n"
                + "inner join empleado on documento.idempemi = empleado.idemp; ";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                Documento d = new Documento();
                d.setId(rs.getInt(1));
                d.setAsunto(rs.getString(2));
                d.setIdTipoD(rs.getString(3));
                d.setIdExp(rs.getInt(4));
                d.setIdEmpl(rs.getString(5));
                d.setEstado(rs.getString(6));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error en DocumentoDAO - listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public int add(Object[] o) {
        int r = 0;
        String sql = "insert into documento(ASUDOC, IDTIPDOC, IDEXP, IDEMPEMI, ESTDOC) values (?,?,?,?,?)";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            ps.setObject(5, o[4]);
            r = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en DocumentoDAO - add: " + e.getMessage());
        }
        return r;
    }

    @Override
    public int update(Object[] o) {
        int r = 0;
        String sql = "update documento set ASUDOC = ?, IDTIPDOC = ?, IDEXP = ?, IDEMPEMI = ? , ESTDOC = ? where IDDOC = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            ps.setObject(5, o[4]);
             ps.setObject(6, o[5]);

            r = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en DocumentoDAO - update: " + e.getMessage());
        }
        return r;
    }

    @Override
    public void eliminar(int id) {
        String sql = "delete from documento where IDDOC = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en DocumentoDAO - eliminar: " + e.getMessage());
        }
    }
    
    
    public List cbDoc() {

        ObservableList lista = FXCollections.observableArrayList();

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
        return lista;
    }
    
    
}

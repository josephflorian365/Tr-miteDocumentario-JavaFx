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
import model.dominio.TipoDeDocumento;

public class TipoDocumentoDAO implements CRUD<Object>{

    Connection con;
    DatabaseMySQL cn = new DatabaseMySQL();
    PreparedStatement ps;
    ResultSet rs;
  @Override
    public List Listar() {
    ObservableList lista = FXCollections.observableArrayList();
    String sql = "Select * from tipo_documento";
        try {
            con =cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoDeDocumento td = new TipoDeDocumento();
                td.setId(rs.getInt(1));
                td.setNombre(rs.getString(2));
                td.setEstado(rs.getString(3));
                lista.add(td);
            }
            
        } catch (SQLException e) {
            System.out.println("Error en TipoDocumentoDAO - listar: " + e.getMessage());
        }
    return lista;
    }

    @Override
    public int add(Object[] o) {
    int r = 0;
        String sql = "insert into tipo_documento(NOMTIPDOC, ESTTIPDOC) values (?,?)";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            r = ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error en TipoDocumentoDAO - add: " + e.getMessage());
        }
        return r;
    }

    @Override
    public int update(Object[] o) {
     int r = 0;
     String sql = "update tipo_documento set NOMTIPDOC = ?, ESTTIPDOC = ? where IDTIPDOC = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en TipoDocumentoDAO - update: " + e.getMessage());
        }
        return r;
    
    
    
    
    
    }

    @Override
    public void eliminar(int id) {
    String sql = "delete from tipo_documento where IDTIPDOC = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
             System.out.println("Error en AreaDAO - eliminar: " + e.getMessage());
        }
    }
    
    
    
        public List cmbtipdoc() {
        ObservableList lista = FXCollections.observableArrayList();

        String sql = "Select idtipdoc, nomtipdoc from tipo_documento";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoDeDocumento e = new  TipoDeDocumento();
                e.setId(rs.getInt(1));
                e.setNombre(rs.getString(2));

                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error en tipodocDAO - cmbTipDoc: " + e.getMessage());
        }
        return lista;

    }
}

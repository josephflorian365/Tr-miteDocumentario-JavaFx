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
import model.dominio.Area;

public class AreaDAO implements CRUD<Object> {
    public String valor = "NINGUNO";
    Connection con;
    DatabaseMySQL cn = new DatabaseMySQL();

   
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List Listar() {
        ObservableList lista = FXCollections.observableArrayList();

        String sql = "Select idare, nomare, estare from area";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Area a = new Area();
                a.setId(rs.getInt(1));
                a.setNombre(rs.getString(2));
                a.setEstado(rs.getString(3));
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error en AreaDAO - listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public int add(Object[] o) {
        int r = 0;
        String sql = "insert into area(NOMARE,ESTARE) values (?,?)";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            r = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en AreaDAO - add: " + e.getMessage());
        }
        return r;
    }

    @Override
    public int update(Object[] o) {
        int r = 0;
        String sql = "update area set NOMARE = ?, ESTARE = ? where IDARE = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en AreaDAO - update: " + e.getMessage());
        }
        return r;

    }

    @Override
    public void eliminar(int id) {
        String sql = "delete from Area where IDARE = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en AreaDAO - eliminar: " + e.getMessage());
        }
    }

  public List cbArea() {

        ObservableList lista = FXCollections.observableArrayList();

        String sql = "Select idare, nomare from area";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Area a = new Area();
                a.setId(rs.getInt(1));
                a.setNombre(rs.getString(2));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error en AreaDAO - cbArea: " + e.getMessage());
        }
        return lista;
    }

  

    }


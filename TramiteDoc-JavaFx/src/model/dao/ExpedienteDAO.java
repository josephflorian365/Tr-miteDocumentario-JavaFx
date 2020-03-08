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
import model.dominio.Empleado;
import model.dominio.Expediente;

public class ExpedienteDAO implements CRUD<Object> {

    Connection con;
    DatabaseMySQL cn = new DatabaseMySQL();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List Listar() {
        ObservableList lista = FXCollections.observableArrayList();
        String sql = "select * from expediente";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Expediente e = new Expediente();
                e.setId(rs.getInt(1));
                e.setNumero(rs.getInt(2));
                e.setFechaI(rs.getString(3));
                e.setAsunto(rs.getString(4));
                e.setEstado(rs.getString(5));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error en ExpedienteDAO - listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public int add(Object[] o) {
        int r = 0;
        String sql = "insert into expediente(NUMEXP, FECEXP, ASUEXP, ESTEXP) values (?,?,?,?)";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            r = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error en ExpedienteDAO - add: " + e.getMessage());
        }
        return r;
    }

    @Override
    public int update(Object[] o) {
        int r = 0;
        String sql = "update expediente set NUMEXP = ?, FECEXP = ?, ASUEXP = ?, ESTEXP = ? where IDEXP = ?";
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
            System.out.println("Error en ExpedienteDAO - update: " + e.getMessage());
        }
        return r;
    }

    @Override
    public void eliminar(int id) {
        String sql = "delete from expediente where IDEXP = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en AreaDAO - eliminar: " + e.getMessage());
        }
    }

    public List cmbExpediente() {
        ObservableList lista = FXCollections.observableArrayList();

        String sql = "Select idexp, asuexp from expediente";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Expediente e = new Expediente();
                e.setId(rs.getInt(1));
                e.setAsunto(rs.getString(2));

                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error en ExpedienteDAO - cmbExpediente: " + e.getMessage());
        }
        return lista;

    }
}

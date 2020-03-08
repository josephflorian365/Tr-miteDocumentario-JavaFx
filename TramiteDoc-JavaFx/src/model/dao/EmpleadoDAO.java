/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import model.dominio.Empleado;

/**
 *
 * @author Julio
 */
public class EmpleadoDAO implements CRUD<Object> {

    Connection con;
    DatabaseMySQL cn = new DatabaseMySQL();
    PreparedStatement ps;
    ResultSet rs;

    public Empleado validarEmpleado(String user, String pass) {

        Empleado emp = new Empleado();
        String sql = "select USEREMP, PASSEMP from empleado where USEREMP = ? AND PASSEMP = ? AND idare in (1,2)";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                emp.setUsuario(rs.getString(1));
                emp.setPassword(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("Error en Validar ADMIN: " + e.getMessage());
        }

        return emp;
    }
    
    
    
     public Empleado validarUsuario(String user, String pass) {

        Empleado emp = new Empleado();
        String sql = "select USEREMP, PASSEMP from empleado where USEREMP = ? AND PASSEMP = ? not idare in (1,2";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                emp.setUsuario(rs.getString(1));
                emp.setPassword(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("Error en Validar Usuario: " + e.getMessage());
        }

        return emp;
    }

    @Override
    public List Listar() {
        ObservableList lista = FXCollections.observableArrayList();

        String sql = "Select idemp, nomemp,apepatemp,apematemp, `POSEMP`, `DNIEMP`, `NOMARE`, `ESTEMP`, `SEXEMP` FROM `empleado` \n"
                + " inner join area on empleado.idare = area.idare";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt(1));
                e.setNombre(rs.getString(2));
                e.setApellidoP(rs.getString(3));
                e.setApellidoM(rs.getString(4));
                e.setPosicion(rs.getString(5));
                e.setDni(rs.getString(6));
                e.setIdArea(rs.getString(7));
                e.setEstado(rs.getString(8));
                e.setSexo(rs.getString(9));
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error en EmpleadoDAO - listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public int add(Object[] o) {
        int r = 0;
        String sql = "insert into empleado(NOMEMP,APEPATEMP,APEMATEMP, POSEMP, DNIEMP, IDARE,SEXEMP, ESTEMP) values (?,?,?,?,?,?,?,?)";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            ps.setObject(5, o[4]);
            ps.setObject(6, o[5]);
            ps.setObject(7, o[6]);
            ps.setObject(8, o[7]);
            r = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en EmpleadoDAO - add: " + e.getMessage());
        }
        return r;
    }

    @Override
    public int update(Object[] o) {
        int r = 0;
        String sql = "update empleado set NOMEMP = ?, APEPATEMP = ?, APEMATEMP = ?, POSEMP = ?, DNIEMP = ?, IDARE = ?,SEXEMP = ?, ESTEMP = ? where IDEMP = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            ps.setObject(5, o[4]);
            ps.setObject(6, o[5]);
            ps.setObject(7, o[6]);
            ps.setObject(8, o[7]);
            ps.setObject(9, o[8]);
            r = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en EmpleadoDAO - update: " + e.getMessage());
        }
        return r;

    }

    @Override
    public void eliminar(int id) {
        String sql = "delete from empleado where IDEMP = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en EmpleadoDAO - eliminar: " + e.getMessage());
        }

    }

    public List cmbEmpleado() {
        ObservableList lista = FXCollections.observableArrayList();

        String sql = "Select idemp, concat(nomemp," + "\" \"" + ",apepatemp," + "\" \"" + ",apematemp)" + " from empleado";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt(1));
                e.setNombre(rs.getString(2));

                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error en EmpleadoDAO - cmbEmpleado: " + e.getMessage());
        }
        return lista;

    }
    
    
    public List cbArea() {

        ObservableList lista = FXCollections.observableArrayList();

        String sql = "Select  nomare from area";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Area a = new Area();
         
                a.setNombre(rs.getString(2));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error en AreaDAO - cbArea: " + e.getMessage());
        }
        return lista;
    }

}

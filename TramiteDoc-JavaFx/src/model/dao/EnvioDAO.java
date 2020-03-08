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
import model.dominio.Documento;
import model.dominio.Envio;

/**
 *
 * @author Julio
 */
public class EnvioDAO implements CRUD<Object> {

    Connection con;
    DatabaseMySQL cn = new DatabaseMySQL();
    PreparedStatement ps;
    ResultSet rs;
//Prueba

    
    
    
    
    
    @Override
    public List Listar() {
        ObservableList lista = FXCollections.observableArrayList();
        String sql = "SELECT IDENV, IDDOC, FECENV,"
                + "concat(nomemp,"+"\" \""+",apepatemp,"+"\" \""+",apematemp),"
                + " AREA.NOMARE, ESTENV FROM envios\n"
                + "INNER JOIN EMPLEADO ON ENVIOS.IDEMPREC = EMPLEADO.IDEMP\n"
                + "INNER JOIN AREA ON ENVIOS.IDAREREC = AREA.IDARE";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Envio en = new Envio();
         
                en.setId(rs.getInt(1));
                en.setIdDoc(rs.getInt(2));
                en.setFecEnv(rs.getString(3));
                en.setIdemplRec(rs.getString(4));
                en.setIdAreaR(rs.getString(5));
                en.setEstadoEnv(rs.getString(6));

                lista.add(en);
            }

        } catch (SQLException e) {
            System.out.println("Error en EnvioDAO - listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public int add(Object[] o) {
        int r = 0;
        String sql = "insert into envios(IDDOC,FECENV,IDEMPREC, IDAREREC, ESTENV) values (?,?,?,?,?)";
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
            System.out.println("Error en EnviosDAO - add: " + e.getMessage());
        }
        return r;
    }

    @Override
    public int update(Object[] o) {
        int r = 0;
        String sql = "update envios set IDDOC = ?,FECENV = ?,IDEMPREC = ?, IDAREREC = ?, ESTENV = ? where IDENV = ?";
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
            System.out.println("Error en EnvioDAO - update: " + e.getMessage());
        }
        return r;
    }

    @Override
    public void eliminar(int id) {
        String sql = "delete from envios where IDENV = ?";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en EnvioDAO - eliminar: " + e.getMessage());
        }
    }

}

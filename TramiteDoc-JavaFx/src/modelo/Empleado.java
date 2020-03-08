/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import model.database.Conexion;

public class Empleado {
  Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    private IntegerProperty codigoEmpleado;
    private StringProperty nombre;
    private StringProperty apellidoP;
    private StringProperty apellidoM;
    private StringProperty genero;
    private StringProperty posicion;
    private StringProperty dni;
    private StringProperty estado;

    private StringProperty user;
    private StringProperty pass;
    private Area area;
private Conexion conexion;
    public Empleado(Integer codigoEmpleado, String nombre, String apellidoP, String apellidoM) {
        this.codigoEmpleado = new SimpleIntegerProperty(codigoEmpleado);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidoP = new SimpleStringProperty(apellidoP);
        this.apellidoM = new SimpleStringProperty(apellidoM);
    }

    public Empleado(String user, String pass) {

        this.user = new SimpleStringProperty(user);;
        this.pass = new SimpleStringProperty(pass);
    }

    public Empleado(Integer codigoEmpleado, String nombre, String apellidoP, String apellidoM, String genero, String posicion, String dni, String estado, String user, String pass, Area area) {
        this.codigoEmpleado = new SimpleIntegerProperty(codigoEmpleado);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidoP = new SimpleStringProperty(apellidoP);
        this.apellidoM = new SimpleStringProperty(apellidoM);
        this.genero = new SimpleStringProperty(genero);
        this.posicion = new SimpleStringProperty(posicion);
        this.dni = new SimpleStringProperty(dni);
        this.estado = new SimpleStringProperty(estado);;
        this.user = new SimpleStringProperty(user);;
        this.pass = new SimpleStringProperty(pass);
        this.area = area;
    }

    public Integer getCodigoEmpleado() {
        return codigoEmpleado.get();
    }

    public void setCodigoEmpleado(Integer codigoEmpleado) {
        this.codigoEmpleado = new SimpleIntegerProperty(codigoEmpleado);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getApellidoP() {
        return apellidoP.get();
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = new SimpleStringProperty(apellidoP);
    }

    public String getApellidoM() {
        return apellidoM.get();
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = new SimpleStringProperty(apellidoM);
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero = new SimpleStringProperty(genero);
    }

    public String getDni() {
        return dni.get();
    }

    public void setDni(String dni) {
        this.dni = new SimpleStringProperty(dni);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado = new SimpleStringProperty(estado);
    }

    public String getUser() {
        return user.get();
    }

    public void setUser(String user) {
        this.user = new SimpleStringProperty(user);
    }

    public String getPass() {
        return pass.get();
    }

    public void setPass(String pass) {
        this.pass = new SimpleStringProperty(pass);
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getPosicion() {
        return posicion.get();
    }

    public void setPosicion(String posicion) {
        this.posicion = new SimpleStringProperty(posicion);
    }

//    //Property
//    
//    private IntegerProperty codigoEmpleado;
//    private StringProperty nombre;
//    private StringProperty apellidoP;
//    private StringProperty apellidoM;
//    private StringProperty genero;
//    private StringProperty posicion;
//    private StringProperty dni;
//    private StringProperty estado;
//
//    private StringProperty user;
//    private StringProperty pass;
//    private Area area;
    public IntegerProperty codigoEmpleadoProperty() {
        return codigoEmpleado;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty apellidoPProperty() {
        return apellidoP;
    }

    public StringProperty apellidoMProperty() {
        return apellidoM;
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public StringProperty posicionProperty() {
        return posicion;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public StringProperty dniProperty() {
        return dni;
    }

    public StringProperty userProperty() {
        return user;
    }

    public StringProperty passProperty() {
        return pass;
    }

    public int guardarRegistro(Connection connection) {
        try {
            //Evitar inyeccion SQL.
//            nomemp,apepatemp,apematemp, posemp, dniemp,area.idare,area.nomare,area.estare, `ESTEMP`, `SEXEMP`, useremp, passemp
//            "insert into empleado(NOMEMP,APEPATEMP,APEMATEMP, POSEMP, DNIEMP, SEXEMP, ESTEMP, USEREMP,PASSEMP, IDARE)"       + " values (?,?,?,?,?,?,?,?,?,?)"
            PreparedStatement instruccion
                    = connection.prepareStatement("INSERT INTO `empleado`("
                            + " `NOMEMP`, "
                            + "`APEPATEMP`, "
                            + "`APEMATEMP`, "
                            + "`POSEMP`, "
                            + "`DNIEMP`, "
                            + "`IDARE`, "
                            + "`USEREMP`, "
                            + "`PASSEMP`, "
                            + "`ESTEMP`, "
                            + "`SEXEMP`)  values (?,?,?,?,?,?,?,?,?,?)");
            instruccion.setString(1, nombre.get());
            instruccion.setString(2, apellidoP.get());
            instruccion.setString(3, apellidoM.get());
            instruccion.setString(4, posicion.get());
            instruccion.setString(5, dni.get());

            instruccion.setInt(6, area.getCodigoArea());
            instruccion.setString(7, user.get());
            instruccion.setString(8, pass.get());
            instruccion.setString(9, estado.get());
            instruccion.setString(10, genero.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int actualizarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion
                    = connection.prepareStatement(
                            "update empleado set NOMEMP = ?, APEPATEMP = ?, APEMATEMP = ?,"
                            + " POSEMP = ?, DNIEMP = ?, IDARE = ?,USEREMP = ?,"
                            + " PASSEMP = ?, ESTEMP =?, SEXEMP=? where IDEMP = ?"
                    );
            instruccion.setString(1, nombre.get());
            instruccion.setString(2, apellidoP.get());
            instruccion.setString(3, apellidoM.get());
            instruccion.setString(4, posicion.get());
            instruccion.setString(5, dni.get());

            instruccion.setInt(6, area.getCodigoArea());
            instruccion.setString(7, user.get());
            instruccion.setString(8, pass.get());
            instruccion.setString(9, estado.get());
            instruccion.setString(10, genero.get());
            instruccion.setInt(11, codigoEmpleado.get());
            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "delete from empleado where IDEMP = ?"
            );
            instruccion.setInt(1, codigoEmpleado.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void llenarInformacion(Connection connection,
            ObservableList<Empleado> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "Select idemp, nomemp,apepatemp,apematemp, posemp, dniemp,area.idare,area.nomare,area.estare,estemp, sexemp, useremp, passemp FROM `empleado` \n"
                    + " inner join area on empleado.idare = area.idare"
            );
            while (resultado.next()) {
                lista.add(
                        new Empleado(
                                resultado.getInt("idemp"),
                                resultado.getString("nomemp"),
                                resultado.getString("apepatemp"),
                                resultado.getString("apematemp"),
                                resultado.getString("sexemp"),
                                resultado.getString("posemp"),
                                resultado.getString("dniemp"),
                                resultado.getString("estemp"),
                                resultado.getString("useremp"),
                                resultado.getString("passemp"),
                                new Area(
                                        resultado.getInt("idare"),
                                        resultado.getString("nomare"),
                                        resultado.getString("estare")
                                )
                        )
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return codigoEmpleado.get() + ". " + nombre.get() + " " + apellidoP.get() + " " + apellidoM.get();
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Julio
 */
public class Envio {

    private IntegerProperty codigoEnvio;
    private Date fechaIngreso;
    private Documento iddocumento;
    private Area idarea;
    private Empleado idempleado;
    private StringProperty estado;

    public Envio(Integer codigoEnvio, Date fechaIngreso, String estado, Documento iddocumento, Area idarea, Empleado idempleado) {
        this.codigoEnvio = new SimpleIntegerProperty(codigoEnvio);
        this.fechaIngreso = fechaIngreso;
        this.estado = new SimpleStringProperty(estado);
        this.iddocumento = iddocumento;
        this.idarea = idarea;
        this.idempleado = idempleado;

    }

    public IntegerProperty getCodigoEnvioProperty() {
        return codigoEnvio;
    }

    public Documento getIddocumentoProperty() {
        return iddocumento;
    }

    public Area getIdareaProperty() {
        return idarea;
    }

    public Empleado getIdempleadoProperty() {
        return idempleado;
    }

    public StringProperty getEstadoProperty() {
        return estado;
    }

// getter y setter sin property
    public Integer getCodigoEnvio() {
        return codigoEnvio.get();
    }

    public void setCodigoEnvio(Integer codigoEnvio) {
        this.codigoEnvio = new SimpleIntegerProperty(codigoEnvio);
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Documento getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(Documento iddocumento) {
        this.iddocumento = iddocumento;
    }

    public Area getIdarea() {
        return idarea;
    }

    public void setIdarea(Area idarea) {
        this.idarea = idarea;
    }

    public Empleado getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(Empleado idempleado) {
        this.idempleado = idempleado;
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado = new SimpleStringProperty(estado);
    }

    public static void llenarInformacion(Connection connection,
         
            ObservableList<Envio> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT IDENV, FECENV,ESTENV,documento.iddoc, area.idare, area.nomare, empleado.idemp,empleado.nomemp,empleado.apepatemp,empleado.apematemp FROM envios \n" +
"                    INNER JOIN EMPLEADO ON ENVIOS.IDEMPREC = EMPLEADO.IDEMP\n" +
"                    \n" +
"                    INNER JOIN DOCUMENTO ON ENVIOS.IDDOC = DOCUMENTO.IDDOC\n" +
"                    INNER JOIN AREA ON ENVIOS.IDAREREC = AREA.IDARE"
            );
            while (resultado.next()) {
                lista.add(
                        new Envio(
                                resultado.getInt("IDENV"),
                                resultado.getDate("FECENV"),
                                resultado.getString("ESTENV"),
                                new Documento(
                                        resultado.getInt("iddoc")
                                ),
                                new Area(
                                        resultado.getInt("idare"),
                                        resultado.getString("nomare")
                                ),
                                new Empleado(
                                        resultado.getInt("idemp"),
                                        resultado.getString("nomemp"),
                                        resultado.getString("apepatemp"),
                                        resultado.getString("apematemp")
                                )
                        //                                resultado.getInt("idtipdoc"),
                        //                                resultado.getInt("idexp"),
                        //                                resultado.getInt("idempemi"),

                        )
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public int eliminarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "delete from envios where IDENV = ?"
            );
            instruccion.setInt(1, codigoEnvio.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int guardarRegistro(Connection connection) {
        try {
            //Evitar inyeccion SQL.
//            nomemp,apepatemp,apematemp, posemp, dniemp,area.idare,area.nomare,area.estare, `ESTEMP`, `SEXEMP`, useremp, passemp
//            "INSERT INTO `documento`(`ASUDOC`, `IDTIPDOC`, `IDEXP`, `IDEMPEMI`, `ESTDOC`) VALUES (?,?,?,?,?)
            PreparedStatement instruccion
                    = connection.prepareStatement("INSERT INTO `envios`( `FECENV`,"
                            + "`ESTENV`,"
                            + "`IDDOC`,"
                            + "`IDAREREC`,"
                            + "`IDEMPREC` ) "
                            + "VALUES (?,?,?,?,?)");
            instruccion.setDate(1, fechaIngreso);
            instruccion.setString(2, estado.get());
            instruccion.setInt(3, iddocumento.getCodigoDocumento());
            instruccion.setInt(4, idarea.getCodigoArea());
            instruccion.setInt(5, idempleado.getCodigoEmpleado());

            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int actualizarRegistro(Connection connection) {
        //UPDATE `documento` SET `ASUDOC`=?,`IDTIPDOC`=?,
        // `IDEXP`=?,`IDEMPEMI`=?,
        //`ESTDOC`=[value-6]
        // WHERE `IDDOC`=?,

        try {
            PreparedStatement instruccion
                    = connection.prepareStatement(
                            "UPDATE `envios` "
                                    + "SET `FECENV`=?,\n"
                            + "`ESTENV`=?,\n"
                            + "`IDDOC`=?,\n"
                            + "`IDAREREC`=?,\n"
                            + "`IDEMPREC`=?\n"
                            + "WHERE `IDENV`=?"
                    );
            instruccion.setDate(1, fechaIngreso);
            instruccion.setString(2, estado.get());
            instruccion.setInt(3, iddocumento.getCodigoDocumento());
            instruccion.setInt(4, idarea.getCodigoArea());
            instruccion.setInt(5, idempleado.getCodigoEmpleado());
              instruccion.setInt(6, codigoEnvio.get());

            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return 0;
        }

    }
}

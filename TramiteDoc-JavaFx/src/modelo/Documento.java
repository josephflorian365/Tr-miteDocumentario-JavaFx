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

/**
 *
 * @author Julio
 */
public class Documento {

    private IntegerProperty codigoDocumento;
    private StringProperty asunto;
    private TipoDoc tipodocumento;
    private Expediente idexpediente;
    private Empleado idempleado;
    private StringProperty estado;
    private byte[] pdf;

    public Documento(Integer codigoDocumento, String asunto, String estado, TipoDoc tipodocumento, Expediente idexpediente, Empleado idempleado) {
        this.codigoDocumento = new SimpleIntegerProperty(codigoDocumento);
        this.asunto = new SimpleStringProperty(asunto);
                this.estado = new SimpleStringProperty(estado);
        this.tipodocumento = tipodocumento;
        this.idexpediente = idexpediente;
        this.idempleado = idempleado;

    }
        public Documento(Integer codigoDocumento, String asunto, String estado, TipoDoc tipodocumento, Expediente idexpediente, Empleado idempleado, byte[] pdf) {
        this.codigoDocumento = new SimpleIntegerProperty(codigoDocumento);
        this.asunto = new SimpleStringProperty(asunto);
                this.estado = new SimpleStringProperty(estado);
        this.tipodocumento = tipodocumento;
        this.idexpediente = idexpediente;
        this.idempleado = idempleado;
        this.pdf = pdf;

    }


    public Documento(Integer codigoDocumento) {
       this.codigoDocumento = new SimpleIntegerProperty(codigoDocumento);
    }
    

    public IntegerProperty getCodigoDocumentoProperty() {
        return codigoDocumento;
    }

    public StringProperty getAsuntoProperty() {
        return asunto;
    }

    public TipoDoc getTipodocumentoProperty() {
        return tipodocumento;
    }

    public Expediente getIdexpedienteProperty() {
        return idexpediente;
    }

    public Empleado getIdempleadoProperty() {
        return idempleado;
    }

    public StringProperty getEstadoProperty() {
        return estado;
    }

// getter y setter sin property
    public Integer getCodigoDocumento() {
        return codigoDocumento.get();
    }

    public void setCodigoDocumento(Integer codigoDocumento) {
        this.codigoDocumento = new SimpleIntegerProperty(codigoDocumento);
    }

    public String getAsunto() {
        return asunto.get();
    }

    public void setAsunto(String asunto) {
        this.asunto = new SimpleStringProperty(asunto);
    }

    public TipoDoc getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(TipoDoc tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Expediente getIdexpediente() {
        return idexpediente;
    }

    public void setIdexpediente(Expediente idexpediente) {
        this.idexpediente = idexpediente;
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
    
 

    public int guardarRegistro(Connection connection) {
        try {
            //Evitar inyeccion SQL.
//            nomemp,apepatemp,apematemp, posemp, dniemp,area.idare,area.nomare,area.estare, `ESTEMP`, `SEXEMP`, useremp, passemp
//            "INSERT INTO `documento`(`ASUDOC`, `IDTIPDOC`, `IDEXP`, `IDEMPEMI`, `ESTDOC`) VALUES (?,?,?,?,?)
            PreparedStatement instruccion
                    = connection.prepareStatement("INSERT INTO `documento`"
                            + "(`ASUDOC`, "
                            + "`IDTIPDOC`,"
                            + " `IDEXP`, "
                            + "`IDEMPEMI`, "
                            + "`ESTDOC`,"
                            + "`ARCDOC`"
                            + ") VALUES (?,?,?,?,?,?)");
            instruccion.setString(1, asunto.get());
            instruccion.setInt(2, tipodocumento.getCodigoTipDoc());
            instruccion.setInt(3, idexpediente.getCodigoExpediente());
            instruccion.setInt(4, idempleado.getCodigoEmpleado());
            instruccion.setString(5, estado.get());
             instruccion.setBytes(6, pdf);
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
                            "      UPDATE `documento` SET `ASUDOC`=?,"
                            + "`IDTIPDOC`=?,"
                            + "`IDEXP`=?,"
                            + "`IDEMPEMI`=?,"
                            + "`ESTDOC`=?"
                            + "WHERE `IDDOC`=?"
                    );
            instruccion.setString(1, asunto.get());
            instruccion.setInt(2, tipodocumento.getCodigoTipDoc());
            instruccion.setInt(3, idexpediente.getCodigoExpediente());
            instruccion.setInt(4, idempleado.getCodigoEmpleado());
            instruccion.setString(5, estado.get());
            instruccion.setInt(6, codigoDocumento.get());

            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int eliminarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "delete from documento where IDDOC = ?"
            );
            instruccion.setInt(1, codigoDocumento.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void llenarInformacion(Connection connection,
            //Integer codigoDocumento, String asunto, TipoDoc tipodocumento, Expediente idexpediente, Empleado idempleado, String estado
            ObservableList<Documento> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT IDDOC,"
                    + " ASUDOC,"
                    + " tipo_documento.idtipdoc,"
                    + " tipo_documento.nomtipdoc,"
                    + " expediente.idexp,"
                    + " expediente.numexp,"
                    + " empleado.idemp,"
                    + " empleado.nomemp,empleado.apepatemp,empleado.apematemp,"
                    + " ESTDOC \n"
                    + "FROM documento\n"
                    + "inner join tipo_documento on documento.idtipdoc = \n"
                    + "tipo_documento.idtipdoc \n"
                    + "inner join expediente on documento.idexp = expediente.idexp \n"
                    + "inner join empleado on documento.idempemi = empleado.idemp; "
            );
            while (resultado.next()) {
                lista.add(
                        new Documento(
                                resultado.getInt("iddoc"),
                                resultado.getString("asudoc"),
                                 resultado.getString("estdoc"),
                                new TipoDoc(
                                        resultado.getInt("idtipdoc"),
                                        resultado.getString("nomtipdoc")
                                ),
                                new Expediente(
                                  resultado.getInt("idexp"),
                                 resultado.getInt("numexp")
                                
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
        }
    }

    @Override
    public String toString() {
        return "DOC-0" + codigoDocumento.get();
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
    
    
 
}

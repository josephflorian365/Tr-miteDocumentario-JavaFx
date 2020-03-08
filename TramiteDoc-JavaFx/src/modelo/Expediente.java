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

public class Expediente {

    private IntegerProperty codigoExpediente;
    private IntegerProperty numero;
    private Date fecha;
    private StringProperty asunto;
    private StringProperty estado;

    public Expediente(Integer codigoExpediente, Integer numero) {
   this.codigoExpediente = new SimpleIntegerProperty(codigoExpediente);
        this.numero = new SimpleIntegerProperty(numero);
    }

    public Expediente(Integer codigoExpediente, Integer numero, Date fecha, String asunto, String estado) {
        this.codigoExpediente = new SimpleIntegerProperty(codigoExpediente);
        this.numero = new SimpleIntegerProperty(numero);
        this.fecha = fecha;
        this.asunto = new SimpleStringProperty(asunto);
        this.estado = new SimpleStringProperty(estado);
    }

    public Integer getCodigoExpediente() {
        return codigoExpediente.get();
    }

    public void setCodigoExpediente(Integer codigoExpediente) {
        this.codigoExpediente = new SimpleIntegerProperty(codigoExpediente);
    }

    public Integer getNumero() {
        return numero.get();
    }

    public void setNumero(Integer numero) {
        this.numero = new SimpleIntegerProperty(numero);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto.get();
    }

    public void setAsunto(String asunto) {
        this.asunto = new SimpleStringProperty(asunto);
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
            PreparedStatement instruccion
                    = connection.prepareStatement("insert into expediente(NUMEXP, FECEXP, ASUEXP, ESTEXP) values (?,?,?,?)");
            instruccion.setInt(1, numero.get());
            instruccion.setDate(2, fecha);
            instruccion.setString(3, asunto.get());
            instruccion.setString(4, estado.get());

            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion
                    = connection.prepareStatement(
                            "update expediente set NUMEXP = ?, FECEXP = ?, ASUEXP = ?, ESTEXP = ? where IDEXP = ?"
                    );
            instruccion.setInt(1, numero.get());
            instruccion.setDate(2, fecha);
            instruccion.setString(3, asunto.get());
            instruccion.setString(4, estado.get());
            instruccion.setInt(5, codigoExpediente.get());
            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }}
                
                
                	

    public int eliminarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "delete from expediente where IDEXP = ?"
            );
            instruccion.setInt(1, codigoExpediente.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    
    public static void llenarInformacion(Connection connection,
				ObservableList<Expediente> lista){
		try {
			Statement instruccion = connection.createStatement();
			ResultSet resultado = instruccion.executeQuery(
					"select * from expediente"
			);
			while(resultado.next()){
				lista.add(
						new Expediente(
								resultado.getInt("IDEXP"),
								resultado.getInt("NUMEXP"),
								resultado.getDate("FECEXP"),
								resultado.getString("ASUEXP"),
								resultado.getString("ESTEXP")
						
						)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    @Override
    public String toString() {
        return "EXP" + " - " + numero.get() ;
    }
    
}


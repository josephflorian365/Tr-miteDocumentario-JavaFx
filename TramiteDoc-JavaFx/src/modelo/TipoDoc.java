package modelo;

/* Java Bean
* Clase: Area  */
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



public class TipoDoc {
    
  
	private IntegerProperty codigoTipDoc;
	private StringProperty nombreTipDoc;
	private StringProperty estado;

    public TipoDoc(Integer codigoTipDoc, String nombreTipDoc) {
        this.codigoTipDoc = new SimpleIntegerProperty(codigoTipDoc);
        this.nombreTipDoc = new SimpleStringProperty(nombreTipDoc);
    }

   public TipoDoc(Integer codigoTipDoc, String nombreTipDoc, String estado) {
        this.codigoTipDoc = new SimpleIntegerProperty(codigoTipDoc);
        this.nombreTipDoc = new SimpleStringProperty(nombreTipDoc);
        this.estado = new SimpleStringProperty(estado);
    }
    public int getCodigoTipDoc() {
        return codigoTipDoc.get();
    }

    public void setCodigoTipDoc(Integer codigoArea) {
        this.codigoTipDoc =  new SimpleIntegerProperty(codigoArea);
    }

    public String getNombreTipDoc() {
        return nombreTipDoc.get();
    }

    public void setNombreTipDoc(String nombreArea) {
        this.nombreTipDoc =  new SimpleStringProperty(nombreArea);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado = new SimpleStringProperty(estado);
    }
    public static void llenarInformacion(Connection connection, ObservableList<TipoDoc> lista){
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery(
					"Select * from tipo_documento"
			);
			while (resultado.next()){
				lista.add(
						new TipoDoc(
								resultado.getInt("idtipdoc"),
								resultado.getString("nomtipdoc"),
								resultado.getString("esttipdoc")
						)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

   public int guardarRegistro(Connection connection){
		try {
			//Evitar inyeccion SQL.
			PreparedStatement instruccion =
					connection.prepareStatement("insert into tipo_documento(NOMTIPDOC, ESTTIPDOC) values (?,?)");
			instruccion.setString(1, nombreTipDoc.get());
			instruccion.setString(2, estado.get());
		
			return instruccion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
public int actualizarRegistro(Connection connection){
		try {
			PreparedStatement instruccion =
					connection.prepareStatement(
								"update tipo_documento set NOMTIPDOC = ?, ESTTIPDOC = ? where IDTIPDOC = ?"
					);
			instruccion.setString(1, nombreTipDoc.get());
			instruccion.setString(2, estado.get());
			instruccion.setInt(3, codigoTipDoc.get());
			
			return instruccion.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

		public int eliminarRegistro(Connection connection){
		try {
			PreparedStatement instruccion = connection.prepareStatement(
							"delete from tipo_documento where IDTIPDOC = ?"
			);
			instruccion.setInt(1, codigoTipDoc.get());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

    @Override
    public String toString() {
        return  nombreTipDoc.get() ;
    }
                
}

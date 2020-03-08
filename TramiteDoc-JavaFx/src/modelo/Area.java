

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



public class Area {
    
  
	private IntegerProperty codigoArea;
	private StringProperty nombreArea;
	private StringProperty estado;
        


    public Area(Integer codigoArea, String nombreArea, String estado) {
        this.codigoArea = new SimpleIntegerProperty(codigoArea);
        this.nombreArea = new SimpleStringProperty(nombreArea);
        this.estado = new SimpleStringProperty(estado);
    }

    public Area(Integer codigoArea, String nombreArea) {
      this.codigoArea = new SimpleIntegerProperty(codigoArea);
       this.nombreArea = new SimpleStringProperty(nombreArea);
    }



 

    public int getCodigoArea() {
        return codigoArea.get();
    }

    public void setCodigoArea(Integer codigoArea) {
        this.codigoArea =  new SimpleIntegerProperty(codigoArea);
    }

    public String getNombreArea() {
        return nombreArea.get();
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea =  new SimpleStringProperty(nombreArea);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado = new SimpleStringProperty(estado);
    }
    
    
	public IntegerProperty codigoAreaProperty(){
		return codigoArea;
	}

	public StringProperty nombreAreaProperty(){
		return nombreArea;
	}

	public  StringProperty estadoProperty(){
		return estado;
	}
    public static void llenarInformacion(Connection connection, ObservableList<Area> lista){
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery(
					"Select idare, nomare, estare from area"
			);
			while (resultado.next()){
				lista.add(
						new Area(
								resultado.getInt("idare"),
								resultado.getString("nomare"),
								resultado.getString("estare")
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
					connection.prepareStatement("insert into area(NOMARE,ESTARE) values (?,?)");
			instruccion.setString(1, nombreArea.get());
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
								"update area set NOMARE = ?, ESTARE = ? where IDARE = ?"
					);
			instruccion.setString(1, nombreArea.get());
			instruccion.setString(2, estado.get());
			instruccion.setInt(3, codigoArea.get());
			
			return instruccion.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

		public int eliminarRegistro(Connection connection){
		try {
			PreparedStatement instruccion = connection.prepareStatement(
							"delete from Area where IDARE = ?"
			);
			instruccion.setInt(1, codigoArea.get());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

    @Override
    public String toString() {
        return  codigoArea.get() + " - " + nombreArea.get();
    }



}

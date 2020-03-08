
package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseMySQL {
    private Connection connection;
    
    public Connection Conectar() throws SQLException{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/doqinter";
        String user = "root";
        String pass = "";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en Conexion " + e.getMessage());
        }
        return connection;
    
    }
    
    
    public void Desconectar(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error en desconectar: " + e.getMessage());
        }
   }   
    
    
}

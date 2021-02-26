/*
 * Clase creada para manejar la creacion de la Base de Datos
 * de forma automatica cuando se enciende el programa
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Angel Esquinas
 */
public class CreateBBDD {
    
    public static Connection con = null;
    
    public static void connect(String name) { // conexion a la base de datos.
        
        String user= "system";
        String pass = "root";
        
        try {          
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", user, pass);  // conexion a la URL.
       
            //createDataBase(name); // creao la base.
            
            //createTypes();
            
            //createTables(); // creo las tablas.
           
//            if(isEmpty() <= 0){
//                //insertTables(); // inserto datos.
//            }
           
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "No es posible conectarse a la Base de Datos.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }       
    }
    
     public static void disconnect() { // desconexion de la base de datos.
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());

        }
    }

    
     private static void createDataBase(String name) throws SQLException { // creo la BBDD
        Statement stmt = con.createStatement();
            
        String data = String.format("CREATE DATABASE IF NOT EXISTS %s;", name);           
        stmt.executeUpdate(data);
        
        String base = String.format("USE %s;", name); 
        stmt.executeUpdate(base);
        
        stmt.close();
    }
     
     private static void createTypes() throws SQLException {  // contiene toda la creación de tablas.
        Statement stmt = con.createStatement();

        String muebleType = "CREATE OR REPLACE TYPE t_mueble AS OBJECT (\n" + // creacion tipo Mueble.
        "modelo INT(10),\n" +
        "nombre VARCHAR2(100),\n" +
        "precio INT(10),\n" +
        "paquetes INT(5),\n" +
        "cantidad INT(5)\n" +
        ");";
        
        stmt.executeUpdate(muebleType);
        
        String tamanoType = "CREATE OR REPLACE TYPE t_tamano AS OBJECT (\n" + // creacion tipo Tamaño.
        "id INT(10),\n" +
        "ancho INT(10),\n" +
        "fondo INT(10),\n" +
        "altura INT(10),\n" +
        "peso_balda INT(10),\n" +
        "mueble REF t_mueble\n" +
        ");";
        
        stmt.executeUpdate(tamanoType);
        
        String materialType = "CREATE OR REPLACE TYPE t_material AS OBJECT (\n" + // creacion tipo Material.
        "id INT(10),\n" +
        "principal VARCHAR(100),\n" +
        "secundario VARCHAR(100),\n" +
        "mueble REF t_mueble\n" +
        ");";
        
        stmt.executeUpdate(materialType);
 
        stmt.close();
    }
     
    private static void createTables() throws SQLException {  // contiene toda la creación de tablas.
        Statement stmt = con.createStatement();

        String muebleTable = "CREATE TABLE mueble OF t_mueble"; // creación tabla Mueble.
        
        stmt.executeUpdate(muebleTable);
        
        String tamanoTable = "CREATE TABLE tamano OF t_tamano"; // creación tabla Tamaño.
        
        stmt.executeUpdate(tamanoTable);
        
        String materialTable = "CREATE TABLE material OF t_material"; // creación tabla Material.
        
        stmt.executeUpdate(materialTable);
 
        stmt.close();
    }
     
    private static void insertTables() throws SQLException {  // insertamos valores a las diferentes tablas.
        Statement stmt = con.createStatement();

        String muebleData = "INSERT INTO mueble VALUES\n" + // inserto los datos en Mueble.
        "('00278578', 'HYLLIS', '10', '1', '1');\n";
        
        stmt.executeUpdate(muebleData);
        
        String tamanoData = "INSERT INTO tamano VALUES\n" +
        "(1, '60', '27', '140', '25', (SELECT REF(m) FROM mueble m WHERE m.modelo = '00278578'));\n"; // inserto los datos en Tamaño.
        
        stmt.executeUpdate(tamanoData);
        
        String materialData = "INSERT INTO material VALUES\n" +
        "(1, 'Acero galvanizado', 'Plástico amídico', (SELECT REF(m) FROM mueble m WHERE m.modelo = '00278578'));\n"; // inserto los datos en Material.
        
        stmt.executeUpdate(materialData);
               
        stmt.close();
    }
     
    public static int isEmpty() throws SQLException {  // comprueba si esta vacía la Base de Datos.            
        int model = 0 ;
        
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS muebles FROM mueble;");  // le pedimos la cantidad de muebles que hay.
        
        if (rs.next()) {
            model = rs.getInt("muebles");
        }else {
            model = -1;
        }

        rs.close();
        stmt.close();
        
        return model;
    }
}

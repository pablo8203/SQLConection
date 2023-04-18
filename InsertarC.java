package carrito.tpf;

/**
Procedimiento para conectar la DB y realizar insercion de datos en la tabla
Sin pedir los datos por pantalla.  
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class InsertarC {
  public static void main(String[] args) {
    
    Connection conexion;
    String url="jdbc:mysql://localhost:3306/carrito";  // conectamos la tabla
    String usuario="root";
    String clave="pabLo987$";
    String consulta= "insert into cliente(ClienDni,ClienNom,ClienApe) values ('5','Pablo','Rodriguez')";
        
    try {
    	Class.forName("com.mysql.jdbc.Driver");     
        conexion=DriverManager.getConnection(url,usuario,clave);    
        Statement sentencia=conexion.createStatement();
        sentencia.execute(consulta);   
        System.out.println("Los nuevos datos se agregaron a la tabla!!");
    } catch (Exception e) {  
      e.printStackTrace();
      System.out.println("Error en la insercion de datos!!");
    }
    //---------
    
    
    
    
    //----------
  }
}
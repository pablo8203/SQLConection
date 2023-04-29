package carrito.tpf;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Procedimiento para conectar la DB y realizar SOLO insercion de datos en la tabla y PEDIR los datos por pantalla.
*/
public class InsercionFor {
	   
    public static void main(String[] args) {
    
        String usuario="root";
        String password="pabLo987$";
        Scanner sc = new Scanner(System.in);//se crea un objeto de tipo Scanner (sc) para recibir datos en la consola
        
        System.out.println("Escriba el nro libreta: ");
        int NroLib  = sc.nextInt(); //asigna la entrada de nro libreta
        
        System.out.println(" Ingrese el nombre de la escuela:  ");
        String NomEsc = sc.next(); //asigna la entrada nombre escuela
        
        System.out.println(" Ingrese el DNI alumno:  ");
        int LibALumDni = sc.nextInt(); //asigna la entrada de usuario a la variable nombre
                
        String sql = "insert into libreta (NroLib,NomEsc,LibAlumDni) values ('"+NroLib+"','"+NomEsc+"','"+LibALumDni+"')";
        Connection con=null;
            try{
        //En la siguiente linea se crea la conexion a la Base de datos
       con=DriverManager.getConnection("jdbc:mysql://localhost:3306/escuela", usuario, password);  
       Statement s = con.createStatement();    //Se crea un statement
       int m = s.executeUpdate(sql); //Se ejecuta la instruccion sql 
         if (m == 1)
             System.out.println("Se realizo correctamente la insercion : "+sql);
         else
             System.out.println("fallo la insercion");
     // con.close();  //se cierra la conexion a la base de datos
    }
    catch(Exception e)
    {
       e.printStackTrace();
    }
 
   // }
   
  //***
   // public static void main(String[] args) {
        
        try{
// String usuario="u377283307_topicosuser";
 //String password="Datos#123";
 //En la siguiente linea se crea la conexion a la Base de datos
 // Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrito", usuario, password);  
  Statement s=con.createStatement();    //Se crea un statement
  ResultSet rs=s.executeQuery("select * from cliente");  //Se ejecuta la instruccion sql
  //en el siguiente bloque se implementa un ciclo while que recorre la lista de resultados obtenidos imprimiendolos en la consola
  while(rs.next()){
     System.out.println(rs.getInt(1)+" "+rs.getString(2)); //imprime el contenido del primer campo de la tabla y del segundo campo de la tabla
  }

  con.close();  //se cierra la conexion a la base de datos
}
catch(Exception e)
{
   e.printStackTrace();
}

}
  //***  
    
}
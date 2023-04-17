package carrito.tpf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 Procedimiento para conectar la DB y realizar una consulta de las tablas.
 */
public class PruebaC {

 
    Connection conexion = null;
    Statement stm = null;
   
    public static void main(String[] args) {
      
        Scanner sc = new Scanner(System.in);
        PruebaC m = new PruebaC();

        m.conectar();    //CONECTO LA BBDD ANTES DE INICIAR EL MENÚ
        boolean salir = false;
        do {

            switch (menuPrin()) {
            	
            	case 3:
            		m.eliminarRegistoEmpleados();
            	break;	
            	case 2:
            		m.agregarTablaEmpleados();    //Cuando pulse la opción 2 del menú me llevará a la función consultaTablaEstudiantes
                break;
                case 1:
                    m.consultaTablaEstudiantes();    //Cuando pulse la opción 1 del menú me llevará a la función consultaTablaEstudiantes
                    break;
                case 0:
                    System.out.println("Vuelve pronto");
                    m.desconectar();   //CUANDO PULSO EL 0 CIERRO LA BBDD Y CIERRO LA APL.
                    salir = true;
                    break;
                default:
                    System.out.println("Opción incorrecta");
                    break;
            }
        } while (!salir);

    } //fin main

    //MENU PPAL:
    private static int menuPrin() {

        Scanner sc = new Scanner(System.in);

        System.out.println("--------------------------------");
        System.out.println("Conexión de bbdd MySQL");
        System.out.println("--------------------------------");
        System.out.println("3.ELIMINAR UN REGISTRO EN LA TABLA CLIENTES");
        System.out.println("2.INSERTAR UN REGISTRO EN LA TABLA CLIENTES");
        System.out.println("1.MOSTRAR EL CONTENIDO DE LA TABLA CLIENTES");
        System.out.println("0.SALIR");
        System.out.println("\n Por favor, escoja una opción.");
        System.out.println("--------------------------------");

        return sc.nextInt(); //Recibo un entero

    }//Fin menuPrin
   
   
   
   
     /*MÉTODO QUE CONECTA CON LA BBDD DE MYSQL*/
    public void conectar() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            /*En esta línea es importante que indiquemos:
                  el nombre de la base de datos --> qatar2022
                  El usuario y contraseña que tengamos en nuestro gestor de base de datos phpMyAdmin*/
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/carrito", "root", "pabLo987$");

            System.out.println("**************************************");
            System.out.println(" * CONEXIÓN REALIZADA CORRECTAMENTE * ");
            System.out.println("**************************************");
           
        } catch (Exception e) {

            System.out.println("*****************************************");
            System.out.println(" * NO SE HA PODIDO REALIZAR LA CONEXIÓN * ");
            System.out.println("******************************************");
        }

    }//conectar

    /*DESCONECTAR LA BBDD*/
    private void desconectar() {

        try {
            conexion.close();
            System.out.println("\n************************************************************\n");
            System.out.println("La conexion a la base de datos se ha terminado");
            System.out.println("\n************************************************************");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }//desconectar
   
    ////////////////////////////////////////////////////////////////////////////
   
    /*MÉTODO PARA REALIZAR UNA CONSULTA A UNA TABLA MYSQL*/
        private void consultaTablaEstudiantes() {
        //Realizamos la consulta sql para mostrar todos los datos de la tabla estudiante
        ResultSet r = buscar("select ClienDni,ClienNom,ClienApe from cliente");
       
        try {
            System.out.println("REGISTROS DE LA TABLA CLIENTES");
           
            /*
            Hacemos un While para recorrer toda la tabla estudiantes
            y así poder sacar todos los registros de la tabla
            */
            while (r.next()) {
                /*Se muestra los datos que queremos sacar por consola indicandole:
                        El tipo de dato (int,String...) de cada campo
                        El nombre de los campos de la tabla entre comillas doble " "
                */
                System.out.println(r.getInt("ClienDni") + " | " + r.getString("ClienNom") + " | " + r.getString("ClienApe"));// + " | " + r.getInt("EmpDep"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PruebaC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//mostrarTablaPropietarios
   
       
        //Este método lo uso para mostrar los datos de una tabla: (executeQuery)
    ResultSet buscar(String sql) {
        try {
            stm = conexion.createStatement();
            return stm.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PruebaC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }//buscar
     
    
    ////////////////////////////////////////////////////////////////////////////
    
    /*MÉTODO PARA REALIZAR UNA CARGA A UNA TABLA MYSQL*/
        private void agregarTablaEmpleados() {
        	String usuario="root";
            String password="pabLo987$";
            Scanner k = new Scanner(System.in);//se crea un objeto de tipo Scanner (K) para recibir datos en la consola
            
            System.out.println("Escriba el DNI del cliente: ");
            int ClienDni  = k.nextInt(); //asigna la entrada de usuario a la DNI
            
            System.out.println(" Ingrese el nombre del cliente:  ");
            String ClienNom = k.next(); //asigna la entrada de usuario a la variable nombre
            
            System.out.println(" Ingrese el apellido del cliente:  ");
            String ClienApe = k.next(); //asigna la entrada de usuario a la variable nombre
                    
            String sql = "insert into cliente (ClienDni,ClienNom,ClienApe) values ('"+ClienDni+"','"+ClienNom+"','"+ClienApe+"')";
            Connection con=null;
                try{
            //En la siguiente linea se crea la conexion a la Base de datos
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrito", usuario, password);  
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
    }//mostrarTablaPropietarios
   
        ////////////////////////////////////////////////////////////////////////////
        
        /*MÉTODO PARA REALIZAR UNA ELIMINACION A UNA TABLA MYSQL*/
            private void eliminarRegistoEmpleados() {
            	String usuario="root";
                String password="pabLo987$";
                Scanner k = new Scanner(System.in);//se crea un objeto de tipo Scanner (K) para recibir datos en la consola

                System.out.println("Escriba el DNI del cliente a eliminar:...");
                int ClienDni  = k.nextInt(); //asigna la entrada de usuario a la DNI
                
                String sql ="DELETE FROM cliente WHERE ClienDni = '"+ClienDni+"'";
                Connection con=null;
                // DELETE FROM Producto WHERE ClienDni = 7   
                try {
                	//Class.forName("com.mysql.jdbc.Driver");     
                	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrito", usuario, password);  
                    Statement sentencia=conexion.createStatement();
                    sentencia.execute(sql);   
                    System.out.println("El registro se elimino!!");
                } catch (Exception e) {  
                  e.printStackTrace();
                  System.out.println("Error en el borrado del registro!!");
                }
              }
    
    
    }//FIN
package carrito.tpf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 Procedimiento para conectar la DB y realizar una consulta de las tablas.
 */
public class PruebaC {
    Connection conexion = null;
    Statement sentencia = null; // 3. Crear la sentencia - Este método es usado para crear la sentencia.
    							// Esta sentencia es la responsable de ejecutar las consultas a la DB.

   
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PruebaC m = new PruebaC();

        m.conectar();    //CONECTO LA BBDD ANTES DE INICIAR EL MENÚ
        boolean salir = false;
        do {
            switch (menuPrin()) {
            	case 1:
            		m.consultarTabla();  //Cuando pulse la opción 1 del menú me llevará a la función AGREGAR
            		break;
            	case 2:
            		m.agregarTablaEmpleados();    //Cuando pulse la opción 2 del menú me llevará a la función AGREGAR
            		break;
            	case 3:
            		m.eliminarRegistoEmpleados(); //Cuando pulse la opción 3 del menú me llevará a la función ELIMINAR
            		break;	
                case 0:
                    System.out.println("Vuelva pronto");
                    m.desconectar();               //CUANDO PULSE EL 0 CIERRO LA BBDD Y CIERRO LA APL.
                    salir = true;
                    break;
                default:
                    System.out.println("Opción incorrecta");
                    break;
            }
        } while (!salir);

    } //fin main
//-------------------------------------------------------------------------------
    
    //MENU PRINCIPAL:
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

    }//Fin menuPrincipal

//-----------------------------------------------------------------------------------------------
    
/*MÉTODO QUE CONECTA CON LA BBDD DE MYSQL*/
    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //1. Registrar la ‘clase’ del driver - Este método se usa para registrar la clase que se utilizará como driver.
            /*En esta línea es importante que indiquemos: el nombre de la base de datos --> qatar2022 o cualquier otra
              El usuario y contraseña que tengamos en nuestro gestor de base de datos MySQL*/
             conexion = DriverManager.getConnection("jdbc:mysql://localhost/carrito", "root", "pabLo987$");
            //2. Crear el objeto de conexión - Este método se utiliza para establecer conexión con la DB.
            // aca ingresamos el camino y el nombre de la DB
            System.out.println("**************************************");
            System.out.println(" * CONEXIÓN REALIZADA CORRECTAMENTE * ");
            System.out.println("**************************************");
        } catch (Exception e) {
            System.out.println("*****************************************");
            System.out.println(" * NO SE HA PODIDO REALIZAR LA CONEXIÓN * ");
            System.out.println("******************************************");
        }

    }// FIN METODO CONECTAR
//-----------------------------------------------------------------------------------------------

 /*METODO PARA DESCONECTAR LA BBDD*/
    private void desconectar() {
        try {
            conexion.close(); // 5. Cerrar la conexión - Este método finaliza la conexión con la DB.
            System.out.println("\n************************************************************\n");
            System.out.println("La conexion a la base de datos se ha terminado");
            System.out.println("\n************************************************************");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }//desconectar
   
//----------------------------------------------------------------------------------------------   

/*MÉTODO PARA REALIZAR UNA CONSULTA A UNA TABLA MYSQL*/
        private void consultarTabla() {
        //Realizamos la consulta sql para mostrar todos los datos de la tabla estudiante
        ResultSet r = buscar("select ClienDni,ClienNom,ClienApe from cliente");  // Resulset devuelve el resultado de una consulta
        try {
            System.out.println("REGISTROS DE LA TABLA CLIENTES");
             /*
            Hacemos un While para recorrer toda la tabla clientes y así poder sacar todos los registros de la tabla
            */
            while (r.next()) {
                /*Se muestra los datos que queremos sacar por consola indicandole: El tipo de dato (int,String...) de cada campo
                        El nombre de los campos de la tabla entre comillas doble " " */
                System.out.println(r.getInt("ClienDni") + " | " + r.getString("ClienNom") + " | " + r.getString("ClienApe"));// + " | " + r.getInt("EmpDep"));
            }
        } catch (SQLException ex) {
       //     Logger.getLogger(PruebaC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//mostrarTabla
          
    //Este método lo uso para mostrar los datos de un registro de la tabla: (executeQuery)
    ResultSet buscar(String sql) {
        try {
            sentencia = conexion.createStatement(); // 3.Este método es usado para crear la sentencia.
            //Esta sentencia es la responsable de ejecutar las consultas a la DB.
            return sentencia.executeQuery(sql);  // 4. Ejecutar consulta - Este método devuelve el resultado de una consulta (filas).
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
            Scanner sc = new Scanner(System.in);//se crea un objeto de tipo Scanner (sc) para recibir datos en la consola
            
            System.out.println("Escriba el DNI del cliente: ");
            int ClienDni  = sc.nextInt(); //asigna la entrada de usuario a la DNI
            sc.nextLine(); // Consume "\n"
            System.out.println("Ingrese el nombre del cliente:  ");
            String ClienNom = sc.nextLine();//asigna la entrada de usuario a la variable nombre
            
            System.out.println("Ingrese el apellido del cliente:  ");
            String ClienApe = sc.nextLine(); //asigna la entrada de usuario a la variable nombre
                    
            String sql = "insert into cliente (ClienDni,ClienNom,ClienApe) values ('"+ClienDni+"','"+ClienNom+"','"+ClienApe+"')";
            Connection con=null;
                try{
            //En la siguiente linea se crea la conexion a la Base de datos
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrito", usuario, password);  
           Statement sentencia = con.createStatement();    //3. Crear la sentencia - Este método es usado para crear la sentencia.
                                                  //Esta sentencia es la responsable de ejecutar las consultas a la DB.

           int m = sentencia.executeUpdate(sql); //Se ejecuta la instruccion sql (que paso por parametros previamente)
             if (m == 1)
                 System.out.println("Se realizo correctamente la insercion : "+sql);
             else
                 System.out.println("fallo la insercion");
          con.close();  //se cierra la conexion a la base de datos
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
    }
   
 //-----------------------------------------------------------------------------------------------------
        
        /*MÉTODO PARA REALIZAR UNA ELIMINACION A UNA TABLA MYSQL*/
            private void eliminarRegistoEmpleados() {
            	String usuario="root";
                String password="pabLo987$";
                Scanner sc = new Scanner(System.in);//se crea un objeto de tipo Scanner (SC) para recibir datos en la consola

                System.out.println("Escriba el DNI del cliente a eliminar:...");
                int ClienDni  = sc.nextInt(); //asigna la entrada de usuario a la DNI
                
                String sql ="DELETE FROM cliente WHERE ClienDni = '"+ClienDni+"'";
                Connection con=null;
                // DELETE FROM Producto WHERE ClienDni = 7   
                try {
                	//Class.forName("com.mysql.jdbc.Driver");     
                	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrito", usuario, password);  
                    Statement sentencia = conexion.createStatement();
                    sentencia.execute(sql);   
                    System.out.println("El registro se elimino!!");
                } catch (Exception e) {  
                  e.printStackTrace();
                  System.out.println("Error en el borrado del registro!!");
                }
              }
    
 }//FIN
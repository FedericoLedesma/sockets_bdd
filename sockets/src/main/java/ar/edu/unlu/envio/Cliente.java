package ar.edu.unlu.envio;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Cliente {
	private String nombreArchivo = "";
	private String nombre="C:\\Users\\feder\\consulta.xml";
	private InetAddress direccion;
	private Socket socket;
	private DataInputStream dis;
	private FileOutputStream fos;
	private BufferedOutputStream out;
	private BufferedInputStream in;
	private DataOutputStream dos;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
     
     public Cliente( String nombreArchivo )
     {
          this.nombreArchivo = nombreArchivo;
     }
     public Cliente() {
    	 
     }
     public void crearConexion() {
    	 try {
    		// Creamos la direccion IP de la maquina servidor en este caso sera localhost
	    	 direccion = InetAddress.getByName( "localhost" );
	         
	         // Creamos el Socket con la direccion y elpuerto de comunicacion
	         socket = new Socket( direccion, 4400 );
	         socket.setSoTimeout( 40000 );
	         socket.setKeepAlive( true );
	      
	         // Creamos flujo de entrada para leer los datos que envia el cliente 
	         dis = new DataInputStream( socket.getInputStream() );
	         // Creamos flujo de salida para leer los datos que envia el cliente 
	         dos = new DataOutputStream(socket.getOutputStream());
         
      //   in = new BufferedInputStream( socket.getInputStream() );
    	 }catch(Exception e) {
    		 System.out.println("Error al establecer conexion");
    	 }
     }
     public void recibir( )
     {
     //--------------------AGREGAR LOS METODOS DE COMUNICACION CHAT-
    	 try
	     {
    		 recibirArchivo();
	       
    		 cerrarRecibir();
	         
	     }catch( Exception e )
    	 {
    		 System.out.println( "Recibir: "+e.toString() ); 
    	 }
     
     }
     
     private void cerrarEnviar() {
		// TODO Auto-generated method stub
    	 try {
    	 bis.close();
         bos.close();
    	 }catch(Exception e ) {}
	}
     
	private void cerrarRecibir() {
		// TODO Auto-generated method stub
    	 try {
    	    	out.flush(); 
    	        in.close();
    	        out.close(); 
    	 }catch(Exception e) {}
	}
	
	private void recibirArchivo() {
		// TODO Auto-generated method stub
    	// Obtenemos el nombre del archivo
    	 int tam;
    	 try {
    		 String nombreArchivo = dis.readUTF().toString(); 
    	 
	         // Obtenemos el tamaño del archivo
	         tam = dis.readInt(); 
	
	       //  System.out.println( "Recibiendo archivo "+nombreArchivo );
	  
	         // Creamos flujo de salida, este flujo nos sirve para 
	         // indicar donde guardaremos el archivo
	          fos = new FileOutputStream( "C:\\Users\\feder\\"+nombreArchivo );
	          out = new BufferedOutputStream( fos );
	          in = new BufferedInputStream( socket.getInputStream() );
	
	         // Creamos el array de bytes para leer los datos del archivo
	         byte[] buffer = new byte[ tam ];
	
	         // Obtenemos el archivo mediante la lectura de bytes enviados
	         for( int i = 0; i < buffer.length; i++ )
	         {
	            buffer[ i ] = ( byte )in.read( ); 
	         }
	
	         // Escribimos el archivo 
	         out.write( buffer ); 
	
	        // System.out.println( "Archivo Recibido "+nombreArchivo );
	  
	     }
	     catch( Exception e )
	     {
	        System.out.println( "Recibir: "+e.toString() ); 
	     }
     } 
    public void cerrarTodo() {
    	try {
    	out.flush(); 
        in.close();
        out.close(); 
       
        bis.close();
         bos.close();
         socket.close();
    	}
    	catch(Exception e0) {}

	}
    //--------metodo para enviar sql en formato xml-------------------------------------------------------------------
     public void enviarArchivo() {
    	 try {
 			// TODO Auto-generated method stub
 	    	   File archivo = new File( nombre );
 	           
 	            // Obtenemos el tamaño del archivo
 	            int tamañoArchivo = ( int )archivo.length();
 	         
 	            // Creamos el flujo de salida, este tipo de flujo nos permite 
 	            // hacer la escritura de diferentes tipos de datos tales como
 	            // Strings, boolean, caracteres y la familia de enteros, etc.
 	            //dos = new DataOutputStream( socket.getOutputStream() );
 	         
 	            System.out.println( "Enviando Archivo: "+archivo.getName() );
 	         
 	            // Enviamos el nombre del archivo 
 	            dos.writeUTF( archivo.getName() );
 	         
 	            // Enviamos el tamaño del archivo
 	            dos.writeInt( tamañoArchivo );
 	         
 	            // Creamos flujo de entrada para realizar la lectura del archivo en bytes
 	            fis = new FileInputStream( nombre );
 	            bis = new BufferedInputStream( fis );
 	         
 	            // Creamos el flujo de salida para enviar los datos del archivo en bytes
 	            bos = new BufferedOutputStream( socket.getOutputStream()          );
 	         
 	            // Creamos un array de tipo byte con el tamaño del archivo 
 	            byte[] buffer = new byte[ tamañoArchivo ];
 	         
 	            // Leemos el archivo y lo introducimos en el array de bytes 
 	            bis.read( buffer ); 
 	         
 	            // Realizamos el envio de los bytes que conforman el archivo
 	            for( int i = 0; i < buffer.length; i++ )
 	            {
 	                bos.write( buffer[ i ] ); 
 	            } 
 	         
 	            System.out.println( "Archivo Enviado: "+archivo.getName() );
 	            // Cerramos socket y flujos
 	           bis.close();
 	          bos.close();
 	          socket.close(); 
 	          }
 	          catch( Exception e )
 	          {
 	            System.out.println( e.toString() );
 	          }
	    	
     }
     
     //------n es la opcion del servidor (1 firebird, 2 postgresql)------------------------------------
     public void enviarTexto(String n,String s) {
    	 try {
 			//dos = new DataOutputStream( socket.getOutputStream() );
 			//System.out.println(s);
 			dos.writeInt(Integer.valueOf(n));
 			dos.flush();
          // Enviamos el nombre del archivo 
 			dos.writeUTF( s );       
          	dos.flush();
          	dos.close();
          	socket.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			System.out.println("------------ERROR AL ENVIAR SQL----------");
 		}//
     }

     // 
     /*public static void main( String args[] )
     {
    	 int n=0;
		Cliente ea = new Cliente( "C:\\Users\\feder\\query.xml" );
    	while (true) {
        ea.crearConexion(); 
        //System.out.println("Esta por recibir archivo");
        //ea.recibir();
       
        Scanner sc = new Scanner(System.in);
        
	    System.out.println("INGRESE la consulta sql a ENVIAR ");
	    String s = sc.nextLine();
	    // ea.crearConexion();
	    ea.enviarTexto("2",s);
	    System.out.println("esta recibiendo la respuesta");
	    //   ea.enviarArchivo();
	    ea.crearConexion();
	    ea.recibir();
	    System.out.println("Respuesta recibida con exitoo");
    	}
       
     }*/
     
}

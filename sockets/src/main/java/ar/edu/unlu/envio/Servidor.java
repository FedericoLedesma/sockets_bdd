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
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.edu.unlu.bd.ConexionFirebird;
import ar.edu.unlu.bd.ConexionPostgres;

public class Servidor {
	private String nombreArchivo = "C:\\Users\\feder\\git\\repository\\sockets\\query.xml";
	private ServerSocket servidor = null;
	private Socket socket;
	private DataOutputStream dos;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private DataInputStream dis;
	private BufferedOutputStream out;
	private OutputStream fos;
	private BufferedInputStream in;
	private ConexionFirebird conexionFirebird;
	private ConexionPostgres conexionPostgres;
	//private boolean band;
	public Servidor( ) throws IOException{
		// Creamos socket servidor escuchando en el mismo puerto donde se comunica el cliente
	    // en este caso el puerto es el 4400
	    servidor = new ServerSocket(4400);
	    conexionFirebird= new ConexionFirebird();
	    conexionPostgres= new ConexionPostgres();
	    try {
			conexionFirebird.realizarConexion();
			conexionPostgres.realizarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("NO SE HA PODIDO REALIZAR LA CONEXIONA A LA BASE DE DATOS");
			e.printStackTrace();
		}
	    System.out.println( "Esperando recepcion de archivos..." ); 
	}
	 
	public void iniciarServidor(){
		while( true ){
			try
	        	{
	            // Creamos el socket que atendera el servidor
	            iniciarConexion();	               
	            recibirConsulta();   
	            System.out.println("consulta recibida");
	            //la comunicacion se cierra luego del envio o la recepcion
	            iniciarConexion();
	            enviarArchivo();  
	            System.out.println("enviando archivo de respuesta");	           
	            }catch( Exception e )
	             {
	            	System.out.println( e.toString() );
	             }
	         } 
	}
	private void recibirConsulta() {
	// TODO Auto-generated method stub
		String st="";
		int n=0;
		try {
			n=dis.readInt();
			st = dis.readUTF();
			dis.close();
			socket.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(st);
			try {
				switchServer(n,st);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error al enviar consulta");
				e.printStackTrace();
			}
		}
	public void switchServer(int n,String s) {
		switch (n) {
		case 1: try {
				conexionFirebird.consultar(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//b=false;
			}
				break;
		case 2: try {
				conexionPostgres.consultar(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			break;
		default : break;
		}
	}
	
	public void iniciarConexion() {
		try {
			socket = servidor.accept(); 
			dos = new DataOutputStream( socket.getOutputStream() );//
			dis = new DataInputStream( socket.getInputStream() );
            //bos = new BufferedOutputStream( socket.getOutputStream()          );
            //   in = new BufferedInputStream( socket.getInputStream() );
		}catch(Exception e) {System.out.println("errori al iniciar nueva conexion tcp");}
	}
	      
	private void enviarArchivo() {
		try {
			// TODO Auto-generated method stub
			File archivo = new File( nombreArchivo );			
			// Obtenemos el tamaño del archivo
			int tamañoArchivo = ( int )archivo.length();
			
			// Creamos el flujo de salida
			// DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );
			
			System.out.println( "Enviando Archivo: "+archivo.getName() );
			
			// Enviamos el nombre del archivo 
			dos.writeUTF( archivo.getName() );
			// Enviamos el tamaño del archivo
			dos.writeInt( tamañoArchivo );
	    
			// Creamos flujo de entrada para realizar la lectura del archivo en bytes
			fis = new FileInputStream( nombreArchivo );
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
			archivo.delete();
		}	
		catch( Exception e )
		{
			System.out.println( e.toString() );
		}
		
	    	
	}
	public void cerrarTodo() {
		try {
			bis.close();
			bos.close();				
			in.close();
			out.close();
			socket.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	            
	}
	//--------metodos para recibir consultas sql en formato xml-------------------------------------------------------
	public void recibirArchivo() {
		int tam;
		try {
			String nombreArchivo = dis.readUTF().toString(); 
			
			// Obtenemos el tamaño del archivo
			tam = dis.readInt(); 
			
			System.out.println( "Recibiendo archivo "+nombreArchivo );
			
			// Creamos flujo de salida, este flujo nos sirve para 
			// indicar donde guardaremos el archivo
			fos = new FileOutputStream( "C:\\Users\\feder\\hola\\"+nombreArchivo );
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
			
			// Cerramos flujos
			out.flush(); 
			in.close();
			out.close(); 
			socket.close();
			
			System.out.println( "Archivo Recibido "+nombreArchivo );
	    
	       }
	       catch( Exception e )
	       {
	          System.out.println( "Recibir: "+e.toString() ); 
	       }
	      	 
	}
	//-----------RECIBIR SQL EN STRING--------------------------------------------------------------
	       public void recibirTexto() {
	    	   try {
	    		   
	               String st = dis.readUTF().toString(); 
	               //System.out.println("------------------------------------------");
	               //System.out.println(st);
	    	   }catch(Exception e ) {
	    		   System.out.println("Error al recibir SQL");
	    	   }
	       }

		// Lanzamos el servidor para la recepción de archivos
	       public static void main( String args[] ) throws IOException
	       {
	           new Servidor().iniciarServidor(); 
	       }
	
}

package ar.edu.unlu.bd;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ar.edu.unlu.xml.XMLGenerator;

public class ConexionPostgres {

	 private static Connection conn = null;
	    private static Statement stmt = null;
	    private XMLGenerator generador;
	    public void realizarConexion() throws SQLException{
	            
	            String urlDatabase = "jdbc:postgresql://192.168.1.40:5432/facturacion"; 
	            try {
	                conn = DriverManager.getConnection(urlDatabase, "postgres", "admin");
	            } catch (Exception e) {
	                System.out.println("Ocurrio un error : "+e.getMessage());
	            }
	            System.out.println("La conexión se realizo sin problemas! =) ");

	    }
	    
	    
	    public void consultar(String sql) throws SQLException{
	    	
	        ArrayList<String> numero=new ArrayList<>();
	        ArrayList<String> razonsocial=new ArrayList<>();
	        ArrayList<String> fecha=new ArrayList<>();
	        ArrayList<String> idcliente=new ArrayList<>();
	        String s="";
	        try{
	         stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql );
	        
	            int i=1;
	          
	            while (rs.next()){
	            	
	                numero.add(String.valueOf(rs.getInt("numero")));
	               
	                razonsocial.add(rs.getString("razonsocial"));
	            	
	                fecha.add((rs.getDate(("fecha")).toString()));
	            	
	                idcliente.add(String.valueOf(rs.getInt("idcliente")));
	            
	            }
	           
	             generador= new XMLGenerator();
	             generador.crearXML(numero,"numero",razonsocial,"razonsocial",fecha,"fecha",idcliente,"idcliente");
	            
	        }catch (Exception e){//b=false;
	        System.out.println("----SE HA PRODUCIDO UN ERROR AL CONSULTAR A LA BASE DE DATOS-");}
	
	    }

}

package ar.edu.unlu.bd;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.xml.transform.Result;

import ar.edu.unlu.xml.XMLGenerator;

/**
 *
 * @author feder
 */
public class ConexionFirebird {
    private static Connection conn = null;
    private static Statement stmt = null;
    private XMLGenerator generador;
    
    public void realizarConexion() throws SQLException{
            
            String urlDatabase = "jdbc:firebirdsql://localhost:3050/C:\\Users\\feder\\Documents\\NetBeansProjects\\projectchat\\personal_tp.fdb";//\\Firebird\\tp_1.fdb";//"jdbc:postgresql://localhost:5432/fabrica"; 
            try {
            	conn = DriverManager.getConnection(urlDatabase, "SYSDBA","masterkey" );
            } catch (Exception e) {
                System.out.println("Ocurrio un error : "+e.getMessage());
            }
            System.out.println("La conexión se realizo sin problemas! =) ");
           
    }
    //------
    public void consultar(String sql) throws SQLException{
    	//boolean b=true;
        ArrayList<String> id=new ArrayList<>();
        ArrayList<String> nombre=new ArrayList<>();
        ArrayList<String> apellido=new ArrayList<>();
        ArrayList<String> direccion=new ArrayList<>();
        String s="";
        try{
        	stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql );
            
            while (rs.next()){
            	
                id.add(String.valueOf(rs.getInt("id")));
                
                nombre.add(rs.getString("nombre"));
                       	
                apellido.add(rs.getString("apellido"));
                        	
                direccion.add(rs.getString("direccion"));
                
            }
             generador= new XMLGenerator();
             generador.crearXML(id,"id", nombre,"nombre", apellido,"apellido", direccion,"direccion");
             //final File localFile= new File("query.xml");
             //System.out.println("GENERANDO XML-------------------");
             //System.out.println(localFile.getName());
             
        }catch (Exception e){//b=false;
        System.out.println("----SE HA PRODUCIDO UN ERROR AL CONSULTAR A LA BASE DE DATOS");}
     
            
        
    }
  
}


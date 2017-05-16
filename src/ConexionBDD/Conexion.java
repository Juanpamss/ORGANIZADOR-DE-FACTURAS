/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Pa
 */
public class Conexion {
    
    PreparedStatement pst = null;
    static Connection conn = null;
    
    public static Connection conecxionBDD() throws ClassNotFoundException{                     
        
        try{
            
            Class.forName("org.sqlite.JDBC");
            
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Juan Pa\\Documents\\NetBeansProjects\\Proyecto_Libres\\proyecto_libres.sqlite");
            
            JOptionPane.showMessageDialog(null, "Conectado");
                     
            return conn;
            
        }catch(Exception ex){
        
            JOptionPane.showMessageDialog(null, ex);
                    
        }
        
        return conn;
    }
    
    public void insertar(String sql) {
        
        System.out.println(sql);
        
        try {
            
            pst = conn.prepareStatement(sql);
                   
            pst.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage()+"caca");
        }
    }
    
}

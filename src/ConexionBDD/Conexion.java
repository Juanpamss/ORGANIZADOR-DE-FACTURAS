/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Pa
 */
public class Conexion {

    PreparedStatement pst = null;
    public static Connection conn, conn2 = null;

    public static Connection conecxionBDD() throws ClassNotFoundException {

        try {

            Class.forName("org.sqlite.JDBC");
            String fileBDD = "src/BaseDatos/BDD.sqlite";
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileBDD);

            //JOptionPane.showMessageDialog(null, "CONEXION A LA BASE DE DATOS ESTABLECIDA","CONEXION",JOptionPane.INFORMATION_MESSAGE);
            return conn;

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);

        }

        return conn;
    }

    public static Connection conecxionBDDTipos() throws ClassNotFoundException {

        try {

            Class.forName("org.sqlite.JDBC");
            String fileBDD = "src/BaseDatos/TipoGasto.sqlite";
            conn2 = DriverManager.getConnection("jdbc:sqlite:" + fileBDD);

            //JOptionPane.showMessageDialog(null, "CONEXION A LA BASE DE DATOS ESTABLECIDA","CONEXION",JOptionPane.INFORMATION_MESSAGE);
            return conn2;

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);

        }

        return conn2;
    }

    public void insertar(String sql) {
        
        try {
            conn = Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            pst = conn.prepareStatement(sql);

            pst.execute();
            
            cerrarConeccion();

            //JOptionPane.showMessageDialog(null, "FACTURA CARGADA A LA BASE DE DATOS EXITOSAMENTE");
        } catch (Exception e) {

            System.out.println(e.getMessage() + " ERROR AL CARGAR LA FACTURA");
        }
    }

    /*public void insertarTipos(String producto, String tipo) {

        String query = "insert into 'main'.'Tipos' values ('" + producto + "','" + tipo + "')";

        try {
            conn2 = Conexion.conecxionBDDTipos();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {

            pst = conn2.prepareStatement(query);

            pst.execute();
            
            cerrarConeccion2();

            //JOptionPane.showMessageDialog(null, "FACTURA CARGADA A LA BASE DE DATOS EXITOSAMENTE");
        } catch (Exception e) {

            System.out.println(e.getMessage() + " ERROR AL CARGAR LA FACTURA");
        }
        
        
        
    }*/

    public boolean esVacia() throws ClassNotFoundException {

        String query = "SELECT count(nombre) FROM 'main'.'cliente'";

        Boolean aux = true;

        try {

            conn = Conexion.conecxionBDD();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.getInt(1) == 0) {

                return aux;

            } else {

                aux = false;
            }
            
            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);

        }

        return aux;

    }

    public String consultar(String tabla) {

        try {
            Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(tabla);

            if (rs.next()) {
                return rs.getString("ID_FACTURA");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";

    }

    public ArrayList<String> consultarProveedores() {

        String query = "SELECT nombre FROM 'main'.'proveedor',factura where proveedor.ruc = factura.id_proveedor group by proveedor.nombre";

        try {
            conn = Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList proveedores = new ArrayList<String>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("nombre");
                proveedores.add(temp);

            }

            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return proveedores;
    }

    public ArrayList<String> consultarClientes() {
        String query = "SELECT nombre FROM 'main'.'cliente'";

        try {
            conn = Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList clientes = new ArrayList<String>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("nombre");
                clientes.add(temp);

            }

            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clientes;
    }

    public ArrayList<String> consultarAnios() {

        String query = "SELECT strftime('%Y',fecha) as Anio from FACTURA group by Anio";

        try {
            conn = Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList anio = new ArrayList<String>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("Anio");
                anio.add(temp);

            }

            cerrarConeccion();

        } catch (SQLException ex) {

            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return anio;

    }

    public ArrayList<String> consultarTipoGastos() {

        String query = "SELECT tipoGasto from detalle group by tipoGasto";

        try {
            conn = Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList anio = new ArrayList<String>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("tipoGasto");
                anio.add(temp);

            }

            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return anio;

    }

    public ArrayList<String> consultarFacturas() throws ClassNotFoundException {

        String query = "SELECT id_factura from factura";

        conn = Conexion.conecxionBDD();

        ArrayList anio = new ArrayList<String>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("id_factura");
                anio.add(temp);

            }

            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return anio;

    }

    public List<String[]> consultarFacturas2() {

        String query = "SELECT proveedor.nombre, id_factura from factura, proveedor where ruc = factura.id_proveedor";

        try {
            conn = Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList resultado = new ArrayList<String>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[2];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);

                resultado.add(temp);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;

    }

    public String tipoGastoAutomatico(String item) {

        try {
            conn2 = Conexion.conecxionBDDTipos();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "select TipoGast from Tipos where NombreProd like '" + item + "%' or NombreProd like '%" + item + "%' or NombreProd like '%" + item + "' order by NombreProd";

        String tipo = "";
        try {
            Statement st = conn2.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String temp = rs.getString("TipoGast");
                tipo = (temp);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion2();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipo;

    }

    public ArrayList<String[]> consultarFacturaPorProveedor(String proveedor) {

        try {
            conn = Conexion.conecxionBDD();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "Select id_factura,cliente.nombre,proveedor.nombre,fecha,total_alimentacion,total_vestimenta,total_vivienda,total_salud,total_educacion,total_otros,total_sin_iva,iva,total_con_iva from proveedor,factura,cliente where id_proveedor=ruc and id_cliente=ci and proveedor.nombre = '" + proveedor + "'";

        ArrayList<String[]> resultado = new ArrayList<String[]>();

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[13];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);
                temp[3] = rs.getString(4);
                temp[4] = rs.getString(5);
                temp[5] = rs.getString(6);
                temp[6] = rs.getString(7);
                temp[7] = rs.getString(8);
                temp[8] = rs.getString(9);
                temp[9] = rs.getString(10);
                temp[10] = rs.getString(11);
                temp[11] = rs.getString(12);
                temp[12] = rs.getString(13);

                resultado.add(temp);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;

    }

    public ArrayList<String[]> consultarFacturaTipoGastos(String tipo_gasto, String nombreCliente) {

        String auxTipo = "";

        if (tipo_gasto.equals("VIVIENDA")) {

            auxTipo = "total_vivienda";

        } else if (tipo_gasto.equals("SALUD")) {

            auxTipo = "total_salud";

        } else if (tipo_gasto.equals("EDUCACIÓN")) {

            auxTipo = "total_educacion";

        } else if (tipo_gasto.equals("ALIMENTACIÓN")) {

            auxTipo = "total_alimentacion";

        } else if (tipo_gasto.equals("VESTIMENTA")) {

            auxTipo = "total_vestimenta";

        } else if (tipo_gasto.equals("OTROS")) {

            auxTipo = "total_otros";

        }

        try {
            conn = Conexion.conecxionBDD();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "SELECT SUM(" + auxTipo + ") from factura, cliente where factura.id_cliente = cliente.ci and cliente.nombre = " + "'" + nombreCliente + "'";

        ArrayList<String[]> resultado = new ArrayList<String[]>();

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[1];

                temp[0] = rs.getString(1);

                resultado.add(temp);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;

    }

    public void cerrarConeccion() throws SQLException {

        conn.close();

    }

    public void cerrarConeccion2() throws SQLException {

        conn2.close();

    }

    public ArrayList<String[]> consultarFacturaPorCliente(String nombre_cliente, String anio) {
        /////
        //FALTA ANADIR EL ANO EN EL QUERY
        ///

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();
            String query = "select factura.id_factura, proveedor.ruc, proveedor.nombre, "
                    + "factura.total_sin_iva, factura.iva, factura.total_con_iva, "
                    + "factura.total_alimentacion, factura.total_vestimenta, factura.total_vivienda, "
                    + "factura.total_salud, factura.total_educacion, factura.total_otros "
                    + "from factura,proveedor,cliente "
                    + "where factura.id_proveedor = proveedor.ruc "
                    + "and factura.id_cliente = cliente.ci "
                    + "and cliente.nombre ='" + nombre_cliente + "' and (SELECT strftime('%Y',fecha) as 'Anio' from FACTURA where factura.id_cliente = cliente.ci) = '" + anio + "'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[12];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);
                temp[3] = rs.getString(4);
                temp[4] = rs.getString(5);
                temp[5] = rs.getString(6);
                temp[6] = rs.getString(7);
                temp[7] = rs.getString(8);
                temp[8] = rs.getString(9);
                temp[9] = rs.getString(10);
                temp[10] = rs.getString(11);
                temp[11] = rs.getString(12);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarFacturaPorProveedor(String cliente, String anio, String prov) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query2 = "select COUNT(factura.id_factura), proveedor.ruc, proveedor.nombre, SUM(factura.total_alimentacion),  SUM(factura.total_vestimenta), SUM(factura.total_vivienda), SUM(factura.total_salud), SUM(factura.total_educacion), SUM(factura.total_otros) from factura,proveedor,cliente where factura.id_proveedor = proveedor.ruc and factura.id_cliente = cliente.ci and cliente.nombre = '" + cliente + "' and proveedor.nombre = '" + prov + "' and (SELECT strftime('%Y',fecha) as 'Anio' from FACTURA where factura.id_cliente = cliente.ci) = '" + anio + "'";

            String query = "select factura.id_factura, proveedor.ruc, proveedor.nombre, factura.total_sin_iva, factura.iva, factura.total_con_iva, "
                    + "factura.total_alimentacion, factura.total_vestimenta, factura.total_vivienda, factura.total_salud, factura.total_educacion, "
                    + "factura.total_otros from factura,proveedor,cliente where factura.id_proveedor = proveedor.ruc and factura.id_cliente = cliente.ci and cliente.nombre = '"
                    + cliente + "' and proveedor.nombre = '" + prov + "' and (SELECT strftime('%Y',fecha) as 'Anio' from FACTURA where factura.id_cliente = cliente.ci) = '" + anio + "'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query2);
            while (rs.next()) {

                String temp[] = new String[12];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);
                temp[3] = rs.getString(4);
                temp[4] = rs.getString(5);
                temp[5] = rs.getString(6);
                temp[6] = rs.getString(7);
                temp[7] = rs.getString(8);
                temp[8] = rs.getString(9);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarFacturaNegocio(String cliente, String anio, String tipo) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT factura.id_factura,factura.fecha,item,proveedor.nombre, cantidad, totalgasto from detalle,factura,proveedor,cliente "
                    + "where detalle.tipogasto = '" + tipo + "'" + "and strftime('%Y',fecha) = '" + anio + "'"
                    + "and cliente.nombre = '" + cliente + "'"
                    + "and detalle.id_factura = factura.id_factura and factura.id_proveedor = proveedor.ruc and factura.id_cliente = cliente.ci group by item";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[6];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);
                temp[3] = rs.getString(4);
                temp[4] = rs.getString(5);
                temp[5] = rs.getString(6);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarInfoProveedor(String factura) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT ruc,nombre,direccion,tipogasto from proveedor,factura where proveedor.ruc = factura.id_proveedor and factura.id_factura = '" + factura + "'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[4];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);
                temp[3] = rs.getString(4);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarInfoCliente(String factura) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT ci,nombre from cliente,factura where cliente.ci = factura.id_cliente and factura.id_factura = '" + factura + "'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[2];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarDetalleProductos(String factura) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT item,cantidad,tipogasto,totalgasto from detalle,factura where detalle.id_factura = factura.id_factura and detalle.id_factura = '" + factura + "' group by item";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String temp[] = new String[4];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);
                temp[3] = rs.getString(4);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarTotalesFactura(String factura) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT distinct total_sin_iva,iva,total_con_iva as Total_Gasto from detalle,factura where detalle.Id_factura = factura.id_factura and detalle.id_factura = '" + factura + "' group by tipogasto";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[3];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarValoresTotalesGastos(String factura) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT total_sin_iva,iva,total_con_iva from factura where id_factura = '" + factura + "'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[3];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public List<String[]> consultarValoresTotalesEspecificos(String factura) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT distinct tipogasto, SUM(totalgasto) as Total from detalle where id_factura = '" + factura + "' group by tipogasto";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[2];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }
    
    public List<String[]> consultarInfoFactura(String factura) {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT id_factura, fecha, tipo_factura from factura where id_factura = '" + factura + "'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[3];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);
                temp[2] = rs.getString(3);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    public ArrayList<String[]> consultarNumeroFacturas(String nombreCliente, String anio) {

        try {
            conn = Conexion.conecxionBDD();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "SELECT COUNT(id_factura) from FACTURA,CLIENTE where factura.id_cliente = cliente.ci and nombre = '"
                + nombreCliente + "' and (SELECT strftime('%Y',fecha) as 'Anio' from FACTURA where factura.id_cliente = cliente.ci) = '" + anio + "'";

        ArrayList<String[]> resultado = new ArrayList<String[]>();

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                String temp[] = new String[1];

                temp[0] = String.valueOf(rs.getInt(1));

                resultado.add(temp);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;

    }

    public String tipoGastoGeneral(String ruc) {

        String query = "select * from 'main'.proveedor where proveedor.ruc ='" + ruc + "'";
        String tipo = "Error";
        try {
            conn = conecxionBDD();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            tipo = rs.getString("tipogasto");

            //CAMBIOOOO
            cerrarConeccion();

            return tipo;

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Error en tipo de gasto general por proveedor ");
        return tipo;

    }

    public boolean isNuevoProveedor(String ruc) {
        String query = "Select count(*) from 'main'.'proveedor' where proveedor.ruc ='" + ruc + "'";

        try {
            conn = Conexion.conecxionBDD();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.getInt(1) == 0) {

                return true;

            } else {

                return false;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Mirar inNuevoProveedor en Conexion.java");
        return true;

    }

    public String consultarProveedor(String query) {
        try {
            Conexion.conecxionBDD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                return rs.getString("TipoGasto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public ArrayList<String[]> consultarCantidadGastos() {

        ArrayList<String[]> resultado = new ArrayList();
        try {

            conn = Conexion.conecxionBDD();

            String query = "SELECT tipogasto, SUM(totalgasto) as Total_Gasto from detalle group by tipogasto";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String temp[] = new String[2];

                temp[0] = rs.getString(1);
                temp[1] = rs.getString(2);

                resultado.add(temp);

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            cerrarConeccion();
            try {
                conecxionBDD();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;

    }

}

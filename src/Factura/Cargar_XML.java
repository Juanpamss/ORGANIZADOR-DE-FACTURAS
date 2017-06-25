/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factura;

import ConexionBDD.Conexion;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Juan Pa
 */
public class Cargar_XML {

    public Cargar_XML() {

    }

    public DatosFactura cargarXml(String name) throws JDOMException, UnsupportedEncodingException, IOException {

        //se instancia el objeto de regreso;
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();

        File xmlFile = new File(name);
        try {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build(xmlFile);
            Conexion cp = new Conexion();

            ArrayList elementos = new ArrayList();

            int cont;
            elementos.add("estado");
            elementos.add("ambiente");
            elementos.add("razonSocial");
            elementos.add("dirMatriz");
            elementos.add("ruc");
            elementos.add("estab");
            elementos.add("ptoEmi");
            elementos.add("secuencial");
            elementos.add("fechaEmision");
            elementos.add("razonSocialComprador");
            elementos.add("identificacionComprador");
            elementos.add("totalSinImpuestos");
            elementos.add("valor");
            elementos.add("importeTotal");
            elementos.add("descripcion");
            elementos.add("precioTotalSinImpuesto");

            //Se obtiene la raiz 'tables'
            Element rootNode = document.getRootElement();

            // Datos sin cabecera
            cont = elementos.indexOf("estado");
            String estado = "";
            if (cont != -1) {
                Element est = (Element) rootNode.getChild(elementos.get(cont).toString());
                if (est != null) {
                    estado = est.getTextTrim();
                }
            }

            cont = elementos.indexOf("ambiente");
            String ambiente = "";
            if (cont != -1) {
                Element amb = (Element) rootNode.getChild(elementos.get(cont).toString());
                if (amb != null) {
                    ambiente = amb.getTextTrim();
                }
            }

            Element tabla = rootNode.getChild("comprobante");

            if (tabla == null) {

                tabla = rootNode;
                tabla = tabla.getChild("autorizaciones").getChild("autorizacion").getChild("comprobante");
                System.out.println(tabla);
            }

            
            String ex = tabla.getText();
            InputStream stream = new ByteArrayInputStream(ex.getBytes("UTF-8"));
            
            Document parse = builder.build(stream);
            tabla = parse.getRootElement();
            System.out.println(tabla.toString());
            String fechaCompleta = tabla.getChildren().get(1).getChildTextTrim("fechaEmision");
            StringTokenizer tk = new StringTokenizer(fechaCompleta, "/");
            String verificarFecha = "";

            while (tk.hasMoreTokens()) {
                verificarFecha = tk.nextToken();
            }

            List lista_campos = tabla.getChildren();
            Element campo;

            Element tributaria = (Element) lista_campos.get(0);

            // Info Tributaria
            cont = elementos.indexOf("razonSocial");
            String nombreEst = "";
            if (cont != -1) {
                nombreEst = tributaria.getChildTextTrim(elementos.get(cont).toString());
            }

            cont = elementos.indexOf("dirMatriz");
            String dirMatriz = "";
            if (cont != -1) {
                dirMatriz = tributaria.getChildTextTrim(elementos.get(cont).toString());
            }

            cont = elementos.indexOf("ruc");
            String ruc = "";
            if (cont != -1) {
                ruc = tributaria.getChildTextTrim(elementos.get(cont).toString());
            }

            cont = elementos.indexOf("estab");
            String estab = "";
            if (cont != -1) {
                estab = tributaria.getChildTextTrim(elementos.get(cont).toString());
            }

            cont = elementos.indexOf("ptoEmi");
            String emision = "";
            if (cont != -1) {
                emision = tributaria.getChildTextTrim(elementos.get(cont).toString());
            }

            cont = elementos.indexOf("secuencial");
            String secuencial = "";
            if (cont != -1) {
                secuencial = tributaria.getChildTextTrim(elementos.get(cont).toString());
            }

            String numFact = estab + "-" + emision + "-" + secuencial;

            
            //Se obtiene la raiz de la factura
            Element factura = (Element) lista_campos.get(1);

            // Info Factura
            cont = elementos.indexOf("fechaEmision");
            String fecha = "";
            if (cont != -1) {
                fecha = factura.getChildTextTrim(elementos.get(cont).toString());
                
                String[] split = fecha.split("/");
                
                String anio,mes,dia;
                
                anio = split[2];
                mes = split[1];
                dia = split[0];
                
                fecha = anio + "-" + mes + "-" + dia;
                
            }

            cont = elementos.indexOf("razonSocialComprador");
            String nombre_cliente = "";
            if (cont != -1) {
                nombre_cliente = factura.getChildTextTrim(elementos.get(cont).toString());
            }

            cont = elementos.indexOf("identificacionComprador");
            String ced_cliente = "";
            if (cont != -1) {
                ced_cliente = factura.getChildTextTrim(elementos.get(cont).toString());
            }

            cont = elementos.indexOf("totalSinImpuestos");
            Double totalSinImp = 0.0;
            if (cont != -1) {
                totalSinImp = Double.parseDouble(factura.getChildTextTrim(elementos.get(cont).toString()));
            }

            cont = elementos.indexOf("importeTotal");
            Double totalFact = 0.0;

            if (cont != -1) {
                totalFact = Double.parseDouble(factura.getChildTextTrim(elementos.get(cont).toString()));

            }

            String facturaQ = "INSERT INTO 'main'.'factura' ('ID_FACTURA','ID_CLIENTE','ID_ENTIDAD','FECHA_EMISION','TOTAL_SIN_IVA','IVA','TOTAL_CON_IVA') VALUES ('"
                    + numFact + "','" + ced_cliente + "','" + ruc + "','" + fecha + "'," + totalSinImp + "," + (totalFact - totalSinImp) + "," + totalFact + ")";

            Element detalles = (Element) lista_campos.get(2);
            List detalle = detalles.getChildren();

            Object datosProducto[][] = new Object[detalle.size()][3];

            for (int j = 0; j < detalle.size(); j++) {

                campo = (Element) detalle.get(j);

                // Detalle
                cont = elementos.indexOf("descripcion");
                String descripcion = "";
                if (cont != -1) {
                    descripcion = campo.getChildTextTrim(elementos.get(cont).toString());
                }

                cont = elementos.indexOf("precioTotalSinImpuesto");
                Double total = 0.0;
                if (cont != -1) {
                    total = Double.parseDouble(campo.getChildTextTrim(elementos.get(cont).toString()));
                }

                if (!descripcion.equals("")) {
                    datosProducto[j][0] = descripcion;
                    datosProducto[j][1] = total;
                    datosProducto[j][2] = "";
                }
            }

            Double iva = totalFact - totalSinImp;

            Double valorIva = (double) Math.round(iva * 100d) / 100d;

            return obtenerDatos(nombreEst, ruc, dirMatriz, ced_cliente, nombre_cliente,
                    numFact, fecha, totalSinImp, valorIva, totalFact,
                    datosProducto
            );
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Error en la lectura de la factura");
        return null;

    }
    
    private String getStringElement(Element rootNode,String child){
        return rootNode.getChild(child).getTextTrim();
    }

    DatosFactura obtenerDatos(
            String prov_nombre,
            String prov_ruc,
            String prov_dir,
            String cliente_ci,
            String cliente_nombre,
            String fac_cod,
            String fac_fecha,
            double fac_total_sin_iva,
            double fac_iva,
            double fac_total_con_iva,
            Object[][] datosProductos
    ) {

        DatosFactura datos = new DatosFactura();

        datos.setProveedor_nombre(prov_nombre);
        datos.setProveedor_ruc(prov_ruc);
        datos.setProveedor_direccion(prov_dir);
        //datos.setProveedor_ciudad(prov_ciudad);

        datos.setCliente_ci(cliente_ci);
        datos.setCliente_nombre(cliente_nombre);

        datos.setFactura_codigo(fac_cod);
        datos.setFactura_fecha(fac_fecha);
        datos.setFactura_total_sin_iva(fac_total_sin_iva);
        datos.setFactura_iva(fac_iva);
        datos.setFactura_total_con_iva(fac_total_con_iva);
        //datos.setTotal_alimentacion(fac_total_sin_iva);

        datos.setDatosProductos(datosProductos);

        return datos;
    }

}

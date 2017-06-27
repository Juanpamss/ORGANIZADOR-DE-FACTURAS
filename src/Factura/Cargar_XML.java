/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factura;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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
            //Se obtiene la raiz 'tables'
            Element rootNode = document.getRootElement();
            // Datos sin cabecera  
            boolean band = true;
            Element tabla = rootNode.getChild("comprobante");
            if (tabla == null) {
                try {
                    tabla = rootNode.getChild("autorizaciones").getChild("autorizacion").getChild("comprobante");
                } catch (Exception e) {
                    tabla = rootNode;
                    band = false;   
                }
            }

            if (band) {
                String ex = tabla.getText();
                InputStream stream = new ByteArrayInputStream(ex.getBytes("UTF-8"));
                Document parse = builder.build(stream);
                tabla = parse.getRootElement();
            }

            List lista_campos = tabla.getChildren();
            HashMap<String, String> atributos = new HashMap<>();

//-----------------------infoTributaria--------------       
            String infoTributaria[] = {"razonSocial", "dirMatriz", "ruc", "estab", "ptoEmi", "secuencial"};
            for (String infoTribArray1 : infoTributaria) {
                atributos.put(infoTribArray1, getElement((Element) lista_campos.get(0), infoTribArray1));
            }

//-----------------------infoFactura--------------            
            atributos.put("fechaEmision", getFechaEmision((Element) lista_campos.get(1)));
            String infoFactura[] = {"razonSocialComprador", "identificacionComprador", "totalSinImpuestos", "importeTotal"};
            for (String string : infoFactura) {
                atributos.put(string, getElement((Element) lista_campos.get(1), string));
            }

//-----------------------detalles--------------
            Element detalles = (Element) lista_campos.get(2);
            List detalle = detalles.getChildren();
            Object datosProducto[][] = new Object[detalle.size()][3];
            Element campo;
            for (int j = 0; j < detalle.size(); j++) {
                campo = (Element) detalle.get(j);
                // Detalle
                String descripcion = getElement(campo, "descripcion");
                Double total = Double.parseDouble(getElement(campo, "precioTotalSinImpuesto"));
                if (!descripcion.equals("")) {
                    datosProducto[j][0] = descripcion;
                    datosProducto[j][1] = total;
                    datosProducto[j][2] = "";
                }
            }
            //calculo de IVA
            Double totalFact = Double.parseDouble(atributos.get("importeTotal")), totalSinImp = Double.parseDouble(atributos.get("totalSinImpuestos"));
            Double iva = totalFact - totalSinImp;
            Double valorIva = (double) Math.round(iva * 100d) / 100d;

            //retorno Datos
            return obtenerDatos(atributos.get("razonSocial"), atributos.get("ruc"), atributos.get("dirMatriz"), atributos.get("identificacionComprador"), atributos.get("razonSocialComprador"),
                    atributos.get("estab") + "-" + atributos.get("ptoEmi") + "-" + atributos.get("secuencial"), atributos.get("fechaEmision"), totalSinImp, valorIva, totalFact,
                    datosProducto
            );
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Error en la lectura de la factura");
        return null;

    }

    private String getElement(Element rootNode, String child) {
        return rootNode.getChild(child).getTextTrim();

    }

    private String getFechaEmision(Element rootNode) {
        String fecha = rootNode.getChildTextTrim("fechaEmision");
        String[] split = fecha.split("/");
        return split[2] + "-" + split[1] + "-" + split[0];
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

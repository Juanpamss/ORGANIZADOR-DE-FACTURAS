/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_libres;

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

public void cargarXml(String name) throws JDOMException, UnsupportedEncodingException, IOException {
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
            elementos.add("totalSinImpuestos");
            elementos.add("valor");
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

            if (tabla != null) {
                String ex = tabla.getText();

                InputStream stream = new ByteArrayInputStream(ex.getBytes("UTF-8"));
                Document parse = builder.build(stream);

                tabla = parse.getRootElement();
            } else {
                tabla = rootNode;
            }

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

                
                /*String establecimiento = "INSERT INTO ESTABLECIMIENTO (id_establecimiento,nombre_establecimiento,direccion_establecimiento)"
                            + "VALUES ('" + ruc + "','" + nombreEst + "','" + dirMatriz + "')";
                //cp.insertar(establecimiento);*/
                

                //Se obtiene la raiz de la factura
                Element factura = (Element) lista_campos.get(1);

                // Info Factura
                cont = elementos.indexOf("fechaEmision");
                String fecha = "";
                if (cont != -1) {
                    fecha = factura.getChildTextTrim(elementos.get(cont).toString());
                }

                cont = elementos.indexOf("totalSinImpuestos");
                Double totalSinImp = 0.0;
                if (cont != -1) {
                    totalSinImp = Double.parseDouble(factura.getChildTextTrim(elementos.get(cont).toString()));
                }

                List totalConImp = factura.getChild("totalConImpuestos").getChildren();
                Element totalImp = (Element) totalConImp.get(0);

                cont = elementos.indexOf("valor");
                Double Imps = 0.0;
                if (cont != -1) {
                    Imps = Double.parseDouble(totalImp.getChildTextTrim(elementos.get(cont).toString()));
                }

                Double totalConImps = totalSinImp + Imps;

                
                String facturaQ = "INSERT INTO 'main'.'factura' ('ID_FACTURA','ID_CLIENTE','ID_ENTIDAD','FECHA_EMISION','TOTAL_SIN_IVA','IVA','TOTAL_CON_IVA') VALUES ('" 
                        + numFact + "','1004700685','" + ruc + "','"  + fecha + "'," + totalSinImp + "," + Imps + "," + totalConImps + ")";
                    cp.insertar(facturaQ);

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

                    /*if (datosProducto.length != 0) {
                        if (tipo.equals("Personal")) {
                            SeleccionarTipoGastoPersonal seleccionarP = new SeleccionarTipoGastoPersonal(cp, datosProducto, numFact, anio, cedulaCli, tipo);
                            seleccionarP.setVisible(true);
                        } else {
                            SeleccionarTipoGastoNegocios seleccionarH = new SeleccionarTipoGastoNegocios(cp, datosProducto, numFact, anio, cedulaCli, tipo);
                            seleccionarH.setVisible(true);
                        }
                    }*/
                
                           
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
    }    

}

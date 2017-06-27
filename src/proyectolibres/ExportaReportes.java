/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolibres;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
//import static com.itextpdf.text.ElementTags.FONT;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.html.HtmlTags.FONT; //Libreria cambiada por la com.itextpdf.text.ElementTags.FONT;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jairo
 */
public class ExportaReportes {
    
    String fecha;
    public void generar(String path,String[] header,Object [][]matriz,String[] tema){
         Date date= new Date();   
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/YYYY");   
        fecha = formato.format(date);
        
      generarPDF(path,header,matriz,tema);
      generarCALC(path,header,matriz,tema);
        
    }

    private void generarPDF(String path,String[] header, Object[][] matriz, String[] tema) {
        try {
            FileOutputStream archivo = new FileOutputStream(path+"Reporte"+fecha.replace("/","-")+tema[1]+".pdf");
            Document doc = new Document(PageSize.A4.rotate());
           String encabezado="";
           encabezado+="Reporte por "+tema[0]+"\n\n";
           encabezado+="Año:        "+tema[2]+"\n";
           encabezado+="Cliente:    "+tema[1]+"\n";
           if(!tema[3].equals("khe"))
           encabezado+="Proveedor:  "+tema[3]+"\n";
           encabezado+="Fecha:      "+fecha;
           encabezado+="\n\n";
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            
            doc.add(new Paragraph(encabezado));
            doc.add(tablaDesglocePDF(header, matriz));
                     
            doc.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarCALC(String path,String[] header, Object[][] matriz, String[] tema) {
       String salida="\tReporte por "+tema[0]+"\n";
       salida+="Anio:\t"+tema[2]+"\n";
       salida+="Cliente:\t"+tema[1]+"\n";
       if(!tema[3].equals("khe"))
       salida+="Proveedor\t"+tema[3]+"\n\n";
       salida+="Fecha\t"+fecha;
       salida+="\n\n";
  
       
        for (int i = 0; i < header.length; i++) {
            salida+="\t"+header[i];
        }
        salida+="\n";
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                salida+="\t"+matriz[i][j];
            }
            salida+="\n";
        }
        
        String arch=path+"Reporte"+fecha.replace("/","-")+tema[1]+".xls";
        try {
            PrintWriter pw = new PrintWriter(arch);
            pw.write(salida);
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
      
       
    }

    private PdfPTable tablaDesglocePDF(String[] cabecera, Object[][] datos) {
       PdfPTable table = new PdfPTable(cabecera.length);
        // the cell object
       
        Font f = FontFactory.getFont(FONT, "Cp1250", true);
        f.setSize(5f);
      
        for(String cab:cabecera){
            PdfPCell cell= new PdfPCell(new Phrase(cab,f));
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[0].length; j++) {
                 PdfPCell cell= new PdfPCell(new Phrase((String)datos[i][j],f));
                   cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
    }
        return table;
    }
    
}

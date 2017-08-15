/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolibres;

import com.itextpdf.testutils.ITextTest;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
       salida+="Fecha de generacion:\t"+fecha;
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

    void generarReporteFactura(String path, ArrayList<SeccionReporte> datosExportar) {
      this.generarReporteFacturaPDF(path,datosExportar);
      this.generarReporteFacturaExcel(path,datosExportar);
    }
    
    void generarReporteFacturaExcel(String path, ArrayList<SeccionReporte> datosExportar){
      /* String salida="\tReporte por de factura\n";
       
     
       salida+="\n\n";
  
       
//        for (int i = 0; i < header.length; i++) {
//            salida+="\t"+header[i];
//        }
//        salida+="\n";
//        for (int i = 0; i < matriz.length; i++) {
//            for (int j = 0; j < matriz[0].length; j++) {
//                salida+="\t"+matriz[i][j];
//            }
//            salida+="\n";
//        }
        
        for(SeccionReporte sr:datosExportar){
            salida+="\t"+sr.tema;
            salida+="\n";
            for (String s : sr.cabezera) {
                salida+="\t"+s;
            }
            salida+="\n";
            for (int i = 0; i < sr.datos.length; i++) {
                for (int j = 0; j < sr.datos[0].length; j++) {
                    salida+="\t"+sr.datos[i][j];
                    
                }
                salida+="\n";
            }
            
            salida+="\n";
            
        }
                
        
        String arch=path+"Reporte"+"Excel.xls";
        try {
            PrintWriter pw = new PrintWriter(arch);
            pw.write(salida);
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } 
       */
   
        String arch=path+"Reporte"+"Excel.xls";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        
        int rowNum = 0;
        
        Row rowtitulo = sheet.createRow(rowNum++);
        Cell celltitulo = rowtitulo.createCell(1);
        celltitulo.setCellValue("Reporte de Factura");
        
        
        for (SeccionReporte sr : datosExportar){
            rowNum+=3;
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0);
            String temaGrupo=sr.tema.replaceAll("\n", "");
            cell.setCellValue(temaGrupo);
            System.out.println(temaGrupo);
            cell.setCellStyle(style);
            int colNum=0;
            row = sheet.createRow(rowNum++);
            for(String cabezera:sr.cabezera){                
                 cell = row.createCell(colNum++);
                 cell.setCellValue(cabezera);
                 cell.setCellStyle(style);
            }
            
            
            for(int i=0;i<sr.datos.length;i++){
                colNum=0;
                row=sheet.createRow(rowNum++);
                for (int j = 0; j < sr.datos[0].length; j++) {
                    cell = row.createCell(colNum++);
                    cell.setCellValue(sr.datos[i][j]+"");
                    cell.setCellStyle(style);
                }
            }
            
            
        }
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 600);
        }
        
            FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(arch);
              workbook.write(outputStream);
            workbook.close();
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
    }
    
    void generarReporteFacturaPDF(String path, ArrayList<SeccionReporte> datosExportar){
          try {
            System.out.println("hola 1"+path+"\t"+Arrays.toString(datosExportar.toArray()));
            FileOutputStream archivo = new FileOutputStream(path+"Reporte de factura"+".pdf");
            Document doc = new Document(PageSize.A4.rotate());
           String encabezado="";
           encabezado+="Reporte de Factura\n\n";
           System.out.println("Hola x2");
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            
            doc.add(new Paragraph(encabezado));
           
            for(SeccionReporte sr : datosExportar){
                doc.add(new Paragraph(sr.tema+"\n"));
               
                doc.add(tablaDesglocePDF(sr.cabezera, sr.datos));
                 doc.add(new Paragraph("\n\n"));
            }
                     
            doc.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void generarReporteGastos(String path, ArrayList<SeccionReporte> datosExportar) {
        this.generarReporteGastosPDF(path,datosExportar);
      this.generarReporteGastosExcel(path,datosExportar);
    }

    private void generarReporteGastosExcel(String path, ArrayList<SeccionReporte> datosExportar) {
         String arch=path+"Reporte"+"Gastos.xls";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        
        int rowNum = 0;
        
        Row rowtitulo = sheet.createRow(rowNum++);
        Cell celltitulo = rowtitulo.createCell(1);
        celltitulo.setCellValue("Reporte de Gastos");
        
        
        for (SeccionReporte sr : datosExportar){
            rowNum+=3;
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0);
            String temaGrupo=sr.tema.replaceAll("\n", "");
            cell.setCellValue(temaGrupo);
            System.out.println(temaGrupo);
            cell.setCellStyle(style);
            int colNum=0;
            row = sheet.createRow(rowNum++);
            for(String cabezera:sr.cabezera){                
                 cell = row.createCell(colNum++);
                 cell.setCellValue(cabezera);
                 cell.setCellStyle(style);
            }
            
            
            for(int i=0;i<sr.datos.length;i++){
                colNum=0;
                row=sheet.createRow(rowNum++);
                for (int j = 0; j < sr.datos[0].length; j++) {
                    cell = row.createCell(colNum++);
                    cell.setCellValue(sr.datos[i][j]+"");
                    cell.setCellStyle(style);
                }
            }
            
            
        }
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 600);
        }
        
            FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(arch);
              workbook.write(outputStream);
            workbook.close();
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
    }

    private void generarReporteGastosPDF(String path, ArrayList<SeccionReporte> datosExportar) {
          try {
          
            FileOutputStream archivo = new FileOutputStream(path+"Reporte de Gastos"+".pdf");
            Document doc = new Document(PageSize.A4.rotate());
           String encabezado="";
           encabezado+="Reporte de Gastos\n\n";
           System.out.println("Hola x2");
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            
            doc.add(new Paragraph(encabezado));
           
            for(SeccionReporte sr : datosExportar){
                doc.add(new Paragraph(sr.tema+"\n"));
               
                doc.add(tablaDesglocePDF(sr.cabezera, sr.datos));
                 doc.add(new Paragraph("\n\n"));
            }
                     
            doc.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ExportaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

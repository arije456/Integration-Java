
package Services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import Tools.connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entities.Activite;
import java.sql.Statement;


public class GenaratePDF {
    
    
   public static void main(String[] args){
       Activite a= new Activite();
       Connection mc =connexion.getInstance().getCnx();
       ResultSet rs;
       String file_name="C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Services\\test.pdf";
      // AreaBreak aB = new AreaBreak();  
       try {
           Statement ste=  mc.createStatement();
           rs= ste.executeQuery("SELECT *From activite");
           Document doc= new Document();
           PdfWriter.getInstance(doc, new FileOutputStream(file_name));
           
           doc.open();
           PdfPTable table_act = new PdfPTable(4);
           PdfPCell table_cell;

           while(rs.next()){
               String ida= (String.valueOf(rs.getInt("id_a ")));
               table_cell=new PdfPCell(new Phrase(ida));
               table_act.addCell(table_cell);
               
               String nom= rs.getString("nom_a");
               table_cell=new PdfPCell(new Phrase(nom));
               table_act.addCell(table_cell);
               
               String cat= (String.valueOf(rs.getInt("cat_age")));
               table_cell=new PdfPCell(new Phrase(cat));
               table_act.addCell(table_cell);
               
               String type= rs.getString("type");
               table_cell=new PdfPCell(new Phrase(type));
               table_act.addCell(table_cell);
               
               String ide = (String.valueOf(rs.getInt("id_enfant")));
               table_cell=new PdfPCell(new Phrase(ide));
               table_act.addCell(table_cell);
               
               
           }
           doc.add(table_act);
           doc.close(); 
           
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
       } 
               
       /*
       try {
            String file_name="C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Services\\test.pdf";
            Document document = new Document();
            
            //Simple Paragraphe
           PdfWriter.getInstance(document, new FileOutputStream(file_name)); 
           document.open();
         //  Paragraph par= new Paragraph("This is testing");
           //document.add(par);
           
           
           //add Table
           PdfPTable table = new PdfPTable(3);
          // PdfPCell c1= new PdfPCell(new Phrase("Heading1");
          PdfPCell c1 = new PdfPCell(new Phrase("Nom"));
           table.addCell(c1);
           
            c1 = new PdfPCell(new Phrase("Cat_Age"));
           table.addCell(c1);
           
           c1 = new PdfPCell(new Phrase("Type"));
           table.addCell(c1);
           table.setHeaderRows(2);
           
           table.addCell("1.0");
           table.addCell("1.1");
           table.addCell("1.2");
           table.addCell("2.1");
           table.addCell("2.2");
           table.addCell("2.3");
           document.add(table);
           
           //Image
           document.add(Image.getInstance("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assets\\logo_epicode.png"));
           
           System.out.println("finished");
           
           document.close();
       } catch (Exception ex) {
              System.out.println(ex.getMessage());
       }
      */
       /*
       try {
            String file_name="C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Services\\test.pdf";
            Document document = new Document();
           PdfWriter.getInstance(document,new FileOutputStream(file_name));
           document.open();
           
           
                
                
                String query= "select nom_a,cat_age,type from activite";
                ste= mc.prepareStatement(query);
                rs= ste.executeQuery();
                   
                while(rs.next())
                {
                    PdfPTable table = new PdfPTable(3);
                    
                   PdfPCell c1 = new PdfPCell(new Phrase("Nom"));
                   table.addCell(a.getNom_a());
           
            c1 = new PdfPCell(new Phrase("Cat_Age"));
           table.addCell(String.valueOf(a.getCat_age()));
           
           c1 = new PdfPCell(new Phrase("Type"));
           table.addCell(a.getType());
           table.setHeaderRows(2);
           document.add(table);
                   //Paragraph par = new Paragraph(rs.getString("nom_a")+ " "+rs.getInt("cat_age")+" "+rs.getString("type"));
                   //document.add(par);
                   //document.add(new Paragraph(" "));
                }
                   } catch (Exception ex) {
                     System.out.println(ex.getMessage());
                   } */
               
   
   }}


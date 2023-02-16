
package Services;
import Entities.Enfant;
import Tools.connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interfaces.IEnfantServices;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;





import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSetMetaData;

public class EnfantServices implements IEnfantServices<Enfant>{
     Connection mc;
    PreparedStatement ste;

    public EnfantServices() {
         mc=connexion.getInstance().getCnx();
    }
    
    // Fonction AJOUTER
     @Override
    public void ajouterEnfant(Enfant e)
    {
      //enfant
        String sql ="insert into enfant(nom,prenom,age,sexe,photo,id_a) Values(?,?,?,?,?,?)";
        
        try{
            ste=mc.prepareStatement(sql);
            ste.setString(1, e.getNom());
           ste.setString(2,e.getPrenom());
           ste.setInt(3,e.getAge());
           ste.setString(4,e.getSexe());
           ste.setString(5,e.getPhoto());
           ste.setInt(6,e.getId_a());
           ste.executeUpdate();
           System.out.println("Enfant Ajouté avec succés !");
        }
        catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
    }
     @Override
    public List<Enfant> afficherEnfant()
    {
        List<Enfant> enfants =  new ArrayList<>();
        String sql="select * from enfant";
        
        try{
            ste=mc.prepareStatement(sql);
          
          ResultSet rs=ste.executeQuery();
          
          while(rs.next()){
                      Enfant e = new Enfant();
                      e.setId_enfant(rs.getInt("id_enfant"));
                      e.setNom(rs.getString("nom"));
                      e.setPrenom(rs.getString("prenom"));
                      e.setAge(rs.getInt("age"));
                      e.setSexe(rs.getString("sexe"));
                      e.setPhoto(rs.getString("photo"));
                      e.setId_a(rs.getInt("id_a"));
                      enfants.add(e);
                      System.out.println("ID_enfant : "+e.getId_enfant()+"\n Nom : "+e.getNom()+"\n Prenom : "+e.getPrenom()+"\n Age : "
                              +e.getAge()+"\n Sexe: "+e.getSexe()+"\n Photo: "+e.getPhoto()+"Id Activité: "+e.getId_a());
                      //System.out.println("Afficher avec succés !");
                  }
        }
        catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
      return enfants;
    }
    
     @Override
     public void supprimerEnfant(int id){
        String sql = "DELETE from Enfant where id_enfant= '"+id+"' "; 
        try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println("Enfant supprimé avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
       }
     }
     @Override
     public void modifierEnfant(int id, String nom,String prenom, int age, String sexe, String image, int ida){
       String sql= "UPDATE enfant SET nom='"+nom+"',prenom= '"+prenom+"' ,age= '"+age+"',sexe='"+sexe+"',photo='"+image+"' ,id_a='"+ida+"' where id_enfant = '"+id+"'";
       try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println(" enfant modifié avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
     
     //------------------------------- Liste 2 --------------------------------------------------------------------//
    public List <Enfant> liste2()
    {
        String sql = "select id_enfant, nom,prenom,age,sexe,photo,id_a from enfant";
        
       List <Enfant> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Enfant(rs.getInt("id_enfant"),rs.getString("nom"),rs.getString("prenom"),rs.getInt("age"),rs.getString("sexe"),rs.getString("photo"),rs.getInt("id_a")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
    
     //--------------------------------- getActiviteList() ------------------------------------------------------//
    public ObservableList<Enfant> getEnfantList() throws SQLException
    {
         String sql = "select id_enfant,nom,prenom,age,sexe,photo,id_a from enfant";
        ObservableList<Enfant> EnfantList = FXCollections.observableArrayList();
        
        List <Enfant> list_e = new ArrayList<>(); 
        Statement st= mc.createStatement();
        
        
        ResultSet rs;
        rs = st.executeQuery(sql);
        Enfant enfants;
        while (rs.next()) {
           enfants=new Enfant(rs.getInt("id_enfant"),rs.getString("nom"),rs.getString("prenom"),rs.getInt("age"),rs.getString("sexe"),rs.getString("photo"),rs.getInt("id_a"));  
            //System.out.println(events);
            EnfantList.add(enfants);

        }
         return EnfantList;    
    }
     
     //------------------------------ Display By ID ----------------------------------------//
     
    
    public  List <Enfant> displayByID(int id) {
    List <Enfant> myList= new ArrayList<>();

    String req="select * from enfant where id_enfant='"+id+"' ";

    try {
        Statement st = mc.createStatement();
        ResultSet rs=st.executeQuery(req);
        while(rs.next()){
        
                      Enfant a =new Enfant();
                      a.setId_enfant(rs.getInt("id_enfant"));
                      a.setNom(rs.getString("nom"));
                      a.setPrenom(rs.getString("prenom"));
                      a.setAge(rs.getInt("age"));
                      a.setSexe(rs.getString("sexe"));
                      a.setPhoto(rs.getString("photo"));
                      a.setId_a(rs.getInt("id_a"));
                      myList.add(a);

        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
}
    
    //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNomEbyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from enfant where id_enfant=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("nom");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
   
  //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getPrenomEbyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from enfant where id_enfant=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("prenom");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
       //----------------------------------- Display Age by ID -----------------------------------------------------------------// 
    public Integer getAgebyId(int id){
        try {
            ste = mc.prepareStatement("select * from enfant where id_enfant=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("age");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
    
    //----------------------------------- Display Sexe by ID -----------------------------------------------------------------//
    public String getSexebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from enfant where id_enfant=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("sexe");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
     //----------------------------------- Display Photo by ID -----------------------------------------------------------------//
    public String getPhotobyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from enfant where id_enfant=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("photo");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
     //----------------------------------------  Display ID_User by ID --------------------------------------------------------------//
     public Integer getID_UserbyId(int id){
        try {
            ste = mc.prepareStatement("select * from enfant where id_enfant=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("id_a");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
     
     //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Enfant a ,int id) {
           try {
              /*ste =mc.prepareStatement(
                      "UPDATE produit SET photo = ?, poids = ?," + " prix = ?, desription = ?, categorie = ?, " + " libelle = ? WHERE id = ?");*/
              
             // ste= mc.prepareStatement("UPDATE activite SET nom_a= '"+a.getNom_a()+"' ,cat_age= ''"+a.getCat_age()+"' , " + " type= '"+a.getType()+"'," + " id_enfant='"+a.getId_enfant()+"' where id_a =?");
              
              ste= mc.prepareStatement("UPDATE enfant SET nom=? ,prenom=? ,age=? ,sexe=? ,photo=?, id_a=? where id_enfant =?");
              System.out.println(ste);
     ste.setString(1, a.getNom());
     ste.setString(2, a.getPrenom());
     ste.setInt(3,a.getAge());
     ste.setString(4,a.getSexe());
      ste.setString(5,a.getPhoto());
      ste.setInt(6,a.getId_a());
      ste.setInt(7, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     
     //----------------------------------- Nb Enfant -----------------------------------------------------------//
     public String calculer_nbAct(String Nom) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM enfant where nom='"+Nom+"'"; 
        try {
           
           Statement st =mc.createStatement();
           ResultSet rs=st.executeQuery(requete);
           if (rs.next()){
          String chaine = String.valueOf(rs.getString(1));
          l=chaine;
            return l;}
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
      return l;
    }
     
     //---------------------------------------- Excel -----------------------------------------------------------//
     
     public void getDefendants( String db) throws Exception  { 
        
        
        @SuppressWarnings("unused")
        Workbook rbook = WorkbookFactory.create(new FileInputStream("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\test2.xls") );
        @SuppressWarnings("resource")
        Workbook writeWorkbook = (Workbook) new HSSFWorkbook();
        Sheet desSheet = writeWorkbook.createSheet("new sheet");

        Statement stmt = null;
        ResultSet rs = null;
        try{
            String query ="SELECT * FROM enfant"+db;

            stmt = mc.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            Row desRow1 = desSheet.createRow(0);
            for(int col=0 ;col < columnsNumber;col++) {
                Cell newpath = desRow1.createCell(col);
                newpath.setCellValue(rsmd.getColumnLabel(col+1));
            }
            while(rs.next()) {
                System.out.println("Row number" + rs.getRow() );
                Row desRow = desSheet.createRow(rs.getRow());
                for(int col=0 ;col < columnsNumber;col++) {
                    Cell newpath = desRow.createCell(col);
                    newpath.setCellValue(rs.getString(col+1));  
                }
                FileOutputStream fileOut = new FileOutputStream("C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\test2.xls");
                writeWorkbook.write(fileOut);
                fileOut.close();
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get data from database");
        }
    }
}


package Services;
import Entities.Commentaire;
import Tools.connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommentaireService {
     Connection mc;
    PreparedStatement ste;

    public CommentaireService() {
         mc=connexion.getInstance().getCnx();
    }
    
   //---------------------------------------------------- Ajouter Commentaire --------------------------------------------//
      public void ajouterCommentaire(Commentaire c1){
        String sql ="insert into commentaire(Nom_c,Email,Message,Date,Nom_article,Id_b) Values(?,?,?,?,?,?)";
        try {
            ste=mc.prepareStatement(sql);
            ste.setString(1, c1.getNom_c());
            ste.setString(2,c1.getEmail());
            ste.setString(3, c1.getMessage());
            ste.setString(4, c1.getDate());
            ste.setString(5, c1.getNom_article());
            ste.setInt(6, c1.getId_b());
            ste.executeUpdate();
            System.out.println("Commentaire Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
      //--------------------------------------------- Modifier paramétré ------------------------------------------------------//
      public void updateCommentaire(int Id_com, String Nom_c, String Email, String Message, String Date, String Nom_article){
       String sql= "UPDATE commentaire SET Nom_c='"+Nom_c+"',Email= '"+Email+"',Message='"+Message+"',Date='"+Date+"',Nom_article='"+Nom_article+"'where Id_com = '"+Id_com+"'";
       try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println(" commentaire modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
      
   //------------------------------------------- afficherCommentaire -------------------------------------------------------------//
      public List<Commentaire> afficherCommentaire(){
        List<Commentaire> Commentaires= new ArrayList<>();
        String sql="select * from commentaire";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Commentaire c= new Commentaire();
                c.setId_com(rs.getInt("Id_com"));
                c.setNom_c(rs.getString("Nom_c"));
                c.setEmail(rs.getString("Email"));
                c.setMessage(rs.getString("Message"));
                c.setDate(rs.getString("Date"));
                c.setNom_article(rs.getString("Nom_article"));
                c.setId_b(rs.getInt("Id_b"));
                Commentaires.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return Commentaires;
    } 
      
      //------------------------------------------- supprimerCommentaire --------------------------------------------------------------//
      public void supprimerCommentaire(int Id_com){
        String sql = "DELETE from commentaire where Id_com= '"+Id_com+"' "; 
        try{
           Statement ste= mc.createStatement();
           ste.executeUpdate(sql);
           System.out.println("commentaire supprimée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
      
      //---------------------------------------------------------- afficherCommentaire1 (Trier)---------------------------------------------//
      public List<Commentaire> afficherCommentaire1(){
        List<Commentaire> Commentaires= new ArrayList<>();
        String sql="select * from commentaire order by Nom_c";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Commentaire c= new Commentaire();
                c.setId_com(rs.getInt("Id_com"));
                c.setNom_c(rs.getString("Nom_c"));
                c.setEmail(rs.getString("Email"));
                c.setMessage(rs.getString("Message"));
                c.setDate(rs.getString("Date"));
                c.setNom_article(rs.getString("Nom_article"));
                c.setId_b(rs.getInt("Id_b"));
                
                Commentaires.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return Commentaires;
    } 
      
 //----------------------------------------------------- afficherCommentairerecherche(int Id_com) ---------------------------------------------//
      public List<Commentaire> afficherCommentairerecherche(int Id_com){
        List<Commentaire> Commentaires= new ArrayList<>();
        String sql="select * from commentaire where Id_com= '"+Id_com+"' " ; 
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Commentaire c= new Commentaire();
                c.setId_com(rs.getInt("Id_com"));
                c.setNom_c(rs.getString("Nom_c"));
                c.setEmail(rs.getString("Email"));
                c.setMessage(rs.getString("Message"));
                c.setDate(rs.getString("Date"));
                c.setNom_article(rs.getString("Nom_article"));
                 c.setId_b(rs.getInt("Id_b"));
                
                Commentaires.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return Commentaires;
    } 
      
  //------------------------------- Liste 2 --------------------------------------------------------------------//
    public List <Commentaire> liste2()
    {
        String sql = "select Id_com, Nom_c, Email, Message, Date, Nom_article, Id_b from commentaire";
        
       List <Commentaire> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Commentaire(rs.getInt("Id_com"),rs.getString("Nom_c"),rs.getString("Email"),rs.getString("Message"),rs.getString("Date"),rs.getString("Nom_article"),rs.getInt("Id_b")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
    
    //---------------------------------------- getCommentaireList() ---------------------------------------------------------------//
     public ObservableList<Commentaire> getCommentaireList() throws SQLException
    {
        ObservableList<Commentaire> Commentairelist = FXCollections.observableArrayList();
        
        List <Commentaire> listb = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select Id_com, Nom_c, Email, Message, Date, Nom_article, Id_b from commentaire";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Commentaire Commentaires;
        while (rs.next()) {
           Commentaires= new Commentaire(rs.getInt("Id_com"),rs.getString("Nom_c"),rs.getString("Email"),rs.getString("Message"),rs.getString("Date"),rs.getString("Nom_article"),rs.getInt("Id_b")); 
            //System.out.println(events);
            Commentairelist.add(Commentaires);

        }
         return Commentairelist;    
    }

   //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNombyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from commentaire where Id_com=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Nom_c");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
       //----------------------------------- Display Cat_age by ID -----------------------------------------------------------------// 
    public String getEmailbyId(int id){
        try {
            ste = mc.prepareStatement("select * from commentaire where Id_com=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("Email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
    
    //----------------------------------------  Display Type by ID --------------------------------------------------------------//
     public String getMessagebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from commentaire where Id_com=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Message");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     
      public String getDatebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from commentaire where Id_com=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Date");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
      
               public String getNom_articlebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from commentaire where Id_com=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Nom_article");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     
     //----------------------------------------  Display ID_Enfant by ID --------------------------------------------------------------//
     public Integer getId_bbyId(int id){
        try {
            ste = mc.prepareStatement("select * from commentaire where Id_com=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Id_b");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
     
     //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Commentaire c ,int id) {
           try {
             
              
              ste= mc.prepareStatement("UPDATE commentaire SET Nom_c=? ,Email=? ,Message=?,Date=?,Nom_article=?,Id_b=? where Id_com =?");
              System.out.println(ste);
     ste.setString(1, c.getNom_c());
     ste.setString(2,c.getEmail());
     ste.setString(3,c.getMessage());
      ste.setString(4,c.getDate());
      ste.setString(5,c.getNom_article());
      ste.setInt(6,c.getId_b());
      ste.setInt(7, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     
     public String calculer_nbseance(String Date) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM commentaire where Date='"+Date+"'"; 
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
}

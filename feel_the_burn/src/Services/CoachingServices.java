
package Services;
import Entities.Coaching;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Tools.connexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CoachingServices {
    Connection mc;
    PreparedStatement ste;

    public CoachingServices() {
          mc=connexion.getInstance().getCnx();
    }
    
    //------------------------------------------------------ Ajouter ------------------------------------------------------------------//
      public void ajouterCoaching(Coaching co){
        String sql;
        sql = "insert into coaching(Date_S,Prix,Id_Co,Nom_User,Prenom_User) Values(?,?,?,?,?)";
        try {
            ste=mc.prepareStatement(sql);
            if (co.getPrix()>0){
            ste.setString(1, co.getDate_S());
            ste.setFloat(2, co.getPrix());
            ste.setInt(3, co.getId_Co());
            ste.setString(4, co.getNom_User());
            ste.setString(5, co.getPrenom_User());
            
            ste.executeUpdate();
            System.out.println("Coaching Ajouté");
            }
            else System.out.println("le prix ne peut pas étre negatif");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
           }
      
      //---------------------------------------- Afficher -----------------------------------------------------------//
      public List<Coaching> afficherCoaching(){
        List<Coaching> coachings = new ArrayList<>();
        String sql="select * from coaching";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Coaching co =new Coaching();
                co.setId_S(rs.getInt("Id_S"));
                co.setDate_S(rs.getString("Date_S"));
                co.setPrix(rs.getFloat("Prix"));
                 co.setId_Co(rs.getInt("Id_Co"));
                 co.setId_Co(rs.getInt("Id_Co"));
                 co.setNom_User(rs.getString("Nom_User"));
                 co.setPrenom_User(rs.getString("Prenom_User"));
                 
                 
                 
                coachings.add(co);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return coachings;
    }
   //---------------------------------------------  Updatecoaching ----------------------------------------------------//
      public void Updatecoaching(Coaching co){
         String sql = "UPDATE coaching SET Date_s=?, Prix=?, Id_Co=?, Nom_User=?, Prenom_User=? WHERE Id_S=?";
 try{
PreparedStatement statement = mc.prepareStatement(sql);
statement.setString(1, co.getDate_S());
statement.setFloat(2, co.getPrix());
statement.setInt(3, co.getId_Co());
statement.setString(4, co.getNom_User());
statement.setString(5, co.getPrenom_User());
statement.setInt(6, co.getId_S());
 
int rowsUpdated = statement.executeUpdate();
if (rowsUpdated > 0) {
    System.out.println("COACHING MODIFIE!");
}

 } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 
     }
      
//---------------------------------------------- supprimerCoaching -----------------------------------------------------------------//
      public void supprimerCoaching(int Id_S){
        String sql = "DELETE from coaching where Id_S= '"+Id_S+"' "; 
        try{

            
                    Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println("Coaching supprimé avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
//------------------------------------------------- prixtotal() -----------------------------------------------------------------//
      public double prixtotal() throws SQLException {
        double total = 0;
        PreparedStatement pt1 = mc.prepareStatement("SELECT sum(prix) FROM coaching");
        try{
        ResultSet rs1 = pt1.executeQuery();
        while (rs1.next()) {
            total = rs1.getInt(1);
        }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
                return total;
    }
  
//---------------------------------------------- calculer_nbseance ------------------------------------------------------------------//
       public int calculer_nbseance(int Id_Co) {
        int l=0 ;
        String requete ="SELECT COUNT(*) FROM coaching where Id_Co='"+Id_Co+"'"; 
        try {
           
           Statement st =mc.createStatement();
           ResultSet rs=st.executeQuery(requete);
           if (rs.next()){
          String chaine = String.valueOf(rs.getString(1));
          l=Integer.parseInt(chaine);
            return l;}
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
      return l;
    }
   //------------------------------- Liste 2 --------------------------------------------------------------------//
    public List <Coaching> liste2()
    {
        String sql = "select Id_S, Date_S, Prix, Id_Co, Nom_User, Prenom_User from coaching";
        
       List <Coaching> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Coaching(rs.getInt("Id_S"),rs.getString("Date_S"),rs.getFloat("Prix"),rs.getInt("Id_Co"),rs.getString("Nom_User"),rs.getString("Prenom_User")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
    
    //--------------------------------- getActiviteList() ------------------------------------------------------//
    public ObservableList<Coaching> getCoachingList() throws SQLException
    {
        ObservableList<Coaching> Coachinglist = FXCollections.observableArrayList();
        
        List <Coaching> listb = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select Id_S, Date_S, Prix, Id_Co, Nom_User, Prenom_User from coaching";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Coaching Coachings;
        while (rs.next()) {
           Coachings= new Coaching(rs.getInt("Id_S"),rs.getString("Date_S"),rs.getFloat("Prix"),rs.getInt("Id_Co"),rs.getString("Nom_User"),rs.getString("Prenom_User")); 
            //System.out.println(events);
            Coachinglist.add(Coachings);

        }
         return Coachinglist;    
    }
    
    //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getDatebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from coaching where Id_S=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Date_S");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CoachingServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
     //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNombyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from coaching where Id_S=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Nom_User");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CoachingServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getPrenombyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from coaching where Id_S=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Prenom_User");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CoachingServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //----------------------------------- Display Cat_age by ID -----------------------------------------------------------------// 
    public Integer getId_CobyID(int id){
        try {
            ste = mc.prepareStatement("select * from coaching where Id_S=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Id_Co");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoachingServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
    
    //----------------------------------------  Display Type by ID --------------------------------------------------------------//
     public Float getPrixbyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from coaching where Id_S=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getFloat("Prix");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CoachingServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.F;
    }
     
      //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Coaching a ,int id) {
           try {
              /*ste =mc.prepareStatement(
                      "UPDATE produit SET photo = ?, poids = ?," + " prix = ?, desription = ?, categorie = ?, " + " libelle = ? WHERE id = ?");*/
              
             // ste= mc.prepareStatement("UPDATE activite SET nom_a= '"+a.getNom_a()+"' ,cat_age= ''"+a.getCat_age()+"' , " + " type= '"+a.getType()+"'," + " id_enfant='"+a.getId_enfant()+"' where id_a =?");
              
              ste= mc.prepareStatement("UPDATE coaching SET Date_S=?,Prix=?,Id_Co=?, Nom_User=?,Prenom_User=? where Id_S =?");
              System.out.println(ste);
     ste.setString(1, a.getDate_S());
     ste.setFloat(2,a.getPrix());
     ste.setInt(3,a.getId_Co());
     ste.setString(4, a.getNom_User());
     ste.setString(5, a.getPrenom_User());
      ste.setInt(6, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     //------------------------------------------ NB Seances -----------------------------------------------//
      public String calculer_nbseance(String Date_S) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM coaching where Date_S='"+Date_S+"'"; 
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



package Services;
import Entities.Consultation;
import Entities.Programme;
import Interfaces.IConsultationService;
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

public class consultationservices implements IConsultationService{
     Connection mc;
    PreparedStatement ste;

    public consultationservices() {
         mc=connexion.getInstance().getCnx();
    }
   
    //------------------------------------ Ajouter ----------------------------------------------------//
    public void ajouterconsultation(Consultation c){
        String sql ="insert into consultation(Nom,Age,Sexe,Date_rdv,Etat_physique,categorie_c,Prenom,Email) Values(?,?,?,?,?,?,?,?)";
        try {
            ste=mc.prepareStatement(sql);
            ste.setString(1,c.getNom());
            ste.setInt(2,c.getAge());
            ste.setString(3,c.getSexe());
            ste.setString(4,c.getDate_rdv());
            ste.setString(5,c.getEtat_physique());
            ste.setString(6,c.getcategorie_c());
            ste.setString(7,c.getPrenom());
            ste.setString(8,c.getEmail());
            ste.executeUpdate();
            System.out.println("consultation Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
   
  //------------------------------------ Afficher ----------------------------------------------------//   
    public List<Consultation> afficherconsultation(){
        List<Consultation> consultation = new ArrayList<>();
        String sql="select * from consultation";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Consultation c = new Consultation();
                c.setId_c(rs.getInt("id_c"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAge(rs.getInt("age"));
                c.setEmail(rs.getString("Email")); 
                c.setEtat_physique(rs.getString("Etat_physique"));
                c.setDate_rdv(rs.getString("Date_rdv"));
                c.setcategorie_c(rs.getString("categorie_c"));
           
                consultation.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return consultation;
    }
    
    //-------------------------------------- Supprimer -------------------------------------------------//
    public void supprimerconsultation(int id){
        String sql = "DELETE from  consultation where Id_c= '"+id+"' "; 
        try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println("Activité supprimée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    //-----------------------------------  Updateconsultation ---------------------------------------//
    public void Updateconsultation(String nom, String prenom,int age,String sexe ,String Email,String date, String etat,int id ){
      
        String sql = "UPDATE consultation SET Nom= '"+nom+"',Prenom= '"+prenom+"',Age= '"+age+"',Sexe='"+sexe+"',Email= '"+Email+"',Date_rdv= '"+date+"',Etat_physique= '"+etat+"' where Id_c= '"+id+"' ";
     
        try{
           Statement st = mc.createStatement();
           st.executeUpdate(sql);
            System.out.println(" consultation modifiée avec succés !");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //----------------------------------- Trier --------------------------------------------------//
    public List<Consultation> trieconsultation(){
        List<Consultation> consultation = new ArrayList<>();
        String sql="select * from consultation order by age";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Consultation c = new Consultation();
                c.setId_c(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAge(rs.getInt("age"));
                c.setEmail(rs.getString("Email")); 
                c.setEtat_physique(rs.getString("Etat_physique"));
                c.setDate_rdv(rs.getString("Date_rdv"));
                c.setcategorie_c(rs.getString("categorie_c"));
           
                consultation.add(c);
                System.out.println("ID: "+c.getId_c()+"Nom: "+c.getNom()+"Prenom: "
                        +c.getPrenom()+"Age: "+c.getAge()+"Mail: "+c.getEmail()
                        +"Etat Physique"+c.getEtat_physique()
                        +"Date RDV"+c.getDate_rdv()+"ategorie_c: "+c.getcategorie_c());

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return consultation;
    }
    
    //------------------------------- Liste 2 --------------------------------------------------------------------//
    public List <Consultation> liste2()
    {
        String sql = "select Id_c, Nom, Age, Sexe,Date_rdv,Etat_physique, categorie_c,Prenom,Email from consultation";
        
       List <Consultation> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
          // list.add(new Consultation(rs.getInt("Id_c"),rs.getInt("age"),rs.getString("Nom"),rs.getString("sexe"),rs.getString("Date_rdv"), rs.getString("Etat_physique"), rs.getString("Prenom"),rs.getString("categorie_c"),rs.getString("Email")));
       list.add(new Consultation(rs.getInt("Id_c"),rs.getString("Nom"),rs.getInt("Age"),rs.getString("Sexe"),rs.getString("Date_rdv"),rs.getString("Etat_physique"),rs.getString("categorie_c"),rs.getString("Prenom"),rs.getString("Email")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
    
    //--------------------------------- getActiviteList() ------------------------------------------------------//
     public ObservableList<Consultation> getconsultationList() throws SQLException
    {
     ObservableList<Consultation> conslist= FXCollections.observableArrayList();
             
        
        List <Consultation> listc = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select Id_c, Nom, Age, Sexe,Date_rdv,Etat_physique, categorie_c,Prenom,Email from consultation";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Consultation consultation;
    
        while (rs.next()) {
         //consultation= new Consultation(rs.getInt("Id_c"),rs.getInt("Id_age"),rs.getString("Nom"),rs.getString("Prenom"), rs.getString("Sexe"),rs.getString("Email"), rs.getString("Etat_physique"),rs.getString("Date_rdv"), rs.getString("categorie_c"));
        consultation= new Consultation(rs.getInt("Id_c"),rs.getString("Nom"),rs.getInt("Age"),rs.getString("Sexe"),rs.getString("Date_rdv"),rs.getString("Etat_physique"),rs.getString("categorie_c"),rs.getString("Prenom"),rs.getString("Email")); 
         
         conslist.add(consultation);

        }
         return conslist;    
    }
     
     //------------------------------------ getprogrammeList() ------------------------------------------//
      public ObservableList<Programme> getprogrammeList() throws SQLException
    {
     ObservableList<Programme> conslist= FXCollections.observableArrayList();
             
        
        List <Programme> listc = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select Id, Nom_p,Date_r,Id_kine,description,Id_c from programme";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Programme programme;
    
        while (rs.next()) {
         //consultation= new Consultation(rs.getInt("Id_c"),rs.getInt("Id_age"),rs.getString("Nom"),rs.getString("Prenom"), rs.getString("Sexe"),rs.getString("Email"), rs.getString("Etat_physique"),rs.getString("Date_rdv"), rs.getString("categorie_c"));
        programme=new Programme(rs.getInt("Id"),rs.getString("Nom_p"),rs.getString("date_r"),rs.getInt("Id_kine"),rs.getString("description"),rs.getInt("Id_c"));
         
         conslist.add(programme);

        }
         return conslist;    
    }
      
       //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNombyID(int idxx)
    {
       
        try{
            ste= mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Nom");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(consultationservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //----------------------------------- Display Age by ID -----------------------------------------------------------------// 
    public Integer getAgebyId(int id){
        try {
            ste= mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Age");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
    
     //----------------------------------- Display Sexe by ID -----------------------------------------------------------------// 
    public String getSexebyId(int id){
         /*
         try{
            ste= mc.prepareStatement("select * from activite where id_a=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("nom_a");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        */
        try {
            ste = mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("Sexe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(consultationservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
    
    //----------------------------------------  Display Date by ID --------------------------------------------------------------//
     public String getadd_datebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Date_rdv");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(consultationservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
     //-------------------------------------- get Etat by ID -------------------------------------------//
     public String getadd_etatbyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Etat_physique");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(consultationservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     //-------------------------------------- get Etat by ID -------------------------------------------//
     public String getadd_categoriebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("categorie_c");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(consultationservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     //-------------------------------------- get Prenom by ID -------------------------------------------// 
     public String getadd_prenombyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Prenom");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(consultationservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     //-------------------------------------- get Email by ID -------------------------------------------// 
      public String getadd_mailbyID(int idxx)
          {
        try{
            ste= mc.prepareStatement("select * from consultation where Id_c=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Email");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(consultationservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
      
       //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Consultation c ,int id) {
           try {
             
              ste= mc.prepareStatement("UPDATE consultation SET Nom=? ,Age=? ,Sexe=?,Date_rdv=?,Etat_physique=?,categorie_c=?,Prenom=?,	Email=? where Id_c=?");
              System.out.println(ste);
       ste.setString(1, c.getNom());
       ste.setInt(2,c.getAge());
       ste.setString(3,c.getSexe());
       ste.setString(4,c.getDate_rdv());
       ste.setString(5,c.getEtat_physique());
       ste.setString(6,c.getcategorie_c());
       ste.setString(7,c.getPrenom());
       ste.setString(8,c.getEmail());
       ste.setInt(9,id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }      
     
    //--------------------------------- nb Consult ---------------------------------------------------------//
     public String calculer_nbseance(String Nom_C) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM consultation where Nom='"+Nom_C+"'"; 
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

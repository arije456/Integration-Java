
package Services;
import Entities.Consultation;
import Entities.Programme;
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
import Interfaces.IProgrammeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Programmeservices {
    Connection mc;
    PreparedStatement ste;

    public Programmeservices() {
        mc=connexion.getInstance().getCnx();
    }

  //----------------------------------------- Ajouter --------------------------------------------------------//
    public void ajouterProgramme(Programme p){
        // List <Programme> programme=displayByName(p.getNom_p());
        //if(programme.size()==0) {
        String sql ="insert into programme(Nom_p,Date_r,Id_kine,description,Id_c) Values(?,?,?,?,?)";
        try {
            ste=mc.prepareStatement(sql);
            ste.setString(1,p.getNom_p());
            ste.setString(2,p.getDate_r());
            ste.setInt(3,p.getId_kine());
              ste.setString(4,p.getdescription());
            ste.setInt(5,p.getId_c());
          
            ste.executeUpdate();
            System.out.println("programme Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    //}   else System.out.println("you can not add programme");
        
    }
    
     public  List <Programme> displayByName(String Nom_p) {
    List <Programme> myList= new ArrayList<>();

    String req="select * from programme where Nom_p='"+Nom_p+"' ";

    try {
        Statement st = mc.createStatement();
        ResultSet rs=st.executeQuery(req);
        while(rs.next()){
        //rs.next();
            Programme p =new Programme();
            p.setId(rs.getInt("Id")); 
        p.setNom_p(rs.getString("Nom_p"));
        p.setDate_r(rs.getString("Date_r"));
        p.setId_kine(rs.getInt("Id_kine"));
        p.setdescription(rs.getString("description"));
        p.setId_c(rs.getInt("Id_c"));
        myList.add(p);

        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
}
    //------------------------------------------------ Afficher ------------------------------------------------//
    public List<Programme> afficherprogramme(){
        List<Programme> Programme;
         Programme = new ArrayList<>();
        String sql="select * from programme";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Programme p = new Programme();
                p.setId(rs.getInt("Id"));
                p.setNom_p(rs.getString("Nom_p"));
                p.setDate_r(rs.getString("Date_r"));
                p.setId_kine(rs.getInt("Id_kine"));
                p.setdescription(rs.getString("description"));
                p.setId_c(rs.getInt("Id_c"));
               
                Programme.add(p);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return Programme;
    }
    
  //--------------------------------- Supprimer ---------------------------------------------//
    public void supprimerprogramme(int id){
        String sql = "DELETE from  programme where Id= '"+id+"' "; 
        try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println("programme supprimée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
   //----------------------------------- Modifier ------------------------------------------//
    public void Updateprogramme(String Nom_p, String Date,int Id_kine,int id){
      
        String sql = "UPDATE programme SET Nom_p='"+Nom_p+"' ,Date_r= '"+Date+"',Id_kine= '"+Id_kine+"' where Id= '"+id+"'";
     
        try{
           Statement st = mc.createStatement();
           st.executeUpdate(sql);
            System.out.println(" programme modifiée avec succés !");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //----------------------------------------- Trier ---------------------------------------//
    /*
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
    }*/
    
    //------------------------------- Liste 2 --------------------------------------------------------------------//
    public List <Programme> liste2()
    {
        String sql = "select Id, Nom_p,Date_r,Id_kine,description,Id_c from programme";
        
       List <Programme> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
          // list.add(new Consultation(rs.getInt("Id_c"),rs.getInt("age"),rs.getString("Nom"),rs.getString("sexe"),rs.getString("Date_rdv"), rs.getString("Etat_physique"), rs.getString("Prenom"),rs.getString("categorie_c"),rs.getString("Email")));
       list.add(new Programme(rs.getInt("Id"),rs.getString("Nom_p"),rs.getString("date_r"),rs.getInt("Id_kine"),rs.getString("description"),rs.getInt("Id_c")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
    
    //--------------------------------- getprogrammeList() ------------------------------------------------------//
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
            ste= mc.prepareStatement("select * from programme where Id=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Nom_p");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Programmeservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //----------------------------------- Display Date_rdv by ID -----------------------------------------------------------------// 
    public String getDate_rbyId(int id){
        try {
            ste= mc.prepareStatement("select * from programme where Id=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("Date_r");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Programmeservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
    //----------------------------------------  Display ID_kine by ID --------------------------------------------------------------//
     public Integer getId_kinebyId(int id){
        try {
            ste= mc.prepareStatement("select * from programme where Id=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Id_Kine");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Programmeservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
      //----------------------------------------  Display Description by ID --------------------------------------------------------------//
     public String getdescriptionbyID(int idxx)
    {
        try{
             ste= mc.prepareStatement("select * from programme where Id=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("description");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Programmeservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
//----------------------------------------  Display ID_c by ID --------------------------------------------------------------//
    public Integer getId_cbyId(int id){
        try {
             ste= mc.prepareStatement("select * from programme where Id=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Id_c");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Programmeservices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
    
     //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Programme p ,int id) {
         
         
           try {
     // ste= mc.prepareStatement("UPDATE `programme` SET `Nom_p`=?,`Date_r`=?,`Id_Kine`=?,`description`=?,`Id_c`=? WHERE `Id`=?");
     ste= mc.prepareStatement("UPDATE programme SET Nom_p=? ,Date_r=? ,Id_Kine=?,description=?,Id_c =? where Id =?");
     System.out.println(ste);
     ste.setString(1, p.getNom_p());
     ste.setString(2,p.getDate_r());
     ste.setInt(3,p.getId_kine());
      ste.setString(4,p.getdescription());
       ste.setInt(5,p.getId_c());
      ste.setInt(6,id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex.getMessage());
          }

         }
     
     //----------------------------------------- NB séance -----------------------------------------------//
     public String calculer_nbseance(String Date) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM programme where Date_r='"+Date+"'"; 
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

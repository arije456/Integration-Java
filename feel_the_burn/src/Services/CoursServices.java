
package Services;
import Entities.Cours;
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

public class CoursServices {
    Connection mc;
    PreparedStatement ste;

    public CoursServices() {
        mc= connexion.getInstance().getCnx();
    }
    
    //--------------------------------------------- Ajouter Cours ----------------------------------------------------------//
    public void ajouterCours(Cours c){
        String sql;
        //List <Cours> Courss=displayByName(c.getNom_C());
        //if(Courss.size()==0) {
        sql = "insert into cours(Nom_C,Date_C,Id_Co) Values(?,?,?)";
        try {
            ste=mc.prepareStatement(sql);
            ste.setString(1, c.getNom_C());
            ste.setString(2, c.getDate_C());
            ste.setInt(3, c.getId_Co());
            
            ste.executeUpdate();
            System.out.println("Cours Ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
       // else System.out.println("you can not add Cours");
        
    //}
    
    //---------------------------------------- afficherCours() --------------------------------------------------------------//
    public List<Cours> afficherCours(){
        List<Cours> courss = new ArrayList<>();
        String sql="select * from cours";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Cours c =new Cours();
                c.setId_C(rs.getInt("Id_C"));
                c.setNom_C(rs.getString("Nom_C"));
                c.setDate_C(rs.getString("Date_C"));
                 c.setId_Co(rs.getInt("Id_Co"));
                 
                 
                courss.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return courss;
    }
    
    
    //------------------------------------- supprimerCours(int id) -----------------------------------------------------------------//
    
         public void supprimerCours(int id){
        String sql = "DELETE from Cours where Id_C= '"+id+"' "; 
        try{

            
                    Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println("Cours supprimé avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
         
    //--------------------------------------- UpdateCours ------------------------------------------------------------------------//
         public void Updatecours(Cours c){
         String sql = "UPDATE cours SET Nom_C=?, Date_C=?,Id_Co=? WHERE Id_C=?";
 try{
PreparedStatement statement = mc.prepareStatement(sql);
statement.setString(1, c.getNom_C());
statement.setString(2, c.getDate_C());
statement.setInt(3, c.getId_Co());
statement.setInt(4,c.getId_C());

 
int rowsUpdated = statement.executeUpdate();
if (rowsUpdated > 0) {
    System.out.println("COURS MODIFIE!");
}

 } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 
     }
         
 //---------------------------------------------------- List <Cours> displayByName(String Nom_C) ---------------------------------------//
         public  List <Cours> displayByName(String Nom_C) {
    List <Cours> myList= new ArrayList<>();

    String req="select * from cours where Nom_C='"+Nom_C+"' ";

    try {
        Statement st = mc.createStatement();
        ResultSet rs=st.executeQuery(req);
        while(rs.next()){
        //rs.next();
            Cours c =new Cours();
            c.setId_C(rs.getInt("Id_C")); 
        c.setNom_C(rs.getString("Nom_C"));
        c.setDate_C(rs.getString("Date_C"));
        c.setId_Co(rs.getInt("Id_Co"));
        myList.add(c);

        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
}
         
   //------------------------------- Liste 2 --------------------------------------------------------------------//
    public List <Cours> liste2()
    {
        String sql = "select Id_C, Nom_C, Date_C,Id_Co from cours";
        
       List <Cours> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Cours(rs.getInt("Id_C"),rs.getString("Nom_C"),rs.getString("Date_C"),rs.getInt("Id_Co")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
    
     //--------------------------------- getCoursList() ------------------------------------------------------//
    public ObservableList<Cours> getCoursList() throws SQLException
    {
        ObservableList<Cours> Courslist = FXCollections.observableArrayList();
        
        List <Cours> listb = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select Id_C, Nom_C, Date_C,Id_Co from cours";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Cours Courss;
        while (rs.next()) {
           Courss= new Cours(rs.getInt("Id_C"),rs.getString("Nom_C"),rs.getString("Date_C"),rs.getInt("Id_Co")); 
            //System.out.println(events);
            Courslist.add(Courss);

        }
         return Courslist;    
    }
    
     //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNombyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from cours where Id_C=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Nom_C");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
         //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getDatebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from cours where Id_C=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Date_C");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //----------------------------------- Display Cat_age by ID -----------------------------------------------------------------// 
    public Integer getId_CobyID(int id){
        try {
            ste = mc.prepareStatement("select * from cours where Id_C=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Id_Co");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoursServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
     //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Cours a ,int id) {
           try {
              /*ste =mc.prepareStatement(
                      "UPDATE produit SET photo = ?, poids = ?," + " prix = ?, desription = ?, categorie = ?, " + " libelle = ? WHERE id = ?");*/
              
             // ste= mc.prepareStatement("UPDATE activite SET nom_a= '"+a.getNom_a()+"' ,cat_age= ''"+a.getCat_age()+"' , " + " type= '"+a.getType()+"'," + " id_enfant='"+a.getId_enfant()+"' where id_a =?");
              
              ste= mc.prepareStatement("UPDATE cours SET Nom_C=? ,Date_C=? ,Id_Co=? where Id_C =?");
              System.out.println(ste);
     ste.setString(1, a.getNom_C());
     ste.setString(2,a.getDate_C());
     ste.setInt(3,a.getId_Co());
      ste.setInt(4, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     
     //------------------------------------ Nb Seance ----------------------------------------------------//
     public String calculer_nbseance(String Nom_C) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM cours where Nom_C='"+Nom_C+"'"; 
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

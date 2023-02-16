
package Services;
import Entities.Activite;
import Tools.connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Interfaces.IActiviteService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ActiviteService implements IActiviteService<Activite>{
    Connection mc;
    PreparedStatement ste;

    public ActiviteService() {
         mc=connexion.getInstance().getCnx();
    }
    
    // ---------------------------------- Ajouter Activité ---------------------------------------//
    @Override
    public void ajouterActivite(Activite a)
    {
        String sql ="insert into activite(nom_a,cat_age,type,id_enfant) Values(?,?,?,?)";
        
        try
        {
           ste=mc.prepareStatement(sql);
           ste.setString(1, a.getNom_a());
           ste.setInt(2,a.getCat_age());
           ste.setString(3,a.getType());
           ste.setInt(4, a.getId_enfant());
           ste.executeUpdate();
           System.out.println("Activite Ajoutée");
        }
        catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
    }
    
    //--------------------------------------- Afficher Activité ------------------------------------------------//
    @Override
    public List<Activite> afficherActivite()
    {
      List<Activite> activites =  new ArrayList<>();
      String sql="select * from activite";
      
      try
      {
          ste=mc.prepareStatement(sql);
          
          ResultSet rs=ste.executeQuery();
                  while(rs.next()){
                      Activite a = new Activite();
                      a.setId_a(rs.getInt("id_a"));
                      a.setNom_a(rs.getString("nom_a"));
                      a.setCat_age(rs.getInt("cat_age"));
                      a.setType(rs.getString("type"));
                      a.setId_enfant(rs.getInt("id_enfant"));
                      activites.add(a);
                      System.out.println("ID : "+a.getId_a()+"\n Nom : "+a.getNom_a()+"\n catégorie d'age : "+a.getCat_age()+"\n Type: "+a.getType()+"Id Enfant: "+a.getId_enfant());
                      //System.out.println("Afficher avec succés !");
                  }
      }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      return activites;
    }
    //------------------------------- Liste 2 --------------------------------------------------------------------//
    public List <Activite> liste2()
    {
        String sql = "select id_a, nom_a, cat_age, type, id_enfant from activite";
        
       List <Activite> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Activite(rs.getInt("id_a"),rs.getString("nom_a"),rs.getInt("cat_age"),rs.getString("type"),rs.getInt("id_enfant")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
  
    //----------------------------------------- Modifier Activité ---------------------------------------------------//
    @Override
    public void updateActivite(int id, String nom, int idC, String typ, int ide){
       String sql= "UPDATE activite SET nom_a='"+nom+"',cat_age= '"+idC+"',type='"+typ+"',id_enfant='"+ide+"' where id_a = '"+id+"'";
       try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println(" activité modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    //------------------------------------------- Update 2 ----------------------------------------------------//
    public void update2(Activite a)
    {
       String sql= "UPDATE activite SET nom_a=? ,cat_age=? ,type=?,id_enfant=? where id_a =?";  
       try{
          ste = mc.prepareStatement(sql);
          
          ste.setInt(1,a.getId_a());
          ste.setString(2,a.getNom_a());
          ste.setInt(3,a.getCat_age());
          ste.setString(4,a.getType());
          ste.setInt(5,a.getId_enfant());
          
          ste.execute();
          
           System.out.println(" activité modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    //---------------------------------------- List new --------------------------------------------------------//
     public ObservableList<Activite> getActiviteslistnew() throws SQLException
     {
         String sql = "select id_a, nom_a, cat_age, type, id_enfant from activite";
         ObservableList<Activite> Activiteslist = FXCollections.observableArrayList();
         
         try
         {
           ResultSet rs = ste.executeQuery(sql);   
           while(rs.next()){
                      Activite a = new Activite();
                      a.setId_a(rs.getInt("id_a"));
                      a.setNom_a(rs.getString("nom_a"));
                      a.setCat_age(rs.getInt("cat_age"));
                      a.setType(rs.getString("type"));
                      a.setId_enfant(rs.getInt("id_enfant"));
                      Activiteslist.add(a);
                      //System.out.println("ID : "+a.getId_a()+"\n Nom : "+a.getNom_a()+"\n catégorie d'age : "+a.getCat_age()+"\n Type: "+a.getType()+"Id Enfant: "+a.getId_enfant());
                      //System.out.println("Afficher avec succés !");
                  }
         }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
         return Activiteslist;
     }
    
    //-------------------------------------- Supprimer Activité ------------------------------------------------//
    @Override
    public void supprimerActivite(int id){
        String sql = "DELETE from Activite where id_a= '"+id+"' "; 
        try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           
           /*
           pst = conn.prepareStatement(req);
            
            pst.setString(1, a.getNom());
           */
           
           
           System.out.println("Activité supprimée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    //------------------------------------ Trier Activité --------------------------------------------//
    @Override
    public List<Activite> afficherTrie()
    {
      List<Activite> activites =  new ArrayList<>();
      String sql="select * from activite order by cat_age desc";
      
      try
      {
          ste=mc.prepareStatement(sql);
          
          ResultSet rs=ste.executeQuery();
                  while(rs.next()){
                      Activite a = new Activite();
                      a.setId_a(rs.getInt("id_a"));
                      a.setNom_a(rs.getString("nom_a"));
                      a.setCat_age(rs.getInt("cat_age"));
                      a.setType(rs.getString("type"));
                      a.setId_enfant(rs.getInt("id_enfant"));
                      activites.add(a);
                      //System.out.println("ID : "+a.getId_a()+"\n Nom : "+a.getNom_a()+"\n catégorie d'age : "+a.getCat_age()+"\n Type: "+a.getType()+"Id Enfant: "+a.getId_enfant());
                      //System.out.println("Afficher avec succés !");
                  }
      }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      return activites;  
    }
    
    //-------------------------------- Search Activité -------------------------------------------------//
    @Override
    public List<Activite> SearchActivite(Activite A)
    {
        List<Activite> activites =  new ArrayList<>();
        
        for (Activite i : activites){
            if (i.equals(A))
            {
                activites.add(i);
                System.out.println(" Recherche avec succés !");
            }
            else {
               System.out.println(" Recherche échouée !"); 
            }
        }
        return activites; 
    }
    
    
    //--------------------------------- getActiviteList() ------------------------------------------------------//
    public ObservableList<Activite> getActiviteList() throws SQLException
    {
        ObservableList<Activite> Activitelist = FXCollections.observableArrayList();
        
        List <Activite> lista = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select id_a, nom_a, cat_age, type, id_enfant from activite";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Activite activites;
        while (rs.next()) {
           activites= new Activite(rs.getInt("id_a"), rs.getString("nom_a"), rs.getInt("cat_age"),rs.getString("type"),rs.getInt("id_enfant") );  
            //System.out.println(events);
            Activitelist.add(activites);

        }
         return Activitelist;    
    }
    
     //--------------------------- Display by Name ----------------------------------------//
    
    public  List <Activite> displayByName(String Titre) {
    List <Activite> myList= new ArrayList<>();

    String req="select * from activite where nom_a='"+Titre+"' ";

    try {
        Statement st = mc.createStatement();
        ResultSet rs=st.executeQuery(req);
        while(rs.next()){
        
                      Activite a =new Activite();
                      a.setId_a(rs.getInt("id_a"));
                      a.setNom_a(rs.getString("nom_a"));
                      a.setCat_age(rs.getInt("cat_age"));
                      a.setType(rs.getString("type"));
                      a.setId_enfant(rs.getInt("id_enfant"));
                      myList.add(a);

        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
}
    
    //------------------------------ Display By ID ----------------------------------------//
     
    
    public  List <Activite> displayByID(int id) {
    List <Activite> myList= new ArrayList<>();

    String req="select * from activite where id_a='"+id+"' ";

    try {
        Statement st = mc.createStatement();
        ResultSet rs=st.executeQuery(req);
        while(rs.next()){
        
                      Activite a =new Activite();
                      a.setId_a(rs.getInt("id_a"));
                      a.setNom_a(rs.getString("nom_a"));
                      a.setCat_age(rs.getInt("cat_age"));
                      a.setType(rs.getString("type"));
                      a.setId_enfant(rs.getInt("id_enfant"));
                      myList.add(a);

        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
}
    //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNombyID(int idxx)
    {
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
    }
    
   //----------------------------------- Display Cat_age by ID -----------------------------------------------------------------// 
    public Integer getCatAgebyId(int id){
        try {
            ste = mc.prepareStatement("select * from activite where id_a=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("cat_age");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
    
    //----------------------------------------  Display Type by ID --------------------------------------------------------------//
     public String getTypebyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from activite where id_a=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("type");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     
     //----------------------------------------  Display ID_Enfant by ID --------------------------------------------------------------//
     public Integer getID_EnfantbyId(int id){
        try {
            ste = mc.prepareStatement("select * from activite where id_a=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("id_enfant");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
     
     //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Activite a ,int id) {
           try {
             
              ste= mc.prepareStatement("UPDATE activite SET nom_a=? ,cat_age=? ,type=?,id_enfant=? where id_a =?");
              System.out.println(ste);
     ste.setString(1, a.getNom_a());
     ste.setInt(2,a.getCat_age());
     ste.setString(3,a.getType());
      ste.setInt(4,a.getId_enfant());
      ste.setInt(5, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     //----------------------------------- Count Rows --------------------------------------------------------//
     /*
      ObservableList<ObservableList> queryRows(String tableName) {
        String sql = "SELECT * from " + tableName;
        ObservableList<Activite> Activitelist = FXCollections.observableArrayList();
        try (
                ste = mc.prepareStatement("SELECT * from " + tableName;);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                ObservableList<Activite> list = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    list.add(rs.getString(i));
                }
                Activitelist.add(list);
            }
            rs.close();
        } catch (SQLException rowQueryException) {
            System.err.println(rowQueryException.toString());
        }
        return rows;
    }*/
     
     public ObservableList<ObservableList> getCountAct(TableView TableName) throws SQLException
    {
        ObservableList<ObservableList> Activitelist = FXCollections.observableArrayList();
        
        List <Activite> lista = new ArrayList<>(); 
        Statement st= mc.createStatement();
       // String query = "select id_a, nom_a, cat_age, type, id_enfant from activite";
        String query = "SELECT * from " + TableName;
        ResultSet rs;
        rs = st.executeQuery(query);
        Activite activites;
        while (rs.next()) {
            
             ObservableList<Activite> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(new Activite(rs.getInt("id_a"),rs.getString("nom_a"),rs.getInt("cat_age"),rs.getString("type"),rs.getInt("id_enfant")));
                    
                }
             Activitelist.add(row);
          // activites= new Activite(rs.getInt("id_a"), rs.getString("nom_a"), rs.getInt("cat_age"),rs.getString("type"),rs.getInt("id_enfant") );  
            //System.out.println(events);
           // Activitelist.add(activites);
          

        }
         return Activitelist;    
    }
     
     //------------------------------------ Calculer nbAct -------------------------------------------//
     public String calculer_nbAct(String Nom) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM activite where nom_a='"+Nom+"'"; 
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

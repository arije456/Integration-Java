
package Services;
import Entities.Produit;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProduitService {
    Connection mc;
    PreparedStatement ste;

    public ProduitService() {
         mc=connexion.getInstance().getCnx();
    }
   
    //------------------------------------------------- Ajouter Produit ---------------------------------------------------//
    public  void ajouterProduit(Produit p)
    {
      String sql ="insert into produit(Nom_P,Prix,Photo,Categories) Values(?,?,?,?)";
      try{
          ste=mc.prepareStatement(sql);
          ste.setString(1, p.getNom_P());
           ste.setFloat(2,p.getPrix());
           ste.setString(3,p.getPhoto());
           ste.setString(4,p.getCategories());
           ste.executeUpdate();
           System.out.println("produit Ajoutée");
      } catch (SQLException ex) {
             System.out.println(ex.getMessage());
      }
      
    }
    
        //------------------------------------------- Afficher Produit -----------------------------------------------------------//
    
    public  List<Produit> afficherProduit()
    {
       List<Produit> produits =  new ArrayList<>();
      String sql="select * from produit";
      
      try
      {
          ste=mc.prepareStatement(sql);
          
          ResultSet rs=ste.executeQuery();
                  while(rs.next()){
                      Produit p = new Produit();
                      p.setId_P(rs.getInt("Id_P"));
                      p.setNom_P(rs.getString("Nom_P"));
                      p.setPrix(rs.getFloat("Prix"));
                      p.setPhoto(rs.getString("Photo"));
                      p.setCategories(rs.getString("Categories"));
                      produits.add(p);
                      System.out.println("ID : "+p.getId_P()+"\n Nom : "+p.getNom_P()+"\n Prix : "+p.getPrix()+"\n Photo: "+p.getPhoto()+"Catégorie: "+p.getCategories());
                      
                  }
      }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      return produits;   
    }
    
    //-------------------------------------------------- Modifier Produit -----------------------------------------------------//
    
    public void updateProduit(int id, String nom, Float p, String Photo, String cat){
       String sql= "UPDATE produit SET Nom_P='"+nom+"',Prix= '"+p+"',Photo='"+Photo+"',Categories='"+cat+"' where Id_P = '"+id+"'";
       try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println(" produit modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
        //------------------------------------------------- Update 2 ------------------------------------------------------//
    
    public void update2(Produit a)
    {
       String sql= "UPDATE produit SET Nom_P=? ,Prix=? ,Photo=?,Categories=? where Id_P =?";  
       try{
          ste = mc.prepareStatement(sql);
          
          ste.setInt(1,a.getId_P());
          ste.setString(2,a.getNom_P());
          ste.setFloat(3, a.getPrix());
          ste.setString(4,a.getPhoto());
         ste.setString(5,a.getCategories());
          
          ste.execute();
          
           System.out.println(" produit modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
     //-------------------------------------------------- Liste 2 --------------------------------------//
    public List <Produit> liste2()
    {
        String sql = "select Id_P, Nom_P,Prix,Photo,Categories from produit";
        
       List <Produit> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Produit(rs.getInt("Id_P"),rs.getString("Nom_P"),rs.getFloat("Prix"),rs.getString("Photo"),rs.getString("Categories")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
    
     //--------------------------------------------- Supprimer --------------------------------------------------------------------//
    
    public void supprimerProduit(int id){
        String sql = "DELETE from produit where Id_P= '"+id+"' "; 
        try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
          
           
           System.out.println("Produit supprimée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
     //---------------------------------------- getProduitList() ------------------------------------------------------------------//
    
    public ObservableList<Produit> getProduitList() throws SQLException
    {
        ObservableList<Produit> ProduitList = FXCollections.observableArrayList();
        
        List <Produit> listp = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String sql = "select Id_P, Nom_P,Prix,Photo,Categories from produit";
        
        ResultSet rs;
        rs = st.executeQuery(sql);
        Produit produits;
        while (rs.next()) {
           produits= new Produit(rs.getInt("Id_P"),rs.getString("Nom_P"),rs.getFloat("Prix"),rs.getString("Photo"),rs.getString("Categories")); 
            //System.out.println(events);
            ProduitList.add(produits);

        }
         return ProduitList;    
    }
    
        //-------------------------------------- ListNew() ------------------------------------------------------------//
    
    public ObservableList<Produit> getProduitslistnew() throws SQLException
     {
         String sql = "select Id_P, Nom_P,Prix,Photo,Categories from produit";
         ObservableList<Produit> Produitslist = FXCollections.observableArrayList();
         
         try
         {
           ResultSet rs = ste.executeQuery(sql);   
           while(rs.next()){
                      Produit a = new Produit();
                      a.setId_P(rs.getInt("Id_P"));
                      a.setNom_P(rs.getString("Nom_P"));
                      a.setPrix(rs.getFloat("Prix"));
                      a.setPhoto(rs.getString("Photo"));
                      a.setCategories("Categories");
                      Produitslist.add(a);
                      
                  }
         }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
         return Produitslist;
     }
    
     //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNombyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from produit where Id_P=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Nom_P");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //----------------------------------- Display Cat_age by ID -----------------------------------------------------------------// 
    public Float getPrixbyId(int id)
    {
        try {
            ste = mc.prepareStatement("select * from produit where Id_P=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getFloat("Prix");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.F;
        }
    
     //----------------------------------------  Display Type by ID --------------------------------------------------------------//
     public String getPhotoyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from produit where Id_P=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Photo");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     
      //----------------------------------------  Display ID_Enfant by ID --------------------------------------------------------------//
     public String getCategoriesbyId(int id){
        try {
            ste = mc.prepareStatement("select * from produit where Id_P=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("Categories");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
     
      //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier( Produit p ,int id) {
           try {
              /*ste =mc.prepareStatement(
                      "UPDATE produit SET photo = ?, poids = ?," + " prix = ?, desription = ?, categorie = ?, " + " libelle = ? WHERE id = ?");*/
              
             // ste= mc.prepareStatement("UPDATE activite SET nom_a= '"+a.getNom_a()+"' ,cat_age= ''"+a.getCat_age()+"' , " + " type= '"+a.getType()+"'," + " id_enfant='"+a.getId_enfant()+"' where id_a =?");
              
              ste= mc.prepareStatement("UPDATE produit SET Nom_P_a=? ,Prix=? ,Photo=?,Categories=? where Id_P =?");
              System.out.println(ste);
     ste.setString(1, p.getNom_P());
     ste.setFloat(2,p.getPrix());
     ste.setString(3,p.getPhoto());
      ste.setString(4,p.getCategories());
      ste.setInt(5, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     
     //------------------------------------ prix Total ---------------------------------------------------//
     public Integer prixtotal() throws SQLException {
        int total = 0;
        PreparedStatement pt1 = mc.prepareStatement("SELECT sum(Prix) FROM produit");
        try{
        ResultSet rs1 = pt1.executeQuery();
        while (rs1.next()) {
            total = (int) rs1.getFloat(1);
        }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
                return total;
    }
    
     //---------------------------- Nb de Produit -------------------------------------------//
     public String calculer_nbProd(String Nom_P) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM produit where Nom_P='"+Nom_P+"'"; 
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

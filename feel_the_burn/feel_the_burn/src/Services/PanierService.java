
package Services;
import Entities.Panier;
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

public class PanierService {
    Connection mc;
    PreparedStatement ste;

    public PanierService() {
        mc=connexion.getInstance().getCnx();
    }
    
     //------------------------------------------------- Ajouter Paniert ---------------------------------------------------//
    public  void ajouterPanier(Panier pa)
    {
      String sql ="insert into panier(Quantite,Coupon,Id_P) Values(?,?,?)";
      try{
          ste=mc.prepareStatement(sql);
          ste.setInt(1, pa.getQuantite());
           ste.setString(2,pa.getCoupon());
           ste.setInt(3,pa.getId_P());
           
           ste.executeUpdate();
           System.out.println("panier Ajoutée");
      } catch (SQLException ex) {
             System.out.println(ex.getMessage());
      }
      
    }
    
     //------------------------------------------- Afficher Panier -----------------------------------------------------------//
    
    public  List<Panier> afficherPanier()
    {
       List<Panier> paniers =  new ArrayList<>();
      String sql="select * from panier";
      
      try
      {
          ste=mc.prepareStatement(sql);
          
          ResultSet rs=ste.executeQuery();
                  while(rs.next()){
                      Panier pa = new Panier();
                      pa.setId_Pa(rs.getInt("Id_Pa"));
                      pa.setQuantite(rs.getInt("Quantite"));
                      pa.setCoupon(rs.getString("Coupon"));
                      pa.setId_P(rs.getInt("Id_P"));
                      
                      paniers.add(pa);
                      System.out.println("ID : "+pa.getId_Pa()+"\n Quantite : "+pa.getQuantite()+"\n Coupon : "+pa.getCoupon()+"\n Id_P: "+pa.getId_P());
                      
                  }
      }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      return paniers;   
    }
    
     //-------------------------------------------------- Modifier Panier -----------------------------------------------------//
    
    public void updatePanier(int id_pa, int quantite, String coupon,int id_P){
       String sql= "UPDATE panier SET Quantite='"+quantite+"',Coupon= '"+coupon+"',Id_P='"+id_P+"', where Id_Pa = '"+id_pa+"'";
       try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println(" panier modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
     //------------------------------------------------- Update 2 ------------------------------------------------------//
    
    public void update2(Panier pa)
    {
       String sql= "UPDATE panier SET Quantite=? ,Coupon=? ,Id_P=?, where Id_Pa =?";  
       try{
          ste = mc.prepareStatement(sql);
          
          ste.setInt(1,pa.getQuantite());
          ste.setString(2,pa.getCoupon());
          ste.setInt(3, pa.getId_P());
          ste.setInt(4,pa.getId_Pa());
         
          
          ste.execute();
          
           System.out.println(" panier modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    //-------------------------------------------------- Liste 2 --------------------------------------//
  public List <Panier> liste2()
    {
        String sql = "select Id_Pa,Quantite,Coupon,Id_P from panier";
        
       List <Panier> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Panier(rs.getInt("Id_Pa"),rs.getInt("Quantite"),rs.getString("Coupon"),rs.getInt("Id_P")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    return list; 
    }
  
  //--------------------------------- getPanierList() ------------------------------------------------------//
    public ObservableList<Panier> getPanierList() throws SQLException
    {
        ObservableList<Panier> Panierlist = FXCollections.observableArrayList();
        
        List <Panier> listpa = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select Id_Pa,Quantite,Coupon,Id_P  from panier";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Panier Paniers;
        while (rs.next()) {
           Paniers= new Panier(rs.getInt("Id_Pa"),rs.getInt("Quantite"),rs.getString("Coupon"),rs.getInt("Id_P")); 
            //System.out.println(events);
            Panierlist.add(Paniers);

        }
         return Panierlist;    
    }

    //--------------------------------------------- Supprimer --------------------------------------------------------------------//
    
    public void supprimerPanier(int id){
        String sql = "DELETE from panier where Id_Pa= '"+id+"' "; 
        try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
          
           
           System.out.println("Panier supprimée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    //-------------------------------------- ListNew() ------------------------------------------------------------//
    
    public ObservableList<Panier> getPanierslistnew() throws SQLException
     {
         String sql = "select Id_Pa,Quantite,Coupon,Id_P from panier";
         ObservableList<Panier> Panierslist = FXCollections.observableArrayList();
         
         try
         {
           ResultSet rs = ste.executeQuery(sql);   
           while(rs.next()){
                      Panier pa = new Panier();
                      pa.setId_Pa(rs.getInt("Id_Pa"));
                      pa.setQuantite(rs.getInt("Quantite"));
                      pa.setCoupon(rs.getString("Coupon"));
                       pa.setId_P(rs.getInt("Id_P"));
                    
                      Panierslist.add(pa);
                      
                  }
         }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
         return Panierslist;
     }
    
     //----------------------------------- Display Quantite by ID -----------------------------------------------------------------//
    public int getQuantitebyID(int idxx)
    {
       
        try {
            ste = mc.prepareStatement("select * from panier where Id_Pa=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Quantite");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
     //----------------------------------------  Display coupon by ID --------------------------------------------------------------//
     public String getCouponyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from panier where Id_Pa=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Coupon");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     
      //----------------------------------- Display Id_P by ID -----------------------------------------------------------------// 
    public int getId_PbyId(int id)
    {
        try {
            ste = mc.prepareStatement("select * from panier where Id_Pa=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getInt("Id_P");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        }
    
    //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier( Panier pa ,int id) {
           try {
              
              
              ste= mc.prepareStatement("UPDATE panier SET Quantite=? ,Coupon=? ,Id_P=? where Id_Pa =?");
              System.out.println(ste);
     ste.setInt(1, pa.getQuantite());
     ste.setString(2,pa.getCoupon());
      ste.setInt(3,pa.getId_P());
      ste.setInt(4, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     
       public String calculer_nbcoupon(String Coupon) {
        String l = null ;
        String requete ="SELECT COUNT(*) FROM panier where Coupon='"+Coupon+"'"; 
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

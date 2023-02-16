
package Services;
import Entities.Blog;
import Tools.connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BlogService {
    Connection mc;
    PreparedStatement ste;

    public BlogService() {
       mc=connexion.getInstance().getCnx();
    }
    
    //--------------------------------------------------- Ajouter Blog ----------------------------------------------------//
        public void ajouterBlog(Blog b1){
       
        String sql ="insert into blg(Titre,Contenu,Date,Auteur,Image,categorie) Values(?,?,?,?,?,?)";
        try {
            ste=mc.prepareStatement(sql);
            ste.setString(1, b1.getTitre());
            ste.setString(2, b1.getContenu());
            ste.setString(3, b1.getDate());
            ste.setString(4, b1.getAuteur());
            ste.setString(5, b1.getImage());
            ste.setString(6, b1.getcategorie());
         
            ste.executeUpdate();
            System.out.println("Blog Ajoutée");
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        
    }
        
        //----------------------------------- Modifier  Paramétré Blog -----------------------------------------------------------------------//
        public void updateBlog(int Id_b, String Titre, String Contenu, String Date, String Auteur, String Image,String categorie){
       String sql= "UPDATE Blg SET Titre='"+Titre+"',Contenu= '"+Contenu+"',Date='"+Date+"',Auteur='"+Auteur+"',categorie='"+categorie+"'where Id_b = '"+Id_b+"'";
       try{
           Statement st= mc.createStatement();
           st.executeUpdate(sql);
           System.out.println(" blog modifiée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    
     //----------------------------------- Supprimer par Id Blog -----------------------------------------------------------------------//
        public void supprimerBlog(int Id_b){
        String sql = "DELETE from blg where Id_b= '"+Id_b+"' "; 
        try{
           Statement ste= mc.createStatement();
           ste.executeUpdate(sql);
           System.out.println("Activité supprimée avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
        
    //----------------------------------- Afficher Blog -----------------------------------------------------------------------//  
        public List<Blog> afficherBlog(){
        List<Blog> Blogs= new ArrayList<>();
        String sql="select * from blg";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                Blog b = new Blog();
                b.setId_b(rs.getInt("Id_b"));
                b.setTitre(rs.getString("titre"));
                b.setContenu(rs.getString("contenu"));
                b.setDate(rs.getString("date"));
                b.setAuteur(rs.getString("auteur"));
                b.setImage(rs.getString("image"));
                b.setcategorie(rs.getString("categorie"));
                Blogs.add(b);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return Blogs;
    }
        
       //----------------------------------- displayByName Blog -----------------------------------------------------------------------//   
        public  List <Blog> displayByName(String Titre) {
    List <Blog> myList= new ArrayList<>();

    String req="select * from blg where Titre='"+Titre+"' ";

    try {
        Statement st = mc.createStatement();
        ResultSet rs=st.executeQuery(req);
        while(rs.next()){
        //rs.next();
            Blog b =new Blog();
            b.setId_b(rs.getInt("Id_b")); 
        b.setTitre(rs.getString("Titre"));
        b.setContenu(rs.getString("Contenu"));
        b.setDate(rs.getString("Date"));
        b.setAuteur(rs.getString("Auteur"));
        b.setImage(rs.getString("Image"));
        b.setcategorie(rs.getString("categorie"));
        myList.add(b);

        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
} 
  //---------------------------------------------------- calculer_nbblog ------------------------------------------------------------//
         public int calculer_nbblog(String Auteur) {
        int l=0 ;
        String requete ="SELECT COUNT(*) FROM blg where Auteur='"+Auteur+"'"; 
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
        
        System.out.println("Le nombre de blog de cet auteur est :");
      return l;
    }
         
    //------------------------------- Liste 2 --------------------------------------------------------------------//
         public List <Blog> liste2()
    {
        String sql = "select Id_b, Titre, Contenu, Date, Auteur, Image, categorie from blg";
        
       List <Blog> list = new ArrayList<>(); 
       try {
       ste=mc.prepareStatement(sql);
         ResultSet rs=ste.executeQuery();
       
       while (rs.next())
       {
           list.add(new Blog(rs.getInt("Id_b"),rs.getString("Titre"),rs.getString("Contenu"),rs.getString("Date"),rs.getString("Auteur"),rs.getString("Image"),rs.getString("categorie")));
       }
       
       }
       catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }   
    return list; 
    }
         //-------------------------------------- getBlogList() ---------------------------------------------------------//
         public ObservableList<Blog> getBlogList() throws SQLException
    {
        ObservableList<Blog> Bloglist = FXCollections.observableArrayList();
        
        List <Blog> listb = new ArrayList<>(); 
        Statement st= mc.createStatement();
        String query = "select Id_b, Titre, Contenu, Date, Auteur, Image, categorie from blg";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Blog Blogs;
        while (rs.next()) {
           Blogs= new Blog(rs.getInt("Id_b"),rs.getString("Titre"),rs.getString("Contenu"),rs.getString("Date"),rs.getString("Auteur"),rs.getString("Image"),rs.getString("categorie")); 
            //System.out.println(events);
            Bloglist.add(Blogs);

        }
        
         return Bloglist;    
    }
         
         //----------------------------------- Display Nom by ID -----------------------------------------------------------------//
    public String getNombyID(int idxx)
    {
        try{
            ste= mc.prepareStatement("select * from blg where Id_b=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Titre");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //----------------------------------- Display Cat_age by ID -----------------------------------------------------------------// 
    public String getContenubyId(int id){
         try {
            ste = mc.prepareStatement("select * from blg where Id_b=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("Contenu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
    
   //----------------------------------- Display Cat_age by ID -----------------------------------------------------------------// 
    public String getAuteurbyId(int id){
         try {
            ste = mc.prepareStatement("select * from blg where Id_b=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("Auteur");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
  //----------------------------------------  Display Type by ID --------------------------------------------------------------//
     public String getDatebyID(int idxx)
    {
         try{
            ste= mc.prepareStatement("select * from blg where Id_b=?");
            ste.setInt(1, idxx);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            
            if (rs.next()) {
                return rs.getString("Date");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
     
     //----------------------------------------  Display ID_Enfant by ID --------------------------------------------------------------//
     public String getImagebyId(int id){
        try {
            ste = mc.prepareStatement("select * from blg where Id_b=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("Image");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
   
     //----------------------------------------  Display ID_Enfant by ID --------------------------------------------------------------//
     public String getcategoriebyId(int id){
        try {
            ste = mc.prepareStatement("select * from blg where Id_b=?");
            ste.setInt(1, id);
            ResultSet rs = ste.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                return rs.getString("categorie");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        }
     //------------------------------------------ Update 3 -------------------------------------------------------------//
     public void modifier(Blog b ,int id) {
           try {
              /*ste =mc.prepareStatement(
                      "UPDATE produit SET photo = ?, poids = ?," + " prix = ?, desription = ?, categorie = ?, " + " libelle = ? WHERE id = ?");*/
              
             // ste= mc.prepareStatement("UPDATE activite SET nom_a= '"+a.getNom_a()+"' ,cat_age= ''"+a.getCat_age()+"' , " + " type= '"+a.getType()+"'," + " id_enfant='"+a.getId_enfant()+"' where id_a =?");
              
              ste= mc.prepareStatement("UPDATE blg SET Titre=? ,Contenu=? ,Date=? ,Auteur=? ,Image=? ,categorie=? where Id_b =?");
              System.out.println(ste);
     ste.setString(1, b.getTitre());
     ste.setString(2,b.getContenu());
     ste.setString(3,b.getDate());
     ste.setString(4,b.getAuteur());
     ste.setString(5,b.getImage());
     ste.setString(6,b.getcategorie());
     ste.setInt(7, id);
   
    ste.executeUpdate();
    
          } catch (SQLException ex) {
              System.out.println(ex);
          }

         }
     
     
  /*  @Override
    public boolean likeCours(int iduser, int idcours,int like) {
        
        try{
            
            if(userLikeExist(iduser, idcours) == true) {
                System.out.println("tu as deja liker ce"
                        + " Blog  d'id="+idcours);
                return false;
            } 
             
            
            if(like == 1 || like == 0 ) {
                
            } String request = "INSERT INTO `like_blog`( `iduser`, `id`,`like_etat`) VALUES ('"+iduser+"','"+idcours+"','"+like+"')";
            Statement st = mc.createStatement();
             st.executeUpdate(request);
             if(like == 1) {
                System.out.println("Like Blog ajoutee avec succes");

             }else if(like == 0) {
                System.out.println("Dislike Blog ajoutee avec succes");

             }
             else {
                 System.out.println("Like € [1,0]");
             }
       
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }       
        
        return true;
    }

    @Override
    public int countLikes() {
   int i = 0;
   

	try{
		try{
                        Statement st = mc.createStatement();
			String query = "SELECT COUNT(*) FROM like_blog WHERE like_etat ="+1;
			ResultSet rs=st.executeQuery(query);			
			//Extact result from ResultSet rs
			while(rs.next()){
			    System.out.println("Nombre totale des Likes(*)="+rs.getInt("COUNT(*)"));
                            i++;
			  }
			// close ResultSet rs
		   } catch(SQLException s){						
				s.printStackTrace();
			 }
		// close Connection and Statement
		}catch (Exception e){
			e.printStackTrace();
		 }
        return  i;    }

    @Override
    public int countDislike() {
        int i=0;
      try{
		try{
                        Statement st = mc.createStatement();
			String query = "SELECT COUNT(*) FROM like_blog WHERE like_etat ="+0;
			ResultSet rs=st.executeQuery(query);			
			//Extact result from ResultSet rs
			while(rs.next()){
			    System.out.println("Nombre totale des Dislike(*)="+rs.getInt("COUNT(*)"));
                            i++;
			  }
			// close ResultSet rs
		   } catch(SQLException s){						
				s.printStackTrace();
			 }
		// close Connection and Statement
		}catch (Exception e){
			e.printStackTrace();
		 }
        return  i;    }

    @Override
    public double statLikeParRapportBlog() {
        
        int nb = 0; 
         float res;
         int nbTotalCours = 0;
        try{
        String req1 = "SELECT count(*) FROM `blog`";
                PreparedStatement preparedStatement = mc.prepareStatement(req1);

        ResultSet result = preparedStatement.executeQuery(req1);
        if (result.first()) {
            nb = result.getInt(1);
        }
        nbTotalCours = nb;

   
          
        }catch(Exception exx) {
            exx.printStackTrace();
        }
         res= ((float) countLikes() / nbTotalCours);
        System.out.println("Statistique like blog par rapport total des cours :\n"+Double.valueOf(new DecimalFormat("##.##").format(res * 100)) + "%");
     
     return Double.valueOf(new DecimalFormat("##.##").format(res * 100));    }

*/
}

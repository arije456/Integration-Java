
package Tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connexion {
    static String url ="jdbc:mysql://localhost:3306/feel_the_burn";
    static String user="root";
    static String pwd="";
    public static connexion con;
    private Connection cnx;
    
    private connexion()
    {
     try{
          cnx=DriverManager.getConnection(url, user, pwd);
          System.out.println("Connexion etablie");   
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }   
    }
    
    public static connexion getInstance()
    {
      if(con==null)
          con= new connexion();
      return con;
    }
    public Connection getCnx() {
        return cnx;
    }
}

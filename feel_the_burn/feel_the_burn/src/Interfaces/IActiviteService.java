
package Interfaces;
import Entities.Activite;
import java.sql.SQLException;
import java.util.List;

public interface IActiviteService <A>{
    public void ajouterActivite(Activite a);
    public List<Activite> afficherActivite();
    public void updateActivite(int id, String nom, int idC, String typ, int ide);
    public void supprimerActivite(int id);
    public List<Activite> afficherTrie();
    public List<Activite> SearchActivite(Activite A);
}


package Interfaces;
import Entities.Programme;
import java.util.List;

public interface IProgrammeService {
    public void ajouterProgramme(Programme p);
    public List<Programme> afficherprogramme();
    public void supprimerprogramme(int id);
     public void Updateprogramme(String Nom_p, String Date,int Id_kine,int id);
     public  List <Programme> displayByName(String Nom_p);
}

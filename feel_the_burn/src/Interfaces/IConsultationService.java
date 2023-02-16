
package Interfaces;
import Entities.Consultation;
import java.util.List;

public interface IConsultationService {
    public void ajouterconsultation(Consultation c);
    public List<Consultation> afficherconsultation();
    public void supprimerconsultation(int id);
        public void Updateconsultation(String nom, String prenom,int age,String sexe ,String Email, String etat,String date,int id );
        public List<Consultation> trieconsultation();
}

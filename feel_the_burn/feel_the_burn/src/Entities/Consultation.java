
package Entities;


public class Consultation {
    private int Id_c;
    //private String Nom,Prenom,Sexe,Email,Etat_physique,Date_edv,categorie_c;
     private String Nom;
     private int Age;
     private String Sexe;
     private String Date_rdv;
     private String Etat_physique;
     private String categorie_c;
     private String Prenom;
     private String Email;
     
 public Consultation() {
         
    }

    public Consultation(int Id_c, String Nom, int Age, String Sexe, String Date_rdv, String Etat_physique, String categorie_c, String Prenom, String Email) {
        this.Id_c = Id_c;
        this.Nom = Nom;
        this.Age = Age;
        this.Sexe = Sexe;
        this.Date_rdv = Date_rdv;
        this.Etat_physique = Etat_physique;
        this.categorie_c = categorie_c;
        this.Prenom = Prenom;
        this.Email = Email;
    }
  
    

    

    public int getId_c() {
        return Id_c;
    }

    public void setId_c(int Id_c) {
        this.Id_c = Id_c;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String Sexe) {
        this.Sexe = Sexe;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEtat_physique() {
        return Etat_physique;
    }

    public void setEtat_physique(String Etat_physique) {
        this.Etat_physique = Etat_physique;
    }

    public String getDate_rdv() {
        return Date_rdv;
    }

    public void setDate_rdv(String Date_edv) {
        this.Date_rdv = Date_edv;
    }

    public String getcategorie_c() {
        return categorie_c;
    }

    public void setcategorie_c(String categorie_c) {
        this.categorie_c = categorie_c;
    }

    @Override
    public String toString() {
          
        return "Consultation{" + "Id_c=" + Id_c + ", Age=" + Age + ", Nom=" + Nom + ", Prenom=" + Prenom + ", Sexe=" + Sexe + ", Email=" + Email + ", Etat_physique=" + Etat_physique + ", Date_rdv=" + Date_rdv + ", categorie_c=" + categorie_c + '}';
    }
}

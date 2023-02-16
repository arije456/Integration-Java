
package Entities;


public class Coaching {
    private int Id_S;
    private String Date_S;
    private Float Prix;
    private int Id_Co;
    private String Nom_User;
    private String Prenom_User;
    

    public Coaching() {
    }

    public Coaching(int Id_S, String Date_S, Float Prix, int Id_Co, String Nom_User, String Prenom_User) {
        this.Id_S = Id_S;
        this.Date_S = Date_S;
        this.Prix = Prix;
        this.Id_Co = Id_Co;
        this.Nom_User = Nom_User;
        this.Prenom_User = Prenom_User;
    }



    public int getId_S() {
        return Id_S;
    }

    public void setId_S(int Id_S) {
        this.Id_S = Id_S;
    }

    public String getDate_S() {
        return Date_S;
    }

    public void setDate_S(String Date_S) {
        this.Date_S = Date_S;
    }

    public Float getPrix() {
        return Prix;
    }

    public void setPrix(Float Prix) {
        this.Prix = Prix;
    }

    public int getId_Co() {
        return Id_Co;
    }

    public void setId_Co(int Id_Co) {
        this.Id_Co = Id_Co;
    }

    public void setNom_User(String Nom_User) {
        this.Nom_User = Nom_User;
    }

    public void setPrenom_User(String Prenom_User) {
        this.Prenom_User = Prenom_User;
    }
    

    public String getNom_User() {
        return Nom_User;
    }

    public String getPrenom_User() {
        return Prenom_User;
    }

    @Override
    public String toString() {
        return "Coaching{" + "Id_S=" + Id_S + ", Date_S=" + Date_S + ", Prix=" + Prix + ", Id_Co=" + Id_Co + ", Nom_User=" + Nom_User + ", Prenom_User=" + Prenom_User + '}';
    }

   
}

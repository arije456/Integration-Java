
package Entities;


public class Cours {
     private int Id_C;
    private String Nom_C;
    private String Date_C;
    private int Id_Co;

    public Cours() {
    }
    
    

    public Cours(int Id_C, String Nom_C, String Date_C, int Id_Co) {
        this.Id_C = Id_C;
        this.Nom_C = Nom_C;
        this.Date_C = Date_C;
        this.Id_Co = Id_Co;
    }

    public int getId_C() {
        return Id_C;
    }

    public void setId_C(int Id_C) {
        this.Id_C = Id_C;
    }

    public String getNom_C() {
        return Nom_C;
    }

    public void setNom_C(String Nom_C) {
        this.Nom_C = Nom_C;
    }

    public String getDate_C() {
        return Date_C;
    }

    public void setDate_C(String Date_C) {
        this.Date_C = Date_C;
    }

    public int getId_Co() {
        return Id_Co;
    }

    public void setId_Co(int Id_Co) {
        this.Id_Co = Id_Co;
    }

    @Override
    public String toString() {
        return "Cours{" + "Id_C=" + Id_C + ", Nom_C=" + Nom_C + ", Date_C=" + Date_C + ", Id_Co=" + Id_Co + '}';
    }
}

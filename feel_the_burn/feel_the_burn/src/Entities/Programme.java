
package Entities;


public class Programme {
     private int Id,Id_c  ;
    private String Nom_p,Date_r,description ;
    private  int Id_kine;

    
    public Programme() {

    }

    
    public Programme(int Id, String Nom_p, String Date_r, int Id_kine, String description ,int Id_c) {
        this.Id = Id;
        this.Nom_p = Nom_p;
        this.Date_r = Date_r;
        this.Id_kine = Id_kine;
        this.description=description;
        this.Id_c = Id_c;
    }
    

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId_c() {
        return Id_c;
    }

    public void setId_c(int Id_c) {
        this.Id_c = Id_c;
    }

    public String getNom_p() {
        return Nom_p;
    }

    public void setNom_p(String Nom_p) {
        this.Nom_p = Nom_p;
    }

    public String getDate_r() {
        return Date_r;
    }

    public void setDate_r(String Date_r) {
        this.Date_r = Date_r;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public int getId_kine() {
        return Id_kine;
    }

    public void setId_kine(int Id_kine) {
        this.Id_kine = Id_kine;
    }

    @Override
    public String toString() {
        return "Programme{" + "Id=" + Id + ", Id_c=" + Id_c + ", Nom_p=" + Nom_p + ", Date_r=" + Date_r + ", description=" + description + ", Id_kine=" + Id_kine + '}';
    }   
}

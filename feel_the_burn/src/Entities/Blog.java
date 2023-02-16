
package Entities;


public class Blog {
    public static String pathfile; 
    public static String filename="";
    private int Id_b;
     private String Titre;
     private String Contenu;
     private String Date;
     private String Auteur;
     private String Image;
     private String categorie;

    public Blog() {
        
    }

    public Blog(int Id_b, String Titre, String Contenu, String Date, String Auteur, String Image, String categorie) {
        this.Id_b = Id_b;
        this.Titre = Titre;
        this.Contenu = Contenu;
        this.Date = Date;
        this.Auteur = Auteur;
        this.Image = Image;
        this.categorie = categorie;
    }
     
     public int getId_b() {
        return Id_b;
    }

    public String getTitre() {
        return Titre;
    }

    public String getContenu() {
        return Contenu;
    }

    public String getDate() {
        return Date;
    }

    public String getAuteur() {
        return Auteur;
    }

    public String getImage() {
        return Image;
    }

    public String getcategorie() {
        return categorie;
    }

    public void setId_b(int Id_b) {
        this.Id_b = Id_b;
    }

    public void setTitre(String Titre) {
        this.Titre = Titre;
    }

    public void setContenu(String Contenu) {
        this.Contenu = Contenu;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setAuteur(String Auteur) {
        this.Auteur = Auteur;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setcategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Blog{" + "Id_b=" + Id_b + ", Titre=" + Titre + ", Contenu=" + Contenu + ", Date=" + Date + ", Auteur=" + Auteur + ", Image=" + Image + ", categorie=" + categorie + '}';
    }

    
   
}


package Entities;

import javafx.scene.control.TextField;


public class Produit {
    public static String pathfile; 
    public static String filename="";
     private int Id_P;
    private String Nom_P;
    private Float Prix;
    private String Photo;
    private String Categories;

    public Produit() {
    }

    public Produit(int Id_P, String Nom_P, Float Prix, String Photo, String Categories) {
        this.Id_P = Id_P;
        this.Nom_P = Nom_P;
        this.Prix = Prix;
        this.Photo = Photo;
        this.Categories = Categories;
    }

    public Produit(int id, String text, Float prix, String filename, TextField aj_cat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId_P() {
        return Id_P;
    }

    public void setId_P(int Id_P) {
        this.Id_P = Id_P;
    }

    public String getNom_P() {
        return Nom_P;
    }

    public void setNom_P(String Nom_P) {
        this.Nom_P = Nom_P;
    }

    public Float getPrix() {
        return Prix;
    }

    public void setPrix(Float Prix) {
        this.Prix = Prix;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String Categories) {
        this.Categories = Categories;
    }

    @Override
    public String toString() {
        return "Produit{" + "Id_P=" + Id_P + ", Nom_P=" + Nom_P + ", Prix=" + Prix + ", Photo=" + Photo + ", Categories=" + Categories + '}';
    }
    
}

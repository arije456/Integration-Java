
package Entities;


public class Enfant {
    public static String pathfile; 
    public static String filename="";
    private int id_enfant;
    private String nom;
    private String prenom;
    private int age;
    private String sexe;
    private String photo;
    private int id_a;

    public Enfant() {
    }

    public Enfant(int id_enfant, String nom, String prenom, int age, String sexe, String photo, int id_a) {
        this.id_enfant = id_enfant;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
        this.photo = photo;
        this.id_a = id_a;
    }

    public int getId_enfant() {
        return id_enfant;
    }

    public void setId_enfant(int id_enfant) {
        this.id_enfant = id_enfant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId_a() {
        return id_a;
    }

    public void setId_a(int id_a) {
        this.id_a = id_a;
    }

    @Override
    public String toString() {
        return "Enfant{" + "id_enfant=" + id_enfant + ", nom=" + nom + ", prenom=" + prenom + ", age=" + age + ", sexe=" + sexe + ", photo=" + photo + ", id_a=" + id_a + '}';
    }
}

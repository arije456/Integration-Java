
package Entities;


public class Commentaire {
       private int Id_com;
   private String Nom_c;
   private String Email;
   private String Message;
   private String Date;
   private String Nom_article;
   private int Id_b;

    public Commentaire() {
    }

    public Commentaire(int Id_com, String Nom_c, String Email, String Message, String Date, String Nom_article, int Id_b) {
        this.Id_com = Id_com;
        this.Nom_c = Nom_c;
        this.Email = Email;
        this.Message = Message;
        this.Date = Date;
        this.Nom_article = Nom_article;
        this.Id_b = Id_b;
    }
   
   public int getId_com() {
        return Id_com;
    }

    public String getNom_c() {
        return Nom_c;
    }

    public String getEmail() {
        return Email;
    }

    public String getMessage() {
        return Message;
    }

    public String getDate() {
        return Date;
    }
     public int getId_b() {
        return Id_b;
    }

    public String getNom_article() {
        return Nom_article;
    }

    public void setId_com(int Id_com) {
        this.Id_com = Id_com;
    }

    public void setNom_c(String Nom_c) {
        this.Nom_c = Nom_c;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setNom_article(String Nom_article) {
        this.Nom_article = Nom_article;
    }
     public void setId_b(int Id_b) {
        this.Id_b = Id_b;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "Id_com=" + Id_com + ", Nom_c=" + Nom_c + ", Email=" + Email + ", Message=" + Message + ", Date=" + Date + ", Nom_article=" + Nom_article + ", Id_b=" + Id_b + '}';
    }

}

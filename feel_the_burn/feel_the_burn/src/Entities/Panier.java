
package Entities;


public class Panier {
    private int Id_Pa;
    private int Quantite;
    private String Coupon;
    private int Id_P;

    public Panier() {
    }

    public Panier(int Id_Pa, int Quantite, String Coupon, int Id_P) {
        this.Id_Pa = Id_Pa;
        this.Quantite = Quantite;
        this.Coupon = Coupon;
        this.Id_P = Id_P;
    }

    public int getId_Pa() {
        return Id_Pa;
    }

    public void setId_Pa(int Id_Pa) {
        this.Id_Pa = Id_Pa;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int Quantite) {
        this.Quantite = Quantite;
    }

    public String getCoupon() {
        return Coupon;
    }

    public void setCoupon(String Coupon) {
        this.Coupon = Coupon;
    }

    public int getId_P() {
        return Id_P;
    }

    public void setId_P(int Id_P) {
        this.Id_P = Id_P;
    }

    @Override
    public String toString() {
        return "Panier{" + "Id_Pa=" + Id_Pa + ", Quantite=" + Quantite + ", Coupon=" + Coupon + ", Id_P=" + Id_P + '}';
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Enfant;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author zeine
 */
public interface IEnfantServices <E>{
    public void ajouterEnfant(Enfant e);
    public List<Enfant> afficherEnfant();
    public void supprimerEnfant(int id);
    public void modifierEnfant(int id, String nom,String prenom, int age, String sexe, String image, int ida);
    
}

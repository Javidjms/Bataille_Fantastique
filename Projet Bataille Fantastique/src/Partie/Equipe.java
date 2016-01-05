package Partie;
import java.util.ArrayList;
import Personnage.Personnage;

/**
 * Cette classe représente une équipe de personnage qui est un ArrayList de Personnages
 */
public class Equipe extends ArrayList<Personnage>  {
	
	/**
	 * Ajoute un personnage dans l'équipe
	 * @param p personnage à ajouter
	 */
	public void ajouter(Personnage p){
		this.add(p);
	}
	
	/**
	 * Retourne un personnage de l'équipe
	 * @param i numéro du personnage 
	 * @return le personnage ayant le numéro i
	 */
	public Personnage getJoueur(int i){
		return this.get(i);
	}
	/**
	 * Retourne un booléen si le personnage  appartient à l'équipe
	 * @param p personnage 
	 * @return <code>true</code> si le personnage est dans l'équipe; <code>false</code> sinon
	 */
	public boolean possedePersonnage(Personnage p) {
		return this.contains(p);
	}


}

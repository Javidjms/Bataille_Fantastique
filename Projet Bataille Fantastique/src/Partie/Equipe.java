package Partie;
import java.util.ArrayList;
import Personnage.Personnage;

/**
 * Cette classe repr�sente une �quipe de personnage qui est un ArrayList de Personnages
 */
public class Equipe extends ArrayList<Personnage>  {
	
	/**
	 * Ajoute un personnage dans l'�quipe
	 * @param p personnage � ajouter
	 */
	public void ajouter(Personnage p){
		this.add(p);
	}
	
	/**
	 * Retourne un personnage de l'�quipe
	 * @param i num�ro du personnage 
	 * @return le personnage ayant le num�ro i
	 */
	public Personnage getJoueur(int i){
		return this.get(i);
	}
	/**
	 * Retourne un bool�en si le personnage  appartient � l'�quipe
	 * @param p personnage 
	 * @return <code>true</code> si le personnage est dans l'�quipe; <code>false</code> sinon
	 */
	public boolean possedePersonnage(Personnage p) {
		return this.contains(p);
	}


}

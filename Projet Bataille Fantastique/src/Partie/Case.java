package Partie;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import Personnage.Personnage;

/**
 * Cette classe représente une case du plateau
 */
public class Case extends Observable implements Serializable {
	private Set<Observer> observers = new HashSet<Observer>();
	private Personnage personnage;
	/**
	 * Constructeur qui crée une case vide 
	 */
	public Case(){
		personnage=null;
	}
	
	/**
	 * Ajoute un observateur sur cette case
	 */
	public void addObserver(Observer o){
		observers.add(o);
	}
	
	/**
	 * Retourne si la Case est vide ou non
	 * @return <code>true</code> si la case est vide; <code>false</code> sinon
	 */
	public boolean estVide() {
		return (personnage==null);
	}
	/**
	 * Retourne le personnage présent sur cette case
	 * @return le personnage de cette case
	 */
	public Personnage getPersonnage(){
		return personnage;
	}
	
	/**
	 * Libere le personnage de cette Case
	 */
	public void liberer(){
		personnage=null;
		notifyObservers();
	}
	/**
	 * Notifie les Observateurs d'un évènement
	 */
	public void notifyObservers(){
		for(Observer o:observers){
			o.update(this,this.personnage);
		}
	}
	
	/**
	 * Pose un personnage sur cette case
	 * @param _personnage personnage à poser
	 */
	public void poserPersonnage(Personnage _personnage){
		personnage=_personnage;
		notifyObservers();
	}
	/**
	 * Supprime un observateur
	 * @param o Observateur
	 */
	public void removeObserver(Observer o){
		observers.remove(o);
	}

}

package Partie;
import java.io.Serializable;

/**
 * Cette classe représente le tour courant du jeu
 */
public class Tour implements Serializable {
	
	private boolean etatAction;
	private boolean etatDéplacé;
	private boolean etatPlacement;
	private Joueur joueurCourant;
	private int MultipleAction=0;
	private int nbActionCourant=0;
	private int numeroTour;
	
	/**
	 * Constructeur qui initialise le début du tour du jeu
	 * @param j joueur qui commence
	 */
	public Tour(Joueur j){
		numeroTour=1;
		joueurCourant=j;
		etatDéplacé=false;
		etatAction=false;
		etatPlacement=false;
	}
	
	/**
	 * Retourne un booléen si l'attaque multiple est finie
	 * @return <code>true</code> si l'attaque multiple est finie; <code>false</code> sinon
	 */
	public boolean finMultipleAction(){
		return (nbActionCourant==MultipleAction);
	}
	
	/**
	 * Retourne le joueur courant
	 * @return joueur courant
	 */
	public Joueur getJoueurCourant(){
		return joueurCourant;
	}
	
	/**
	 * Retourne le numéro du tour courant
	 * @return numéro du tour courant
	 */
	public int getNumeroTour() {
		return numeroTour;
	}
	
	/**
	 * Met etatAction à vrai
	 */
	public void hasAction() {
		etatAction = true;
	}

	/**
	 * Met etatDeplacé à vrai
	 */
	public void hasDéplacé() {
		etatDéplacé = true;
	}

	/**
	 * Incrémente le compteur d'action multiple
	 * @param nbMultipleAction
	 */
	public void hasMultipleAction(int nbMultipleAction) {
		MultipleAction=nbMultipleAction;
		nbActionCourant++;
		
	}
	/**
	 * Met l'etatPlacement a vrai
	 */
	public void hasPlacé() {
		etatPlacement = true;
		
	}

	/**
	 * Retourne un booléen si l'action est finie
	 * @return <code>true</code> si l'action est finie; <code>false</code> sinon
	 */
	public boolean isAction() {
		return etatAction;
	}

	/**
	 * Retourne un booléen si le deplacement est finie
	 * @return <code>true</code> si le deplacement est finie; <code>false</code> sinon
	 */
	public boolean isDéplacé() {
		return etatDéplacé;
	}

	/**
	 * Retourne un booléen si l'action est multiple
	 * @return <code>true</code> si l'action  est multiple; <code>false</code> sinon
	 */
	public boolean isMultipleAction(){
		return (MultipleAction>0);
	}

	/**
	 * Retourne un booléen si le placement est finie
	 * @return <code>true</code> si le placement est finie; <code>false</code> sinon
	 */
	public boolean isPlacé() {
		return etatPlacement;
	}

	/**
	 * Passe la main au joueur suivant
	 */
	public void passerlaMain(){
		numeroTour++;
		etatDéplacé=false;
		etatAction=false;
		MultipleAction=0;
		nbActionCourant=0;
	}
	
	/**
	 * Met a jour le joueur courant
	 * @param j nouveau joueur courant
	 */
	public void setJoueurCourant(Joueur j){
		 joueurCourant=j;
	}
	
	
}

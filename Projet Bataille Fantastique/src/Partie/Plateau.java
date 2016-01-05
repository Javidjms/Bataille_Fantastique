package Partie;

import java.io.Serializable;

import Personnage.Personnage;

/**
 * Cette classe repr�sent le plateau du jeu
 */
public class Plateau implements Serializable {
	
	private Case[][] plateau;
	
	/**
	 * Constructeur qui initialise le plateau du jeu de taille X*Y
	 * @param TailleX taille X du plateau (en largeur)
	 * @param TailleY taille Y du plateau (en longueur)
	 */
	public Plateau(int TailleX,int TailleY){
		plateau = new Case[TailleX][TailleY];
		for(int i=0;i<TailleX;i++){
			for(int j=0;j<TailleY;j++){
				plateau[i][j]=new Case();
			}	
		}
	}
	
	/**
	 * Retourne un bool�en si la case est vide
	 * @param position de la case � verifier
	 * @return <code>true</code> si la case est vide; <code>false</code> sinon
	 */
	public boolean caseEstVide(int[] position) {
		return this.getCase(position).estVide();		
	}

	/**
	 * Retourne le personnage pr�sent sur cette case
	 * @param position position de la case du personnage � recuperer 
	 * @return le personnage de cette case
	 */
	public Personnage caseGetPersonnage(int[] position) {
		return this.getCase(position).getPersonnage();
	}

	/**
	 * Retourne une case
	 * @param position position de la case voulue
	 * @return case � la position voulue
	 */
	public Case getCase(int[] position) {
		int x =position[0];
		int y =position[1];
		return this.plateau[x][y];
	}

	/**
	 * Libere le personnage de cette Case
	 * @param position position de la case � liberer
	 */
	public void libererCase(int[] position){
		this.getCase(position).liberer();
	}
	
	/**
	 * Pose un personnage sur cette case
	 * @param p personnage � poser
	 * @param position position de la case o� poser le personnage
	 */
	public void poser(Personnage p, int[] position) {
		this.getCase(position).poserPersonnage(p);
	}

}

package Partie;

import java.awt.Color;
import java.io.Serializable;
import Personnage.Personnage;

/**
 * Cette Classe représente un joueur
 */
public class Joueur implements Serializable {

	private Color color;
	private Equipe equipe;
	private String nom;
	
	/**
	 * Constructeur initialisant le joueur avec sa couleur pour les icones
	 * @param c couleur choisie
	 */
	public Joueur(Color c){
		this("Joueur");
		color=c;
	}
	/**
	 * Constructeur initialisant le joueur avec son nom (Non officiel)
	 * @param _nom
	 */
	public Joueur(String _nom){
		this.nom=_nom;
		equipe = new Equipe();
	}

	/**
	 * Ajoute un personnage à son équipe
	 * @param p personnage à ajouter
	 */
	public void ajouterPersonnage(Personnage p){
		this.equipe.ajouter(p);
	}

	/**
	 * Retourne la couleur du joueur
	 * @return couleur du joueur
	 */
	public Color getColor(){
		return color;
	}

	/**
	 * Retourne l'équipe du joueur
	 * @return l'équipe du joueur
	 */
	public Equipe getEquipe() {
		return equipe;
	}
	
	/**
	 * Retourne le nom du joueur
	 * @return le nom du joueur
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Retourne un personnage dans l'équipe
	 * @param i numero du joueur dans l'équipe
	 * @return le joueur numero i dans l'équipe
	 */
	public Personnage getPersonnage(int i){
		return equipe.getJoueur(i);
	}

	/**
	 * Retourne un booléen si le personnage est présent dans l'équipe
	 * @param p personnage à verifier
	 * @return <code>true</code> si le personnage est dans l'équipe; <code>false</code> sinon
	 */
	public boolean possedePersonnage(Personnage p) {
		return this.equipe.possedePersonnage(p);
	}
	/**
	 * Met le nom du joueur
	 * @param nom String à mettre comme nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	

}

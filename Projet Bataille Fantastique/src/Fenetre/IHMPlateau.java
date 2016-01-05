package Fenetre;
import javax.swing.*;

import Partie.Jeu;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.Serializable;

/**
 * Classe affichant l'interface graphique du plateau
 */
public class IHMPlateau extends JPanel implements Serializable {
	
	private IHMCase[][] Guiplateau;
	
	/**
	 * Constructeur qui initialise le plateau graphique (tableau de Jbutton)
	 * @param ihmjeu la fenetre du jeu
	 * @param jeu le controleur du jeu
	 * @param tailleX la taille X du plateau (en largeur)
	 * @param tailleY la taille Y du plateau (en longueur)
	 */
	public IHMPlateau(IHMJeu ihmjeu,Jeu jeu,int tailleX, int tailleY){
		Guiplateau= new IHMCase[tailleX][tailleY];
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx=20;
		c.ipady=20;
		for(int i=0; i<tailleX; i++){
				for(int j=0;j<tailleY;j++){
				int[] position ={i,j};
				Guiplateau[i][j] = new IHMCase(jeu,position);
				c.gridx=i;
				c.gridy=j;
				this.add(Guiplateau[i][j],c);
				}
			}
		this.setBackground(Color.GRAY);
		this.setVisible(true);
	}
	/**
	 * Retourne une IHMCase
	 * @param position position de la case graphique voulue
	 * @return la case voulue
	 */
	public IHMCase getGuiCase(int[] position){
		int x=position[0];
		int y=position[1];
		return Guiplateau[x][y];
	}
	
}

package Fenetre;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Classe affichant l'interface graphique d'une case (JButton)
 */
public class IHMCase extends JButton implements Observer,Serializable {	
	private Jeu jeu;
	private int[] position;

	/**
	 * Constructeur qui crée et initialise le JButton
	 * @param _jeu le controleur du jeu
	 * @param _position tableau contenant les coordonnée [x,y] de la case 
	 */
	public IHMCase(Jeu _jeu,int[] _position){
		jeu=_jeu;
		position=_position;
		this.setPreferredSize(new Dimension(40,20));
		this.setBackground(Color.WHITE);
		this.addActionListener(
				new ActionListenerSerializable() {
					public void actionPerformed(ActionEvent e){
						jeu.setpositionCaseSelectionné(position);
						jeu.fonction();
						jeu.voirInfoPersonnage(position);
					}
				});
	} 

	/**
	 * Met à jour le JButton par le biais du design pattern Observer/Observable
	 */
	public void update(Observable o, Object arg) {
		if(arg!=null){
			Personnage p= (Personnage)arg;
			this.setIcon(p.getMiniIcon());
			this.setBackground(jeu.getJoueurCourant().getColor());
			}
		else{
			this.setIcon(null);
			this.setBackground(Color.WHITE);
		}
	}

	

	
}

package Fenetre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import Partie.Jeu;
import Partie.Jeu.Phase;

/**
 * Classe permettant d'afficher les boutons commandes pour le déplacement, les actions et
 * la fin du tour 
 */
public class IHMCommandeVue2 extends JPanel implements Serializable {
	private JToggleButton bouton1;
	private JButton bouton2,bouton3;
	private IHMJeu ihmjeu;
	private Jeu jeu;
	
	/**
	 * Constructeur qui afficher les boutons Deplacer,Actions et Fin du Tour
	 * @param ie interface graphique du jeu
	 * @param _jeu controleur du jeu
	 */
	public IHMCommandeVue2(IHMJeu ie, Jeu _jeu){
		ihmjeu = ie;
		jeu=_jeu;
		bouton1=new JToggleButton("Deplacer");
		bouton2=new JButton("Action");
		bouton3=new JButton("Fin du Tour");
		bouton1.setPreferredSize(new Dimension(150,40));
		bouton2.setPreferredSize(new Dimension(150,40));
		bouton3.setPreferredSize(new Dimension(150,40));
		bouton1.setEnabled(false);
		bouton2.setEnabled(false);
		bouton3.setEnabled(false);
		bouton1.setIcon(new ImageIcon("image/deplacement.png"));
		bouton2.setIcon(new ImageIcon("image/action.png"));
		bouton3.setIcon(new ImageIcon("image/fintour.png"));
		this.add(bouton1);
		this.add(bouton2);
		this.add(bouton3);
		this.setSize(300, 300);
		this.setBackground(Color.GRAY);
		this.setVisible(true);
		bouton1.addActionListener(
				new ActionListenerSerializable() {
					public void actionPerformed(ActionEvent e){deplacementAppuyé();
					}
				});
		bouton2.addActionListener(
				new ActionListenerSerializable() {
					public void actionPerformed(ActionEvent e){jeu.IhmActionAppuyé();
					}
				});
		bouton3.addActionListener(
				new ActionListenerSerializable() {
					public void actionPerformed(ActionEvent e){jeu.IhmFinTour();
					}
				});
	}
	
	/**
	 * Desactive le bouton Action lorsqu'elle est finite
	 */
	public void actionFinie(){
		bouton2.setEnabled(false);
	}
	
	/**
	 * Active le bouton Action si elle est multiple
	 */
	public void actionMultiple() {
		bouton2.setEnabled(true);
		
	}

	/**
	 * Active tous les boutons
	 */
	public void boutonActive() {
		bouton1.setEnabled(true);
		bouton2.setEnabled(true);
		bouton3.setEnabled(true);
		
	}

	/**
	 * Desactive tous les boutons
	 */
	public void boutonDesactive() {
		bouton1.setEnabled(false);
		bouton2.setEnabled(false);
		bouton3.setEnabled(false);
	}
	
	/**
	 * Desactive/Active les autres boutons si le bouton Deplacer est selectionné
	 */
	public void deplacementAppuyé(){
		if(bouton1.isSelected()){
			bouton2.setEnabled(false);
			bouton3.setEnabled(false);
			jeu.setPhase(Phase.DEPLACEMENT_PERSONNAGE);
			jeu.IhmVoirCasePossibleDeplacement();
			ihmjeu.afficherTerminal("Selectionner la Case où vous voulez déplacer le personnage ");
		}
		else{
			if(!jeu.isAction())
				bouton2.setEnabled(true);
			bouton3.setEnabled(true);
			jeu.setPhase(Phase.NORMAL);
			jeu.IhmFinVoirCasePossibleDeplacement();
			ihmjeu.afficherTerminal("Selectionner une Commande");
		}
	}

	/**
	 * Desactive le bouton Deplacer lorsqu'elle est finie
	 */
	public void deplacementFinie() {
		bouton1.setEnabled(false);
		if(!jeu.isAction())
			bouton2.setEnabled(true);
		bouton3.setEnabled(true);
		
	}
	
}

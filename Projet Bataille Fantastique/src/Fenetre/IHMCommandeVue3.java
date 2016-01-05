package Fenetre;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import Partie.Jeu;
import Partie.Joueur;
import Personnage.Personnage;

/**
 * Classe permettant d'afficher les boutons commandes des actions d'un personnage sélectionné
 */
public class IHMCommandeVue3 extends JPanel implements Serializable {
	private JToggleButton[] bouton;
	private JToggleButton boutonRetour;
	private IHMJeu ihmjeu;
	private Jeu jeu;
	private Personnage p;
	
	/**
	 * Constructeur qui afficher les boutons Actions du personnage sélectionné
	 * @param _p personnage sélectionné
	 * @param ie interface graphique du jeu
	 * @param _jeu controleur du jeu
	 * @param joueur joueur courant
	 */
	public IHMCommandeVue3(Personnage _p,IHMJeu ie, Jeu _jeu,Joueur joueur){
		p=_p;
		ihmjeu =ie;
		jeu=_jeu;
		bouton=new JToggleButton[p.getActions().size()+1];
		for(int i=0;i<p.getActions().size();i++){
			bouton[i]=new JToggleButton(p.getAction(i).getSimpleName());
			bouton[i].setPreferredSize(new Dimension(200,40));
			bouton[i].setIcon(new ImageIcon("image/action.png"));
			this.add(bouton[i]);
			final int idAction =i;
			bouton[i].addActionListener(
					new ActionListenerSerializable() {
						public void actionPerformed(ActionEvent e){boutonActive(idAction);
						}
					});
		}
		boutonRetour=new JToggleButton("Retour");
		boutonRetour.addActionListener(
				new ActionListenerSerializable() {
					public void actionPerformed(ActionEvent e){jeu.IhmFinAction();
					}
				});
		boutonRetour.setPreferredSize(new Dimension(200,40));
		this.add(boutonRetour);
		this.setSize(300, 300);
		this.setVisible(true);
		this.setBackground(Color.GRAY);
		if(!jeu.finMultipleAction() && jeu.isMultipleAction())
			this.MultipleAction();
	}

	/**
	 * Active/Desactive les autres boutons actions
	 * @param idAction numero du bouton Action selectionné
	 */
	public void boutonActive(int idAction){
		if(bouton[idAction].isSelected()){
			for(int i=0;i<p.getActions().size();i++){
				if(i!=idAction){
					bouton[i].setEnabled(false);
				}
			}	
			boutonRetour.setEnabled(false);
			jeu.setidActionSelectionné(idAction);
			jeu.IhmVoirCasePossibleAction(idAction);
			ihmjeu.afficherTerminal("Selectionner la Case où vous voulez lancer l'action");
		}
		else{
			if(!jeu.isMultipleAction()){
				for(int i=0;i<p.getActions().size();i++){
					bouton[i].setEnabled(true);
				}
				
			}
			ihmjeu.afficherTerminal("Selectionner une Action");
			boutonRetour.setEnabled(true);
			jeu.IhmFinVoirCasePossibleAction(idAction);
			jeu.setidActionSelectionné(-1);
		}
	}
	
	/**
	 * Desactive les autres boutons Actions en cas d'action multiple
	 */
	public void MultipleAction(){
		for(int i=0;i<p.getActions().size();i++){
			if(i!=jeu.getidActionSelectionné())
				bouton[i].setEnabled(false);
		}
	}
	
}

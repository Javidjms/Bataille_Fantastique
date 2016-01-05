package Fenetre;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import Partie.Jeu;
import Partie.Jeu.Phase;
import Partie.Joueur;

/**
 * Classe permettant d'afficher les boutons personnages pour les placer sur le plateau 
 */
public class IHMCommandeVue1 extends JPanel implements Serializable {
	private JToggleButton[] bouton;
	private Jeu jeu;
	
	/**
	 * Constructeur qui afficher les boutons personnages
	 * @param _jeu controleur du jeu
	 * @param joueur joueur qui commence par placer les personnages sur le plateau
	 */
	public IHMCommandeVue1( Jeu _jeu,Joueur joueur){
		jeu=_jeu;
		bouton=new JToggleButton[jeu.getNbPersonnage()];
		for(int i=0;i<jeu.getNbPersonnage();i++){
			final int p=i;
			bouton[i]=new JToggleButton(joueur.getPersonnage(i).toString());
			bouton[i].setIcon(joueur.getPersonnage(i).getMiniIcon());
			bouton[i].setPreferredSize(new Dimension(200,40));
			this.add(bouton[i]);	
			bouton[i].addActionListener(
					new ActionListenerSerializable() {
						public void actionPerformed(ActionEvent e){jeu.setPersonnageSelectionné(p);boutonActive(p);
						}
					});
		}
		this.setBackground(Color.GRAY);
		this.setSize(300, 300);
		this.setVisible(true);
	}
	
	/**
	 * Desactive/Réactive les autres bouton non selectionné
	 * @param idPersonnage numero du bouton personnage selectionné 
	 */
	public void boutonActive(int idPersonnage){
		if(bouton[idPersonnage].isSelected()){
			for(int i=0;i<jeu.getNbPersonnage();i++){
				if(i!=idPersonnage){
					bouton[i].setEnabled(false);
				}
			}
			jeu.IhmVoirCasePossiblePlacement();
			jeu.setPhase(Phase.PLACEMENT_PERSONNAGE);
		}
		else{
			for(int i=0;i<jeu.getNbPersonnage();i++){
				bouton[i].setEnabled(true);
				jeu.IhmFinVoirCasePossiblePlacement();
				jeu.setPhase(Phase.NORMAL);
			}
		}
	}
	
	/**
	 * Retourne si tous les personnages ont été mis sur le plateau
	 * @return <code>true</code> si tous les personnages sont posés; <code>false</code> sinon
	 */
	public boolean finPlacement(){
		for(JToggleButton b:bouton){
			if(b.isVisible())
				return false;
		}
		return true;
	}
	
	/**
	 * Desactive le bouton du personnage posé
	 * @param idPersonnage numero du bouton du personnage posé
	 */
	public void placementReussi(int idPersonnage){
		bouton[idPersonnage].setVisible(false);
		bouton[idPersonnage].setSelected(false);
	}
	
	
}

package Fenetre;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Partie.Jeu;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Classe affichant la fenêtre permettant de choisir le nombre de personnage
 * par équipe
 *
 */
 public class FenetreSelectionNb extends JFrame {
		private JButton jbouton;
		private Jeu jeu;
		private JLabel jlabel1;
		private JTextField jtext;
	
	/**
	 * Constructeur qui affiche la fenêtre de sélection	du nombre de personnage
	 * @param _jeu le controleur du jeu
	 */
	public FenetreSelectionNb(Jeu _jeu){
		super("Bataille Fantastique");
		jeu = _jeu;
		jlabel1=new JLabel("Entrer le nombre de personnage par équipe entre 1 et 5");
		jtext=new JTextField();
		jbouton=new JButton("Valider");
		this.setSize(350,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		this.add(jlabel1);
		this.add(jtext);
		this.add(jbouton);
		this.setLocationRelativeTo(null);
		this.getRootPane().setDefaultButton(jbouton);
		this.setVisible(true);
		
	
		jbouton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e){valider();}
				});
		
	}
	/**
	 * Récupère le texte entré dans le JTextField jtext et teste si la valeur est correcte (compris entre 1 et 5)
	 * Affiche ensuite la fenêtre de sélection de la taille du plateau
	 */
	public void valider(){
		int num;
		try {
		     num = Integer.parseInt(jtext.getText());
			 jeu.verifiernbPersonnagesEquipe(num);
			 this.dispose();
			 new FenetreSelectionTaille(jeu);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Le nombre de personnage par équipe doit être entre 1 et 5", e.toString(),
                    JOptionPane.ERROR_MESSAGE);
		}

		
	}
		

}


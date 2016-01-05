package Fenetre;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Partie.Jeu;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe affichant la fenêtre permettant de choisir la taille du plateau
 * 
 *
 */
 public class FenetreSelectionTaille extends JFrame {

	private JButton jbouton;
	private Jeu jeu;
	private JLabel jlabel1,jlabelX,jlabelY;
	private JPanel jpanel1;
	private JTextField jtextX,jtextY;
	
	
	/**
	 * Constructeur qui affiche la fenêtre de sélection de la taille
	 * @param _jeu le controleur du jeu
	 */
	public FenetreSelectionTaille(Jeu _jeu){
		super("Bataille Fantastique");
		jeu = _jeu;
		jpanel1 = new JPanel();
		jlabel1=new JLabel("Entrer la taille du plateau entre 5x10 et 10x15");
		jtextX=new JTextField();
		jtextY=new JTextField();
		jbouton=new JButton("Valider");
		jlabelX = new JLabel("X :");
		jlabelY = new JLabel("Y :");
		jpanel1.setLayout(new GridLayout(2,2));
		jpanel1.add(jlabelX);
		jpanel1.add(jtextX);
		jpanel1.add(jlabelY);
		jpanel1.add(jtextY);
		this.setSize(300,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		this.add(jlabel1);
		this.add(jpanel1);
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
	 * Récupère les texte entré dans les JTextField jtextX et jtextY et teste si leur valeur est correcte 
	 * (compris entre 5 et 10 pour X et 10 et 15 pour Y)
	 * Affiche ensuite la fenêtre récapitulative
	 */
	public void valider(){
		int numx,numy;
		try {
		     numx = Integer.parseInt(jtextX.getText());
		     numy = Integer.parseInt(jtextY.getText());
			 jeu.verifierTaille(numx,numy);
			 this.dispose();
			 new FenetreRecapitulatif(jeu);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "La taille doit être comprise entre 5x10 et 15x30",e.toString(),
                    JOptionPane.ERROR_MESSAGE);
		}
		
	}
		

}


package Test;

import Exception.NbPersonnageIncorrect;
import Exception.TaillePlateauIncorrect;
import Partie.Jeu;
import Personnage.*;

/**
 * Classe Test Avance préconfiguré (utile pour tester une iteration rapidement)
 */
public class TestAvance {
	public static void main(String[] args) throws NbPersonnageIncorrect, TaillePlateauIncorrect, InstantiationException, IllegalAccessException {
		Jeu jeu=new Jeu("TEST");
		jeu.verifiernbPersonnagesEquipe(3);
		jeu.verifierTaille(10,15);
		jeu.setnomjoueur(jeu.getJoueur1(),"Javid");
		jeu.setnomjoueur(jeu.getJoueur2(),"Steven");
		jeu.ajouterPersonnage(jeu.getJoueur1(), Magicien.class);
		jeu.ajouterPersonnage(jeu.getJoueur1(), Guerrier.class);
		jeu.ajouterPersonnage(jeu.getJoueur1(), CavalierCeleste.class);
		jeu.ajouterPersonnage(jeu.getJoueur2(), CavalierCeleste.class);
		jeu.ajouterPersonnage(jeu.getJoueur2(), Voleur.class);
		jeu.ajouterPersonnage(jeu.getJoueur2(), Voleur.class);
		jeu.getJoueur1().getPersonnage(0).setNom("Mage Léo");
		jeu.getJoueur1().getPersonnage(1).setNom("Gu Alpha");
		jeu.getJoueur1().getPersonnage(2).setNom("Cv Arrow");
		jeu.getJoueur2().getPersonnage(0).setNom("Cv Beta");
		jeu.getJoueur2().getPersonnage(1).setNom("Vol Kid");
		jeu.getJoueur2().getPersonnage(1).setNom("Vol Vol");
		jeu.init();
	}

}

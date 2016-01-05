package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe repr�sente l'action "Terre Mar�cageuse"
 */
public class TerreMarecageuse extends Action {
	/**
	 * Elle n'inflige pas de d�gat au personnage attaqu�
	 */
	private static int degat=0;
	/**
	 * La port�e minimale de cette action est de 2 cases
	 */
	private static int porteeMin=2;
	/**
	 * Le type de cette action est Normale
	 */
	private static typeAction typeaction=typeAction.NORMAL;
	/**
	 * Le type de port�e de cette action est cibl�e (Attaque Cibl�e)
	 */
	private static typePortee typeportee=typePortee.CIBLE;
	/**Retourne le nombre de d�gats qu'inflige cette action
	 * @return variable degat
	 */
	public static int getDegat() {
		return degat;
	}
	
	/**
	 * Retourne la description de l'attaque du personnage
	 * @return un String contenant la description
	 */
	public static String getDescription(){
		return "<HTML><BODY>la terre mar�cageuse  ralentit toutes les cibles d�une zone, "
				+ "de 9 cases, centr�e<BR> autour d�une case cible</BODY></HTML>";
	}


	/**
	 * Retourne la port�e minimum de l'action
	 * @return variable port�eMin
	 */
	public static int getPorteeMin() {
		return porteeMin;
	}

	/**
	 * Retourne le type de l'action
	 * @return variable typeaction
	 */
	public static typeAction getTypeAction() {
		return typeaction;
	}
	
	/**
	 * Retourne le type de port�e de l'action
	 * @return variable typeport�e
	 */
	public static typePortee getTypePortee() {
		return typeportee;
	}
	
	/**
	 * Routine de l'action de Terre Mar�cageuse sur le personnage attaqu�
	 * (instanci� uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqu�
	 * @param jeu le controleur du jeu
	 */
	public TerreMarecageuse(Personnage p,Jeu jeu){
		if(!p.isRalenti())
			p.hasRalenti(jeu.getTour().getNumeroTour());
	}
}

package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe repr�sente l'action "Tremblement de Terre"
 */
public class TremblementDeTerre extends Action {
	/**
	 * Elle inflige 3 point de d�gat au personnage attaqu�
	 */
	private static int degat=3;
	/**
	 * La port�e minimale de cette action est de 2 cases
	 */
	private static int porteeMin=2;
	/**
	 * Le type de cette action est une action Terreste (elle ne vise que les personnage terrestres)
	 */
	private static typeAction typeaction=typeAction.SOL;
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
		return "<HTML><BODY>le tremblement de terre  inflige 3 dommages aux cr�atures terrestres"
				+ " situ�es dans une <BR> zone, de 9 cases, centr�e autour d�une case cible</BODY></HTML>";
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
	 * Routine de l'action de Tremblement de Terre sur le personnage attaqu�
	 * (instanci� uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqu�
	 * @param jeu le controleur du jeu
	 */
	
	public TremblementDeTerre(Personnage p,Jeu jeu){
		if(!p.getSaitVoler())
			p.reduirePV(degat);
	}
}

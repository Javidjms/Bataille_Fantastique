package Action;
import Partie.Jeu;
import Personnage.Personnage;

/**
 * Cette classe repr�sente l'action "Temp�te"
 */
	public class Tempete extends Action {
	/**
	 * Elle inflige 4 point de d�gat au personnage attaqu�
	 */
	private static int degat=4;
	/**
	 * La port�e minimale de cette action est de 2 cases
	 */
	private static int porteeMin=2;
	/**
	 * Le type de cette action est une action a�rienne (elle ne vise que les personnage aeriens)
	 */
	private static typeAction typeaction=typeAction.VOL;
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
		return "<HTML><BODY>la temp�te  inflige 4 dommages aux cr�atures volantes dans une zone,"
				+ " de 9 cases <BR>, centr�e autour d�une case cible </BODY></HTML>";
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
	 * Routine de l'action de Temp�te sur le personnage attaqu�
	 * (instanci� uniquement lors d'une attaque)
	 * @param p Le personnage qui est attaqu�
	 * @param jeu le controleur du jeu
	 */
	public Tempete(Personnage p,Jeu jeu){
		if(p.getSaitVoler())
			p.reduirePV(degat);
	}
}

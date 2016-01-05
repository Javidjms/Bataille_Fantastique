
package Action;

/**
 * La classe Action est une classe Abstraite dont h�rite tous les actions (sorts) des personnages. 
 */
abstract public class Action {
	
	/**
	 *Le type �num�r� pour une action qui d�finit si c'est une attaque terrestre, a�rienne ou normale 
	 */
	public static enum typeAction {NORMAL,SOL,VOL};
	/**
	 *Le type �num�r� pour la port�e de l'action qui d�finit si elle est une attaque cibl�, normale 
	 *ou sur le personnage qui lance l'attaque (ex: Instinct Esquive)
	 */
	public static enum typePortee {CIBLE,NORMAL,SELF};

	
	
}

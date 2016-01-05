
package Action;

/**
 * La classe Action est une classe Abstraite dont hérite tous les actions (sorts) des personnages. 
 */
abstract public class Action {
	
	/**
	 *Le type énuméré pour une action qui définit si c'est une attaque terrestre, aérienne ou normale 
	 */
	public static enum typeAction {NORMAL,SOL,VOL};
	/**
	 *Le type énuméré pour la portée de l'action qui définit si elle est une attaque ciblé, normale 
	 *ou sur le personnage qui lance l'attaque (ex: Instinct Esquive)
	 */
	public static enum typePortee {CIBLE,NORMAL,SELF};

	
	
}

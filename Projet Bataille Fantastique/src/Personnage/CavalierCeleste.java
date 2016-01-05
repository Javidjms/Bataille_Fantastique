package Personnage;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Action.*;

public class CavalierCeleste extends Personnage {
	private static int _PM=2;
	private static int _PMD=2;
	private static int _PV=8;
	private static ArrayList<Class<?>> action=actionInit();
	private static int age =30;
	
	public static ArrayList<Class<?>> actionInit(){
		action=new ArrayList<Class<?>>();
		action.add(EnchevetrementDeRonces.class);
		action.add(CoupDeJarnac.class);
		action.add(NuageMalefique.class);
		return action;
	}
	
	public static ArrayList<String> afficherActions() {
		ArrayList<String> res=new ArrayList<String>();
		for(int i=0;i<actionInit().size();i++){
			res.add(actionInit().get(i).getSimpleName());		
		}
		return res;
	}

	public static int getAge(){
		return age;
	}
	public static int getPM(){
		return _PM;
	}

	public static int getPMD(){
		return _PMD;
	}

	public static int getPV(){
		return _PV;
	}

	private Icon miniIcon;
	private ImageIcon carteIcon;

	public CavalierCeleste() {
		super(_PV, "Cavalier Celeste", _PM,_PMD, true,action);
		miniIcon=new ImageIcon("image/minicavalier.png");
		carteIcon=new ImageIcon("image/cartecavalier.png");
		
	}
	
	
	public void deplacer(int[] _position) {
		this.setPosition(_position);
	}
	
	public Class<?> getAction(int idAction) {
		return action.get(idAction);
	}
	public ArrayList<Class<?>> getActions() {
		return action;
	}
	public Icon getMiniIcon() {
		return miniIcon;
	}
	
	public String toString(){
		return "CavalierCeleste";
	}

	public  ImageIcon getCarteIcon() {
		return carteIcon;
	}
}

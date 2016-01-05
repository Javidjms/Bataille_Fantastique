package Personnage;
import Action.*;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Guerrier extends Personnage {
	private static int _PM=2;
	private static int _PMD=0;
	private static int _PV=12;
	private static ArrayList<Class<?>> action=actionInit();
	private static int age =27;
	public static ArrayList<Class<?>> actionInit(){
		action=new ArrayList<Class<?>>();
		action.add(CoupDeTaille.class);
		action.add(BalisteDeFeu.class);
		action.add(CoupDeJarnac.class);
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

	public Guerrier() {
		super(_PV, "Guerrier", _PM,_PMD, false,action);
		miniIcon=new ImageIcon("image/miniguerrier.png");
		carteIcon=new ImageIcon("image/carteguerrier.png");
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
		return "Guerrier";
	}
	
	public  ImageIcon getCarteIcon() {
		return carteIcon;
	}

}

package tv.mineinthebox.essentials.helpers;



public class EnumHelper {
	
	public static <E extends  Enum<E>> boolean isDefined(Class<E> clazz, String name) {
		if(name == null) {return false;}
		try {
			Enum.valueOf(clazz, name);
			return true;
		} catch(IllegalArgumentException e) {
			return false;
		}
	}
	
	public static <E extends Enum<E>> E getEnum(Class<E> clazz, String name) {
		try {
			return Enum.valueOf(clazz, name); 
		} catch(IllegalArgumentException e) {
			return null;
		}
	}

}

package frc.robot.Util;

import edu.wpi.first.wpilibj.Preferences;

/** if our code is colonial America, this class is Rhode Island */
public class Util {
	
	public static double celsiusToFahrenheit(double celsius) {
		return (celsius * (9/5)) + 32;
	}
	/**
	 *	kind of self explanatory, but with some spice
	 *	use this mainly as a get method to retrieve values the user types into the smart dash
	 *		(the 'Set' part is only in case the value doesn't exist, backup is a default to use and set if it isn't there)
	 */
	static Preferences pref = Preferences.getInstance();
	public static double getAndSetDouble(String key, double backup) {
		if(!pref.containsKey(key)) pref.putDouble(key, backup);
		return pref.getDouble(key, backup);
	}
}
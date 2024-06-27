package controller;

import java.util.Map;

/**
 * Bedrijf moet geinitialiseerd zijn voordat de simulatie worden aangemaakt
 */
public interface ModelBedrijfssimulatie extends ModelBedrijf{
		
	public abstract void sluitVestiging(String plaats);
	
	public abstract void openVestiging(String plaats);

	public abstract Map<String, Integer> getSimVestigingenMap();
}

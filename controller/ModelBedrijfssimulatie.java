package controller;

import java.util.Map;

import exceptions.PoiException;

/**
 * Bedrijf moet geinitialiseerd zijn voordat de simulatie worden aangemaakt
 */
public interface ModelBedrijfssimulatie extends ModelBedrijf{
		
	public abstract void sluitVestiging(String plaats);
	
	public abstract void openVestiging(String plaats);
	
   /*
    * Maakt lijst met aantal klanten per vestiging 
    */
   public abstract Map<String, Integer> getSimVestigingenMap();
	
}

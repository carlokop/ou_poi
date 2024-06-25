package controller;

import exceptions.PoiException;

/**
 * Bedrijf moet geinitialiseerd zijn voordat de simulatie worden aangemaakt
 */
public interface ModelBedrijfssimulatie extends ModelBedrijf{
	
	public abstract void setupSimulatie() throws PoiException;
	
	public abstract void sluitVestiging(String plaats);
	
	public abstract void openVestiging(String plaats);
	
	// Controleert preconditie dat Bedrijf geinitialiseerd is en dus dat de klassenparameters toegekend zijn.
	public abstract void validate() throws PoiException;
	
}

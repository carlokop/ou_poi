package controller;

import java.util.Collection;

/**
 * Facade controller
 * regelt communicatie tussen de GUI en de domeinlaag
 */
public class Controller {
	
	private ModelBedrijf mb;
	private ModelBedrijfssimulatie mbs;
	
	/**
	 * TODO: Aanpassen voor het model van de simulatie
	 * Instantieert de facade en stelt het model in
	 * @param mb    model
	 */
	public Controller(ModelBedrijf mb, ModelBedrijfssimulatie mbs){
		this.mb = mb;
		this.mbs = mbs;
	}

	/**
	 * Haalt alle plaatsnamen van vestigingen op
	 * @return lijst met plaatsnamen
	 */
	public Collection<String> getVestigingPlaatsen() {
	  return mb.getVestigingPlaatsen();
	}
	
	/**
	 * Haalt een lijst op met alle klant id's die in de opgegeven vestiging zitten
	 * @param plaats vestiging plaatsnaam
	 * @return lijst met klant id's 
	 */
	public Collection<String> getVestigingKlanten(String plaats) {
	  return mb.getVestigingKlanten(plaats);
	}

	public void startSimulatie() {
		mbs.startSimulatie();
	}
}

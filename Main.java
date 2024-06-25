import javax.swing.SwingUtilities;

import controller.Controller;
import domein.Bedrijf;
import domein.Bedrijfssimulatie;
import exceptions.PoiException;
import gui.Gui;
import gui.Plugin.visualizer.VisualizerController;

/**
 * Startup klasse
 */
public class Main {

	/**
	 * Maakt gui, controller, model en koppelt deze.
	 * 
	 * @param args argumenten worden niet gebruikt
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					/**
					 * Kan de mapper misschien ook door main gemaakt worden? vermijdt hiermee configuratie duplicatie in de constructor.
					 * En de fout kan worden opgevangen zonder hiermee onnodig een bedrijf aan te maken.
					 * + ontkoppeling door verschuiving verantwoordelijkheid creatie aanspreekpunten in lagen
					 * + ontkoppeling door configuratie duplicatie te vermijden
					 * - koppeling door enkele extra tijdelijke associatie
					 * + Bedrijf kan makkelijker Mapper implementatie uitwisselen
					 */
					Bedrijf b = new Bedrijf(); // bedrijf propageert PoiException naar boven naar main
					Bedrijfssimulatie bs = new Bedrijfssimulatie();
					Controller facade = new Controller(b, bs);
					VisualizerController vc = new VisualizerController();
					Gui gui = new Gui(facade, vc);
					b.attach(gui);
					bs.attach(gui);
				} catch (PoiException e) {
					e.printStackTrace();
				} 
			
			}
		});
	}
}

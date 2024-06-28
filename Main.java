import javax.swing.SwingUtilities;

import controller.Controller;
import domein.Bedrijf;
import exceptions.PoiException;
import gui.Main.Gui;
import gui.simulatie.Visualizer;

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
					 * Kan de mapper misschien ook door main gemaakt worden?
					 * + Mapper fout kan worden opgevangen zonder hiermee onnodig een bedrijf aan te maken.
					 * + ontkoppeling door verschuiving verantwoordelijkheid creatie aanspreekpunten in lagen
					 * + ontkoppeling door configuratie duplicatie te vermijden
					 * - koppeling door enkele extra tijdelijke associatie
					 * + Bedrijf kan makkelijker Mapper implementatie uitwisselen
					 */
					Bedrijf b = new Bedrijf(); // bedrijf propageert PoiException naar boven naar main
					Controller facade = new Controller(b);
					Visualizer visualizer = new Visualizer(facade);
					Gui gui = new Gui(facade);
					facade.attach(gui);
					facade.attach(visualizer);
				} catch (PoiException e) {
					e.printStackTrace();
				} 
			}
		});
	}
}

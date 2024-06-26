import javax.swing.SwingUtilities;

import controller.Controller;
import domein.Bedrijf;
import exceptions.PoiException;
import gui.Main.ViewManager;
import gui.simulatie.Visualizer;
import gui.vestigingoverzicht.VestigingOverzicht;

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
					// Create domein
					Bedrijf b = new Bedrijf(); // bedrijf propageert PoiException naar boven naar main

					// Create controller
					Controller c = new Controller(b);

					// Create gui components and interface
					Visualizer gv = new Visualizer(c);
					b.attach(gv);

					VestigingOverzicht gvo= new VestigingOverzicht(c);
					b.attach(gvo);

					ViewManager gvm = new ViewManager(gvo, gv);

				} catch (PoiException e) {
					e.printStackTrace();
				}
			}
		});
	}
}

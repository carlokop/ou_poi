import javax.swing.SwingUtilities;

import controller.Controller;
import domein.Bedrijf;
import domein.Bedrijfssimulatie;
import guiPrototype.Gui;

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
				Bedrijf b = new Bedrijf(); // bedrijf propageert PoiException naar boven naar main
				Bedrijfssimulatie bs = new Bedrijfssimulatie();
				Controller facade = new Controller(b, bs);
				Gui gui = new Gui(facade);
				b.attach(gui);
				bs.attach(gui);
			}
		});
	}
}

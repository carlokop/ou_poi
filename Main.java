import javax.swing.SwingUtilities;

import controller.Facade;
import domein.Bedrijf;
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
				Bedrijf bedrijf = new Bedrijf();
				Facade facade = new Facade(bedrijf);
				Gui gui = new Gui(facade);
				bedrijf.attach(gui);
			}
		});
	}
}

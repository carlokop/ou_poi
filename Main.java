import javax.swing.SwingUtilities;

import Controller.Facade;
import Domein.Bedrijf;
import Gui.Gui;
import Gui.PoiFrame;

public class Main {

	/**
	 * Maakt gui, controller, model en koppelt deze.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Bedrijf bedrijf = new Bedrijf();
				Facade facade = new Facade(bedrijf);
				PoiFrame gui = new PoiFrame(facade);
				bedrijf.attach(gui);
			}
		});
	}
}

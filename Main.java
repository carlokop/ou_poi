import javax.swing.SwingUtilities;

import Controller.CarloFacade;
import Domein.Bedrijf;
import gui.CarloMainFrame;

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
				CarloFacade facade = new CarloFacade(bedrijf);
				CarloMainFrame gui = new CarloMainFrame(facade);
				bedrijf.attach(gui);
			}
		});
	}
}

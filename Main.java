import javax.swing.SwingUtilities;

import controller.Facade;
import domein.Bedrijf;
//import gui.Gui;
import guiPrototype.Gui7;
import guiPrototype.Gui8;

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
//				Gui7 gui7 = new Gui7(facade);
//				bedrijf.attach(gui7);
				
				Gui8 gui8 = new Gui8(facade);
				bedrijf.attach(gui8);
			}
		});
	}
}

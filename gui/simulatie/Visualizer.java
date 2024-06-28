package gui.simulatie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;
import observer.Observer;
import observer.Subject;

/**
 * 
 * Klasse die klikbare bars (staafjes) op een frame toont. Elke bar wordt
 * gekarakteriseerd door een key en een value.
 * 
 * @author Medewerker OU
 *
 */
public class Visualizer extends JPanel implements Observer {

	// intermediair tussen vizualizer (gui) en domein klassen
	private Controller contr;

	private static final long serialVersionUID = 1L;
	private static final int MARGIN = 10;
	private static final int HGAP = 10;
	private int WIDTH_FRAME = 1200;
	private int HEIGHT_FRAME = 520;
	private int WIDTH_PANE = WIDTH_FRAME - 2 * MARGIN;
	private int OFFSET_HEADER_FOOTER = 80;
	private int HEIGHT_PANE = HEIGHT_FRAME - 3 * MARGIN - OFFSET_HEADER_FOOTER;

	// hulp parameter
	private int openVestigingen;

	/**
	 * Creeert een visualizer (staafdiagram) op grond van een map
	 * 
	 * @param map de map
	 * @param vc  de controller
	 */
	public Visualizer(Controller vc) {
		super();
		this.contr = vc;
		initialize();
		drawBars(contr.getBarInfo());
	}

	private void initialize() {
		Map<String, Integer> map = contr.getBarInfo();
		this.openVestigingen = map.size();
		
		setBounds(MARGIN, MARGIN, WIDTH_PANE, HEIGHT_PANE);
		setBackground(Color.BLUE);
		this.setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(
				(int) Math.round((dim.width - WIDTH_FRAME) / 2),
				(int) Math.round((dim.height - HEIGHT_FRAME) / 2)
				);
		setLayout(null);
	}
	
	/**
	 * Tekent de bars op basis van de map met (key, value) paren. Bars met hoogte 0
	 * worden getoond als bars met maximale hoogte in de achtergrondkleur.
	 * 
	 */
	private void drawBars(Map<String, Integer> map) {
		removeAll();
		int size = map.size();
		int total_hgap = (size + 1) * HGAP;
		int width_bar = (WIDTH_PANE - total_hgap) / size;
		int maxValue = maxValue(map);
		double verticalScaleFactor = (double) HEIGHT_PANE / (double) maxValue;
		Set<String> keys = map.keySet();
		int x_pos = 0;
		for (String key : keys) {
			x_pos = x_pos + HGAP;
			int value = map.get(key);
			createBar(key, value, x_pos, verticalScaleFactor, width_bar);
			x_pos = x_pos + width_bar;
		}
		this.repaint();
		this.revalidate();
	}

	private void createBar(String key, int value, int x_pos, double verticalScaleFactor, int width_bar) {
		Bar bar;
		if (value == 0) {
			// bar in achtergrondkleur met maximale hoogte om label bovenaan te krijgen.
			int height_bar = HEIGHT_PANE;
			int y_pos = 0;
			bar = new Bar(key, 0, x_pos, y_pos, width_bar, height_bar, Color.BLUE);
		} else {
			// // bar in kleur geel met hoogte: schaalfactor * aantal klanten.
			int height_bar = (int) (verticalScaleFactor * value);
			int y_pos = HEIGHT_PANE - height_bar;
			bar = new Bar(key, value, x_pos, y_pos, width_bar, height_bar, Color.YELLOW);
		}
		bar.addMouseListener(new BarLuisteraar());
		this.add(bar);
	}

	/**
	 * Geeft de grootste value in de map
	 * 
	 * @return
	 */
	private int maxValue(Map<String, Integer> map) {
		int max = 0;
		Set<String> keys = map.keySet();
		for (String key : keys) {
			if (map.get(key) > max) {
				max = map.get(key);
			}
		}
		return max;
	}

	class BarLuisteraar extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			Bar bar = (Bar) e.getSource();

			// Waarschuw klant en handel naar diens beslissing.
			if (contr.isVestigingOpen(bar.getName())) {
				if(openVestigingen == 1) {
					int warnDialogInput = JOptionPane.showConfirmDialog(
							Visualizer.this.getParent(),
							"Deze actie kan ervoor zorgen dat klanten uit het zicht raken.",
			                 "U wilt de laatste vestiging sluiten.",
			                 JOptionPane.OK_CANCEL_OPTION,
			                 JOptionPane.WARNING_MESSAGE);
					if (warnDialogInput != 0) {
						return;
					}
				}
				openVestigingen--;
				contr.barClicked(bar.getName(), bar.getLabelValue()); //TODO: Semantics en wat is zinnig om te rapporteren?
			} else {
				openVestigingen++;
				contr.barClicked(bar.getName(), bar.getLabelValue());
			}
		}
	}

	@Override
	public void update(Subject s, Object c) {
		drawBars(contr.getBarInfo());
	}
}
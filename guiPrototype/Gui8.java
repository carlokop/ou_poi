package guiPrototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controller.Facade;
import observerPatroon.Observer;

public class Gui8 extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private Facade fc = null;
	private Container pane;
	private Component header, footer;
	private Component vestigingOverzicht;

	public Gui8(Facade fc) {
		super();
		this.fc = fc;
		pane = getContentPane();
		init();
	}

	public void init() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 500);
		setTitle("Practicum Ontwerpen en implementeren");
		pane.setBackground(Color.white);
		pane.setLayout(new BorderLayout());

		header = createHeader();
		pane.add(header, BorderLayout.NORTH);

		footer = createFooter();
		pane.add(footer, BorderLayout.SOUTH);

		vestigingOverzicht = new VestigingOverzicht(fc);
	}

	/**
	 * Voegt hoofdmenu toe aan de contentpane
	 */
	private Container createHeader() {
		JMenuBar headermenu = new JMenuBar();
		headermenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		headermenu.setBackground(new Color(163, 175, 192));
		JMenu mainMenu = new JMenu("Activiteit");
		JMenuItem jmiInzageVestiging = new JMenuItem("Start Inzage Vestiging");
		JMenuItem jmiVestigingenSluiten = new JMenuItem("Vestigingen sluiten");

		mainMenu.add(jmiInzageVestiging);
		mainMenu.add(jmiVestigingenSluiten);

		jmiInzageVestiging.addActionListener(new VestigingMenuItemLuisteraar());
		jmiVestigingenSluiten.addActionListener(new VestigingenSluitenMenuItemLuisteraar());

		headermenu.add(mainMenu);

		return headermenu;
	}

	/**
	 * Voegt een footer toe aan de content pane met een sluit button
	 */
	private Component createFooter() {
		JPanel footer = new JPanel();
		footer.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton sluitbtn = new JButton("Huidige activiteit stoppen.");
		sluitbtn.setBackground(Color.RED);
		sluitbtn.setForeground(Color.WHITE);
		sluitbtn.addActionListener(new stopActiviteitListener());

		footer.add(sluitbtn);
		return footer;
	}

	/**
	 * Handler voor tonen van kiesvestiging taak
	 *
	 */
	class VestigingMenuItemLuisteraar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clearViews();
			pane.add(vestigingOverzicht, BorderLayout.WEST);
			Gui8.this.pane.revalidate();
			Gui8.this.pane.repaint();
		}
	}

	/**
	 * Dit is voor taak 5 wordt later geimplementeerd
	 */
	class VestigingenSluitenMenuItemLuisteraar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clearViews();
		}
	}

	/**
	 * Handler voor de sluit rapport knop
	 */
	class stopActiviteitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clearViews();
		}
	}

	public void clearViews() {
		BorderLayout bLayout = (BorderLayout) this.pane.getLayout();
		Component cCache;
		cCache = bLayout.getLayoutComponent(BorderLayout.WEST);
		if (cCache != null) {
			this.pane.remove(cCache);
		}
		cCache = bLayout.getLayoutComponent(BorderLayout.CENTER);
		if (cCache != null) {
			this.pane.remove(cCache);
		}
		cCache = bLayout.getLayoutComponent(BorderLayout.EAST);
		if (cCache != null) {
			this.pane.remove(cCache);
		}
		this.pane.revalidate();
		this.pane.repaint();
	}
	
	@Override
	public void update() {
		// Taak 5
	}
}

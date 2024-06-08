package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.Facade;
import ObserverPatroon.Observer;

public class Gui extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

	private Facade fc = null;
	private Container pane = getContentPane();
	private Cursor pointer = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	
	public Gui (Facade fc){
		super();
		this.fc = fc;
		init();
	}
	
	public void init() {
	  setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
	  setSize(800, 300);
      setTitle("Practicum Ontwerpen en implementeren");
      pane.setBackground(new Color(246,246,246));
      pane.setLayout(new BorderLayout());
      hoofdmenu();
      footer();
	}
		
	/**
	 * Voegt hoofdmenu toe aan de contentpane
	 */
	private void hoofdmenu() {
	  JMenuBar hoofdmenu = new JMenuBar();
	  hoofdmenu.setLayout(new FlowLayout(FlowLayout.LEFT));
	  hoofdmenu.setBackground(new Color(163,175,192));
	  JMenu rapporten = new JMenu("Rapporten");
	  rapporten.setCursor(pointer);
	  
      JMenuItem item_klant_vest = new JMenuItem("Klanten per vestiging");
      JMenuItem item_vest_sluiten = new JMenuItem("Vestigingen sluiten");

      item_klant_vest.setBorder(new EmptyBorder(5, 0, 5, 0));
      item_vest_sluiten.setBorder(new EmptyBorder(5, 0, 5, 0));

      rapporten.add(item_klant_vest);
      rapporten.add(item_vest_sluiten);
      
      item_klant_vest.setCursor(pointer);
      item_vest_sluiten.setCursor(pointer);
      
      item_klant_vest.addActionListener(new KlantenVestegingLuisteraar());
      item_vest_sluiten.addActionListener(new VestSluitenLuisteraar());

      hoofdmenu.add(rapporten);

      pane.add(hoofdmenu,BorderLayout.NORTH);
	}
		
	/**
	 * Voegt een footer toe aan de content pane met een sluit button
	 */
	private void footer() {
	  JPanel footer = new JPanel();
	  footer.setLayout(new FlowLayout(FlowLayout.RIGHT));
	  
	  JButton sluitbtn = new JButton("Rapport Sluiten");
	  sluitbtn.setBackground(Color.RED);
	  sluitbtn.setForeground(Color.WHITE);
	  sluitbtn.setBorder(new EmptyBorder(5, 10, 5, 10));
	  sluitbtn.setFocusPainted(false);
	  sluitbtn.setCursor(pointer);
	  sluitbtn.addActionListener(new sluitRapportLuisteraar());
	  
	  footer.add(sluitbtn);
	  pane.add(footer,BorderLayout.SOUTH);
	}
	
	/**
	 * Handler voor tonen van kiesvestiging taak 
	 * @author carlo
	 *
	 */
	class KlantenVestegingLuisteraar implements ActionListener {
	  @Override
      public void actionPerformed(ActionEvent e) {
	    VestigingKlanten vk_view = new VestigingKlanten(fc);
	    pane.add(vk_view,BorderLayout.CENTER);
	    fc.toonView(View.VESTIGINGKLANTEN);	    
      }
	}
	
	/**
	 * Dit is voor taak 5 wordt later geimplenteerd
	 * @author carlo
	 *
	 */
	class VestSluitenLuisteraar implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        /**
         * Doe iets voor taak 5
         */
        System.out.println("Toon overzicht met vraag welke vestiging sluiten Taak 5");
        
      }
    }
	
	/**
	 * Handler voor de sluit rapport knop
	 * @author carlo
	 *
	 */
	class sluitRapportLuisteraar implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        fc.toonView(View.DEFAULT);     
      }
    }
	
	
	@Override
	public void update(View view) {
	  if(View.DEFAULT.equals(view)) {
	    //sluit het actuele rapport in de content pane en herinitialiseert het startscherm
	    pane.removeAll();
	    hoofdmenu();
	    footer();
	    revalidate();
	    repaint();
	  }
	}

    @Override
    public void update() {
      // TODO Auto-generated method stub
      
    }
	
	

}

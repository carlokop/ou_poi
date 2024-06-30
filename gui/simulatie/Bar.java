package gui.simulatie;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

//Docent: deze klasse is op javadoc na niet veranderd
/**
 * Klasse die een Bar (staafje) representeert. Een bar krijgr een label (label name - value).
 * 
 * @author Medewerker OU
 *
 */
public class Bar extends JPanel {

	private static final long serialVersionUID = 1L;
	private String labelName;
	private int labelValue = 0;

	/**
	 * initialiseert een bar in de visualizer
	 * @param labelName    naam van de bar
	 * @param labelValue   waarde van de bar
	 * @param x            locatie x
	 * @param y            locatie y
	 * @param width        breedte
	 * @param height       hoogte
	 * @param color        kleur
	 */
	public Bar(String labelName, int labelValue, int x, int y, int width, int height, Color color) {
		super();
		this.labelName = labelName;
		this.labelValue = labelValue;
		JLabel label = new JLabel(labelName + " - " + labelValue);
		label.setFont(new Font("", Font.ITALIC , 9));
		this.add(label);
		this.setBounds(x, y, width, height);
		this.setBackground(color);
	}	
	
	/**
	 * geeft de labelnaam
	 * return de labelnaam
	 */
	public String getName() {
		return labelName;
	}

	/**
	 * geeft de labelwaarde 
	 * @return de labelwaarde
	 */
	public int getLabelValue() {
		return labelValue;
	}

}

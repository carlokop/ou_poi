package gui.simulatie;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
	 * instantieert een bar
	 * @param labelName    naam van de bar
	 * @param labelValue   numerieke waarde hoe hoog de bar mag zijn
	 * @param x            x positie
	 * @param y            y positie
	 * @param width        breedte van de bar
	 * @param height       hoogte van de bar
	 * @param color        kleur van de bar
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
	 * geeft de naam van de bar
	 * @return de naam
	 */
	@Override
	public String getName() {
		return labelName;
	}

	/**
	 * geeft de numerieke waarde van de bar
	 * @return de waarde
	 */
	public int getLabelValue() {
		return labelValue;
	}

}

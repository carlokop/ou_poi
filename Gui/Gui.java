package Gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Controller.Facade;
import ObserverPatroon.Observer;

public class Gui extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

	Facade fc;
	
	public Gui (Facade fc){
		super();
		this.fc = fc;
		init();
	}
	
	public void init() {
		
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

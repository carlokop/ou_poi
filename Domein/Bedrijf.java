package Domein;

import java.util.Collection;

import Controller.Model;
import ObserverPatroon.Subject;

public class Bedrijf extends Subject implements Model{
	Collection<Vestiging> vestigingen;
	
}

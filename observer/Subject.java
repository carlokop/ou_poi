package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * subject klasse als onderdeel van het observerpatroon
 * @author ounl
 */
public abstract class Subject {

	private List<Observer> observers = new ArrayList<>();
	
	/**
	 * voegt een observer toe aan de lijst met observers
	 * @param observer  de toe te voegen observer
	 */
	public void attach(Observer observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * verwijderd een observer
	 * @param observer de te verwijderen observer
	 */
	public void detach(Observer observer) {
		int index = observers.indexOf(observer);
		if (index != -1) {
			observers.remove(index);
		}
	}

	/**
	 * informeert alle observers en roept daar de update methode van aan zonder argumenten
	 */
	public void notifyObservers() {
	  for (Observer o: observers) {        		
	    o.update(this, null);
	  };
	}
	
	/**
	 * informeert alle observers en roept daar de update methode van aan zonder argumenten
	 * @param arg het mee te geven argument
	 */
	public void notifyObservers(Object arg) {
	  for (Observer o: observers) {        		
	    o.update(this, arg);
	  };
	}

}

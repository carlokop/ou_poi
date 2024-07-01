package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * maakt en beheert observer objecten
 * @author ounl
 */
public abstract class Subject {

    /**
     * lijst met observers
     */
	private List<Observer> observers = new ArrayList<>();

	/**
	 * voegt een observer toe aan de lijst
	 * @param observer de observer om toe te voegen
	 */
	public void attach(Observer observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * verwijderd een observer uit de lijst
	 * @param observer de observer om te verwijderen
	 */
	public void detach(Observer observer) {
		int index = observers.indexOf(observer);
		if (index != -1) {
			observers.remove(index);
		}
	}

	/**
	 * updates alle observers
	 */
	public void notifyObservers() {
	  for (Observer o: observers) {
	    o.update(this, null);
	  }
	}

	/**
	 * updates alle observers
	 * @param arg argument
	 */
	public void notifyObservers(Object arg) {
	  for (Observer o: observers) {
	    o.update(this, arg);
	  }
	}

}

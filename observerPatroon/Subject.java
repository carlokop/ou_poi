package observerPatroon;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer subject
 */
public abstract class Subject {

	private List<Observer> observers;

	/**
	 * initialiseert super() en maakt lege lijst met observers
	 */
	public Subject() {
		observers = new ArrayList<>();
	}
	
	/**
	 * voegt observer toe aan lijst met observers
	 * @param observer  de observer
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}

	/**
	 * verwijderd observer uit lijst met observers
	 * @param observer  de observer
	 */
	public void detach(Observer observer) {
		observers.remove(observer);
	}

	/**
	 * stuurt een update signaal naar alle observers
	 */
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}	
}
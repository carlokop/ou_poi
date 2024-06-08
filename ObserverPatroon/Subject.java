package ObserverPatroon;

import java.util.ArrayList;
import java.util.List;
import ObserverPatroon.Observer;
import gui.View;

public abstract class Subject {

	private List<Observer> observers = new ArrayList<>();

	public void attach(Observer observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void detach(Observer observer) {
		int index = observers.indexOf(observer);
		if (index != -1) {
			observers.remove(index);
		}
	}

	//nog nergerns in gebruik
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}
	
	/*
	 * 
	 */
	public void notifyObservers(View view) {
      for (Observer o : observers) {
          o.update(view);
      }
  }
	
}
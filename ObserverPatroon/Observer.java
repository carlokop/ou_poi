package ObserverPatroon;

import gui.View;

public interface Observer {
	
  void update();

  /**
   * Regelt de gekozen view
   * @param view
   */
  void update(View view);

}

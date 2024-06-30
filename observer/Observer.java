package observer;

/**
 * observer interface
 * @author ounl
 */
public interface Observer {

  /**
   * updates gegevens na wijzigingen in observers
   * @param s   het subject
   * @param arg argumenten
   */
  public void update(Subject s, Object arg);

}


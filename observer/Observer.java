package observer;

/**
 * beschrijft hoe de observer geimplementeerd moet worden
 * @author ounl
 */
public interface Observer {

  /**
   * updates observers na wijzigingen 
   * @param s   het subject
   * @param arg argumenten
   */
  public void update(Subject s, Object arg);

}


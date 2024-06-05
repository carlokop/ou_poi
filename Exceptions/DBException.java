package Exceptions;

/**
 * Database exception
 * @author carlo
 *
 */
public class DBException extends Exception {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DBException(){
    super();
  }
  
  public DBException(String s){
    super(s);
  }

}



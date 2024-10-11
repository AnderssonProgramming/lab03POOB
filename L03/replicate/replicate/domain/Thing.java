package domain;
import java.awt.Color;

/*No olviden adicionar la documentacion*/
public interface Thing{
  public static final int ROUND = 1;
  public static final int SQUARE = 2;


  public abstract void decide();
   
  default void change(){
  };
  
  default int shape(){
      return SQUARE;
  }
  
  default Color getColor(){
      return Color.red;
  };
  
  default boolean isActive(){
      return false;
  }
     
}

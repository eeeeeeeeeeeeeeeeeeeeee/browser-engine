import java.util.*;

public class SimpleSelector extends Selector {
  public String tagName;
  public String id;
  public ArrayList classNames;

  public SimpleSelector(String name, String idName, ArrayList classTitles) {
    // change these later
    super();
    tagName = name;
    idName = name;
    classNames = classTitles;

  }

  /*
  public Specificity specificity() {

  } */
}

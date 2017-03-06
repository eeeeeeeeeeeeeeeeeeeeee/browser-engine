import java.util.*;


public class ElementNode extends Node {
  public String tagName;
  public Map<String, String> attributes;

  public ElementNode(String tagType, Map<String, String> attrs, Node[] childrenNodes) {
    super(childrenNodes);
    tagName = tagType; 
    attributes = attrs;
  }

  public String toString() {
    return "ElementNode of type: " + tagName + ", with attrs: " + attributes;
  }

}

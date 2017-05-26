import java.util.*;


public class TextNode extends Node {
  public String data;

  public TextNode(String nodeData) {
    super();
    data = nodeData;
  }
  
  public String toString() {
    return "TextNode w/ data: " + data;
  }
}


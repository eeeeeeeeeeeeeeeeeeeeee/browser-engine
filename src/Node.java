import java.util.*;


public class Node {
  public Node[] children;

  public Node(Node[] children) {
    children = children; 
  }

  public Node() {
    children = new Node[10];
  }


  public static void main(String[] args) {
    TextNode node1 = new TextNode("hello world!");
    Map<String, String> mappy = new HashMap<String, String>();
    mappy.put("class", "my-div");
    mappy.put("position", "absolute");
    Node[] nodes = new Node[10];
    nodes[0] = new Node();
    nodes[1] = new Node();
    ElementNode node2 = new ElementNode("div", mappy, nodes);
    System.out.println(node1);
    System.out.println(node2);
  }
}


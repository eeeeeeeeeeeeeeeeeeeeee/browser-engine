import java.util.function.*;
import java.lang.*;
import java.util.*;

public class HTMLParser {
  int pos;
  String input;

  public HTMLParser(String source) {
    pos = 0;
    input = source;
  } 

  public Node parse() {
    Node[] nodes = this.parseNodes();
    
    if(nodes.length == 1) {
      return nodes[0];
    } else {
      return new ElementNode("html", new HashMap<String, String>(), nodes);
    }
  }

  public static void main(String[] args) {
    String htmlText = "<html>" +
      "<body>" +
         "<h1>Title</h1>" + 
         "<div id='main' class='test'>" + 
            "<p>Hello <em>world</em>!</p>" +
         "</div>" +
       "</body>" +
     "</html>";

    Parser myParse = new Parser(htmlText);
    Node result = myParse.parse();
    System.out.println(result);
  }

  // Read the current character without consuming it
  private char nextChar() {
    return input.charAt(pos + 1);
  }

  // Read current char and advance this.pos to next character 
  private char consumeChar() {
    pos++;
    return input.charAt(pos - 1);
  }

  // Consume characters until test returns false 
  private String consumeWhile(Predicate<Character> f) {
    String consumedString = "";
    while(!this.eof() && f.test(input.charAt(pos))){
      consumedString += this.consumeChar();
    }
    return consumedString;
  }
    
  // Consume and discard zero or more whitespace characters 
  private void consumeWhitespace() {
    this.consumeWhile(chr -> Character.isWhitespace(chr));
  }

  // Do the next characters start with the given string?
  private boolean startsWith(String str) {
    return input.substring(pos, pos + str.length()).equals(str);
  }

  // Parse a tag or attribute name 
  private String parseTagName() {
    return this.consumeWhile(chr -> Character.isLetterOrDigit(chr) || Character.isDigit(chr));
  }

  // Parse a single node 
  private Node parseNode() {
    if(input.charAt(pos) == '<') {
      return this.parseElement();
    } else {
      return this.parseText();
    }
  }

  // Parse a text node 
  private Node parseText() {
    return new TextNode(this.consumeWhile(chr -> chr != '<'));
  }

  // Parse a single element, including its open tag, contents, & closing tag
  private Node parseElement() {
    // Opening tag
    char openBrack1 = this.consumeChar();
    if(openBrack1 != '<') {
      throw new AssertionError("tag does not start with <");
    }

    String tagName = this.parseTagName();
    Map<String, String> attrs = this.parseAttributes();

    char closeBrack1 = this.consumeChar();
    if(closeBrack1 != '>') {
      throw new AssertionError("tag does close with >.  It closes with " + closeBrack1);
    }
    
    // Contents 
    Node[] children = this.parseNodes();
    
    // Closing Tag
    char openBrack = this.consumeChar();
    char slash = this.consumeChar();
    String name = this.parseTagName();
    char closeBrack = this.consumeChar();

    if(!(openBrack == '<' && slash == '/' && name.equals(tagName) && closeBrack == '>')) {
      throw new AssertionError("closing tag wrong, " + openBrack + slash + name + closeBrack);
    }
 
    return new ElementNode(tagName, attrs, children); 
  }

  // Parse a single name="value" pair 
  private String[] parseAttr() {
    String name = this.parseTagName();
    
    char equals = this.consumeChar();
    if(equals != '=') {
      throw new AssertionError("expected = sign, got " + equals);
    }

    String value = this.parseAttrValue();
    return new String[] { name, value };
  }

  // Parse a quoted value 
  private String parseAttrValue() {
    char openQuote = this.consumeChar();
    
    if(!(openQuote == '"' || openQuote == '\'')) {
      throw new AssertionError("value was: " + (int)openQuote + ", idx: " + pos);
    }

    String value = this.consumeWhile(chr -> chr != openQuote);
    
    char closeQuote = this.consumeChar();
    if(!(openQuote == '"' || openQuote == '\'')) { 
      throw new AssertionError("expect closing quote, instead got: " + closeQuote);
    }

    return value;    
  }

  private Map<String, String> parseAttributes() {
    Map<String, String> attributes = new HashMap<String, String>();
    this.consumeWhitespace();

    String[] attrs;
    while(input.charAt(pos) != '>') {
      attrs = this.parseAttr();
      attributes.put(attrs[0], attrs[1]);
      this.consumeWhitespace();
    }

    return attributes;
  }

  private Node[] parseNodes() {
    List<Node> nodeList = new ArrayList<Node>();
    this.consumeWhitespace();
    
    while(!(this.eof() || this.startsWith("</"))) {
      Node nodez = this.parseNode();
      System.out.println(nodez);
      nodeList.add(nodez);
    }

    Node[] nodeArray = new Node[nodeList.size()];
    nodeArray = nodeList.toArray(nodeArray);
    return nodeArray;
  }

  // Return true if all input is consumed
  private boolean eof() {
    if(pos >= input.length()) {
      return true;
    } else {
      return false;
    }
  }
}

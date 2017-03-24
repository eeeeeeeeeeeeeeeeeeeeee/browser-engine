import java.util.*;
import java.util.function.*;

public class CSSParser {
  
  int pos = 0;
  String input;

  public CSSParser(String inp) {
    input = inp;
  }

  public static void main(String[] args) {
    CSSParser parsie = new CSSParser("#ff0000");
    System.out.println(parsie.parseColor());
  }

/*
  // Parse one simple selector, e.g.: type#id.class1.class2.class3
  private SimpleSelector parseSimpleSelector() {
    //SimpleSelector selector = new SimpleSelector(null, null, new ArrayList());
    String tagName;
    String id;
    ArrayList classNames = new ArrayList();

    while(!this.eof()) {
      char next = this.nextChar();
      switch (next) {
        case '#': 
          this.consumeChar();
          id = this.parseIdentifier();
          break;
        case '.':
          this.consumeChar();
          classNames.add(this.parseIdenifier());
          break;
        case '*':
          this.consumeChar();
          break;
        default: 
          if(validIdentifierChar(next)) {
            tagName = this.parseIdentifier();
          }
      }
    }
    return new SimpleSelector(tagName, id, classNames);
  }
  */

  private Boolean eof() {
    return pos >= input.length(); 
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

  private char consumeChar() {
    pos++;
    return input.charAt(pos - 1);
  }

  private char nextChar() {
    return input.charAt(pos + 1);
  }

  private String parseIdentifier() {
    return this.consumeWhile(chr -> this.validIdentifierCard(chr));
  }

  private int parseHexPair() {
    String pair = input.substring(pos, pos + 2);
    pos += 2;
    return Integer.parseInt(pair, 16);
  } 

  private Color parseColor() {
    char octothorp = this.consumeChar();
    if(octothorp != '#') {
      throw new AssertionError("hex color must start with #");
    } else {
      int R = this.parseHexPair();
      int G = this.parseHexPair();
      int B = this.parseHexPair();

      return new Color(R, G, B, 255);
    }
  }

  // in progress - necessary?
  /*
  private Unit parseUnit() {
    String identifier = this.parseIdentifier();
    if(identifier.toLowerCase() == "px") {
      return new Px();
    }
  }*/

  // for now, only handles px units, and assumes such 
  private Value parseLength() {

  }
   
  private static Boolean validIdentifierCard(char c) {
    String charString = "" + c;
    return charString.matches("[a-zA-Z0-9-_]");
    
  }

 


/*
  // Parse a rule set: &lt;selectors&gt; { &lt;declarations&gt; }.
  private Rule parseRule() {
    return new Rule(this.parseSelectors(), this.parseDeclarations());
  }

  private ArrayList<SimpleSelector> parseSelectors() {
    ArrayList selectors = new ArrayList();

    while(true) {
      selectors.add(SimpleSelector(this.parseSimpleSelector()));
      this.consumeWhitespace();
      
      char next = this.nextChar();
      if(next == ',') {
        this.consumeChar();
        this.consumeWhitespace();
      } else if(next == '{') {
        break; // start of declaration 
      } else {
        throw new ArgumentError("cannot parse " + next);          
      }
    
    }

    return selectors;
    // need to sort by specificity
    // not done
  }
*/

  /*
  private ArrayList parseRules() {
    Arraylist rules = new ArrayList();

    this.consumeWhitespace();
    while(!this.eof()) {
      rules.add(this.parseRule());
    }

    return rules;
  } */


}

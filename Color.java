public class Color {
  int r;
  int g;
  int b;
  int a;

  public Color(int rVal, int gVal, int bVal, int aVal) {
    r = rVal;
    g = gVal;
    b = bVal;
    a = aVal;
  }

  public String toString() {
    return "r: " + r + " g: " + g + " b: " + b + " a: " + a;
  }
}

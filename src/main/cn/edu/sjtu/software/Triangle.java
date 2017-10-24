package cn.edu.sjtu.software;

public class Triangle {

    private int a = 1;
    private int b = 1;
    private int c = 1;

    public Triangle(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public boolean isTriangle() {
        return a > 0 && b > 0 && c > 0 && a + b > c && b + c > a && c + a > b;
    }

    public static boolean isTriangle(int a, int b, int c) {
        return a > 0 && b > 0 && c > 0 && a + b > c && b + c > a && c + a > b;
    }

    public boolean isIsosceles() {
        return isTriangle() && (a == b || b == c || c == a);
    }

    public static boolean isIsosceles(int a, int b, int c) {
        Triangle tri = new Triangle(a,b,c);
        return tri.isTriangle() && tri.isIsosceles();
    }
}

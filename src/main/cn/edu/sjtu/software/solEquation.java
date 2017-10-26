package cn.edu.sjtu.software;

public class SolEquation {

    private int a = 1;
    private int b = 1;
    private int c = 1;

    double delta;
    double[] rootRes = new double[2];

    public Equation(int a, int b, int c) {
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

    public boolean isSolvable() {
        delta = b * b - 4 * a * c;
        return delta < 0;
    }

    public static boolean isSolvable(int a, int b, int c) {
        SolEquation equ = new SolEquation(a,b,c);
        return equ.isSolvable;
    }

    public double[] rootRes() {
        if (isSolvable) {
            rootRes[0] = ( - b + Math.sqrt(delta))/(2*a);
            rootRes[1] = (-b - Math.sqrt(delta))/(2*a);
        }
        else {
            rootRes[0] = null;
            rootRes[1] = null;
        }
            return rootRes;
    }

    public static double[] rootRes(int a, int b, int c) {
        SolEquation equ = new SolEquation(a,b,c);
        return equ.rootRes;
    }
}
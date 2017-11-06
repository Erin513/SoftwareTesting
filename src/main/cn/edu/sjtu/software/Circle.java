package cn.edu.sjtu.software;

public class Circle {
    Point p;
    double radius;

    public Circle(Point p, double radius) throws Exception {
    	
    	if(radius<=0){
    		throw new Exception();
    	}
    	
    	if(p == null){
    		throw new Exception();
    	}
    	
        this.p = p;
        this.radius = radius;
    }

    public boolean equals(Object o){
        if(o instanceof Circle) {
            final Circle c = (Circle) o;
            return c.radius == radius && c.p.x == p.x && c.p.y == p.y;
        }
        return false;
    };
}
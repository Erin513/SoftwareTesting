package cn.edu.sjtu.software;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalRadiusTest {
    private CalRadius calRadius;

    @Before
    public void before() {
        this.calRadius = new CalRadius();
    }

    @Test
    public void equal() throws Exception {
        assertEquals(true, calRadius.equal(3,3));
        assertEquals(true,calRadius.equal(3,3.0000001));
        assertEquals(true,calRadius.equal(3,2.9999999));
        assertEquals(false,calRadius.equal(4,3));
        assertEquals(false,calRadius.equal(3.5,4.5));

    }

    @Test
    public void isCollinear() throws Exception {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,4);
        Point p3 = new Point(3,0);
        Point p4 = new Point(0,8);
        assertEquals(true, calRadius.isCollinear(p1,p2,p4));
        assertEquals(true, calRadius.isCollinear(p2,p1,p4));
        assertEquals(false,calRadius.isCollinear(p1,p2,p3));
    }

    @Test
    public void calRadius() throws Exception {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,4);
        Point p3 = new Point(3,0);
//        Point p4 = new Point(0,8);
        assertEquals(new Circle(new Point(1.5,2),2.5),calRadius.calRadius(p1,p2,p3));
        assertEquals(new Circle(new Point(1.5,2),2.5),calRadius.calRadius(p1,p3,p2));
    }

}
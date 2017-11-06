package cn.edu.sjtu.software;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalRadiusTest {

	private CalRadius calRadius;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		 this.calRadius = new CalRadius();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link cn.edu.sjtu.software.CalRadius#equal(double, double)}.
	 */
	@Test
	public final void testEqual() {
        assertEquals(true, calRadius.equal(3,3));
        assertEquals(true,calRadius.equal(3,3.0000001));
        assertEquals(true,calRadius.equal(3,2.9999999));
        assertEquals(false,calRadius.equal(4,3));
        assertEquals(false,calRadius.equal(3.5,4.5));
	}

	/**
	 * Test method for {@link cn.edu.sjtu.software.CalRadius#isCollinear(cn.edu.sjtu.software.Point, cn.edu.sjtu.software.Point, cn.edu.sjtu.software.Point)}.
	 * @throws Exception 
	 */
	@Test
	public final void testIsCollinear() throws Exception {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,4);
        Point p3 = new Point(3,0);
        Point p4 = new Point(0,8);
        assertEquals(true, calRadius.isCollinear(p1,p2,p4));
        assertEquals(false,calRadius.isCollinear(p1,p2,p3));
        assertEquals(true,calRadius.isCollinear(p1,p1,p1));//同一点
	}
	
	/**
	 * 空输入，抛出异常
	 * @throws Exception
	 */
	@Test(expected=Exception.class)
	public final void testIsCollinearException() throws Exception {
        assertEquals(true,calRadius.isCollinear(null,null,null));
	}

	/**
	 * Test method for {@link cn.edu.sjtu.software.CalRadius#calRadius(cn.edu.sjtu.software.Point, cn.edu.sjtu.software.Point, cn.edu.sjtu.software.Point)}.
	 * @throws Exception 
	 */
	@Test
	public final void testCalRadius() throws Exception {
		
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,4);
        Point p3 = new Point(3,0);
        Point p4 = new Point(0,8);
        
        assertEquals(null,calRadius.calRadius(p1,p2,p4));//共线
        
        assertEquals(null,calRadius.calRadius(p4,p4,p4));//同一点
        
        assertEquals(new Circle(new Point(1.5,2),2.5),calRadius.calRadius(p1,p2,p3));//正常画圆
        
        assertNotEquals(new Circle(new Point(1.5,2),10),calRadius.calRadius(p1,p3,p2));//判定不相等
	}
	
}

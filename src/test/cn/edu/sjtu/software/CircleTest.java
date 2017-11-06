package cn.edu.sjtu.software;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CircleTest {

	private Circle circle;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		circle = new Circle(new Point(0,0),1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link cn.edu.sjtu.software.Circle#equals(java.lang.Object)}.
	 * @throws Exception 
	 */
	@Test
	public final void testEqualsObject() throws Exception {
		
		Assert.assertTrue(circle.equals(circle));
		
		Circle c = new Circle(new Point(0,1),2);
		Assert.assertFalse(circle.equals(c));
		
		Assert.assertFalse(circle.equals(new String()));
	}
	
	@Test(expected=Exception.class)
	public final void testCircleE1() throws Exception{
		
		new Circle(null,1);
	}
	
	@Test(expected=Exception.class)
	public final void testCircleE2() throws Exception{
		
		new Circle(new Point(1, 1),-1);
	}
	
}

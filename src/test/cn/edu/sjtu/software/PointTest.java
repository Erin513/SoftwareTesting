package cn.edu.sjtu.software;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointTest {

	private Point point;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		point = new Point(1, 1);
		
		System.out.println(point.x);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public final void test() throws Exception {
		
	}
}

package cn.edu.sjtu.software;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.runner.RunWith;import cn.edu.sjtu.software.generator.ClasspathTestRunner;@RunWith(ClasspathTestRunner.class)public class GenTest{
private CalRadius calRadius= new CalRadius();
@Test public void test1() throws Exception{
assertEquals(true,calRadius.isCollinear(new Point(1,2),new Point(6,2),new Point(9,2)));
}

@Test public void test2() throws Exception{
assertEquals(false,calRadius.isCollinear(new Point(1,2),new Point(1,4),new Point(3,4)));
}

@Test public void test3() throws Exception{
assertEquals(true,calRadius.isCollinear(new Point(1,2),new Point(1,4),new Point(1,4)));
}

}

package cn.edu.sjtu.software;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class SolEquationTest {
    @Test
    public void isSolvable() throws Exception {
        SolEquation t1 = new SolEquation(1, -4, 3);
        SolEquation t2 = new SolEquation(1, -2, 1);
        SolEquation t3 = new SolEquation(2, -2, 1);
        boolean result1 = SolEquation.isSolvable(1, -4, 3);
        boolean result2 = SolEquation.isSolvable(1, -2, 1);
        boolean result3 = SolEquation.isSolvable(2, -2, 1);
        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(false, result3);
    }


    @Test
    public void rootRes() throws Exception {
        SolEquation t1 = new SolEquation(1, -4, 3);
        SolEquation t2 = new SolEquation(1, -2, 1);
        SolEquation t3 = new SolEquation(2, -2, 1);
        double[] results1 = new double[]{1, 3};
        double[] results2 = new double[]{1, 1};
        double[] results3 = new double[]{0, 0};
        assertArrayEquals(results1, t1.rootRes(), 2);
        assertArrayEquals(results2, t2.rootRes(), 2);
        assertArrayEquals(results3, t3.rootRes(), 2);
    }


}

package cn.edu.sjtu.software;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class SolEquationTest {
    @Test
    public void isSolvable() throws Exception {
        SolEquation t=new SolEquation(1,-4,3);
        assertEquals(true, SolEquation.isSolvable(1,-4,3));
    }

    @Test
    public void isSolvable1() throws Exception {
        SolEquation t=new SolEquation(1,-2,1);
        assertEquals(true, SolEquation.isSolvable(1,-2,1));
    }

    @Test
    public void isSolvable2() throws Exception {
        SolEquation t=new SolEquation(2,-2,1);
        assertEquals(false, SolEquation.isSolvable(2,-2,1));
    }
    @Test
    public void rootRes() throws Exception {
        SolEquation t=new SolEquation(1,-4,3);
        double[] results = new double[]{1,3};
        assertArrayEquals(results, t.rootRes(),2);
    }

    @Test
    public void rootRes1() throws Exception {
        SolEquation t=new SolEquation(1,-2,1);
        double[] results = new double[]{1,1};
        assertArrayEquals(results, t.rootRes(),2);
    }

}


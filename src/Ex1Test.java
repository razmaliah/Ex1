import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
	static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
	static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};;
	static double[] po3 = {2,1,-0.7, -0.02,0.02};
	static double[] po4 = {-3, 0.61, 0.2};
	
 	@Test
	/**
	 * Tests that f(x) == poly(x).
	 */
	void testF() {
		double fx0 = Ex1.f(po1, 0);
		double fx1 = Ex1.f(po1, 1);
		double fx2 = Ex1.f(po1, 2);
		assertEquals(fx0, 2, Ex1.EPS);
		assertEquals(fx1, 4, Ex1.EPS);
		assertEquals(fx2, 6, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1(x) + p2(x) == (p1+p2)(x)
	 */
	void testF2() {
		double x = Math.PI;
		double[] po12 = Ex1.add(po1, po2);
		double f1x = Ex1.f(po1, x);
		double f2x = Ex1.f(po2, x);
		double f12x = Ex1.f(po12, x);
		assertEquals(f1x + f2x, f12x, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1+p2+ (-1*p2) == p1
	 */
	void testAdd() {
		double[] p12 = Ex1.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex1.mul(po2, minus1);
		double[] p1 = Ex1.add(p12, pp2);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1+p2 == p2+p1
	 */
	void testAdd2() {
		double[] p12 = Ex1.add(po1, po2);
		double[] p21 = Ex1.add(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1+0 == p1
	 */
	void testAdd3() {
		double[] p1 = Ex1.add(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1*0 == 0
	 */
	void testMul1() {
        long start = System.currentTimeMillis();
		double[] p1 = Ex1.mul(po1, Ex1.ZERO);
        long end = System.currentTimeMillis();
        double runtime = end - start;
        System.out.println("mul function runtime: " + runtime/1000 + " second");
		assertTrue(Ex1.equals(p1, Ex1.ZERO));
	}
	@Test
	/**
	 * Tests that p1*p2 == p2*p1
	 */
	void testMul2() {
        long start = System.currentTimeMillis();
        double[] p12 = Ex1.mul(po1, po2);
        long end = System.currentTimeMillis();
        double runtime = end - start;
        System.out.println("mul function runtime: " + runtime/1000 + " second");
        double[] p21 = Ex1.mul(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1(x) * p2(x) = (p1*p2)(x),
	 */
	void testMulDoubleArrayDoubleArray() {
		double[] xx = {0,1,2,3,4.1,-15.2222};
		double[] p12 = Ex1.mul(po1, po2);
		for(int i = 0;i<xx.length;i=i+1) {
			double x = xx[i];
			double f1x = Ex1.f(po1, x);
			double f2x = Ex1.f(po2, x);
			double f12x = Ex1.f(p12, x);
			assertEquals(f12x, f1x*f2x, Ex1.EPS);
		}
	}
	@Test
	/**
	 * Tests a simple derivative examples - till ZERO.
	 */
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] pt = {2,6}; // 6x+2
		double[] dp1 = Ex1.derivative(p); // 6x + 2
		double[] dp2 = Ex1.derivative(dp1); // 6
		double[] dp3 = Ex1.derivative(dp2); // 0
		double[] dp4 = Ex1.derivative(dp3); // 0
		assertTrue(Ex1.equals(dp1, pt));
		assertTrue(Ex1.equals(Ex1.ZERO, dp3));
		assertTrue(Ex1.equals(dp4, dp3));
	}
	@Test
	/** 
	 * Tests the parsing of a polynomial in a String like form. using equals and poly function too.
	 */
	public void testFromString() {
		double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
		String sp2 = "3.1x^2 +2.3x -1.1";
		String sp = Ex1.poly(p);
        long start = System.currentTimeMillis();
		double[] p1 = Ex1.getPolynomFromString(sp);
        long end = System.currentTimeMillis();
        double runtime = end - start;
        System.out.println("getPolynomFromString function runtime : " + runtime/1000 + " seconds");
		double[] p2 = Ex1.getPolynomFromString(sp2);
		boolean isSame1 = Ex1.equals(p1, p);
		boolean isSame2 = Ex1.equals(p2, p);
		if(!isSame1) {fail();}
		if(!isSame2) {fail();}
		assertEquals(sp, Ex1.poly(p1));
	}
	@Test
	/**
	 * Tests the equality of pairs of arrays.
	 */
	public void testEquals() {
		double[][] d1 = {{0}, {1}, {1,2,0,0}};
		double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
		double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
		for(int i=0;i<d1.length;i=i+1) {
			assertTrue(Ex1.equals(d1[i], d2[i]));
		}
		for(int i=0;i<d1.length;i=i+1) {
			assertFalse(Ex1.equals(d1[i], xx[i]));
		}
	}
    @Test
    /**
     * Test sameValue for function and x-axis.
     */
    public void testSameValue(){
        double[] po1 = {0,5,5};
        double rss = Ex1.sameValue(po1 ,Ex1.ZERO, -0.5, 2, Ex1.EPS);
        double rss2 = Ex1.sameValue2(po1 ,Ex1.ZERO, -0.5, 2, Ex1.EPS);
        assertEquals(rss, 0.0, Ex1.EPS);
        assertEquals(rss2, 0.0, Ex1.EPS);

    }
	@Test
	/**
	 * Tests if the sameValue function is symmetric.
	 */
	public void testSameValue2() {
		double x1=-4, x2=0;
        long start = System.currentTimeMillis();
		double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
        long end = System.currentTimeMillis();
        double runtime = end - start;
        System.out.println("function sameValue run time is: " + runtime / 1000 + " seconds");
        double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
		assertEquals(rs1,rs2, Ex1.EPS);
        start = System.currentTimeMillis();
        double rs3 = Ex1.sameValue2(po1,po2, x1, x2, Ex1.EPS);
        end = System.currentTimeMillis();
        System.out.println("function sameValue2 run time is: " + runtime / 1000 + " seconds");
        double rs4 = Ex1.sameValue2(po2,po1, x1, x2, Ex1.EPS);
        assertEquals(rs3,rs4, Ex1.EPS);
	}

    /**
     * test the runtime for sameValue function and sameValue2.
     */
    @Test
    public void testSameValue3(){
        double[] bigP = new double[4];
        bigP[bigP.length-1] = 1;
        bigP [1] = 1;

        //  test sameValue
        long start = System.currentTimeMillis();
        double ans = Ex1.sameValue(bigP,Ex1.ZERO,-4,5,Ex1.EPS);
        long end = System.currentTimeMillis();
        double runtime = end - start;
        System.out.println("SameValue funtion runtime: " + runtime/1000 + " seconds");
        assertEquals(0.0, ans, Ex1.EPS);
        System.out.println(Ex1.f(bigP, 0.5));

        // test sameValue2
        start = System.currentTimeMillis();
        ans = Ex1.sameValue2(bigP,Ex1.ZERO,-4,5,Ex1.EPS);
        end = System.currentTimeMillis();
        runtime = end - start;
        System.out.println("SameValue2 funtion runtime: " + runtime/1000 + " seconds");
        assertEquals(0.0, ans, Ex1.EPS);


    }
	@Test
	/**
	 * Test the area function - it should be symmetric.
	 */
	public void testArea() {
		double x1=-4, x2=0;
		double a1 = Ex1.area(po1, po2, x1, x2, 100);
		double a2 = Ex1.area(po2, po1, x1, x2, 100);
		assertEquals(a1,a2, Ex1.EPS);
}
	@Test
	/**
	 * Test the area f1(x)=0, f2(x)=x;
	 */
	public void testArea2() {
		double[] po_a = Ex1.ZERO;
		double[] po_b = {0,1};
		double x1 = -1;
		double x2 = 2;
		double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
		double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
		double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
		double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
		double area =2.5;
		assertEquals(a1,area, Ex1.EPS);
		assertEquals(a2,area, Ex1.EPS);
		assertEquals(a3,area, Ex1.EPS);
		assertEquals(a100,area, Ex1.EPS);
	}
	@Test
	/**
	 * Test the area function.
	 */
	public void testArea3() {
		double[] po_a = {2,1,-0.7, -0.02,0.02};
		double[] po_b = {6, 0.1, -0.2};
		double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
		double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
		double area = 58.5658;
		assertEquals(a1,area, Ex1.EPS);
	}
    @Test
    public void testArea4(){
        double[] po_a = {2,1,-0.7, -0.02,0.02};
        double[] po_b = {6, 0.1, -0.2};
        double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
        double a1 = Ex1.area(po_a,po_b, x1, 6, 1000);
        double area = 60.9963;      // checked with 3 sources - GEMINI, emathhelp.net, desmos.com
        assertEquals(a1,area, Ex1.EPS);
    }

    @Test
    /**
    Test PolynomFromPoints, test for first and second degree polynomials. test cases.
     **/
    public void testPolynomFromPoints(){
        double[] p1x = {1,3};
        double[] p1y = {5,9};
        long start = System.currentTimeMillis();
        double[] p1 = Ex1.PolynomFromPoints(p1x,p1y);
        long end = System.currentTimeMillis();
        System.out.println("PolynomFromPoints function runtime for first degree polynomial: " + (end - start) / 1000.0 + " seconds");
        assertEquals(3.0,p1[0]);
        assertEquals(2.0,p1[1]);

        double[] p2x = {1,2,-1};
        double[] p2y = {1,15,-9};
        start = System.currentTimeMillis();
        double[] p2 = Ex1.PolynomFromPoints(p2x, p2y);
        end = System.currentTimeMillis();
        System.out.println("PolynomFromPoints function runtime for second degree polynomial : " + (end - start) / 1000.0 + " seconds");
        assertEquals(-7, p2[0]);
        assertEquals(5, p2[1]);
        assertEquals(3, p2[2]);
        double[] pNull = null;
        assertNull(Ex1.PolynomFromPoints(null,null));
        assertNull(Ex1.PolynomFromPoints(p1x,p2y));
    }
    @Test
    /**
     * test poly function, test case when polynomial = 0;
     */
    public void testPolyToString(){
        double[] poly = {1.1,2.0,-3.3,0,-5.0};
        boolean isSame = true;
        String resExpected = "-5.0x^4 -3.3x^2 +2.0x +1.1";
        System.out.println(Ex1.poly(poly));
        if(!resExpected.equals(Ex1.poly(poly))){
            isSame = false;
        }
        assertTrue(isSame);

        isSame = true;
        resExpected = "0.0";
        if(!resExpected.equals(Ex1.poly(Ex1.ZERO))){
            isSame = false;
        }
        assertTrue(isSame);
    }
    @Test
    /**
     * test length function, test cases too.
     */
    public void testLenght(){
        double[] p = {0,0.75};
        double[] p2 = {100,0,1};
        double[] p3 = {123,0,0,1};
        long start = System.currentTimeMillis();
        double ans = Ex1.length(p,0,4,3);
        long end = System.currentTimeMillis();
        double runtime = end - start;
        System.out.println("length function runtime: " + runtime/1000 + " seconds");
        assertEquals(5.0,ans);
        assertEquals(2*Math.sqrt(20), Ex1.length(p2,-2,2,2));
        assertEquals(2*Math.sqrt(738), Ex1.length(p3,-3,3,2));
        assertEquals(-1, Ex1.length(p3,-3,3,0));
        assertEquals(5, Ex1.length(Ex1.ZERO,5,0,1));
    }
    @Test
    /**
     * test getDeg function
     */
    public void testGetDeg(){
        String x = " -98.8x ";
        String d = " 98.8x^2424 ";
        assertEquals(2424,Ex1.getDeg(d));
        System.out.println(d);
        System.out.println(Ex1.getDeg(d));

    }
    @Test
    /**
     * test GetNumBeforeX function
     */
    public void testGetNumBeforeX(){
        String x = " -5.5x^7 ";
        String d = " +8.0x^2424 ";
        assertEquals(-5.5,Ex1.getNumBeforeX(x));
        assertEquals(8.0,Ex1.getNumBeforeX(d));
        System.out.println(d);
        System.out.println(Ex1.getNumBeforeX(d));
    }
}

 /**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 *
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1 {
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynomial function is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
	/**
	 * Computes the f(x) value of the polynomial function at x.
	 * @param poly - polynomial function
	 * @param x
	 * @return f(x) - the polynomial function value at x.
	 */
	public static double f(double[] poly, double x) {
		double ans = 0;
		for(int i=0;i<poly.length;i++) {
			double c = Math.pow(x, i);
			ans += c*poly[i];
		}
		return ans;
	}
	/** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0.
	 * This function should be implemented recursively.
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double f1 = f(p,x1);
		double x12 = (x1+x2)/2;
		double f12 = f(p,x12);
		if (Math.abs(f12)<eps) {return x12;}
		if(f12*f1<=0) {return root_rec(p, x1, x12, eps);}
		else {return root_rec(p, x12, x2, eps);}
	}
	/**
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
	 * Note: this function only works for a set of points containing up to 3 points, else returns null.
	 * @param xx - given double[] represent the x values for the points.
	 * @param yy- given double[]  represent the y values for the points.
	 * @return an array of doubles representing the coefficients of the polynom.
	 */
	public static double[] PolynomFromPoints(double[] xx, double[] yy) {
		double [] ans = null;
        if(xx==null || yy == null){
            return null;
        }
		int lx = xx.length;
		int ly = yy.length;
		if(lx==ly && lx>1 && lx<4) {
            if (lx == 2){
                double y1y2 = yy[0]-yy[1];  // represent y1-y2
                double x1x2 = xx[0]-xx[1];  // represent x1-x2
                double a = y1y2 / x1x2;     // represent the a value from : ax+b linear
                double b = yy[0] - (a*xx[0]);   // represent the b value
                ans = new double [2];
                ans[0] = b;
                ans[1] = a;
                return ans;
            }
            else{
                double denom = (xx[0]-xx[1])*(xx[0]-xx[2])*(xx[1]-xx[2]); // denominator
                double a = (xx[2] * (yy[1] - yy[0]) + xx[1] * (yy[0] - yy[2]) + xx[0] * (yy[2] - yy[1])) / denom;
                double b = (xx[2]*xx[2] * (yy[0] - yy[1]) + xx[1]*xx[1] * (yy[2] - yy[0]) + xx[0]*xx[0] * (yy[1] - yy[2])) / denom;
                double c = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] + xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] + xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom;
                ans = new double [3];
                ans [0] = c;
                ans [1] = b;
                ans [2] = a;
                return ans;
            }
		}
		return ans;
	}
	/** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
	 * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
     * Note: 1. if p1 or p2 equals to Null will return false.
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @return true iff p1 represents the same polynomial function as p2.
	 */
	public static boolean equals(double[] p1, double[] p2) {
		boolean ans = true;
        if (p1 == null||p2 == null){
            return false;
        }
        int n = Math.max(p1.length, p2.length);
        for(int i=0; i<=n; i++){
            if(Math.abs(f(p1,i) - f(p2,i))>EPS){
                ans = false;
            }
        }
		return ans;
	}

	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
	 */
	public static String poly(double[] poly) {
		String ans = "";
		if(poly.length==0) {ans="0";}
		else {
            for (int i= poly.length-1;i>1;i--){
                if (poly[i]>0){
                    ans = ans + "+" + poly[i] + "x^" + i + " ";
                }
                if (poly[i]<0){
                    ans = ans + poly[i] + "x^" + i + " ";
                }
            }
            if (poly[1]>0){
                ans = ans + "+" + poly[1] + "x ";
            }
            if (poly[1]<0){
                ans = ans + poly[1] + "x ";
            }
            if(poly[0]>0){
                ans = ans + "+" + poly[0];
            }
            if(poly[0]<0){
                ans = ans + poly[0];
            }
            if(ans.charAt(0)=='+'){
                ans = ans.substring(1);
            }
		}
		return ans;
	}
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double ans = x1;
        double range = Math.abs(x1-x2)/2;
        int counter = 2;
        boolean flag = false;
        while (!flag){
            for (int i = 1; i<counter ; i++){
                double thisX = x1 + ((range/counter)*i);
                double check = Math.abs(Ex1.f(p1,thisX) - Ex1.f(p2,thisX));
                if (check<eps){
                    ans = check;
                    flag = true;
                    break;
                }
            }
            counter++;
        }
		return ans;
	}
	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments) {
		double ans = x1;
        double sum = 0;
        double range = Math.abs(x2-x1);
        double tempX1 = x1;
        double tempX2 = 0;
        for(int i=1; i<=numberOfSegments; i++){
            tempX2 = x1 + ((i*range)/numberOfSegments);
            double a = Math.abs(Ex1.f(p,tempX1) - Ex1.f(p,tempX2));
            double b = Math.abs(tempX1 - tempX2);
            sum = sum + Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
            tempX1 = tempX2;
        }
		return sum;
	}
	
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
		double ans = 0;
        /** add you code below

         /////////////////// */
		return ans;
	}
	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return
	 */
	public static double[] getPolynomFromString(String p) {
		double [] ans = {0};//  -1.0x^2 +3.0x +2.0
        if (!p.contains("x")){
            ans[0] = Double.parseDouble(p);
            return ans;
        }
        int size = 2;
        int i1, i2;
        if (p.contains("^")){
            if(p.indexOf(" ")==0){p=p.substring(1);}
            i1 = p.indexOf("^") + 1;
            i2 = p.indexOf(" ");
            size = getDeg(p)+1;
        }
        ans = new double[size];
        while(p.contains("^")){
            i1 = p.indexOf(" ");
            String temp = p.substring(0,i1);
            int deg = getDeg(temp);
            double per = getNumBeforeX(temp);
            ans[deg] = per;
            p=p.substring(i1+1);
            if(p.indexOf(" ")==0){p=p.substring(1);}
        }
        ans[1] = getNumBeforeX(p);
        int j = p.indexOf(" ");
        p = p.substring(j+1);
        ans[0] = Double.parseDouble(p);
        return ans;
	}
    public static double getNumBeforeX(String s){
        int i = s.indexOf("x");
        s= s.substring(0,i);
        return Double.parseDouble(s);
    }
    public static int getDeg(String s){
        int i = s.indexOf("^")+1;
        s= s. substring(i);
        i= s.indexOf(" ");
        if (i > 0) {
            s= s.substring(0, i);
        }
        return Integer.parseInt(s);
    }
	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
     * Note: if p1 or p2 = null, then function will return ZERO.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @return -  new double array represent polynomial function for the sum of the two polynomial functions
	 */
	public static double[] add(double[] p1, double[] p2) {
        double[] ans = ZERO;
        if(p1==null || p2 == null){return ans;}
        double[] large;
        double[] small;
        if(p1.length>p2.length){
            large = p1;
            small = p2;
            ans = new double[p1.length];
        }
        else {
            large = p2;
            small = p1;
            ans = new double[p2.length];
        }
        for (int i = 0; i<large.length;i++){
            if(i< small.length){
                ans[i]= large[i] + small[i];
            }
            else{
                ans[i] = large[i];
            }
        }

        return ans;
	}
	/**
	 * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double [] ans = ZERO;//
        if(p1 == null || p2 == null){return ans;}
        int p1l = p1.length, p2l = p2.length;
        int size = p1l + p2l;
        ans = new double[size];
        for(int i = 0;i<p1l;i++){
            for(int j=0;j<p2l;j++){
                ans[i+j] += p1[i] * p2[j];
            }
        }
		return ans;
	}
	/**
	 * This function computes the derivative of the p0 polynomial function.
	 * @param po
	 * @return
	 */
	public static double[] derivative (double[] po) {
		double [] ans = ZERO;//
        /** add you code below

         /////////////////// */
		return ans;
	}
}

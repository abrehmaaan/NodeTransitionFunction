package functions;

import java.math.*;  // for BigInteger

public class NodeTransitionFunction {

	
	Integer e;
	Integer K;
	BigInteger eBig;
	BigInteger KBig;
	
	public NodeTransitionFunction(Integer exp, Integer KVal) {
		// CONSTUCTOR: Sets the class to calculate f(x) = (x ^ exp) mod KVal 

		// TODO
		e = exp;
		eBig = BigInteger.valueOf(exp);
		K = KVal;
		KBig = BigInteger.valueOf(KVal);
	}
	

	
	public Integer f(Integer val) {
		// PRE: -
		// POST: Implements f(val)
		
		// TODO
		
		return (int) (Math.pow(val, e)%K);
	}

	public BigInteger f(BigInteger val) {
		// PRE: -
		// POST: Implements f(val), with val as a BigInteger

		// TODO
		
		return val.pow(e).mod(KBig);
	}

	public static void main(String[] args) {
		NodeTransitionFunction ntf = new NodeTransitionFunction(3, 33);
		
		System.out.println(ntf.f(5));
	}
	
}

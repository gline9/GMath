package gmath.test;

import gmath.types.BigInteger;

public class Tester {

	public static void main(String[] args) {
//		BigInteger bigLimit = new BigInteger(10000000);
//		BigInteger limit = new BigInteger(1000000);
//		BigInteger limit2 = new BigInteger(10000);
//		BigInteger limit3 = new BigInteger(1000);
//		for (BigInteger x = new BigInteger(2); x.lessThan(limit3); x = x.inc()){
//			for (BigInteger y = new BigInteger(x.inc()); y.lessThan(limit2); y = y.inc()){
//				BigInteger test = x.multiply(y).inc();
//				if (test.sqrt().square().equals(test)){
//					for (BigInteger z = new BigInteger(y.inc()); z.lessThan(limit); z = z.inc()){
//						BigInteger test1 = x.multiply(z).inc();
//						BigInteger test2 = z.multiply(y).inc();
//						if (test1.sqrt().square().equals(test1) && test2.sqrt().square().equals(test2)){
//							for (BigInteger w = new BigInteger(z.inc()); w.lessThan(limit); w= w.inc()){
//								BigInteger test3 = w.multiply(x).inc();
//								BigInteger test4 = w.multiply(y).inc();
//								BigInteger test5 = w.multiply(z).inc();
//								if (test3.sqrt().square().equals(test3) && test4.sqrt().square().equals(test4) && test5.sqrt().square().equals(test5)){
//									System.out.printf("%s, %s, %s, %s\n", x.toString(), y.toString(), z.toString(), w.toString());
//									for (BigInteger v = new BigInteger(w.inc()); v.lessThan(bigLimit); v= v.inc()){
//										BigInteger test6 = v.multiply(x).inc();
//										BigInteger test7 = v.multiply(y).inc();
//										BigInteger test8 = v.multiply(z).inc();
//										BigInteger test9 = v.multiply(w).inc();
//										if (test6.sqrt().square().equals(test6) && test7.sqrt().square().equals(test7) && test8.sqrt().square().equals(test8) && test9.sqrt().square().equals(test9)){
//											System.out.printf("!!!!!%s, %s, %s, %s, %s!!!!!\n", x.toString(), y.toString(), z.toString(), w.toString(), v.toString());
//											return;
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		new TesterGui();

	}

	public static boolean isPrime(int n) {
		return !new String(new char[n]).matches(".?|(..+?)\\1+");
	}
}

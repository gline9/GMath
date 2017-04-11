package gmath.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import gcore.tuples.Pair;
import gmath.functions.ExponentialFunction;
import gmath.functions.PowerFunction;
import gmath.functions.RFunction;
import gmath.functions.RationalFunction;
import gmath.functions.ZeroFunction;

public class TesterGui extends JFrame {
	private static final long serialVersionUID = 1L;

	private int width = 300;
	private int height = 300;

	public TesterGui() {
		super("Tester");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();

		Insets i = getInsets();

		setSize(width + i.left + i.right, height + i.top + i.bottom);

		setVisible(true);
	}

	public void paint(Graphics g) {
		width = getWidth();
		height = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		Insets i = getInsets();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width + i.left + i.right, height + i.top + i.bottom);
//		int iterations = 11;
//		LinkedList<Pair<Double, Double>> current = new LinkedList<>();
//		LinkedList<Pair<Double, Double>> next = new LinkedList<>();
//		current.add(new Pair<Double, Double>(0d, 0d));
//		LinkedList<Function<Pair<Double, Double>, Pair<Double, Double>>> ifs = new LinkedList<>();
//		ifs.add(new Function<Pair<Double, Double>, Pair<Double, Double>>(){
//			@Override
//			public Pair<Double, Double> apply(Pair<Double, Double> t) {
//				return new Pair<Double, Double>(t.getFirst() * .5 , t.getSecond() * .5);
//			}
//		});
//		ifs.add(new Function<Pair<Double, Double>, Pair<Double, Double>>(){
//			@Override
//			public Pair<Double, Double> apply(Pair<Double, Double> t) {
//				return new Pair<Double, Double>(t.getFirst() * .5 + .5, t.getSecond() * .5);
//			}
//		});
//		ifs.add(new Function<Pair<Double, Double>, Pair<Double, Double>>(){
//			@Override
//			public Pair<Double, Double> apply(Pair<Double, Double> t) {
//				return new Pair<Double, Double>(t.getFirst() * .5 +.25, t.getSecond() * .5 + .43301270189);
//			}
//		});
//		for (int x = 0; x < iterations; x++){
//			Iterator<Pair<Double, Double>> pointIterator = current.iterator();
//			while (pointIterator.hasNext()){
//				Pair<Double, Double> nextPoint = pointIterator.next();
//				Iterator<Function<Pair<Double, Double>, Pair<Double, Double>>> ifsIterator = ifs.iterator();
//				while (ifsIterator.hasNext()){
//					Function<Pair<Double, Double>, Pair<Double, Double>> function = ifsIterator.next();
//					next.add(function.apply(nextPoint));
//				}
//			}
//			current = next;
//			next = new LinkedList<>();
//		}
//		System.out.println("finished");
//		g2d.setColor(Color.BLACK);
//		plotPoints(g2d, current, i.left, i.top, width, height);
		// plot the x axis
		g2d.setColor(Color.BLACK);
		plotRFunction(g2d, new ZeroFunction(), i.left, i.top, width, height);
		RFunction f = new RationalFunction(new PowerFunction(1, 1),
				new ExponentialFunction());
		g2d.setColor(Color.RED);
		plotRFunction(g2d, f, i.left, i.top, width, height);
		g2d.setColor(Color.BLUE);
		plotRFunction(g2d, f.derivate(), i.left, i.top, width, height);
		g2d.setColor(Color.GREEN);
		plotRFunction(g2d, f.derivate().derivate(), i.left, i.top, width, height);
	}

	public void plotRFunction(Graphics2D g2d, RFunction function, int xOffset, int yOffset, int width, int height) {
		double xLeft = -1;
		double xRight = 5;
		double yBot = -.25;
		double yTop = .5;
		double stepSize = 0.1;
		for (double i = 0; i < width; i += stepSize) {
			double x = i / width * (xRight - xLeft) + xLeft;
			double y = function.evaluate(x);
			double xPixel = i + xOffset;
			double yPixel = height - (y - yBot) * height / (yTop - yBot) + yOffset;
			g2d.drawLine((int) xPixel, (int) yPixel, (int) xPixel, (int) yPixel);
		}

	}
	
	public void plotPoints(Graphics2D g2d, List<Pair<Double, Double>> points, int xOffset, int yOffset, int width, int height){
		double xLeft = -1;
		double xRight = 2;
		double yBot = -0.25;
		double yTop = 1.75;
		Iterator<Pair<Double, Double>> iterator = points.iterator();
		
		while (iterator.hasNext()){
			Pair<Double, Double> next = iterator.next();
			double x = next.getFirst();
			double y = next.getSecond();
			
			double xPixel = (x - xLeft) * width / (xRight - xLeft) + xOffset;
			double yPixel = height - (y - yBot) * height / (yTop - yBot);
			
			System.out.printf("%f, %f :: %f, %f%n", x, y, xPixel, yPixel);
			g2d.drawLine((int) xPixel, (int) yPixel, (int) xPixel, (int) yPixel);
		}
	}

}

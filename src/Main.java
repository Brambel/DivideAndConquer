import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// will call both programs
		ConvexHull hull = new ConvexHull();
		Random r = new Random();
		
		int n = 100;
		for(int i=0;i<n;++i){
			hull.addPoint(r.nextInt(n), r.nextInt(n));
		}
		List<Point2D> points = hull.getPoints();
		Polygon convexHull = new Polygon();
		List<Point2D> h = hull.calcHull();
		for(Point2D p : h){
			convexHull.addPoint((int)p.getX(), (int)p.getY());
		}

		hull.printPoints();
		hull.printHull();
		System.out.println("\nThe tromino puzzle, 4x4 starting point 1,0");
		
		Tromino trom = new Tromino(4, new Point2D.Float(0,1));
		trom.print();
		System.out.println("");
		trom.runPuzzle();
		trom.print();
		System.out.println("\nThe tromino puzzle, 8x8 starting point 0,7");
		Tromino trom2 = new Tromino(8, new Point2D.Float(0,7));
		trom2.print();
		System.out.println("");
		trom2.runPuzzle();
		trom2.print();
		
	}

}

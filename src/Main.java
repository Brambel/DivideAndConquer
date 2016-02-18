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
		
		int n = 10;
		for(int i=0;i<n;++i){
			hull.addPoint(r.nextInt(n), r.nextInt(n));
		}
		List<Point2D> points = hull.getPoints();
		Polygon convexHull = new Polygon();
		List<Point2D> h = hull.calcHull();
		for(Point2D p : h){
			convexHull.addPoint((int)p.getX(), (int)p.getY());
		}
		
		System.out.println(points.toString());
		hull.print();
		/**
		for(Point2D p :hull.getPoints()){
			if(!convexHull.contains(p)){
				System.out.println(p.toString()+" is not in the hull");
			}
		}
		**/
		
	}

}


public class Main {

	public static void main(String[] args) {
		// will call both programs
		ConvexHull hull = new ConvexHull();
		hull.addPoint(0, 3);
		hull.addPoint(1, 1);
		hull.addPoint(3, 5);
		hull.addPoint(3, 1);
		hull.addPoint(4, 3);
		hull.addPoint(4, 0);
		hull.addPoint(5, 2);
		
		System.out.println(hull.calcHull().toString());
	}

}

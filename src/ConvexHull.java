import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ConvexHull {
	private List<Point2D> population = new ArrayList<Point2D>();
	private List<Point2D> hull = new ArrayList<Point2D>();
	private List<Point2D> above = new ArrayList<Point2D>();
	private List<Point2D> below = new ArrayList<Point2D>();
	private Point2D xMax = population.get(0);
	private Point2D xMin = population.get(0);
	private Line2D midLine;
	
	
	public void addPoint(Point2D p){
		population.add(p);
	}
	
	public List<Point2D> getPoints(){
		return population;
	}
	
	public int populationSize(){
		return population.size();
	}
	
	public List<Point2D> calcHull(){
		//left and right for top
		Point2D Q1vert = population.get(0);
		Point2D Q2vert = population.get(0);
		//left and right for bottom
		Point2D Q3vert = population.get(0);
		Point2D Q4vert = population.get(0);
		
		
		//find our x's
		for(Point2D p : population){
			if(xMax.getX()<p.getX()){
				xMax = p;
			}
			if(xMin.getX() > p.getX()){
				xMin = p;
			}
			if(Q1vert.getY() < p.getY()){
				Q1vert = p;
				Q2vert = p;
			}
			if(Q3vert.getY() > p.getY()){
				Q3vert = p;
				Q4vert = p;
			}
		}
		
		midLine = new Line2D.Float(xMin, xMax);
		//now we need to split all points into above or below line
		for(Point2D p : population){
			int pos = midLine.relativeCCW(p);
			if(pos>0){
				above.add(p);
			}else if(pos < 0){
				below.add(p);
			}
		}
		
		//at this point were done with the setup
		
		return hull;
	}
	private void hullQ1(){
		
	}
}

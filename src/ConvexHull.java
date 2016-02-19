import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ConvexHull {
	private List<Point2D> population = new ArrayList<Point2D>();
	private List<Point2D> hull = new ArrayList<Point2D>();
	private List<Point2D> aboveLeft = new ArrayList<Point2D>();
	private List<Point2D> belowLeft = new ArrayList<Point2D>();
	private List<Point2D> aboveRight = new ArrayList<Point2D>();
	private List<Point2D> belowRight = new ArrayList<Point2D>();
	private Point2D xMax;
	private Point2D xMin;
	private Line2D midLine;
	
	
	public void addPoint(Point2D p){
		population.add(p);
	}
	
	public void addPoint(int x, int y){
		population.add(new Point(x, y));
	}
	
	public List<Point2D> getPoints(){
		return population;
	}
	
	public int populationSize(){
		return population.size();
	}
	
	public List<Point2D> calcHull(){
		xMax = population.get(0);
		xMin = population.get(0);
		//left and right for top
		Point2D Q1vert = population.get(0);
		Point2D Q2vert = population.get(0);
		//left and right for bottom
		Point2D Q3vert = population.get(0);
		Point2D Q4vert = population.get(0);
		
		
		//find our x's and initial verts
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
			if(pos<0){
				if(p.getX() < Q1vert.getX()){
					aboveLeft.add(p);
				}else{
					aboveRight.add(p);
				}
			}else if(pos > 0){
				if(p.getX() < Q3vert.getX()){
					belowLeft.add(p);
				}else{
					belowRight.add(p);
				}
			}
		}
		//last thing is to add xMin / xMax to the point pools that will use them as endpoints of hull sections
		aboveLeft.add(xMin);
		belowLeft.add(xMin);
		aboveRight.add(xMax);
		belowRight.add(xMax);
		
		//at this point were done with the setup
		//we will push our initial 4 points to the hull counter clockwise conection hull segments between them
		hull.add(Q1vert);	//top point
		hullQ1(Q1vert);
		if(!Q1vert.equals(xMin)){	//we don't want duplicates in the case that a hull has only 3 sides
									//(only happened with small data sets but it's an edge case)
			hull.add(xMin);		//left point
		}
		
		hullQ3(Q3vert);
		if(!xMin.equals(Q4vert)){
			hull.add(Q4vert); 	//bottom point
		}
		hullQ4(Q4vert);
		if(!Q4vert.equals(xMax)){
			hull.add(xMax); 	//right point
		}
		hullQ2(Q2vert);
		hull.add(hull.get(0)); //close the hull
		
		return hull;
	}
	private void hullQ1(Point2D vert){
		Point2D localMax = xMin;  //lowest y point
		List<Point2D> candidates = new ArrayList<Point2D>();
		candidates.add(vert);  //vert is first on list for compare at end
		
		while(! vert.equals(xMin)){//see if we have made it back to x
			
			double distance = 0;  //find greatest distance
			
			for(Point2D p : aboveLeft){
				double cDist = midLine.ptLineDist(p);
				if(vert.getX()>p.getX() && distance<cDist){//find tallest point left of current vert
					localMax = p;
					distance=cDist;
				}else if(cDist==distance){//if there equal we want the left most point
					if(vert.getX()>p.getX()){
						localMax = p;
					}
				}
			}
			if(localMax.equals(vert)){//if we havn't found a new point were done
				//tallest point is below line from vert to xMin
				vert = xMin;
				candidates.add(vert);
			}else{
				//wise add to list of possible vertexes
				vert = localMax;
				candidates.add(vert);
			}
		}
		vert=candidates.remove(0);//pop original vert off list
		while(!candidates.isEmpty()){
			Line2D testLine = new Line2D.Float(vert,xMin); //line from current vert to xMax to see if our local is inside or outside the hull
			if(testLine.relativeCCW(candidates.get(0))== 1){
				vert=candidates.remove(0); //it belongs in the list and will be next vert to test against
				hull.add(vert);
			}else{
				candidates.remove(0);//it's inside the optimum hull and dosn't belong on the list
			}
		}
	}
	
	private void hullQ3(Point2D vert){
		Point2D localMax = xMin;  //lowest y point
		List<Point2D> candidates = new ArrayList<Point2D>();
		candidates.add(vert);  //vert is first on list for compare at end
		
		while(! vert.equals(xMin)){//see if we have made it back to x
			
			double distance = 0;  //find greatest distance
			
			for(Point2D p : belowLeft){
				double cDist = midLine.ptLineDist(p);
				if(vert.getX()>p.getX() && distance<cDist){//find tallest point left of current vert
					localMax = p;
					distance=cDist;
				}else if(cDist==distance){//if there equal we want the left most point
					if(vert.getX()>p.getX()){
						localMax = p;
					}
				}
			}
			if(localMax.equals(vert)){//if we havn't found a new point were done
				//tallest point is below line from vert to xMin
				vert = xMin;
				candidates.add(vert);
			}else{
				//wise add to list of possible vertexes
				vert = localMax;
				candidates.add(vert);
			}
		}
		
		Stack<Point2D> temp = new Stack<Point2D>();//need to reverse order
		vert=candidates.remove(0);//pop original vert off list
		while(!candidates.isEmpty()){
			Line2D testLine = new Line2D.Float(vert,xMin); //line from current vert to xMax to see if our local is inside or outside the hull
			if(testLine.relativeCCW(candidates.get(0))== -1){
				vert=candidates.remove(0); //it belongs in the list and will be next vert to test against
				temp.push(vert);
			}else{
				candidates.remove(0);//it's inside the optimum hull and dosn't belong on the list
			}
		}
		while(!temp.isEmpty()){
			hull.add(temp.pop());
		}
	}
	
	private void hullQ4(Point2D vert){
		Point2D localMax = xMax;  //lowest y point
		List<Point2D> candidates = new ArrayList<Point2D>();
		candidates.add(vert);  //vert is first on list for compare at end
		while(! vert.equals(xMax)){//see if we have made it back to x
			
			double distance = 0;  //find greatest distance
			
			for(Point2D p : belowRight){
				double cDist = midLine.ptLineDist(p);
				if(vert.getX()<p.getX() && distance<cDist){//find tallest point right of current vert
					localMax = p;
					distance=cDist;
				}else if(cDist==distance){//if there equal we want the right most point
					if(vert.getX()<p.getX()){
						localMax = p;
					}
				}
			}

			if(localMax.equals(vert)){//if we havn't found a new point were done
				//tallest point is below line from vert to xMin
				vert = xMax;
				candidates.add(vert);
			}else{
				//wise add to list of possible vertexes
				vert = localMax;
				candidates.add(vert);
			}
		}
		vert=candidates.remove(0);//pop original vert off list
		while(!candidates.isEmpty()){
			Line2D testLine = new Line2D.Float(vert,xMax); //line from current vert to xMax to see if our local is inside or outside the hull
			if(testLine.relativeCCW(candidates.get(0))== 1){
				vert=candidates.remove(0); //it belongs in the list and will be next vert to test against
				hull.add(vert);
			}else{
				candidates.remove(0);//it's inside the optimum hull and dosn't belong on the list
			}
		}
		
	}
	
	private void hullQ2(Point2D vert){
		Point2D localMax = xMax;  //lowest y point
		List<Point2D> candidates = new ArrayList<Point2D>();
		candidates.add(vert);  //vert is first on list for compare at end
		
		while(! vert.equals(xMax)){//see if we have made it back to x
			
			double distance = 0;  //find greatest distance
			
			for(Point2D p : aboveRight){
				double cDist = midLine.ptLineDist(p);
				if(vert.getX()<p.getX() && distance<cDist){//find tallest point right of current vert
					localMax = p;
					distance=cDist;
				}else if(cDist==distance){//if there equal we want the right most point
					if(vert.getX()<p.getX()){
						localMax = p;
					}
				}
			}

			if(localMax.equals(vert)){//if we havn't found a new point were done
				//tallest point is below line from vert to xMin
				vert = xMax;
				candidates.add(vert);
			}else{
				//wise add to list of possible vertexes
				vert = localMax;
				candidates.add(vert);
			}
		}
		
		Stack<Point2D> temp = new Stack<Point2D>();//need to reverse order
		vert=candidates.remove(0);//pop original vert off list
		while(!candidates.isEmpty()){
			Line2D testLine = new Line2D.Float(vert,xMax); //line from current vert to xMax to see if our local is inside or outside the hull
			if(testLine.relativeCCW(candidates.get(0))== -1){
				vert=candidates.remove(0); //it belongs in the list and will be next vert to test against
				temp.push(vert);
			}else{
				candidates.remove(0);//it's inside the optimum hull and dosn't belong on the list
			}
		}
		while(!temp.isEmpty()){
			hull.add(temp.pop());
		}
		
	}
	
	public void print(){
		int i=0;
		System.out.println("The points on the hull are:");
		for(Point2D p : hull){
			System.out.println(++i+")\t("+(int)p.getX()+","+(int)p.getY()+")"); 
		}
	}
}

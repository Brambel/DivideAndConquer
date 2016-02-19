import java.awt.geom.Point2D;

public class Tromino {
	
	private class subBoard{
		int ID;
		int[][] ary;
		boolean empty;
		boolean full;
		
		subBoard(){
			ID=++IDmaker;
			ary=new int[2][2]; //java guarantees initialization to 0, take that c++
			empty=true;
			full=false;
		}

		public void setGiven(int x, int y) {
			ary[x][y]=-1;
			empty=false;
		}
		
		public boolean isEmpty(){
			return empty;
		}
		public boolean isFull(){
			return full;
		}
		public boolean isPartial(){
			return !(empty&&full);
		}
		
		public void placeTrom(int id){
			for(int i=0;i<2;++i){
				for(int j=0;j<2;++j){
					if(ary[i][j]==0){
						ary[i][j]=n;
					}
				}
			}
			full = true;
		}
		
		public void placePartial(int x, int y, int id){
			ary[x][y]=id;
			empty=false;
		}
		
	}
	
	int IDmaker=0;
	int n;
	subBoard[][] board;
	int givenX;
	int givenY;
	
	Tromino(int n_, Point2D given){
		givenX=(int) given.getX();
		givenY=(int) given.getY();
		n=n_;
		board = new subBoard[n/2][n/2];
		board[x/2][y/2].setGiven(givenX%2,givenY%2);
		
	}
	
	
}

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
		
		public void makeEdge(){//will appear as a full board for all further calculations
			full=true;
			empty=false;
		}
		
		public void placePartial(int x, int y, int id){
			ary[x][y]=id;
			empty=false;
		}
		
		public String  getTop(){
			return ary[0][0]+" "+ary[0][1];
		}
		public String  getBot(){
			return ary[1][0]+" "+ary[1][1];
		}
		
	}
	
	int IDmaker=0;
	int n;
	int bounds;
	subBoard[][] board;
	int givenX;
	int givenY;
	
	
	Tromino(int n_, Point2D given){
		givenX=(int) given.getX();
		givenY=(int) given.getY();
		n=n_+4; //we will make board a little bigger and boarder it will full squares, we will need an offest of for all board refrences
		board = new subBoard[n/2][n/2];
		for(int i=0;i<n/2;++i){
			for(int j=0;j<n/2;++j){
				board[i][j] = new subBoard();
			}
		}
		board[(givenX/2)][(givenY/2)].setGiven(givenX%2,givenY%2);
		bounds=(n-4)/2; //make bounds of loops conform to our real board, no borders
		
		for(int i=0;i<(n/2);i++){//using real dimensions to set all our edges
			board[i][0].makeEdge();
			board[i][(n/2)-1].makeEdge(); //arrays are zero based
			board[0][i].makeEdge();
			board[n/2-1][i].makeEdge();
		}
		
	}
	
	private boolean allComplete(){
		for(int i=0;i<bounds;++i){
			for(int j=0;j<bounds;++j){
				if(!board[i+1][j+1].isFull()){
					return false;
				}
			}
		}
		return true;
	}
	
	private void fillPartials(){
		for(int i=0;i<bounds;++i){
			for(int j=0;j<bounds;++j){
				if(board[i+1][j+1].isPartial()){
					board[i+1][j+1].placeTrom(++IDmaker);//placing a new tromino with distinct id
				}
			}
		}
	}
	
	private void placeNewTrom(){//will add the corner tromino across 3 sub boards
		for(int i=0;i<bounds;++i){
			for(int j=0;j<bounds;++j){
				if(board[i+1][j+1].isFull()){//find a full sub board
					
				}
			}
		}
	}
	
	public void print(){
		for(int i=0;i<bounds;++i){
			for(int j=0;j<bounds;++j){
				System.out.print(board[i][j].getTop()+" ");
			}
			System.out.println();
			for(int j=0;j<bounds;++j){
				System.out.print(board[i][j].getBot()+" ");
			}
			System.out.println();
		}
	}
	
}

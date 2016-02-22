import java.awt.geom.Point2D;

public class Tromino {
	
	private class subBoard{
		int[][] ary;
		boolean empty;
		boolean full;
		
		subBoard(){
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
						ary[i][j]=id;
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
		n=n_+4; //we will make board a little bigger and boarder it will full squares, we will need an offset of for all board references
		board = new subBoard[n/2][n/2];
		for(int i=0;i<n/2;++i){
			for(int j=0;j<n/2;++j){
				board[i][j] = new subBoard();
			}
		}
		board[(givenX/2)+1][(givenY/2)+1].setGiven(givenX%2,givenY%2);
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
					//check upLeft, upRight, downRight, downLeft corners for 3 free squares
					if(board[i][j].isEmpty()&&board[i+1][j].isEmpty()&&board[i][j+1].isEmpty()){//upLeft
						int tempID=++IDmaker;
						board[i][j].placePartial(1,1,tempID);//middle		
						board[i+1][j].placePartial(0, 1, tempID);//right	
						board[i][j+1].placePartial(1, 0, tempID);//down
						return;
						
					}
					else if(board[i+2][j].isEmpty()&&board[i+1][j].isEmpty()&&board[i+2][j+1].isEmpty()){//upRight
						int tempID=++IDmaker;
						board[i+2][j].placePartial(0,1,tempID);//middle		
						board[i+1][j].placePartial(1, 1, tempID);//left	
						board[i+2][j+1].placePartial(0, 0, tempID);//down
						return;
					}
					else if(board[i+2][j+2].isEmpty()&&board[i+2][j+1].isEmpty()&&board[i+1][j+2].isEmpty()){//downRight
						int tempID=++IDmaker;
						board[i+2][j+2].placePartial(0,0,tempID);//middle		
						board[i+1][j+2].placePartial(1, 0, tempID);//left	
						board[i+2][j+1].placePartial(0, 1, tempID);//up
						return;
					}
					else if(board[i+2][j+2].isEmpty()&&board[i][j+1].isEmpty()&&board[i+1][j+2].isEmpty()){//downLeft
						int tempID=++IDmaker;
						board[i][j+2].placePartial(1,0,tempID);//middle		
						board[i+1][j+2].placePartial(0,0, tempID);//right	
						board[i][j+1].placePartial(1, 1, tempID);//up
						return;
					}
				}
			}
		}
	}
	
	public void runPuzzle(){
		while(!allComplete()){
			fillPartials();
			placeNewTrom();
		}
	}
	
	public void print(){
		for(int i=0;i<bounds;++i){
			for(int j=0;j<bounds;++j){
				System.out.print(board[i+1][j+1].getTop()+" ");
			}
			System.out.println();
			for(int j=0;j<bounds;++j){
				System.out.print(board[i+1][j+1].getBot()+" ");
			}
			System.out.println();
		}
	}
	
}

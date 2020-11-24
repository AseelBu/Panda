package Model;

import java.util.ArrayList;

import Utils.Directions;
import Utils.PrimaryColor;

public class Soldier extends Piece{

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param color
	 * @param location
	 */
	public Soldier(int id,PrimaryColor color, Location location) {
		super(id, color, location);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void move(Tile targetTile) {
		// TODO Auto-generated method stub
		Location targetLocation = targetTile.getLocation();
		if(this.isMoveLegal(targetLocation)) {
			if(Board.getInstance().canPieceMove(this, targetLocation, null)) {
				//			TODO does moving contains eating?
				this.setLocation(targetLocation);
			}
		}

	}


	@Override
	public boolean isMoveLegal(Location targetLocation) {
		// TODO Auto-generated method stub
		Board board = Board.getInstance();
		int targetRow = targetLocation.getRow();
		char targetCol= targetLocation.getColumn();

		Location currentPos = this.getLocation();
		int curRow = currentPos.getRow();
		char curCol= currentPos.getColumn();

		//rows info
		int rowLowerBound= 1;
		int rowUpperBound=board.getBoardSize();

		int row1Up= curRow+1;
		int row2Up=curRow+2;
		int row1Down=curRow-1;
		int row2Down=curRow-2;

		//column info
		int colLowerBound= board.getColumnLowerbond();
		int colUpperBound=board.getColumnUpperbond();

		int col1Right= (char)curCol+1;
		int col2Right=(char)curCol+2;
		int col1Left=(char)curCol-1;
		int col2Left=(char)curCol-2;



		//		check if it's 1 diagonal move
		if((targetRow==row1Up && row1Up<= rowUpperBound) || (targetRow==row1Down && row1Down>= rowLowerBound) ) {
			if((targetCol == col1Right && col1Right<=colUpperBound)|| (targetCol == col1Left && col1Left>=colLowerBound)) {
				return true;
			}
		}
//		check if it's 2 diagonal move
		else if( (targetRow==row2Up &&  row2Up<= rowUpperBound) || (targetRow==row2Down && row2Down>= rowLowerBound) ) {
			if((targetCol == col2Right && col2Right<=colUpperBound) || (targetCol == col2Left && col2Left>=colLowerBound)) {
				return true;
			}
		}


			return false;
		}


		/**
		 * 
		 * @param piece that we want to check
		 * 
		 * @return list of pieces that are edible for specific piece, null if piece can't eat
		 */
		public ArrayList<Piece> getEdiblePieces() {
			//forSoldier
			ArrayList<Piece> ediblePieces = new ArrayList<Piece>();
			Board board = Board.getInstance();
			Location pieceLoc = this.getLocation();
			Location UL =pieceLoc.addToLocationDiagonally(Directions.UP_LEFT, 1);
			Location UR =pieceLoc.addToLocationDiagonally(Directions.UP_RIGHT, 1);
			Location DL =pieceLoc.addToLocationDiagonally(Directions.DOWN_LEFT, 1);
			Location DR =pieceLoc.addToLocationDiagonally(Directions.DOWN_RIGHT, 1);

			try {
				if(UL!=null) {
					Piece p =board.getTileInLocation(UL).getPiece();
					if(p!=null) {
						ediblePieces.add(p);
					}
				}
				if(UR!=null) {
					Piece p =board.getTileInLocation(UR).getPiece();
					if(p!=null) {
					ediblePieces.add(p);
					}
				}
				if(DL!=null) {
					Piece p =board.getTileInLocation(DL).getPiece();
					if(p!=null) {
					ediblePieces.add(p);
					}
				}
				if(DR!=null) {
					Piece p =board.getTileInLocation(DR).getPiece();
					if(p!=null) {
					ediblePieces.add(p);
					}
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return ediblePieces;

		}

	
		@Override
		public String toString() {
			return "S-"+this.getColor();
		}


	}

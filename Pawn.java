import java.util.ArrayList;

public class Pawn extends Piece {

	private Boolean firstMove = false;

	public Pawn(String pieceType, Boolean colorWhite) {
		super(pieceType, colorWhite);
	}

	public ArrayList<Tile> validMoveLocations(int x, int y) {
		int moveDir = 1;
		if(super.getColor()) moveDir = -1;

		ArrayList<Tile> moveLocations = new ArrayList<Tile>();
		if ((y == 1 && y+moveDir+moveDir>0) || (y == 6 && y+moveDir+moveDir<7)) {
			moveLocations.add(Board.returnTile(x, y+moveDir));
			moveLocations.add(Board.returnTile(x, y+moveDir+moveDir));
			firstMove = true;
		} else if (y < 7 && y > 0) {
			moveLocations.add(Board.returnTile(x, y+moveDir));
		}

		Tile t = Board.returnTile(x, y+moveDir);
		if (x > 0) {
			t = Board.returnTile(x-1, y+moveDir);
			if (t.returnPiece() != null && t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(Board.returnTile(x-1, y+moveDir));
			}
		}	
		if (x < 7) {
			t = Board.returnTile(x+1, y+moveDir);
			if (t.returnPiece() != null && t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(Board.returnTile(x+1, y+moveDir));
			}
		}	
		t = Board.returnTile(x, y+moveDir);
		if (t.returnPiece() != null && t.returnPiece().getColor() != super.getColor()) {
			moveLocations.remove(Board.returnTile(x, y+moveDir));
			if ((y == 1 && y+moveDir+moveDir>0) || (y == 6 && y+moveDir+moveDir<7)) {
				moveLocations.remove(Board.returnTile(x, y+moveDir+moveDir));
			}	
		}
		if ((y == 1 && y+moveDir+moveDir>0) || (y == 6 && y+moveDir+moveDir<7)) {		
			t = Board.returnTile(x, y+moveDir+moveDir);
			if (t.returnPiece() != null && t.returnPiece().getColor() != super.getColor()) {
				moveLocations.remove(Board.returnTile(x, y+moveDir+moveDir));
			}
		}	

		ArrayList<Tile> moveLocations2 = new ArrayList<Tile>();
		for (Tile tile : moveLocations) {
			if (tile.returnPiece() == null || tile.returnPiece().getColor() != super.getColor()) {
				moveLocations2.add(tile);
			}
		}
		return moveLocations2;
	}
}

import java.util.ArrayList;

public class King extends Piece {

	private int locationX;
	private int locationY;

	public King(String pieceType, Boolean colorWhite, int locationX, int locationY) {
		super(pieceType, colorWhite);
	}

	public ArrayList<Tile> validMoveLocations(int x, int y) {
		ArrayList<Tile> moveLocations = new ArrayList<Tile>();

		if (x > 0) {
			moveLocations.add(Board.returnTile(x-1, y));
		}

		if (x < 7) {
			moveLocations.add(Board.returnTile(x+1, y));
		}

		if (y > 0) {
			moveLocations.add(Board.returnTile(x, y-1));
		}

		if (y < 7) {
			moveLocations.add(Board.returnTile(x, y+1));
		}

		if (x > 0 && y > 0) {
			moveLocations.add(Board.returnTile(x-1, y-1));
		}

		if (x < 7 && y > 0) {
			moveLocations.add(Board.returnTile(x+1, y-1));
		}

		if (x > 0 && y < 7) {
			moveLocations.add(Board.returnTile(x-1, y+1));
		}

		if (x < 7 && y < 7) {
			moveLocations.add(Board.returnTile(x+1, y+1));
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
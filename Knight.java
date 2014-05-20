import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(String pieceType, Boolean colorBlack) {
		super(pieceType, colorBlack);
	}

	public ArrayList<Tile> validMoveLocations(int x, int y) {
		ArrayList<Tile> moveLocations = new ArrayList<Tile>();

		for (int i = -2; i<3; i++) {
			int j = 3 - Math.abs(i);
			if (x+i > -1 && x+i < 8 && i != 0 ) {
				if (y+j > -1 && y+j < 8) {
					moveLocations.add(Board.returnTile(x+i, y+j));
				}
				if (y-j > -1 && y-j < 8) {
					moveLocations.add(Board.returnTile(x+i, y-j));
				}
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
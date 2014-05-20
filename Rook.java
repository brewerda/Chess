import java.util.ArrayList;

public class Rook extends Piece {

	public Rook(String pieceType, Boolean colorWhite) {
		super(pieceType, colorWhite);
	}

	public ArrayList<Tile> validMoveLocations(int x, int y) {

		int movedX = x;
		int movedY = y;

		ArrayList<Tile> moveLocations = new ArrayList<Tile>();

		while(movedX < 7) {
			movedX++;
			Tile t = Board.returnTile(movedX, y);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedX = 7;
			}
		}

		movedX = x;
		while(movedX > 0) {
			movedX--;
			Tile t = Board.returnTile(movedX, y);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedX = 0;
			}
		}

		while(movedY < 7) {
			movedY++;
			Tile t = Board.returnTile(x, movedY);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedY = 7;
			}
		}

		movedY = y;
		while(movedY > 0) {
			movedY--;
			Tile t = Board.returnTile(x, movedY);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedY = 0;
			}
		}

		// for (Tile tile : moveLocations) {
		// 	Board.validMoveTile(tile.returnLocationX(), tile.returnLocationY());
		// }
		return moveLocations;
	}
}
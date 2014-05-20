import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(String pieceType, Boolean colorBlack) {
		super(pieceType, colorBlack);
	}

	public ArrayList<Tile> validMoveLocations(int x, int y) {
		int movedX = x;
		int movedY = y;

		ArrayList<Tile> moveLocations = new ArrayList<Tile>();

		while(movedX < 7 && movedY < 7) {
			movedX++;
			movedY++;
			Tile t = Board.returnTile(movedX, movedY);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedX = 7;
			}
		}

		movedX = x;
		movedY = y;
		while(movedX > 0  && movedY > 0) {
			movedX--;
			movedY--;
			Tile t = Board.returnTile(movedX, movedY);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedX = 0;
			}
		}

		movedX = x;
		movedY = y;
		while(movedX < 7 && movedY > 0) {
			movedX++;
			movedY--;
			Tile t = Board.returnTile(movedX, movedY);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedY = 0;
			}
		}

		movedX = x;
		movedY = y;
		while(movedX > 0 && movedY < 7) {
			movedX--;
			movedY++;
			Tile t = Board.returnTile(movedX, movedY);
			if (t.returnPiece() == null || t.returnPiece().getColor() != super.getColor()) {
				moveLocations.add(t);
			}
			if (t.returnPiece() != null) {
				movedY = 7;
			}
		}

		// for (Tile tile : moveLocations) {
		// 	Board.validMoveTile(tile.returnLocationX(), tile.returnLocationY());
		// }
		return moveLocations;
	}
}
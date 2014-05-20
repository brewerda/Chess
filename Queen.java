import java.util.ArrayList;

public class Queen extends Piece {

	private Bishop b;
	private Rook r;

	public Queen(String pieceType, Boolean colorBlack) {
		super(pieceType, colorBlack);
	}

	public ArrayList<Tile> validMoveLocations(int x, int y) {
		if (getColor()) {
			b = new Bishop("bishop_white", true);
			r = new Rook("rook_white", true);
		} else {
			b = new Bishop("bishop_black", false);
			r = new Rook("rook_black", false);
		}
		ArrayList<Tile> moveLocations = new ArrayList<Tile>();
		moveLocations = b.validMoveLocations(x, y);
		for (Tile t : r.validMoveLocations(x, y)) {
			moveLocations.add(t);
		}
		return moveLocations;
	}
}
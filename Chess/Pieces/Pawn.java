public class Pawn extends Piece {
	private Color color;
	private Image white;
	private Image black;
	public Pawn(Color color) {
		this.color = color;
		this.white = Piece.loadImage("pawn_white");
		this.black = Piece.loadImage("pawn_black");
	}
}
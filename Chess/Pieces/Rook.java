public class Rook extends Piece {
	private Color color;
	private Image white;
	private Image black;
	public Rook(Color color) {
		this.color = color;
		this.white = Piece.loadImage("rook_white");
		this.black = Piece.loadImage("rook_black");
	}
}
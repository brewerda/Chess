public class King extends Piece {
	private Color color;
	private Image white;
	private Image black;
	public King(Color color) {
		this.color = color;
		this.white = Piece.loadImage("king_white");
		this.black = Piece.loadImage("king_black");
	}
}
public class Knight extends Piece {
	private Color color;
	private Image white;
	private Image black;
	public Knight(Color color) {
		this.color = color;
		this.white = Piece.loadImage("knight_white");
		this.black = Piece.loadImage("knight_black");
	}
}
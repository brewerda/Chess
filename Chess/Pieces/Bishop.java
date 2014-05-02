public class Bishop extends Piece {
	private Color color;
	private Image white;
	private Image black;
	public Bishop(Color color) {
		this.color = color;
		this.white = Piece.loadImage("bishop_white");
		this.black = Piece.loadImage("bishop_black");
	}
}
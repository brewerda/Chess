import java.io.File;
import java.io.IOException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;

public class Piece {

	private String pieceType;
	private Boolean colorWhite;
	private Boolean hasMoved = false;

	public Piece(String pieceType, Boolean colorWhite) {
		this.pieceType = pieceType;
		this.colorWhite = colorWhite;
	}

	public ImageIcon returnImageIcon() {
		Image image = null;

		try {
			image = ImageIO.read(new File("img" + File.separator + pieceType + ".png"));
		} catch(IOException e) {
			System.exit(1);
		}
		return new ImageIcon(image.getScaledInstance(70, 70, Image.SCALE_SMOOTH));
	}

	public ArrayList<Tile> validMoveLocations(int x, int y) {
		ArrayList<Tile> moveLocations = new ArrayList<Tile>();
		return moveLocations;
	}

	public Boolean getColor() {
		return colorWhite;
	}

	public String returnPieceType() {
		return pieceType;
	}

	public void hasMoved() {
		hasMoved = true;
	}

	public Boolean getHasMoved() {
		return hasMoved;
	}
}
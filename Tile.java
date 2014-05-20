import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

import java.awt.event.*;
 
public class Tile extends JPanel implements MouseListener {

	private int locationX;
	private int locationY;
    private Color color;
    private Boolean possibleMove = false;
    private Piece piece = null;
    private JLabel pieceDisplay;
 
    public Tile(Color color, int x, int y) {
        this.setBackground(color);
        this.color = color;
        locationX = x;
        locationY = y;
        this.addMouseListener(this);
    }

    //adds a piece to this tile
    public void addPiece(Piece piece) {
        if (piece != null) {
            this.piece = piece;
            pieceDisplay = new JLabel(piece.returnImageIcon());
            this.add(pieceDisplay);
            validate();
            repaint();
        }
    }

    //removes the piece on this tile
    public void removePiece() {
        if (piece != null) {
            this.remove(pieceDisplay);
            piece = null;
            validate();
            repaint();
        }
    }

    //returns the piece on this tile
    public Piece returnPiece() {
        return piece;
    }

    //sets tile so a piece can move to it
    public void makeValidMoveTile(Color color) {
        possibleMove = true;
        changeColor(color);
    }

    //sets tile so a piece can not move to it
    public void resetValidMoveTile() {
        possibleMove = false;
        changeColor(this.color);
    }

    //changes the color of the tile
    public void changeColor(Color color) {
        this.setBackground(color);
        validate();
        repaint();
    }

    public int returnLocationX() {
        return locationX;
    }

    public int returnLocationY() {
        return locationY;
    }

    public void checkForPawn() {
        Piece p = Board.returnSelectedPiece();
        if (p.returnPieceType().substring(0,4).equals("pawn")) {
            if ((p.getColor() && locationY == 0)) {
                Board.changePiece("_white");  
            }
            if ((!p.getColor() && locationY == 7)) {
                Board.changePiece("_black");
            }
        }
    }

    public void makeCastle() {
        if (Board.returnCastleKingLoc() == locationX && Board.returnSelectedPiece().returnPieceType().substring(0,4).equals("king")) {
            Tile rookTile = Board.returnCastleRookTile(locationY);
            Piece rook = rookTile.returnPiece();
            Tile rookMoveTo = Board.returnTile(Board.returnCastleRookLoc(), locationY);
            rookTile.removePiece();
            rookMoveTo.addPiece(rook);
            Board.resetCastleKingLoc();
        }
    }

    public void checkForCastle() {
        if (piece.returnPieceType().substring(0,4).equals("king") && !piece.getHasMoved()) {
            Tile t1 = Board.returnTile(0, locationY);
            Tile t2 = Board.returnTile(7, locationY);
            Boolean pathClear = true;

            if (t1.returnPiece() != null) {
                Piece p = t1.returnPiece();
                if (p.returnPieceType().substring(0,4).equals("rook") && !p.getHasMoved()) {

                    for (int j = 1; locationX>j; j++) {
                        if (Board.returnTile(j, locationY).returnPiece() != null) {
                            pathClear = false;
                            j = locationX;
                        }
                    }
                    
                    if (pathClear) Board.validCastle(2, 3, 0, locationY);
                }
            }
            pathClear = true;

            if (t2.returnPiece() != null) {
                Piece p = t2.returnPiece();
                if (p.returnPieceType().substring(0,4).equals("rook") && !p.getHasMoved()) {

                    for (int j = 6; locationX<j; j--) {
                        if (Board.returnTile(j, locationY).returnPiece() != null) {
                            pathClear = false;
                            j = locationX;
                        }
                    }

                    if (pathClear) Board.validCastle(6, 5, 7, locationY);
                }
            } 
        }
    }

    public Boolean checkMoveIntoCheck() {
        Piece p = piece;
        Tile selectedTile = Board.returnSelectedTile();
        Piece selectedPiece = Board.returnSelectedPiece();

        if (selectedPiece.returnPieceType().substring(0,4).equals("king")) {
            Board.moveKingTile(locationX, locationY, selectedPiece.getColor());
        }
        
        removePiece();
        addPiece(selectedPiece);
        Board.removeSelectedPiece();
        ArrayList<Tile> validCheckMoveTiles = Board.checkForCheck(!piece.getColor());

        if (validCheckMoveTiles.size() > 0) {
            System.out.println("Check");
            if (selectedPiece.returnPieceType().substring(0,4).equals("king")) {
                Board.moveKingTile(selectedTile.returnLocationX(), selectedTile.returnLocationY(), selectedPiece.getColor());
            }
            selectedTile.addPiece(piece);
            removePiece();
            addPiece(p);
            return false;
        }
        selectedTile.addPiece(piece);
        removePiece(); 
        addPiece(p);
        return true;

    }

    public void mousePressed(MouseEvent e) {

        if (possibleMove && checkMoveIntoCheck()) {
            checkForPawn();
            makeCastle();
            removePiece();
            addPiece(Board.returnSelectedPiece());
            piece.hasMoved();
            Board.removeSelectedPiece();
            Board.removeValidMoveTiles();
            Board.checkForCheck(piece.getColor());
            Board.changeTurn();         
            if (piece != null) {
                if (piece.returnPieceType().substring(0,4).equals("king")) {
                    Board.moveKingTile(locationX, locationY, piece.getColor());
                }
            }
        } else if (piece != null && piece.getColor() == Board.turn()) {
            Board.selectedTile(locationX, locationY);
            Board.removeValidMoveTiles();
            Board.validMoveTiles(piece.validMoveLocations(locationX, locationY));
            checkForCastle();
            //checkForCheck();
        } 
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
 
public class Board extends JApplet {

    private static Tile[][] tiles = new Tile[8][8];
    private static ArrayList<Tile> validMoveTiles = new ArrayList<Tile>();
    private static Tile tileSelected;
    private static Tile whiteKingTile;
    private static Tile blackKingTile;
    private static Boolean whiteTurn = true;
    private static Boolean forCheck = false;
    private static int castleKingLoc = -1;
    private static int castleRookLoc = -1;
    private static int castleRookCurLoc = -1;
 
    public Board() {
        Container board = getContentPane();
        board.setLayout(new GridLayout(8, 8));

        //Creates all the tiles, empty with no pieces
        for(int i=0; i<8; i++) {
        	for(int j=0; j<8; j++) {
        		if((j%2 == 0 && i%2 == 0) || (j%2 == 1 && i%2 == 1)) {
                    tiles[j][i] = new Tile(Color.RED, j, i);
        			board.add(tiles[j][i]);
        		} else {
        			tiles[j][i] = new Tile(Color.GRAY, j, i);
                    board.add(tiles[j][i]);
        		}
        	}
        }
        initialPieces();
    }

    //Adds all the pieces to the board 
    public static void initialPieces() {
        tiles[0][7].addPiece(new Rook("rook_white", true));
        tiles[1][7].addPiece(new Knight("knighT_white", true));
        tiles[2][7].addPiece(new Bishop("bishop_white", true));
        tiles[3][7].addPiece(new Queen("queen_white", true));
        tiles[4][7].addPiece(new King("king_white", true, 4, 7));
        whiteKingTile = tiles[4][7];
        tiles[5][7].addPiece(new Bishop("bishop_white", true));
        tiles[6][7].addPiece(new Knight("knighT_white", true));
        tiles[7][7].addPiece(new Rook("rook_white", true));
        for (int i=0; i<8; i++) {
            tiles[i][6].addPiece(new Pawn("pawn_white", true));
        }

        tiles[0][0].addPiece(new Rook("rook_black", false));
        tiles[1][0].addPiece(new Knight("knighT_black", false));
        tiles[2][0].addPiece(new Bishop("bishop_black", false));
        tiles[3][0].addPiece(new Queen("queen_black", false));
        tiles[4][0].addPiece(new King("king_black", false, 4, 0));
        blackKingTile = tiles[4][0];
        tiles[5][0].addPiece(new Bishop("bishop_black", false));
        tiles[6][0].addPiece(new Knight("knighT_black", false));
        tiles[7][0].addPiece(new Rook("rook_black", false));
        for (int i=0; i<8; i++) {
            tiles[i][1].addPiece(new Pawn("pawn_black", false));
        }
    }

    //changes the piece, only used for turning pawns into queens
    public static void changePiece(String color) {
        tileSelected.removePiece();
        if (color.equals("_white")) {
           tileSelected.addPiece(new Queen("queen_white", true)); 
        } else {
            tileSelected.addPiece(new Queen("queen_black", false));
        }
    }

    //returns the tile at a specific x and y position
    public static Tile returnTile(int x, int y) {
        return tiles[x][y];
    }

    //stores the tile that has been selected with a piece to be moved
    public static void selectedTile(int x, int y) {
        tileSelected = tiles[x][y];
    }

    //removes the piece from the tile that was selected
    public static void removeSelectedPiece() {
        tileSelected.removePiece();
    }

    //returns the piece that is being moved
    public static Piece returnSelectedPiece() {
        return tileSelected.returnPiece();
    }

    public static Tile returnSelectedTile() {
        return tileSelected;
    }

    //adds all the tiles that the selected piece can move to(doesn't account for check)
    public static void validMoveTiles(ArrayList<Tile> moveLocations) {
        validMoveTiles = moveLocations;
        for (Tile t: validMoveTiles) {
           tiles[t.returnLocationX()][t.returnLocationY()].makeValidMoveTile(Color.GREEN); 
        }
    }

    //removes/resets all the tiles that are validMoveTiles
    public static void removeValidMoveTiles() {
        for (Tile tile : validMoveTiles) {
            tile.resetValidMoveTile();
        }
        validMoveTiles.clear();
    }

    public static void removePiece() {
    }

    //returns what color turn it is
    public static Boolean turn() {
        return whiteTurn;
    }

    //changes the turn each move
    public static void changeTurn() {
        if (whiteTurn) {
            whiteTurn = false;
        } else {
            whiteTurn = true;
        }
    }

    public static void validCastle(int x, int x2, int x3, int y) {
        castleKingLoc = x;
        castleRookLoc = x2;
        castleRookCurLoc = x3;
        validMoveTiles.add(tiles[x][y]);
        tiles[x][y].makeValidMoveTile(Color.blue);
    }

    public static int returnCastleKingLoc() {
        return castleKingLoc;
    }

    public static int returnCastleRookLoc() {
        return castleRookLoc;
    }

    public static Tile returnCastleRookTile(int y) {
        return returnTile(castleRookCurLoc, y);
    }

    public static void resetCastleKingLoc() {
        castleKingLoc = -1;
    }

    public static void moveKingTile(int x, int y, Boolean colorWhite) {
        if (colorWhite) {
            whiteKingTile = tiles[x][y];
        } else {
            blackKingTile = tiles[x][y];
        }
    }

    // Checks all the pieces of the color that just moved to see if the opposite king is in check
    public static ArrayList<Tile> checkForCheck(Boolean colorWhite) {
        ArrayList<Tile> validCheckMoveTiles = new ArrayList<Tile>();
        forCheck = true;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Tile tile = tiles[i][j];
                Piece p = tile.returnPiece();
                if (p != null && p.getColor() == colorWhite) {
                    //System.out.println(tile.returnLocationX() + " " + tile.returnLocationY());
                    for (Tile t : p.validMoveLocations(tile.returnLocationX(), tile.returnLocationY())) {
                        if (!colorWhite && t.returnLocationX() == whiteKingTile.returnLocationX() && t.returnLocationY() == whiteKingTile.returnLocationY()) {
                            //t.changeColor(Color.red);
                            validCheckMoveTiles.add(t);
                            t.resetValidMoveTile();
                        }
                        if (colorWhite && t.returnLocationX() == blackKingTile.returnLocationX() && t.returnLocationY() == blackKingTile.returnLocationY()) {
                            //t.changeColor(Color.red);
                            validCheckMoveTiles.add(t);
                            t.resetValidMoveTile();
                        }
                    }
                }
            } 
        }
        return validCheckMoveTiles;
    }

}
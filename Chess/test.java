import java.util.*;
import java.awt.*;
import java.applet.*;



public class test extends Applet {
   // width of the chessboard square
   int width;
   // number of black pixels surrounding each square
   int border=3;
   // color of chess squares
   Color lightSquare=new Color(210,255,210);  // light green
   Color darkSquare=new Color(80,180,80);     // dark green
   
   boolean whiteTurn=true;
   
   // used when dragging pieces from one square to another
   boolean dragging=false;
   boolean temp;
   int tempRow, tempCol;
   int dragRow, dragCol;
   char  dragPiece;
   
   private int pawnX[]={ 5,11,10,11,12,15,18,19,20,19,25};
   private int pawnY[]={30,18,15,12,11,10,11,12,15,18,30};
   
   private int kingX[]={ 5, 8, 8, 5, 6, 9,13,14,14,12,12,14,14,16,16,18,18,16,16,17,21,24,25,22,22,25};
   private int kingY[]={30,24,17,11, 8, 6, 5, 5, 4, 4, 2, 2, 0, 0, 2, 2, 4, 4, 5, 5, 6, 8,11,17,24,30};
   
   private int knightX[]={ 5, 8, 8, 8,12, 6, 5,14,15,16,17,18,21,24,25,22,22,25};
   private int knightY[]={30,24,17,17,13,14,11, 5, 3, 5, 3, 5, 6, 8,11,17,24,30};
   
   private int queenX[]={ 5, 8, 8, 5, 5, 9,13,15,17,21,25,25,22,22,25};
   private int queenY[]={30,24,17,11, 3, 6, 5, 2, 5, 6, 3,11,17,24,30};
   
   private int bishopX[]={ 5, 8, 8, 5, 6, 9,10,12,15,13,17,21,24,25,22,22,25};
   private int bishopY[]={30,24,17,11, 8, 6, 6,11,10, 5, 5, 6, 8,11,17,24,30};
   
   private int rookX[]={ 5, 8, 8, 5, 5, 9, 9,13,13,17,17,21,21,25,25,22,22,25};
   private int rookY[]={30,24,17,11, 6, 6, 9, 9, 6, 6, 9, 9, 6, 6,11,17,24,30};
       
   // remember the location of all pieces on board
   char piece[] =
        {'R','H','B','K','Q','B','H','R',
         'P','P','P','P','P','P','P','P',
         ' ',' ',' ',' ',' ',' ',' ',' ',
         ' ',' ',' ',' ',' ',' ',' ',' ',
         ' ',' ',' ',' ',' ',' ',' ',' ',
         ' ',' ',' ',' ',' ',' ',' ',' ',
         'p','p','p','p','p','p','p','p',
         'r','h','b','k','q','b','h','r'};
  
  // isWhite -- true if piece is a white chesspiece
  private boolean isWhite(char ch) {
     return (ch>='a' && ch<='z');
  }
  
  private boolean legalMove(int i, int j) {
     // global variables:
     //    subject piece:  dragPiece
     //    from position:  dragCol, dragRow 
     if (i<0 || i>7 || j<0 || j>7) {
        return false;
     }
     // do not allow move to position occupied by 
     // player making the move
     if (piece[j*8+i]!=' ') {
        // '^' is XOR: false if same,  true if different
        return isWhite(dragPiece)^isWhite(piece[j*8+i]);
     }
     // for now move anywhere
     return true;
  }
  
  private void labelSquare(Graphics g, int i, int j, 
               int xpoints[],int ypoints[]) {
     Polygon poly=new Polygon();
     for (int ip=0; ip<xpoints.length; ip++) {
        poly.addPoint(pos(i)+xpoints[ip]*width/31,
                      pos(j)+ypoints[ip]*width/31);
      }
      g.fillPolygon(poly);
      g.setColor(Color.black);
      g.drawPolygon(poly);
  }
  
  // 
  private boolean isDark(int i,int j) {
     // ^ is exclusive or -- true if both operands are the same
     // test: both i and j are even or both i and j are odd
     return ((i&1)^(j&1))!=0;
  }
  
  // determine position of row i or column i
  private int pos(int i) {
     return border+i*(width+border);
  }
  
  // position inverse --
  // determine row i or column i from position
  private int posinv(int p) {
     // we solve for i in pos() function 
     return (p-border)/(width+border);
  }
  
  private void drawSquare(Graphics g, int i, int j, boolean red) {
     if (red) {
        g.setColor(Color.red);
     } else {
        if (isDark(i,j)) {
           g.setColor(darkSquare);
        } else {
           g.setColor(lightSquare); 
        } 
     }
     g.fillRect(pos(i),pos(j),width,width);
     if (isWhite(piece[j*8+i])) {
        g.setColor(Color.white);
     } else {
        g.setColor(Color.black);
     }
     switch (Character.toUpperCase(piece[j*8+i])) {
     case 'P': labelSquare(g,i,j,pawnX,pawnY);
               break;
     case 'K': labelSquare(g,i,j,kingX,kingY);
               break;
     case 'Q': labelSquare(g,i,j,queenX,queenY);
               break;
     case 'R': labelSquare(g,i,j,rookX,rookY);
               break;
     case 'H': labelSquare(g,i,j,knightX,knightY);
               break;
     case 'B': labelSquare(g,i,j,bishopX,bishopY);
               break;
     default:  break;
     }
  }
  
  private void drawBoard(Graphics g) {
     g.setColor(Color.black);
     g.fillRect(0,0,width*8+border*9,width*8+border*9);
     for (int i=0;i<8;i++) {
        for (int j=0;j<8;j++) {
           drawSquare(g,i,j,false);
        }
     }
  }
  
   public void init() {
      // do not allow size below 200 X 200
      resize(size().width < 200 ? 200 : size().width,
	     size().height < 200 ? 200 : size().height);
      Graphics g=getGraphics();
      g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
   }

   // Paint does all the work -- draw the chessboard
   public void paint(Graphics g) {
      Dimension d=size();
      width=(Math.min(d.width,d.height)-border*9)/8;
      drawBoard(g); 
   }


   public void update(Graphics g) {
      paint(g);
   }
   
   public boolean mouseDown(Event e, int x, int y) {
      Graphics g=this.getGraphics();
      int i=posinv(x);
      int j=posinv(y);
      if (i>=8 || j>=8) {
         return true;
      }
      //  do nothing if square is empty
      if (piece[j*8+i]==' ') {
         return true;
      }
      //  do nothing if square is not one of the
      //  pieces of the color whose turn it is
      if (whiteTurn!=isWhite(piece[j*8+i])) {
         return true;
      }
      
      // start dragging a piece      
      dragging=true;
      temp=false;
      dragCol=i;
      dragRow=j;
      dragPiece=piece[j*8+i];
      // highlight the selected square in red
      drawSquare(g,i,j,true);
      return true;
   }
   
   public boolean mouseDrag(Event e, int x, int y) {
      Graphics g=this.getGraphics();
      int i=posinv(x);
      int j=posinv(y);
      if (dragging) {
         // has nothing changed from last time?
         if (temp && i==tempCol && j==tempRow) {
            return true;
         }
         if (temp) {
            // clear the previous temporary move
            drawSquare(g,tempCol,tempRow,false);
            temp=false;
         }
         // check for legal move 
         if (legalMove(i,j)) {
            // update new position to show the piece
            temp=true;
            tempCol=i;
            tempRow=j;
            drawSquare(g,i,j,true);
         }
      }
      return true;
   }
   
   public boolean mouseUp(Event e, int x, int y) {
      Graphics g=this.getGraphics();
      int i=posinv(x);
      int j=posinv(y);
      if (dragging) {
         if (temp && i>=0 && j<8
                  && !(i==dragCol && j==dragRow)) {
            // make the temporary move permanent
            drawSquare(g,tempCol,tempRow,false);
            // remove the piece from its old position
            piece[dragRow*8+dragCol]=' ';
             // place piece in its new position
            piece[j*8+i]=dragPiece;
            drawSquare(g,i,j,false);
            // change the turn
            whiteTurn=!whiteTurn;
         }
         // remove highlighting on old position
         drawSquare(g,dragCol,dragRow,false);
         temp=false;
         dragging=false;
      }
      return true;
   }
}
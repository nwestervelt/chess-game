import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import ChessPiece.*;

public class Board extends JFrame
{
    private BoardPanel boardPanel;
    private BufferedImage board;

    //array variables
    private int selected = -1;
    private int startPX[];
    private int startPY[];
    private int pX[];
    private int pY[];

    //create all white pieces variables
    private Queen wQueen;
    private BufferedImage wQueenI;
    private King wKing;
    private BufferedImage wKingI;
    private Rook wRook;
    private BufferedImage wRookI;
    private Bishop wBishop;
    private BufferedImage wBishopI;
    private Knight wKnight;
    private BufferedImage wKnightI;
    private Pawn wPawn;
    private BufferedImage wPawnI;

    //create all black pieces variables
    private Queen bQueen;
    private BufferedImage bQueenI;
    private King bKing;
    private BufferedImage bKingI;
    private Rook bRook;
    private BufferedImage bRookI;
    private Bishop bBishop;
    private BufferedImage bBishopI;
    private Knight bKnight;
    private BufferedImage bKnightI;
    private Pawn bPawn;
    private BufferedImage bPawnI;

    public Board()
    {
        super("Chess Board");
        
        MouseHandler mh = new MouseHandler();
        MouseMotionHandler mmh = new MouseMotionHandler();

        //create board panel and add event handlers to it
        boardPanel = new BoardPanel();
        boardPanel.addMouseListener(mh);
        boardPanel.addMouseMotionListener(mmh);
        boardPanel.setPreferredSize(new Dimension(800,800));
        add(boardPanel);

        //initialize coordinate arrays
        startPX = new int [32];
        startPY = new int [32];
        pX = new int [32];
        pY = new int [32];

        initializeBoard();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private class BoardPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(board, 0, 0, null);

            //for loop drawing both sets of pawns
            for (int i = 0; i < 8; i++)
            {
                g.drawImage(wPawnI, pX[i],pY[i], null); 
                g.drawImage(bPawnI, pX[i+16],pY[i+16], null);   
            }
            //draw white pieces
            g.drawImage(wRookI, pX[8],pY[8], null);
            g.drawImage(wKnightI, pX[9],pY[9], null);
            g.drawImage(wBishopI, pX[10],pY[10], null);
            g.drawImage(wQueenI, pX[11],pY[11], null);
            g.drawImage(wKingI, pX[12],pY[12], null);
            g.drawImage(wBishopI, pX[13],pY[13], null);
            g.drawImage(wKnightI, pX[14],pY[14], null);
            g.drawImage(wRookI, pX[15],pY[15], null);

            //draw black pieces
            g.drawImage(bRookI, pX[24],pY[24], null);
            g.drawImage(bKnightI, pX[25],pY[25], null);
            g.drawImage(bBishopI, pX[26],pY[26], null);
            g.drawImage(bQueenI, pX[27],pY[27], null);
            g.drawImage(bKingI, pX[28],pY[28], null);
            g.drawImage(bBishopI, pX[29],pY[29], null);
            g.drawImage(bKnightI, pX[30],pY[30], null);
            g.drawImage(bRookI, pX[31],pY[31], null);
        }
    }
    
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed (MouseEvent e)
        {
            int x1=e.getX();
            int y1=e.getY();
            //decides which piece was selected and if it was black or white
            for (int i = 0; i < pX.length;i++)
            {
                if(x1>pX[i]&&x1<pX[i]+100&&y1>pY[i]&&y1<pY[i]+100)
                {
                    selected = i;
                    startPX[selected] = pX[selected];
                    startPY[selected] = pY[selected];
                }
            }
        }

        public void mouseReleased (MouseEvent e)
        {
            if (selected==-1) return;
            int x2=e.getX();
            int y2=e.getY();
            //checks to see if it is wihtin the boundary of the board
            //if it isnt it is sent back to where it was picked up
            if (x2 > 800 || x2 < 0 || y2 > 800 || y2 < 0)
            {
                pX[selected] = startPX[selected];
                pY[selected] = startPY[selected];
            }
            //if it was than it is placed in the middle of the square it was released in
            else
            {
                pX[selected] = (x2/100)*100;
                pY[selected] = (y2/100)*100;
            }
            boardPanel.repaint();
            selected = -1;
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter
    {
        public void mouseDragged (MouseEvent e)
        {
            if (selected==-1) return;
            //checks to see if it was black or white
            {
                //moves the coordinates to where the mouse is on screen
                pX[selected] = e.getX()-50;
                pY[selected] = e.getY()-50;
                //makes sure the piece being dragged is not outside the boundry of the board
                if (pX[selected] > 700) pX[selected] = 700;
                if (pX[selected] < 0) pX[selected] = 0;
                if (pY[selected] > 700) pY[selected] = 700;
                if (pY[selected] < 0) pY[selected] = 0;
            }
            boardPanel.repaint();
        }
    }

    public void initializeBoard()
    {
        //starting values for pieces coordinates
        for (int i = 0; i < pX.length; i++)
        {
            //white pieces
            if (i < 16)
            {
                if (i < 8)
                {
                    pX[i] = i *100;
                    pY[i] = 600;
                }
                else
                {
                    pX[i] = (i%8)*100;
                    pY[i] = 700;
                }
            }
            //black pieces
            else 
            {
                if (i < 24)
                {
                    pX[i] = (i-16) *100;
                    pY[i] = 100;
                }
                else
                {
                    pX[i] = (i%8)*100;
                    pY[i] = 0;
                }
            }  
        }

        //create white pieces
        wKing = new King(); 
        wQueen = new Queen();
        wRook = new Rook();
        wKnight = new Knight();
        wBishop = new Bishop();
        wPawn = new Pawn();
        //create black pieces
        bKing = new King(); 
        bQueen = new Queen();
        bRook = new Rook();
        bKnight = new Knight();
        bBishop = new Bishop();
        bPawn = new Pawn();

        try
        {
            board = ImageIO.read(new File("board.png"));

            //get white piece images
            wKing.setPlayer('W');
            wKingI = wKing.getImage();
            wQueen.setPlayer('W');
            wQueenI = wQueen.getImage();
            wRook.setPlayer('W');
            wRookI = wRook.getImage();
            wKnight.setPlayer('W');
            wKnightI = wKnight.getImage();
            wBishop.setPlayer('W');
            wBishopI = wBishop.getImage();
            wPawn.setPlayer('W');
            wPawnI = wPawn.getImage();

            //get black piece images
            bKing.setPlayer('B');
            bKingI = bKing.getImage();
            bQueen.setPlayer('B');
            bQueenI = bQueen.getImage();
            bRook.setPlayer('B');
            bRookI = bRook.getImage();
            bKnight.setPlayer('B');
            bKnightI = bKnight.getImage();
            bBishop.setPlayer('B');
            bBishopI = bBishop.getImage();
            bPawn.setPlayer('B');
            bPawnI = bPawn.getImage();
        }
        catch(NoSuchPlayerException nspe)
        {
            System.out.println(nspe);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
    
    public static void main (String[] args)
    {
        new Board();
    }
}
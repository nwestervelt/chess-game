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

    //create all white pieces variables
    private int startWX[];
    private int startWY[];
    private int wX[];
    private int wY[];
    private int selectedW = -1;
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
    private int startBX[];
    private int startBY[];
    private int bX[];
    private int bY[];
    private int selectedB = -1;
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
        startWX = new int [16];
        startWY = new int [16];
        wX = new int [16];
        wY = new int [16];

        startBX = new int [16];
        startBY = new int [16];
        bX = new int [16];
        bY = new int [16];

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

            //draw white pieces
            for (int i = 0; i < 8; i++)
            {
                g.drawImage(wPawnI, wX[i],wY[i], null);    
            }
            g.drawImage(wRookI, wX[8],wY[8], null);
            g.drawImage(wKnightI, wX[9],wY[9], null);
            g.drawImage(wBishopI, wX[10],wY[10], null);
            g.drawImage(wQueenI, wX[11],wY[11], null);
            g.drawImage(wKingI, wX[12],wY[12], null);
            g.drawImage(wBishopI, wX[13],wY[13], null);
            g.drawImage(wKnightI, wX[14],wY[14], null);
            g.drawImage(wRookI, wX[15],wY[15], null);

            //draw black pieces
            for (int i = 0; i < 8; i++)
            {
                g.drawImage(bPawnI, bX[i],bY[i], null);    
            }
            g.drawImage(bRookI, bX[8],bY[8], null);
            g.drawImage(bKnightI, bX[9],bY[9], null);
            g.drawImage(bBishopI, bX[10],bY[10], null);
            g.drawImage(bQueenI, bX[11],bY[11], null);
            g.drawImage(bKingI, bX[12],bY[12], null);
            g.drawImage(bBishopI, bX[13],bY[13], null);
            g.drawImage(bKnightI, bX[14],bY[14], null);
            g.drawImage(bRookI, bX[15],bY[15], null);
        }
    }
    
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed (MouseEvent e)
        {
            int x1=e.getX();
            int y1=e.getY();
            //decides which piece was selected and if it was black or white
            for (int i = 0; i < wX.length;i++)
            {
                //white
                if(x1>wX[i]&&x1<wX[i]+100&&y1>wY[i]&&y1<wY[i]+100)
                {
                    selectedW = i;
                    startWX[selectedW] = wX[selectedW];
                    startWY[selectedW] = wY[selectedW];
                }
                //black
                if(x1>bX[i]&&x1<bX[i]+100&&y1>bY[i]&&y1<bY[i]+100)
                {
                    selectedB = i;
                    startBX[selectedB] = bX[selectedB];
                    startBY[selectedB] = bY[selectedB];
                }
            }

        }

        public void mouseReleased (MouseEvent e)
        {
            if (selectedW==-1 && selectedB==-1) return;
            int x2=e.getX();
            int y2=e.getY();
            //checks to see if it was black or white 
            if(selectedW > -1)
            {
                //checks to see if it is wihtin the boundary of the board
                //if it isnt it is sent back to where it was picked up
                if (x2 > 800 || x2 < 0 || y2 > 800 || y2 < 0)
                {
                    wX[selectedW] = startWX[selectedW];
                    wY[selectedW] = startWY[selectedW];
                }
                //if it was than it is placed in the middle of the square it was released in
                else
                {
                    wX[selectedW] = (x2/100)*100;
                    wY[selectedW] = (y2/100)*100;
                }
            }
            //same but for black pieces
            else
            {
                if (x2 > 800 || x2 < 0 || y2 > 800 || y2 < 0)
                {
                    bX[selectedB] = startBX[selectedB];
                    bY[selectedB] = startBY[selectedB];
                }
                else
                {
                    bX[selectedB] = (x2/100)*100;
                    bY[selectedB] = (y2/100)*100;
                }
            }

            boardPanel.repaint();
            selectedW = -1;
            selectedB = -1;
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter
    {
        public void mouseDragged (MouseEvent e)
        {
            if (selectedW == -1 && selectedB==-1) return;
            //checks to see if it was black or white
            if (selectedW > -1)
            {
                //moves the coordinates to where the mouse is on screen
                wX[selectedW] = e.getX()-50;
                wY[selectedW] = e.getY()-50;
                //makes sure the piece being dragged is not outside the boundry of the board
                if (wX[selectedW] > 700) wX[selectedW] = 700;
                if (wX[selectedW] < 0) wX[selectedW] = 0;
                if (wY[selectedW] > 700) wY[selectedW] = 700;
                if (wY[selectedW] < 0) wY[selectedW] = 0;
            }
            //same but for black pieces
            else
            {
                bX[selectedB] = e.getX()-50;
                bY[selectedB] = e.getY()-50;
                if (bX[selectedB] > 700) bX[selectedB] = 700;
                if (bX[selectedB] < 0) bX[selectedB] = 0;
                if (bY[selectedB] > 700) bY[selectedB] = 700;
                if (bY[selectedB] < 0) bY[selectedB] = 0;
            }
            boardPanel.repaint();
        }
    }

    public void initializeBoard()
    {
        //starting values for white piece coordinates
        for (int i = 0; i < wX.length; i++)
        {
            if (i < 8)
            {
                wX[i] = i *100;
                wY[i] = 600;
            }
            else
            {
                wX[i] = (i%8)*100;
                wY[i] = 700;
            }
        }
        //starting values for black piece coordinates
        for (int i = 0; i < bX.length; i++)
        {
            if (i < 8)
            {
                bX[i] = i *100;
                bY[i] = 100;
            }
            else
            {
                bX[i] = (i%8)*100;
                bY[i] = 0;
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
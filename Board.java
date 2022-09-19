// Class file used for controlling the game's logic and drawing the board.
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import ChessPiece.*;

public class Board extends JFrame
{
    //declare constants used to reference piece objects more easily
    public static final int W_ROOK1 = 0, W_KNIGHT1 = 1, W_BISHOP1 = 2, W_KING = 3, W_QUEEN = 4,
         W_BISHOP2 = 5, W_KNIGHT2 = 6, W_ROOK2 = 7;
    public static final int W_PAWN1 = 8, W_PAWN2 = 9, W_PAWN3 = 10, W_PAWN4 = 11, W_PAWN5 = 12,
        W_PAWN6 = 13, W_PAWN7 = 14, W_PAWN8 = 15;
    public static final int B_PAWN1 = 16, B_PAWN2 = 17, B_PAWN3 = 18, B_PAWN4 = 19, B_PAWN5 = 20,
        B_PAWN6 = 21, B_PAWN7 = 22, B_PAWN8 = 23;
    public static final int B_ROOK1 = 24, B_KNIGHT1 = 25, B_BISHOP1 = 26, B_KING = 27, B_QUEEN = 28,
        B_BISHOP2 = 29, B_KNIGHT2 = 30, B_ROOK2 = 31;

    private BoardPanel boardPanel;
    private BufferedImage board;
    private PieceAbstract[] pieces;

    //create integers, used for cursor position and the selected piece
    private int currX, currY, offX, offY, selected = -1;

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

        //create a generic array of ChessPiece objects
        pieces = new PieceAbstract[32];

        //get board's background image
        try
        {
            board = ImageIO.read(new File("board.png"));
        }
        catch(IOException ioe)
        {
            System.out.printf("%s%n%nTerminating.", ioe.getMessage());
            System.exit(1);
        }

        initializePieces();
        
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

            try
            {
                for(int i = 0; i < pieces.length; i++)
                {
                    if(i != selected)
                        g.drawImage(pieces[i].getImage(), pieces[i].getX()*100, pieces[i].getY()*100, null);
                }
                if(selected != -1)
                    g.drawImage(pieces[selected].getImage(), currX, currY, null);
            }
            catch(IOException ioe)
            {
                System.out.printf("%s%n%nTerminating.", ioe.getMessage());
                System.exit(1);
            }
        }
    }
    
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed (MouseEvent e)
        {
            int x=e.getX();
            int y=e.getY();
            //determines which piece was selected
            for (int i = 0; i < pieces.length; i++)
            {
                if(x-(pieces[i].getX()*100) > 0 && x-(pieces[i].getX()*100) <= 100 &&
                    y-(pieces[i].getY()*100) > 0 && y-(pieces[i].getY()*100) <= 100)
                {
                    selected = i;
                    offX = x-(pieces[i].getX()*100);
                    offY = y-(pieces[i].getY()*100);
                }
            }
        }

        public void mouseReleased (MouseEvent e)
        {
            try
            {
                if (selected==-1) return;
                //only set the new piece position if it is within the board's boundraries
                if (currX <= 800 || currX >= 0 || currY <= 800 || currY >= 0)
                {
                    pieces[selected].move((currX+offX)/100, (currY+offY)/100, pieces);
                }
            }
            catch(InvalidMoveException ime)
            {
                System.out.println(ime.getMessage());
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
            {
                //set current cursor position for drawing piece while in motion
                currX = e.getX()-offX;
                currY = e.getY()-offY;
                //make sure the cursor is not outside the boundry of the board
                if (currX > 700) currX = 700;
                if (currX < 0) currX = 0;
                if (currY > 700) currY = 700;
                if (currY < 0) currY = 0;
            }
            boardPanel.repaint();
        }
    }

    private void initializePieces()
    {
        try
        {
            for(int i = 0; i < pieces.length; i++)
            {
                // white pieces
                if(i == W_ROOK1 || i == W_ROOK2)
                    pieces[i] = new Rook(i%8, 0, 'W');
                else if(i == W_KNIGHT1 || i == W_KNIGHT2)
                    pieces[i] = new Knight(i%8, 0, 'W');
                else if(i == W_BISHOP1 || i == W_BISHOP2)
                    pieces[i] = new Bishop(i%8, 0, 'W');
                else if(i == W_QUEEN)
                    pieces[i] = new Queen(i%8, 0, 'W');
                else if(i == W_KING)
                    pieces[i] = new King(i%8, 0, 'W');
                else if(i >= W_PAWN1 && i <= W_PAWN8)
                    pieces[i] = new Pawn(i%8, 1, 'W');

                // black pieces
                else if(i >= B_PAWN1 && i <= B_PAWN8)
                    pieces[i] = new Pawn(i%8, 6, 'B');
                else if(i == B_ROOK1 || i == B_ROOK2)
                    pieces[i] = new Rook(i%8, 7, 'B');
                else if(i == B_KNIGHT1 || i == B_KNIGHT2)
                    pieces[i] = new Knight(i%8, 7, 'B');
                else if(i == B_BISHOP1 || i == B_BISHOP2)
                    pieces[i] = new Bishop(i%8, 7, 'B');
                else if(i == B_QUEEN)
                    pieces[i] = new Queen(i%8, 7, 'B');
                else if(i == B_KING)
                    pieces[i] = new King(i%8, 7, 'B');
            }
        }
        catch(NoSuchPlayerException nspe)
        {
            System.out.printf("%s%n%nTerminating.", nspe.getMessage());
            System.exit(1);
        }
    }
    
    public static void main(String[] args)
    {
        new Board();
    }
}
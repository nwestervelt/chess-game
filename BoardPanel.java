//class file for drawing the board
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class BoardPanel extends JPanel
{
    //integers used for cursor position and the selected piece
    private int currX, currY, selected = -1;

    private BufferedImage board;
    private MainFrame mainFrame;
    private PieceAbstract[] pieces;

    public BoardPanel(MainFrame mainFrame, PieceAbstract[] pieces)
    {
        this.mainFrame = mainFrame;
        this.pieces = pieces;
        MouseHandler mh = new MouseHandler();
        MouseMotionHandler mmh = new MouseMotionHandler();

        //get board's background image
        try
        {
            board = ImageIO.read(new File("images/board.png"));
        }
        catch(IOException ioe)
        {
            System.out.printf("%s%n%nTerminating.", ioe.getMessage());
            System.exit(1);
        }

        addMouseListener(mh);
        addMouseMotionListener(mmh);
        setPreferredSize(new Dimension(800,800));
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(board, 0, 0, null);
        
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 20));

        //draw numbers and letters for the rows and columns
        for (int i = 0; i < 8; i++)
        {
            g.drawString("" + (i+1), 0, ((7-i) * 100) + 15);

           //character cast is using ascii values
           g.drawString("" + (char)(97 + i), ((i + 1) * 100) - 15, 795);
        }
        try
        {
            for(int i = 0; i < pieces.length; i++)
            {
                if(i != selected && !pieces[i].isCaptured())
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
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed (MouseEvent e)
        {
            if (mainFrame.isGameover()) return;
            int x=e.getX();
            int y=e.getY();

            //determines which piece was selected
            for (int i = 0; i < pieces.length; i++)
            {
                if(x-(pieces[i].getX()*100) > 0 && x-(pieces[i].getX()*100) <= 100 &&
                    y-(pieces[i].getY()*100) > 0 && y-(pieces[i].getY()*100) <= 100 &&
                    mainFrame.getTurn() == pieces[i].getPlayer())
                {
                    selected = i;
                }
            }
        }
        public void mouseReleased (MouseEvent e)
        {
            try
            {
                if (selected==-1) return;

                //if new coordinates are inside the board's boundraries
                if (currX <= 800 || currX >= 0 || currY <= 800 || currY >= 0)
                {
                    //move the selected piece, include false because this is a regular move
                    pieces[selected].move((currX+50)/100, (currY+50)/100, false);
                    
                    //if a pawn is moved to the other side of the board
                    if(pieces[selected] instanceof Pawn &&
                        (pieces[selected].getY() == 7 || (pieces[selected].getY() == 0)))
                    {
                        //create and make promotion dialog visible
                        PromotionDialog pd = new PromotionDialog(mainFrame);
                        pd.setVisible(true);
                        PieceAbstract newPiece;

                        //replace currently selected piece with selected piece type
                        if(pd.getSelectedButton() == PromotionDialog.QUEEN)
                        {
                            newPiece = new Queen(pieces[selected].getX(), pieces[selected].getY(),
                                pieces[selected].getPlayer(), mainFrame, pieces);
                            pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.KNIGHT)
                        {
                            newPiece = new Knight(pieces[selected].getX(), pieces[selected].getY(),
                                pieces[selected].getPlayer(), mainFrame, pieces);
                            pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.BISHOP)
                        {
                            newPiece = new Bishop(pieces[selected].getX(), pieces[selected].getY(),
                                pieces[selected].getPlayer(), mainFrame, pieces);
                            pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.ROOK)
                        {
                            newPiece = new Rook(pieces[selected].getX(), pieces[selected].getY(),
                                pieces[selected].getPlayer(), mainFrame, pieces);
                            pieces[selected] = newPiece;
                        }
                        //tell the mainFrame to update the history to include the promotion details
                        mainFrame.addPromotion(pieces[selected]);
                    }
                }
                //update turn and menuPanel information
                if(mainFrame.getTurn() == 'W')
                    mainFrame.setTurn('B');
                else
                    mainFrame.setTurn('W');

                mainFrame.updateMenu();

                //set the value for piece moved/ the piece that put king in check
                if (mainFrame.getTurn() == 'W')
                    ((King)pieces[MainFrame.W_KING]).setChecker(selected);
                else 
                    ((King)pieces[MainFrame.B_KING]).setChecker(selected);

                repaint();

                //if player's king is in check mate, update status of game accordingly
                if(mainFrame.getTurn() == 'W' && ((King)pieces[MainFrame.W_KING]).check())
                {
                    if(((King)pieces[MainFrame.W_KING]).checkMate())
                    {
                        mainFrame.setGameover(true);
                        JOptionPane.showMessageDialog(mainFrame, "Black is the winner by checkmate", "Winner!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else if(mainFrame.getTurn() == 'B' && ((King)pieces[MainFrame.B_KING]).check())
                {
                    if(((King)pieces[MainFrame.B_KING]).checkMate())
                    {
                        mainFrame.setGameover(true);
                        JOptionPane.showMessageDialog(mainFrame, "White is the winner by checkmate", "Winner!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            catch(InvalidMoveException ime)
            {
                System.out.println(ime.getMessage());
            }
            repaint();
            selected = -1;
        }
    }
    private class MouseMotionHandler extends MouseMotionAdapter
    {
        public void mouseDragged (MouseEvent e)
        {
            if (selected==-1) return;

            //set current cursor position for drawing piece while in motion
            currX = e.getX()-50;
            currY = e.getY()-50;

            //make sure the cursor is not outside the boundry of the board
            if (currX > 700) currX = 700;
            if (currX < 0) currX = 0;
            if (currY > 700) currY = 700;
            if (currY < 0) currY = 0;

            repaint();
        }
    }
}
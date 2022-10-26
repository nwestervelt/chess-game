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

    public BoardPanel(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
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
        
        g.setFont(new Font(Font.SERIF,Font.BOLD, 15));

        //draw numbers and letters for the rows and columns
        for (int i = 0; i < 8; i++)
        {
            g.drawString("" + (i+1), 0, ((7-i) * 100) + 15);

           //character cast is using ascii values
           g.drawString("" + (char)(97 + i), ((i + 1) * 100) - 15, 790);
        }
        try
        {
            for(int i = 0; i < mainFrame.pieces.length; i++)
            {
                if(i != selected && !mainFrame.pieces[i].isCaptured())
                    g.drawImage(mainFrame.pieces[i].getImage(), mainFrame.pieces[i].getX()*100, mainFrame.pieces[i].getY()*100, null);
            }
            if(selected != -1)
                g.drawImage(mainFrame.pieces[selected].getImage(), currX, currY, null);
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
            if (mainFrame.gameOver) return;
            int x=e.getX();
            int y=e.getY();

            //determines which piece was selected
            for (int i = 0; i < mainFrame.pieces.length; i++)
            {
                if(x-(mainFrame.pieces[i].getX()*100) > 0 && x-(mainFrame.pieces[i].getX()*100) <= 100 &&
                    y-(mainFrame.pieces[i].getY()*100) > 0 && y-(mainFrame.pieces[i].getY()*100) <= 100 &&
                    mainFrame.turn == mainFrame.pieces[i].getPlayer())
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
                    mainFrame.pieces[selected].move((currX+50)/100, (currY+50)/100, false);
                    
                    //if a pawn is moved to the other side of the board
                    if(mainFrame.pieces[selected] instanceof Pawn &&
                        (mainFrame.pieces[selected].getY() == 7 || (mainFrame.pieces[selected].getY() == 0)))
                    {
                        //create and make promotion dialog visible
                        PromotionDialog pd = new PromotionDialog(mainFrame);
                        pd.setVisible(true);
                        PieceAbstract newPiece;

                        //replace currently selected piece with selected piece type
                        if(pd.getSelectedButton() == PromotionDialog.QUEEN)
                        {
                            newPiece = new Queen(mainFrame.pieces[selected].getX(), mainFrame.pieces[selected].getY(),
                                mainFrame.pieces[selected].getPlayer(), mainFrame);
                            mainFrame.pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.KNIGHT)
                        {
                            newPiece = new Knight(mainFrame.pieces[selected].getX(), mainFrame.pieces[selected].getY(),
                                mainFrame.pieces[selected].getPlayer(), mainFrame);
                            mainFrame.pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.BISHOP)
                        {
                            newPiece = new Bishop(mainFrame.pieces[selected].getX(), mainFrame.pieces[selected].getY(),
                                mainFrame.pieces[selected].getPlayer(), mainFrame);
                            mainFrame.pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.ROOK)
                        {
                            newPiece = new Rook(mainFrame.pieces[selected].getX(), mainFrame.pieces[selected].getY(),
                                mainFrame.pieces[selected].getPlayer(), mainFrame);
                            mainFrame.pieces[selected] = newPiece;
                        }
                        //tell the mainFrame to update the history to include the promotion details
                        mainFrame.updateHistoryPromotion(mainFrame.pieces[selected]);
                    }
                }
                //update turn and menuPanel information
                if(mainFrame.turn == 'W')
                    mainFrame.turn = 'B';
                else
                    mainFrame.turn = 'W';

                mainFrame.updateMenu();
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
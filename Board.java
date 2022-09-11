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
    private int startWX[];
    private int startWY[];
    private int wX[];
    private int wY[];
    private int selected = -1;
    private Queen wQueen;
    private BufferedImage wQueenI;
    public Board()
    {
        super("Chess Board");
        
        MouseHandler mh = new MouseHandler();
        MouseMotionHandler mmh = new MouseMotionHandler();

        boardPanel = new BoardPanel();
        boardPanel.addMouseListener(mh);
        boardPanel.addMouseMotionListener(mmh);
        boardPanel.setPreferredSize(new Dimension(800,800));
        add(boardPanel);

        wQueen = new Queen();
        startWX = new int [16];
        startWY = new int [16];
        wX = new int [16];
        wY = new int [16];
        wX [0]= wQueen.getX();
        wY [0]= wQueen.getY();
        try
        {
            board = ImageIO.read(new File("board.png"));
            wQueen.setPlayer('W');
            wQueenI = wQueen.getImage();
        }
        catch(NoSuchPlayerException nspe)
        {
            System.out.println(nspe);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }

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
            g.drawImage(wQueenI, wX[0],wY[0], null);
        }
    }
    
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed (MouseEvent e)
        {
            int x1=e.getX();
            int y1=e.getY();
            if(x1>wX[0]&&x1<wX[0]+100&&y1>wY[0]&&y1<wY[0]+100)
            {
                selected = 0;
            }

        }

        public void mouseReleased (MouseEvent e)
        {
            if (selected==-1) return;
            int x2=e.getX();
            int y2=e.getY();
            wX[selected] = (x2/100)*100;
            wY[selected] = (y2/100)*100;
            boardPanel.repaint();
            selected = -1;
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter
    {
        public void mouseDragged (MouseEvent e)
        {
            if (selected == -1) return;
            startWX[selected] = wX[selected];
            startWY[selected] = wY[selected];
            wX[selected] = e.getX()-50;
            wY[selected] = e.getY()-50;
            boardPanel.repaint();

        }
    }
    
    public static void main (String[] args)
    {
        new Board();
    }
}
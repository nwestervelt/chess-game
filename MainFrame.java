//class for the frame containing all components of the application
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame
{
    //static constants for pieces indexes
    public static final int W_ROOK1 = 24, W_KNIGHT1 = 25, W_BISHOP1 = 26, W_QUEEN = 27, W_KING = 28,
        W_BISHOP2 = 29, W_KNIGHT2 = 30, W_ROOK2 = 31, W_PAWN_MIN = 16, W_PAWN_MAX = 23;
    public static final int B_ROOK1 = 0, B_KNIGHT1 = 1, B_BISHOP1 = 2, B_QUEEN = 3, B_KING = 4,
        B_BISHOP2 = 5, B_KNIGHT2 = 6, B_ROOK2 = 7, B_PAWN_MIN = 8, B_PAWN_MAX = 15;
    
    //array of chess pieces
    public PieceAbstract[] pieces;

    //variable tracking who's turn it is
    public char turn;

    //variable keeping track if game is over
    public boolean gameOver = false;

    //all panels contained in this frame
    private BoardPanel boardPanel;
    private HistoryPanel historyPanel;
    private MenuPanel menuPanel;

    public MainFrame()
    {
        //call super class's constructor and set title
        super("Chess");
        
        //create board panel
        boardPanel = new BoardPanel(this);
        add(boardPanel,BorderLayout.CENTER);

        //create a history panel
        historyPanel = new HistoryPanel(this);
        add(historyPanel,BorderLayout.EAST);

        //create menu panel
        menuPanel = new MenuPanel(this);
        add(menuPanel,BorderLayout.WEST);

        //create array of chess pieces
        pieces = new PieceAbstract[32];

        //setup public (shared) application variables
        turn = 'W';
        gameOver = false;
        initializePieces();

        //setup this frame's behavior/appearance
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    private void initializePieces()
    {
        for(int i = 0; i < pieces.length; i++)
        {
            // white pieces
            if(i == W_ROOK1 || i == W_ROOK2)
                pieces[i] = new Rook(i%8, 7, 'W', this);
            else if(i == W_KNIGHT1 || i == W_KNIGHT2)
                pieces[i] = new Knight(i%8, 7, 'W', this);
            else if(i == W_BISHOP1 || i == W_BISHOP2)
                pieces[i] = new Bishop(i%8, 7, 'W', this);
            else if(i == W_QUEEN)
                pieces[i] = new Queen(i%8, 7, 'W', this);
            else if(i == W_KING)
                pieces[i] = new King(i%8, 7, 'W', this);
            else if(i >= W_PAWN_MIN && i <= W_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 6, 'W', this);

            // black pieces
            else if(i >= B_PAWN_MIN && i <= B_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 1, 'B', this);
            else if(i == B_ROOK1 || i == B_ROOK2)
                pieces[i] = new Rook(i%8, 0, 'B', this);
            else if(i == B_KNIGHT1 || i == B_KNIGHT2)
                pieces[i] = new Knight(i%8, 0, 'B', this);
            else if(i == B_BISHOP1 || i == B_BISHOP2)
                pieces[i] = new Bishop(i%8, 0, 'B', this);
            else if(i == B_QUEEN)
                pieces[i] = new Queen(i%8, 0, 'B', this);
            else if(i == B_KING)
                pieces[i] = new King(i%8, 0, 'B', this);
        }
    }
    public void reset()
    {
        initializePieces();
        turn = 'W';
        gameOver = false;

        historyPanel.clearHistory();
        menuPanel.updateMenuLabels();
        boardPanel.repaint();
    }
    public void updateMenu()
    {
        menuPanel.updateMenuLabels();
    }
    public void updateHistory(PieceAbstract piece, int moveType, int startX, boolean isPromoting)
    {
        historyPanel.addMove(piece, moveType, startX, isPromoting);
    }
    public void updateHistoryPromotion(PieceAbstract piece)
    {
        historyPanel.addPromotion(piece);
    }
    //start the application
    public static void main(String[] args)
    {
        new MainFrame();
    }
}
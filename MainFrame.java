//class for the frame containing all components of the application
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame
{
    //static constants for pieces indexes
    public static final int W_ROOK1 = 24, W_KNIGHT1 = 25, W_BISHOP1 = 26, W_QUEEN = 27, W_KING = 28,
        W_BISHOP2 = 29, W_KNIGHT2 = 30, W_ROOK2 = 31, W_PAWN_MIN = 16, W_PAWN_MAX = 23, W_MIN = 16;
    public static final int B_ROOK1 = 0, B_KNIGHT1 = 1, B_BISHOP1 = 2, B_QUEEN = 3, B_KING = 4,
        B_BISHOP2 = 5, B_KNIGHT2 = 6, B_ROOK2 = 7, B_PAWN_MIN = 8, B_PAWN_MAX = 15, B_MIN = 0;
    
    //variables tracking the state of the game
    private PieceAbstract[] pieces;
    private char turn;
    private boolean gameOver = false;

    //all panels contained in this frame
    private BoardPanel boardPanel;
    private HistoryPanel historyPanel;
    private MenuPanel menuPanel;

    public MainFrame()
    {
        //call super class's constructor and set title
        super("Chess");
        
        //create array of chess pieces
        pieces = new PieceAbstract[32];

        //setup game state variables
        turn = 'W';
        gameOver = false;
        initializePieces();

        //create board panel
        boardPanel = new BoardPanel(this, pieces);
        add(boardPanel,BorderLayout.CENTER);

        //create a history panel
        historyPanel = new HistoryPanel(pieces);
        add(historyPanel,BorderLayout.EAST);

        //create menu panel
        menuPanel = new MenuPanel(this);
        add(menuPanel,BorderLayout.WEST);

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
                pieces[i] = new Rook(i%8, 7, 'W', this, pieces);
            else if(i == W_KNIGHT1 || i == W_KNIGHT2)
                pieces[i] = new Knight(i%8, 7, 'W', this, pieces);
            else if(i == W_BISHOP1 || i == W_BISHOP2)
                pieces[i] = new Bishop(i%8, 7, 'W', this, pieces);
            else if(i == W_QUEEN)
                pieces[i] = new Queen(i%8, 7, 'W', this, pieces);
            else if(i == W_KING)
                pieces[i] = new King(i%8, 7, 'W', this, pieces);
            else if(i >= W_PAWN_MIN && i <= W_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 6, 'W', this, pieces);

            // black pieces
            else if(i >= B_PAWN_MIN && i <= B_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 1, 'B', this, pieces);
            else if(i == B_ROOK1 || i == B_ROOK2)
                pieces[i] = new Rook(i%8, 0, 'B', this, pieces);
            else if(i == B_KNIGHT1 || i == B_KNIGHT2)
                pieces[i] = new Knight(i%8, 0, 'B', this, pieces);
            else if(i == B_BISHOP1 || i == B_BISHOP2)
                pieces[i] = new Bishop(i%8, 0, 'B', this, pieces);
            else if(i == B_QUEEN)
                pieces[i] = new Queen(i%8, 0, 'B', this, pieces);
            else if(i == B_KING)
                pieces[i] = new King(i%8, 0, 'B', this, pieces);
        }
    }
    //reset the application to beginning state
    public void reset()
    {
        initializePieces();
        turn = 'W';
        menuPanel.updateMenuLabels();
        gameOver = false;

        historyPanel.clearHistory();
        boardPanel.repaint();
    }
    //get the current player's turn
    public char getTurn()
    {
        return turn;
    }
    //set the current player's turn
    public void setTurn(char turn)
    {
        this.turn = turn;
    }
    //get the current game over state
    public boolean isGameover()
    {
        return gameOver;
    }
    //set the current game over state
    public void setGameover(boolean gameOver)
    {
        this.gameOver = gameOver;
    }
    //update the menus in the menu panel
    public void updateMenu()
    {
        menuPanel.updateMenuLabels();
    }
    //add move to history in the history panel
    public void addMove(PieceAbstract piece, int moveType, int startX, boolean isPromoting)
    {
        historyPanel.addMove(piece, moveType, startX, isPromoting);
    }
    //add a promotion to history in the history panel
    public void addPromotion(PieceAbstract piece)
    {
        historyPanel.addPromotion(piece);
    }
    //start the application
    public static void main(String[] args)
    {
        new MainFrame();
    }
}
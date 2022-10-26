//class for panel displaying move history
import java.awt.*;
import javax.swing.*;

public class HistoryPanel extends JPanel
{
    //move type constants
    public static final int NORMAL = -1, CAPTURE = 0, KING_CASTLE = 1, QUEEN_CASTLE = 2;

    private int turnCount = 1;
    private String[][] notation;
    private String[] pawnNotation;

    private JLabel hLabel;
    private JTextArea history;
    private JScrollPane scrollPane;
    private MainFrame mainFrame;

    public HistoryPanel(MainFrame mainFrame)
    {
        //reference to the MainFrame
        this.mainFrame = mainFrame;
        //label for history
        hLabel = new JLabel("Move History");
        hLabel.setPreferredSize(new Dimension (200,100));
        hLabel.setHorizontalAlignment(JLabel.CENTER);
        hLabel.setVerticalAlignment(JLabel.BOTTOM);
        add(hLabel);

        //text area displaying history
        history = new JTextArea();
        history.setPreferredSize(new Dimension (200,5000));
        history.setLineWrap(true);
        history.setWrapStyleWord(true);
        history.setEditable(false);
        history.setAutoscrolls(true);
        add(history);

        //add scrollpane for text area
        scrollPane = new JScrollPane(history);
        scrollPane.setPreferredSize(new Dimension (200,600));
        scrollPane.createVerticalScrollBar();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        //create arrays for the notation
        notation = new String[8][8];
        pawnNotation = new String[8];

        for (int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
               //character cast is using ascii values
               notation[7-i][j] = "" + (char)(97+j) + (i+1);
            }
            pawnNotation[i] = "" + (char)(97+i);
        }
        history.setText("");

        setPreferredSize(new Dimension (200,800));
        setBackground(Color.WHITE);
    }
    //used for adding a move to the move history
    public void addMove(PieceAbstract piece, int moveType, int startX, boolean isPromoting)
    {
        //if White's turn, print turn number
        if(piece.getPlayer() == 'W')
            history.append(turnCount + ".   ");

        //if castling, print the special notation
        if(moveType == KING_CASTLE)
            history.append("0-0");
        else if(moveType == QUEEN_CASTLE)
            history.append("0-0-0");

        //if not castling
        else
        {
            //add symbol for the piece
            addPieceSymbol(piece);
            //add symbol for capturing
            addCapture(piece, moveType, startX);
            //add symbol for ending location
            addEndLocation(piece);
        }
        //stop here if there's a promotion
        if(isPromoting) return;

        //add symbol for check
        addCheck(piece);
        //add transition between players
        addTransition(piece);
    }
    //used to add symbols for a pawn's promotion to the history
    public void addPromotion(PieceAbstract piece)
    {
        history.append("=");
        //add piece symbol
        addPieceSymbol(piece);
        //add check symbol
        addCheck(piece);
        //add transition between players
        addTransition(piece);
    }
    //used to add a piece symbol to the history
    public void addPieceSymbol(PieceAbstract piece)
    {
        //print the symbol for the moved piece (no pawn symbol)
        if(piece instanceof King)
            history.append("K");
        else if(piece instanceof Queen)
            history.append("Q");
        else if(piece instanceof Rook)
            history.append("R");
        else if(piece instanceof Bishop)
            history.append("B");
        else if(piece instanceof Knight)
            history.append("N");
    }
    //used to add a capture symbol to the history
    public void addCapture(PieceAbstract piece, int moveType, int startX)
    {
            //if capturing with a pawn, print special notation for pawns capturing
            if(piece instanceof Pawn && moveType == CAPTURE)
                history.append(pawnNotation[startX]);

            //if capturing a piece, print the capture symbol
            if(moveType == CAPTURE)
                history.append("x");
    }
    //used to add a check symbol to the history
    public void addCheck(PieceAbstract piece)
    {
        //if other player's king is in check
        if(piece.getPlayer() == 'W' && ((King)mainFrame.pieces[MainFrame.B_KING]).check() ||
            piece.getPlayer() == 'B' && ((King)mainFrame.pieces[MainFrame.W_KING]).check())
        {
            history.append("+");
        }
    }
    //used to add a transition between the players to the history
    public void addTransition(PieceAbstract piece)
    {
        //if Black's turn, increment turn counter and start a new line
        if(piece.getPlayer() == 'B')
        {
            turnCount++;
            history.append("\n");
        }
        //if White's turn, print 8 spaces
        else
            history.append("        ");
    }
    //used to add symbols for the ending location to the history
    public void addEndLocation(PieceAbstract piece)
    {
            //print the symbols for the ending location of the move
            history.append(notation[piece.getY()][piece.getX()]);
    }
    //used for clearing the history
    public void clearHistory()
    {
        history.setText("");
        turnCount = 1;
    }
}
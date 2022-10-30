//class for panel displaying turn, new/forfeit buttons, and captured pieces
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPanel extends JPanel
{
    //static constants for piece values
    private static final int PAWN_VALUE = 1, BISHOP_VALUE = 3, KNIGHT_VALUE = 3, ROOK_VALUE = 5, QUEEN_VALUE = 9;

    //variables used to count captured pieces
    private int wQueenCap = 0, wRookCap = 0, wBishopCap = 0, wKnightCap = 0, wPawnCap = 0;
    private int bQueenCap = 0, bRookCap = 0, bBishopCap = 0, bKnightCap = 0, bPawnCap = 0;

    private JButton newGameButton, forfeitButton;
    private JLabel turnLabel, blackLabel, whiteLabel;
    private JTextArea blackCaptured, whiteCaptured;
    private MainFrame mainFrame;

    public MenuPanel(MainFrame mainFrame)
    {
        //reference to the MainFrame
        this.mainFrame = mainFrame;

        //button event handler
        ButtonHandler bh = new ButtonHandler();

        //new game button 
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(bh);
        add(newGameButton);

        //forfeit button
        forfeitButton = new JButton("Forfeit");
        forfeitButton.addActionListener(bh);
        add(forfeitButton);

        //label for turn
        turnLabel = new JLabel("White's Turn");
        add(turnLabel);

        //label for black captured pieces
        blackLabel = new JLabel("Black Pieces Lost");
        blackLabel.setPreferredSize(new Dimension (200,100));
        blackLabel.setHorizontalAlignment(JLabel.CENTER);
        blackLabel.setVerticalAlignment(JLabel.BOTTOM);
        add(blackLabel);

        //text area showing black captured pieces
        blackCaptured = new JTextArea();
        blackCaptured.setPreferredSize(new Dimension (90,250));
        blackCaptured.setLineWrap(true);
        blackCaptured.setWrapStyleWord(true);
        blackCaptured.setEditable(false);
        add(blackCaptured);

        //label for white captured pieces
        whiteLabel = new JLabel("White Pieces Lost");
        whiteLabel.setPreferredSize(new Dimension (200,100));
        whiteLabel.setHorizontalAlignment(JLabel.CENTER);
        whiteLabel.setVerticalAlignment(JLabel.BOTTOM);
        add(whiteLabel);

        //text area showing white captured pieces
        whiteCaptured = new JTextArea();
        whiteCaptured.setPreferredSize(new Dimension (90,250));
        whiteCaptured.setLineWrap(true);
        whiteCaptured.setWrapStyleWord(true);
        whiteCaptured.setEditable(false);
        add(whiteCaptured);

        //initialize captured display
        updateMenuLabels();

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 800));
    }
    //update text fields and counters for captured pieces
    public void updateMenuLabels()
    {
        //update turn label
        if(mainFrame.getTurn() == 'W')
            turnLabel.setText("White's Turn");
        else
            turnLabel.setText("Black's Turn");

        //retrieve white piece capture counts
        wQueenCap = PieceAbstract.getCapturedCount(MainFrame.W_QUEEN);
        wRookCap = PieceAbstract.getCapturedCount(MainFrame.W_ROOK1);
        wBishopCap = PieceAbstract.getCapturedCount(MainFrame.W_BISHOP1);
        wKnightCap = PieceAbstract.getCapturedCount(MainFrame.W_KNIGHT1);
        wPawnCap = PieceAbstract.getCapturedCount(MainFrame.W_PAWN_MIN);

        //retrieve black piece capture counts
        bQueenCap = PieceAbstract.getCapturedCount(MainFrame.B_QUEEN);
        bRookCap = PieceAbstract.getCapturedCount(MainFrame.B_ROOK1);
        bBishopCap = PieceAbstract.getCapturedCount(MainFrame.B_BISHOP1);
        bKnightCap = PieceAbstract.getCapturedCount(MainFrame.B_KNIGHT1);
        bPawnCap = PieceAbstract.getCapturedCount(MainFrame.B_PAWN_MIN);

        //set counts back to 0 if the game is over
        if(mainFrame.isGameover())
        {
            wQueenCap = 0; wRookCap = 0; wBishopCap = 0; wKnightCap = 0; wPawnCap = 0;
            bQueenCap = 0; bRookCap = 0; bBishopCap = 0; bKnightCap = 0; bPawnCap = 0;
        }
        //update counts in this panel
        redrawInterface();
    }
    private void redrawInterface()
    {
        //calculate relative value for each player
        int whiteValue = (wQueenCap * QUEEN_VALUE) + (wRookCap * ROOK_VALUE) +
            (wBishopCap * BISHOP_VALUE) + (wKnightCap * KNIGHT_VALUE) + (wPawnCap * PAWN_VALUE);
        int blackValue = (bQueenCap * QUEEN_VALUE) + (bRookCap * ROOK_VALUE) +
            (bBishopCap * BISHOP_VALUE) + (bKnightCap * KNIGHT_VALUE) + (bPawnCap * PAWN_VALUE);

        //update white captured pieces and value
        whiteCaptured.setText("Queens, " + wQueenCap + "\n\n" +
                        "Rooks, " + wRookCap + "\n\n" +
                        "Bishops, " + wBishopCap + "\n\n" +
                        "Knights, " + wKnightCap + "\n\n" +
                        "Pawns, " + wPawnCap + "\n\n" + 
                        "Value: " + (blackValue - whiteValue));

        //update black captured pieces and value
        blackCaptured.setText("Queens, " + bQueenCap + "\n\n" +
                        "Rooks, " + bRookCap + "\n\n" +
                        "Bishops, " + bBishopCap + "\n\n" +
                        "Knights, " + bKnightCap + "\n\n" +
                        "Pawns, " + bPawnCap + "\n\n" +
                        "Value: " + (whiteValue - blackValue));
    }
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //new game button
            if(e.getSource() == newGameButton)
            {
                if (JOptionPane.showConfirmDialog(mainFrame,"Are you sure you want to play a new game?") == JOptionPane.YES_OPTION)
                    mainFrame.reset();
            }
            //forfeit button
            if(e.getSource() == forfeitButton)
            {
                int result=JOptionPane.showConfirmDialog(mainFrame,"Are you sure you want to forfeit?");
                if (result == JOptionPane.YES_OPTION)
                {
                    mainFrame.setGameover(true);

                    //if its whites turn then white forfeits
                    if (mainFrame.getTurn() == 'W')
                        JOptionPane.showMessageDialog(mainFrame, "Black is the Winner by forfeit","Winner!",JOptionPane.INFORMATION_MESSAGE);

                    //if its blacks turn then black forfeits
                    else
                        JOptionPane.showMessageDialog(mainFrame, "White is the Winner by forfeit","Winner!",JOptionPane.INFORMATION_MESSAGE);

                    result=JOptionPane.showConfirmDialog(mainFrame,"Would you like to play a new game?");

                    if (result == JOptionPane.YES_OPTION)
                        mainFrame.reset();
                }
            }
        }
    }
}

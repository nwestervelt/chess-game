// Class file used for controlling the game's logic and drawing the board.
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

public class Board extends JFrame
{
    //Static constants for piece indexes
    public static final int W_ROOK1 = 24, W_KNIGHT1 = 25, W_BISHOP1 = 26, W_QUEEN = 27, W_KING = 28,
         W_BISHOP2 = 29, W_KNIGHT2 = 30, W_ROOK2 = 31, W_PAWN_MIN = 16, W_PAWN_MAX = 23;
    public static final int B_ROOK1 = 0, B_KNIGHT1 = 1, B_BISHOP1 = 2, B_QUEEN = 3, B_KING = 4,
        B_BISHOP2 = 5, B_KNIGHT2 = 6, B_ROOK2 = 7, B_PAWN_MIN = 8, B_PAWN_MAX = 15;
    
    //Static constants for piece values
    public static final int PAWN_VALUE = 1, BISHOP_VALUE = 3, KNIGHT_VALUE = 3, ROOK_VALUE = 5, QUEEN_VALUE = 9;

    //Variables used to count captured pieces
    private int queenWCap = 0, rookWCap = 0, bishopWCap = 0, knightWCap = 0, pawnWCap = 0;
    private int queenBCap = 0, rookBCap = 0, bishopBCap = 0, knightBCap = 0, pawnBCap = 0;

    //Array of all Pieces
    private PieceAbstract[] pieces;

    //Boolean array used to tell if a piece is captured
    private Boolean[] captured;

    //Variable containing char identifying who's turn it is
    private char turn;

    //Boolean keeping track if game is over
    private boolean gameOver = false;

    //Integers used for cursor position and the selected piece
    private int currX, currY, selected = -1;

    //Main board
    private BoardPanel boardPanel;
    private BufferedImage board;

    //Components for the menu
    private JPanel menuPanel;
    private JButton newGameButton;
    private JButton forfeitButton;
    private JLabel turnLabel;
    private JLabel whiteLabel;
    private JTextArea whiteCaptured;
    private JLabel blackLabel;
    private JTextArea blackCaptured;

    public Board()
    {
        //call super class's constructor and set title
        super("Chess Board");
        
        //create mouse event handlers
        MouseHandler mh = new MouseHandler();
        MouseMotionHandler mmh = new MouseMotionHandler();

        //create action handler for buttons
        ActionHandler ah = new ActionHandler();

        //create board panel and add event handlers to it
        boardPanel = new BoardPanel();
        boardPanel.addMouseListener(mh);
        boardPanel.addMouseMotionListener(mmh);
        boardPanel.setPreferredSize(new Dimension(800,800));
        add(boardPanel,BorderLayout.CENTER);

        //create menu panel to the west
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension (200,800));
        menuPanel.setBackground(Color.WHITE);
        add(menuPanel,BorderLayout.WEST);

        //new game button 
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(ah);
        menuPanel.add(newGameButton);

        //forfeit button
        forfeitButton = new JButton("Forfeit");
        forfeitButton.addActionListener(ah);
        menuPanel.add(forfeitButton);

        //Label for turn
        turnLabel = new JLabel("White's Turn");
        menuPanel.add(turnLabel);

        //Label for black captured pieces
        blackLabel = new JLabel("Black Pieces Lost");
        blackLabel.setPreferredSize(new Dimension (200,100));
        blackLabel.setHorizontalAlignment(JLabel.CENTER);
        blackLabel.setVerticalAlignment(JLabel.BOTTOM);
        menuPanel.add(blackLabel);

        //Text area showing black captured pieces
        blackCaptured = new JTextArea();
        blackCaptured.setPreferredSize(new Dimension (90,250));
        blackCaptured.setLineWrap(true);
        blackCaptured.setWrapStyleWord(true);
        blackCaptured.setEditable(false);
        menuPanel.add(blackCaptured);

        //Label for white captured pieces
        whiteLabel = new JLabel("White Pieces Lost");
        whiteLabel.setPreferredSize(new Dimension (200,100));
        whiteLabel.setHorizontalAlignment(JLabel.CENTER);
        whiteLabel.setVerticalAlignment(JLabel.BOTTOM);
        menuPanel.add(whiteLabel);

        //Text area showing white captured pieces
        whiteCaptured = new JTextArea();
        whiteCaptured.setPreferredSize(new Dimension (90,250));
        whiteCaptured.setLineWrap(true);
        whiteCaptured.setWrapStyleWord(true);
        whiteCaptured.setEditable(false);
        menuPanel.add(whiteCaptured);

        //create a generic array of ChessPiece objects
        pieces = new PieceAbstract[32];

        //create an array tracking captured status of pieces
        captured = new Boolean[32];

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

        //instantiate pieces 
        initializePieces();

        setResizable(false);
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
            char ch = 'A';
            Font font = new Font(Font.SERIF,Font.BOLD, 15);
            g.setFont(font);
            for (int i = 0; i < 8; i++)
            {
                g.drawString("" + (i+1), 0, ((7-i) * 100) + 15);
                g.drawString("" + (char)(ch + i), ((i + 1) * 100) - 15, 790);
            }
            try
            {
                for(int i = 0; i < pieces.length; i++)
                {
                    if(i != selected && !captured[i])
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

    private class ActionHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == newGameButton)
            {
                int result=JOptionPane.showConfirmDialog(null,"Are you sure you want to play a new game?");
                if (result == JOptionPane.YES_OPTION)
                {
                    initializePieces();
                    boardPanel.repaint();
                }
            }
            if(e.getSource() == forfeitButton)
            {
                int result=JOptionPane.showConfirmDialog(null,"Are you sure you want to forfeit?");
                if (result == JOptionPane.YES_OPTION)
                {
                    gameOver = true;
                    if (turn == 'W')
                    {
                        JOptionPane.showMessageDialog(null, "Black is the Winner!","Winner!",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "White is the Winner!","Winner!",JOptionPane.INFORMATION_MESSAGE);
                    }
                    result=JOptionPane.showConfirmDialog(null,"Would you like to play a new game?");
                    if (result == JOptionPane.YES_OPTION)
                    {
                        initializePieces();
                        boardPanel.repaint();
                    }
                }
            }
        }
    }

    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed (MouseEvent e)
        {
            if (gameOver) return;
            int x=e.getX();
            int y=e.getY();
            //determines which piece was selected
            for (int i = 0; i < pieces.length; i++)
            {
                if(x-(pieces[i].getX()*100) > 0 && x-(pieces[i].getX()*100) <= 100 &&
                    y-(pieces[i].getY()*100) > 0 && y-(pieces[i].getY()*100) <= 100 &&
                    turn == pieces[i].getPlayer())
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
                    //move the selected piece
                    pieces[selected].move((currX+50)/100, (currY+50)/100, pieces);
                    checkCaptured();
                    //if a pawn is moved to the other side of the board
                    if(pieces[selected] instanceof Pawn &&
                        ((pieces[selected].getY() == 7 && pieces[selected].getPlayer() == 'B') ||
                        (pieces[selected].getY() == 0 && pieces[selected].getPlayer() == 'W')))
                    {
                        //create and make promotion dialog visible
                        PromotionDialog pd = new PromotionDialog(Board.this);
                        pd.setVisible(true);
                        PieceAbstract newPiece;
                        //replace currently selected piece with selected piece type
                        if(pd.getSelectedButton() == PromotionDialog.QUEEN)
                        {
                            newPiece = new Queen(pieces[selected].getX(), pieces[selected].getY(), pieces[selected].getPlayer());
                            pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.KNIGHT)
                        {
                            newPiece = new Knight(pieces[selected].getX(), pieces[selected].getY(), pieces[selected].getPlayer());
                            pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.BISHOP)
                        {
                            newPiece = new Bishop(pieces[selected].getX(), pieces[selected].getY(), pieces[selected].getPlayer());
                            pieces[selected] = newPiece;
                        }
                        else if(pd.getSelectedButton() == PromotionDialog.ROOK)
                        {
                            newPiece = new Rook(pieces[selected].getX(), pieces[selected].getY(), pieces[selected].getPlayer());
                            pieces[selected] = newPiece;
                        }
                    }
                }
                //change turn and set turnLabel if move was successful
                if(turn == 'W')
                {
                    turn = 'B';
                    turnLabel.setText("Black's Turn");
                }
                else
                {
                    turn = 'W';
                    turnLabel.setText("White's Turn");
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
                currX = e.getX()-50;
                currY = e.getY()-50;
                //make sure the cursor is not outside the boundry of the board
                if (currX > 700) currX = 700;
                if (currX < 0) currX = 0;
                if (currY > 700) currY = 700;
                if (currY < 0) currY = 0;
            }
            boardPanel.repaint();
        }
    }

    private class PromotionDialog extends JDialog
    {
        private JRadioButton queenRButton, knightRButton, bishopRButton, rookRButton;
        private int selectedButton;
        //static constants for selectedButton
        public static final int QUEEN = 1, KNIGHT = 2, BISHOP = 3, ROOK = 4;

        public PromotionDialog(JFrame parent)
        {
            super(parent, true);

            JPanel radioPanel = new JPanel();
            add(radioPanel, BorderLayout.CENTER);

            //create group for radio buttons
            ButtonGroup radioGroup = new ButtonGroup();

            //create radio buttons for each piece type
            queenRButton = new JRadioButton("Queen");
            queenRButton.setSelected(true);
            radioPanel.add(queenRButton);
            radioGroup.add(queenRButton);

            knightRButton = new JRadioButton("Knight");
            radioPanel.add(knightRButton);
            radioGroup.add(knightRButton);

            bishopRButton = new JRadioButton("Bishop");
            radioPanel.add(bishopRButton);
            radioGroup.add(bishopRButton);

            rookRButton = new JRadioButton("Rook");
            radioPanel.add(rookRButton);
            radioGroup.add(rookRButton);

            //create okay button
            JButton okayButton = new JButton("Okay");
            okayButton.addActionListener(new ActionHandler());
            add(okayButton, BorderLayout.SOUTH);

            setSize(300, 200);
            setTitle("Promotion");
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
        private class ActionHandler implements ActionListener
        {
            //action fired when "Okay" button clicked
            public void actionPerformed(ActionEvent e)
            {
                if(queenRButton.isSelected()) selectedButton = QUEEN;
                else if(knightRButton.isSelected()) selectedButton = KNIGHT;
                else if(bishopRButton.isSelected()) selectedButton = BISHOP;
                else if(rookRButton.isSelected()) selectedButton = ROOK;

                setVisible(false);
            }
        }
        public int getSelectedButton()
        {
            return selectedButton;
        }
    }

    private void initializePieces()
    {
        for(int i = 0; i < pieces.length; i++)
        {
            // white pieces
            if(i == W_ROOK1 || i == W_ROOK2)
                pieces[i] = new Rook(i%8, 7, 'W');
            else if(i == W_KNIGHT1 || i == W_KNIGHT2)
                pieces[i] = new Knight(i%8, 7, 'W');
            else if(i == W_BISHOP1 || i == W_BISHOP2)
                pieces[i] = new Bishop(i%8, 7, 'W');
            else if(i == W_QUEEN)
                pieces[i] = new Queen(i%8, 7, 'W');
            else if(i == W_KING)
                pieces[i] = new King(i%8, 7, 'W');
            else if(i >= W_PAWN_MIN && i <= W_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 6, 'W');

            // black pieces
            else if(i >= B_PAWN_MIN && i <= B_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 1, 'B');
            else if(i == B_ROOK1 || i == B_ROOK2)
                pieces[i] = new Rook(i%8, 0, 'B');
            else if(i == B_KNIGHT1 || i == B_KNIGHT2)
                pieces[i] = new Knight(i%8, 0, 'B');
            else if(i == B_BISHOP1 || i == B_BISHOP2)
                pieces[i] = new Bishop(i%8, 0, 'B');
            else if(i == B_QUEEN)
                pieces[i] = new Queen(i%8, 0, 'B');
            else if(i == B_KING)
                pieces[i] = new King(i%8, 0, 'B');
        }
        //initialize number of captured
        queenWCap = 0; rookWCap = 0; bishopWCap = 0; knightWCap = 0; pawnWCap = 0;
        queenBCap = 0; rookBCap = 0; bishopBCap = 0; knightBCap = 0; pawnBCap = 0;

        //initialize captured array
        Arrays.fill(captured,false);

        //initialize captured display
        checkCaptured();

        //initialize player's turn
        turn = 'W';
        turnLabel.setText("White's Turn");
        gameOver = false;
    }
    //check if each piece is captured
    private void checkCaptured()
    {
        //iterate through piece array and a boolean array for captured pieces
        for(int i = 0; i < captured.length; i++)
        {
            //if piece is captured 
            if(pieces[i].getX() == -100 || pieces[i].getY() == -100)
            {
                //if already set as captured
                if (captured[i] == true)
                    continue;
                captured[i] = true;
                //if white piece captured increment the captured counter for that piece
                if(i > 16)
                {
                    if (pieces[i] instanceof Pawn)
                        pawnWCap++;
                    else if (pieces[i] instanceof Queen)
                        queenWCap++;    
                    else if (pieces[i] instanceof Rook)
                        rookWCap++;
                    else if (pieces[i] instanceof Bishop)
                        bishopWCap++;
                    else if (pieces[i] instanceof Knight)
                        knightWCap++;
                }
                //if black piece captured increment the captured counter for that piece
                else
                {
                    if (pieces[i] instanceof Pawn)
                        pawnBCap++;
                    else if (pieces[i] instanceof Queen)
                        queenBCap++;    
                    else if (pieces[i] instanceof Rook)
                        rookBCap++;
                    else if (pieces[i] instanceof Bishop)
                        bishopBCap++;
                    else if (pieces[i] instanceof Knight)
                        knightBCap++;
                }
            }
            //if piece isn't captured (not located at -100,-100), set as not captured
            else 
                captured[i] = false;    
        }
        //calculate each player's value using value of captured pieces
        int whiteValue = (queenWCap * QUEEN_VALUE) + (rookWCap * ROOK_VALUE) + (bishopWCap * BISHOP_VALUE) + (knightWCap * KNIGHT_VALUE) + (pawnWCap * PAWN_VALUE);
        int blackValue = (queenBCap * QUEEN_VALUE) + (rookBCap * ROOK_VALUE) + (bishopBCap * BISHOP_VALUE) + (knightBCap * KNIGHT_VALUE) + (pawnBCap * PAWN_VALUE);
        //update white captured pieces and value
        whiteCaptured.setText("Queens, " + queenWCap + "\n\n" +
                        "Rooks, " + rookWCap + "\n\n" +
                        "Bishops, " + bishopWCap + "\n\n" +
                        "Knights, " + knightWCap + "\n\n" +
                        "Pawns, " + pawnWCap + "\n\n" + 
                        "Value: " + (blackValue - whiteValue));
        //update black captured pieces and value
        blackCaptured.setText("Queens, " + queenBCap + "\n\n" +
                        "Rooks, " + rookBCap + "\n\n" +
                        "Bishops, " + bishopBCap + "\n\n" +
                        "Knights, " + knightBCap + "\n\n" +
                        "Pawns, " + pawnBCap + "\n\n" +
                        "Value: " + (whiteValue - blackValue));
    }
    //start the application
    public static void main(String[] args)
    {
        new Board();
    }
}

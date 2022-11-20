//class for the frame containing all components of the application
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.net.*;

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

    //network menu variables
    private JMenuItem hostItem;
    private JMenuItem joinItem;
    private JMenuItem quitItem;
    private JLabel statusLabel;

    //network variables
    private String host="localhost";
    private int port=5000;
    private PrintWriter out=null;
    private boolean connected=false;
    private boolean yourTurn=true;
    private ServerSocket serverSocket;

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

        //create menu, its items, and action handler
        ActionHandler ah=new ActionHandler();

        JMenuBar jmb=new JMenuBar();
        setJMenuBar(jmb);
    
        JMenu networkMenu=new JMenu("Network");
        jmb.add(networkMenu);
    
        hostItem=new JMenuItem("Host Game...");
        hostItem.addActionListener(ah);
        networkMenu.add(hostItem);
    
        joinItem=new JMenuItem("Join Game...");
        joinItem.addActionListener(ah);
        networkMenu.add(joinItem);
    
        quitItem=new JMenuItem("Quit Game");
        quitItem.addActionListener(ah);
        quitItem.setEnabled(false);
        networkMenu.add(quitItem);

        statusLabel=new JLabel(" ");
        add(statusLabel,BorderLayout.SOUTH);

        //setup this frame's behavior/appearance
        setResizable(false);

        //if someone closes window in network send quit
        this.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                super.windowClosing(e); 
                out.println("quit");
            }
        });
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
    private class ActionHandler implements ActionListener
    {
        //actionhandler for each of the buttons in the network menu
        public void actionPerformed(ActionEvent e)
        {
            //host
            if(e.getSource()==hostItem)
            {
                String s=JOptionPane.showInputDialog(MainFrame.this
                        ,"Enter the port to use",""+port);
                if(s==null) return;
                port=Integer.parseInt(s);
                hostItem.setEnabled(false);
                joinItem.setEnabled(false);
                quitItem.setEnabled(true);
                yourTurn = false;
                new Server().start();
                reset();
            }
            //join
            else if(e.getSource()==joinItem)
            {
                String s=JOptionPane.showInputDialog(MainFrame.this
                        ,"Enter the hostname",""+host);
                if(s==null) return;
                host=s;
                s=JOptionPane.showInputDialog(MainFrame.this
                        ,"Enter the port to use",""+port);
                if(s==null) return;
                port=Integer.parseInt(s);
                hostItem.setEnabled(false);
                joinItem.setEnabled(false);
                quitItem.setEnabled(true);
                new Client().start();
                reset();
            }
            //quit
            else if(e.getSource()==quitItem)
            {
                connected=false;
                if(serverSocket!=null)
                {
                    try
                    {
                        serverSocket.close();
                        serverSocket=null;
                    }
                    catch(IOException ioe)
                    {
                        System.out.println(ioe);
                    }
                }
                hostItem.setEnabled(true);
                joinItem.setEnabled(true);
                quitItem.setEnabled(false);
                if(out!=null) out.println("quit");
            }
        }
    }
    //sends move information to the other player on network
    public void sendMove(int selected, int currX, int currY,int promotionPiece)
    {
        out.println(selected + "," + currX + "," + currY+ ","+promotionPiece);
        yourTurn=false;
        statusLabel.setText("Opponent's turn.");
    }
    //sends forfeit information to other player on network
    public void sendForfeit()
    {
        out.println("Forfeit");
    }
    //send reset information to the other on network
    public void sendReset()
    {
        out.println("Reset");
    }
    //accessor for other methods
    public Boolean getYourTurn()
    {
        return yourTurn;
    }
    //accessor for other methods
    public Boolean getConnected()
    {
        return connected;
    }
    //play who hosts a game runs the server
    private class Server extends Thread
    {
        public void run()
        {
            try
            {
                serverSocket=new ServerSocket(port);
                statusLabel.setText("Waiting for opponent to connect...");
                Socket s=serverSocket.accept();
                statusLabel.setText("Opponent's turn.");
                connected=true;
                BufferedReader in=new BufferedReader(new InputStreamReader(
                          s.getInputStream()));
                out=new PrintWriter(s.getOutputStream(),true);
                String line;
                while(connected&&(line=in.readLine())!=null)
                {
                    if(line.equals("quit")) break;
                    if(line.equals("Forfeit"))
                    {
                        JOptionPane.showMessageDialog(null,"You Win","Forfeit!",JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    if(line.equals("Reset"))
                    {
                        setGameover(true);
                        reset();
                        yourTurn = false;
                        statusLabel.setText("Opponent's turn.");
                        continue;
                    }
                    String[] sa=line.split(",");
                    boardPanel.doMove(Integer.parseInt(sa[0]),Integer.parseInt(sa[1]),Integer.parseInt(sa[2]),Integer.parseInt(sa[3]));
                    if(gameOver) break;
                    yourTurn=true;
                    statusLabel.setText("Your turn.");
                }
                connected=false;
                statusLabel.setText("Game ended.");
                if(serverSocket!=null) serverSocket.close();
                serverSocket=null;
                s.close();
            }
            catch(IOException ioe)
            {}
            hostItem.setEnabled(true);
            joinItem.setEnabled(true);
            quitItem.setEnabled(false);
        }
    }
    //player that joins a game runs the client
    private class Client extends Thread
    {
        public void run()
        {
            try
            {
                Socket s=new Socket(host,port);
                yourTurn = true;
                statusLabel.setText("Your turn.");
                connected=true;
                BufferedReader in=new BufferedReader(new InputStreamReader(
                          s.getInputStream()));
                out=new PrintWriter(s.getOutputStream(),true);
                String line;
                while(connected&&(line=in.readLine())!=null)
                {
                    if(line.equals("quit")) break;
                    if(line.equals("Forfeit"))
                    {
                        JOptionPane.showMessageDialog(null,"You Win","Forfeit!",JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    if(line.equals("Reset"))
                    {
                        setGameover(true);
                        reset();
                        yourTurn = true;
                        statusLabel.setText("Your turn.");
                        continue;
                    }
                    String[] sa=line.split(",");
                    boardPanel.doMove(Integer.parseInt(sa[0]),Integer.parseInt(sa[1]),Integer.parseInt(sa[2]),Integer.parseInt(sa[3]));
                    if(gameOver) connected = false;
                    yourTurn=true;
                    statusLabel.setText("Your turn.");
                }
                connected=false;
                statusLabel.setText("Game ended.");
                s.close();
            }
            catch(IOException ioe)
            {}
            hostItem.setEnabled(true);
            joinItem.setEnabled(true);
            quitItem.setEnabled(false);
        }
    }
    //start the application
    public static void main(String[] args)
    {
        new MainFrame();
    }
}
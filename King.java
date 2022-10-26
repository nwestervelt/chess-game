//class file for King chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class King extends PieceAbstract
{
    private boolean notMoved = true;

    public King(int x, int y, char player, MainFrame mainFrame)
    {
        super(x, y, player, mainFrame);
    }
    public BufferedImage getImage()
        throws IOException
    {
        return ImageIO.read(new File("images/"+player+"King.png"));
    }
    public void move(int x, int y, boolean performingCheck)
        throws InvalidMoveException
    {
        int occupyingPiece = -1, oldX = this.x, oldY = this.y;
        boolean notBetween = true;

        //if moving one space
        if(Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
        {
            for(int i = 0; i < mainFrame.pieces.length; i++)
            {
                //if space is occupied, store that piece's index
                if(mainFrame.pieces[i].getX() == x && mainFrame.pieces[i].getY() == y)
                    occupyingPiece = i;
            }
        }
        //if King has not moved and is moving two spaces horizontally
        if(notMoved && Math.abs(x - this.x) == 2 && y == this.y)
        {
            //get the King's index
            int index = 0;

            if (this.player == 'W')
                index = MainFrame.W_KING;
            else  
                index = MainFrame.B_KING;

            //if king is moving left and left rook has not moved
            if(x - this.x == -2 && ((Rook)mainFrame.pieces[index - 4]).getNotMoved())
            {
                //searching for pieces between king and rook
                for (int j = 0; j < mainFrame.pieces.length; j++)
                {
                    if(mainFrame.pieces[j].getX() < this.x && mainFrame.pieces[j].getX() > this.x - 4 &&
                        mainFrame.pieces[j].getY() == this.y)
                    {
                        notBetween = false;
                    }
                }
                //if nothing between king and rook, move king and rook
                if (notBetween)
                {
                    this.x = x;
                    mainFrame.pieces[index - 4].setX(this.x + 1);
                    ((Rook)mainFrame.pieces[index - 4]).setNotMoved(false);
                    notMoved = false;
                    moveType = HistoryPanel.KING_CASTLE;
                }
            }
            //if king is moving right and right rook has not moved 
            else if(x - this.x == 2 && ((Rook)mainFrame.pieces[index + 3]).getNotMoved())
            {
                //searching for pieces between king and rook
                for (int j = 0; j < mainFrame.pieces.length; j++)
                {
                    if(mainFrame.pieces[j].getX() > this.x && mainFrame.pieces[j].getX() < this.x + 3 &&
                        mainFrame.pieces[j].getY() == this.y)
                    {
                        notBetween = false;
                    }
                }
                //if nothing between king and rook, move king and rook
                if (notBetween)
                {
                    this.x = x;
                    mainFrame.pieces[index + 3].setX(this.x - 1);
                    ((Rook)mainFrame.pieces[index + 3]).setNotMoved(false);
                    notMoved = false;
                    moveType = HistoryPanel.QUEEN_CASTLE;
                }
            }
        }
        if(occupyingPiece < 0 && Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                this.notMoved = false;
            }
        }
        else if (occupyingPiece >= 0 && mainFrame.pieces[occupyingPiece].getPlayer() != player)
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                this.notMoved = false;
                capturePiece(occupyingPiece);
                moveType = HistoryPanel.CAPTURE;
            }
        }
        else
            throw new InvalidMoveException("Kings can only move one space at a time, in any direction.");

        //if not checking for check status of other player's king
        if(!performingCheck)
        {
            //check if this player's King is in check after their move
            kingCheckLogic(oldX, oldY);

            //update the move history (starting x not used in this case)
            mainFrame.updateHistory(this, moveType, -1, false);
        }
    }
    //check if this King is in check
    public boolean check()
    {
        //variable tracking if the King is in check
        boolean inCheck = false;
        //change search index starting position based on player
        int searchIndex;

        if(player == 'W')
            searchIndex = 0;
        else
            searchIndex = 16;

        //search for opponent's pieces that can attack player's King
        for(int i = 0; i < mainFrame.pieces.length / 2; i++)
        {
            try
            {
                //check if a piece can move onto the King using the move method, true parameter needed to perform this
                mainFrame.pieces[searchIndex].move(x, y, true);

                //will only get here if the move was valid
                inCheck = true;
            }
            //if the move was invalid, do nothing and continue to test the next piece
            catch(InvalidMoveException ime) {}
            searchIndex++;
        }
        return inCheck;
    }
}

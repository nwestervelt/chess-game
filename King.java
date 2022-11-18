//class file for King chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class King extends PieceAbstract
{
    private boolean notMoved = true;

    //boolean for if a piece has been captured
    private boolean checkCaptured = false;
    //boolean for if piece being tried is on the same spot as checker
    private boolean checkerB = false;
    //index of piece doing the check on the king
    private int checker = 0;

    public King(int x, int y, char player, MainFrame mainFrame, PieceAbstract[] pieces)
    {
        super(x, y, player, mainFrame, pieces);
    }
    public BufferedImage getImage()
        throws IOException
    {
        return ImageIO.read(new File("images/"+player+"King.png"));
    }
    //allows other classes to change this variable
    public void setCheckCaptured(boolean checkCaptured)
    {
        this.checkCaptured = checkCaptured;
    }
    //allows other classes to change this variable
    public void setChecker(int checker)
    {
        this.checker = checker;
    }
    //set notMoved variable
    public void setNotMoved(boolean notMoved)
    {
        this.notMoved = notMoved;
    }
    public void move(int x, int y, boolean performingCheck)
        throws InvalidMoveException
    {
        int occupyingPiece = -1;
        oldX = this.x;
        oldY = this.y;
        boolean notBetween = true;
        int kingIndex = 0;
        if(player == 'W')
            kingIndex = MainFrame.B_KING;
        else 
            kingIndex = MainFrame.W_KING;
        //if kings are next to each other throw exception
        if((pieces[kingIndex].getX() + 1 == x && pieces[kingIndex].getY() == y) ||
            (pieces[kingIndex].getX() + 1 == x && pieces[kingIndex].getY() + 1 == y) ||
            (pieces[kingIndex].getX() == x && pieces[kingIndex].getY() + 1 == y) ||
            (pieces[kingIndex].getX() - 1 == x && pieces[kingIndex].getY() + 1 == y) ||
            (pieces[kingIndex].getX() - 1 == x && pieces[kingIndex].getY() == y) ||
            (pieces[kingIndex].getX() - 1 == x && pieces[kingIndex].getY() - 1== y) ||
            (pieces[kingIndex].getX() == x && pieces[kingIndex].getY() - 1 == y) ||
            (pieces[kingIndex].getX() + 1 == x && pieces[kingIndex].getY() -1 == y))
        {
            throw new InvalidMoveException("Kings cannot be next to each other.");
        }
        //if moving one space
        if(Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
        {
            for(int i = 0; i < pieces.length; i++)
            {
                //if space is occupied, store that piece's index
                if(pieces[i].getX() == x && pieces[i].getY() == y)
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
            if(x - this.x == -2 && ((Rook)pieces[index - 4]).getNotMoved())
            {
                //searching for pieces between king and rook
                for (int j = 0; j < pieces.length; j++)
                {
                    if(pieces[j].getX() < this.x && pieces[j].getX() > this.x - 4 &&
                        pieces[j].getY() == this.y)
                    {
                        notBetween = false;
                    }
                }
                //if nothing between king and rook, move king and rook
                if (notBetween && !check())
                {
                    //making sure the king is not in check through the movement during castling
                    this.x = x+1;
                    moveBack(oldX,oldY,occupyingPiece);
                    if(this.x != oldX)
                    {
                        this.x = x;
                        moveBack(oldX,oldY,occupyingPiece);
                        if(this.x != oldX && !performingCheck)
                        {
                            pieces[index - 4].setX(this.x + 1);
                            ((Rook)pieces[index - 4]).setNotMoved(false);
                            notMoved = false;
                            moveType = HistoryPanel.QUEEN_CASTLE;
                        }
                    }
                }
            }
            //if king is moving right and right rook has not moved 
            else if(x - this.x == 2 && ((Rook)pieces[index + 3]).getNotMoved())
            {
                //searching for pieces between king and rook
                for (int j = 0; j < pieces.length; j++)
                {
                    if(pieces[j].getX() > this.x && pieces[j].getX() < this.x + 3 &&
                        pieces[j].getY() == this.y)
                    {
                        notBetween = false;
                    }
                }
                //if nothing between king and rook, move king and rook
                if (notBetween && !check())
                {
                    //making sure the king is not in check through the movement in castling
                    this.x = x-1;
                    moveBack(oldX,oldY,occupyingPiece);
                    if(this.x != oldX)
                    {
                        this.x = x;
                        moveBack(oldX,oldY,occupyingPiece);
                        if(this.x != oldX && !performingCheck)
                        {
                            pieces[index + 3].setX(this.x - 1);
                            ((Rook)pieces[index + 3]).setNotMoved(false);
                            notMoved = false;
                            moveType = HistoryPanel.KING_CASTLE;
                        }
                    }
                }
            }
        }
        if(occupyingPiece < 0 && Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2 )
        {
            //if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                if (moveType != HistoryPanel.KING_CASTLE && moveType != HistoryPanel.QUEEN_CASTLE)
                    moveType = HistoryPanel.NORMAL;
                moveBack(oldX,oldY,occupyingPiece);
                //only set notMoved if the move didnt cause check
                if(this.x != oldX || this.y != oldY)
                    this.notMoved = false;
            }
        }
        else if (occupyingPiece >= 0 && pieces[occupyingPiece].getPlayer() != player)
        {
            //if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                moveBack(oldX, oldY,occupyingPiece);
                //only set notMoved and capture if the move didnt cause check
                if(this.x != oldX || this.y != oldY)
                {
                    this.notMoved = false;
                    capturePiece(occupyingPiece);
                    moveType = HistoryPanel.CAPTURE;
                }
            }
            //has to be here or it would not run correctly
            moveBack(oldX,oldY,occupyingPiece);
        }
        else
            throw new InvalidMoveException("Kings can only move one space at a time, in any direction.");

        //if not checking for check status of other player's king
        if(!performingCheck)
        {
            moveBack(oldX,oldY,occupyingPiece);
            //add this move to move history
            mainFrame.addMove(this, moveType, -1, false);
            moveType = HistoryPanel.NORMAL;
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
        for(int i = 0; i < pieces.length / 2; i++)
        {
            try
            {
                //check if a piece can move onto the King using the move method, true parameter needed to perform this
                if(pieces[searchIndex].isCaptured())
                {
                    searchIndex++;
                    continue;
                }
                pieces[searchIndex].move(x, y, true);
                //if a piece is captured and it can be the one that was checking the king
                if(checkCaptured && checkerB)
                {
                    inCheck = false;
                    checkCaptured = false;
                    return inCheck;
                }
                //will only get here if the move was valid
                inCheck = true;
            }
            //if the move was invalid, do nothing and continue to test the next piece
            catch(InvalidMoveException ime) {}
            searchIndex++;
        }
        return inCheck;
    }
    public boolean checkMate()
    {
        int startingIndex = 16;

        if(player == 'B')
            startingIndex = 0;
        //check for any legal moves for this player's pieces
        for(int n = startingIndex; n < startingIndex + 16; n++)
        {
            //x coordinate
            for(int i = 0; i < 8; i++)
            {
                //y coordinate
                for(int j = 0; j < 8; j++)
                {
                    //save old position
                    int oldX = pieces[n].getX();
                    int oldY = pieces[n].getY();
                    try
                    {
                        //attempt the move
                        pieces[n].move(i, j,true);
                        //if the move is going on to the piece that is checking the king
                        if(pieces[checker].getX() == i && pieces[checker].getY() == j)
                            checkerB = true;
                        //actually set the x and y for when when the check method is called
                        pieces[n].setX(i);
                        pieces[n].setY(j);
                        //if not in check
                        if(!check())
                        {
                            //if no longer in check set piece back to its old square
                            //and set the checker back to false and return false
                            pieces[n].setX(oldX);
                            pieces[n].setY(oldY);
                            checkerB = false;
                            return false;
                        }
                        else
                        {
                            //if still in check set back to old square and checker back to false
                            pieces[n].setX(oldX);
                            pieces[n].setY(oldY);
                            checkerB = false;
                        }
                    }
                    //if move is invalid, do nothing and continue
                    catch(InvalidMoveException ime)
                    {
                    }
                }
            }
        }
        //if here, this King is in check mate
        return true;
    }
}

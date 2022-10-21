// Abstract class file providing default method implementations.
import java.awt.image.*;
import java.io.*;
public abstract class PieceAbstract
{
    //variables common to all classes extending PieceAbstract
    int x;
    int y;
    char player;
    
    //constructor taking an initial position and player value
    public PieceAbstract(int x, int y, char player)
    {
        this.player = player;
        this.x = x;
        this.y = y;
    }
    //used to tell who owns a piece
    public char getPlayer()
    {
        return player;
    }
    //used to get the image associated with this piece
    public abstract BufferedImage getImage()
        throws IOException;
    //used to get the x position of this piece
    public int getX()
    {
        return x;
    }
    //used to get the y position of this piece
    public int getY()
    {
        return y;
    }
    //used to set the x position of this piece
    public void setX(int x)
    {
        this.x = x;
    }
    //used to set the y position of this piece
    public void setY(int y)
    {
        this.y = y;
    }
    //used to move this piece according to it's movement rules
    public abstract void move(int x, int y, boolean check, PieceAbstract[] pieces)
        throws InvalidMoveException;
    //used to capture a piece
    public void capturePiece(int occupyingPiece, PieceAbstract[] pieces)
    {
        //sets the captured piece offscreen so it is no longer visible
        pieces[occupyingPiece].setX(-100);
        pieces[occupyingPiece].setY(-100);
    }
    //used to return this piece to it's starting position if the player's king is in check
    protected void kingCheckLogic(PieceAbstract[] pieces, int oldX, int oldY)
        throws InvalidMoveException
    {
        //check if the player's King is in check
        if(player == 'W' && ((King)pieces[Board.W_KING]).check(pieces))
        {
            //return piece to starting position if King is in check
            this.x = oldX;
            this.y = oldY;
            throw new InvalidMoveException("The white king was in check after the move.");
        }
        else if(player == 'B' && ((King)pieces[Board.B_KING]).check(pieces))
        {
            //return piece to starting position if King is in check
            this.x = oldX;
            this.y = oldY;
            throw new InvalidMoveException("The black king was in check after the move.");
        }
    }
    //used to move the Bishop, located here so that Queens can also use it
    public void bishopMove(int x, int y, boolean performingCheck, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        boolean notBetween = true;
        int occupyingPiece = -1, oldX = this.x, oldY = this.y;
        //if in the diagonal 
        if(Math.abs(x - this.x) == Math.abs(y - this.y))
        {
            //iterating through each piece
            for (int i = 0; i < pieces.length; i++)
            {
                //if moving up and to the right 
                if(x - this.x > 0 && y - this.y < 0)
                {
                    if ((Math.abs(pieces[i].getX() - this.x) == Math.abs(pieces[i].getY() - this.y))&&
                        (pieces[i].getX() < x && pieces[i].getX() > this.x) &&
                        (pieces[i].getY() > y && pieces[i].getY() < this.y))
                    {
                        notBetween = false;
                    }
                }
                //if moving down and to the right
                else if(x - this.x > 0 && y - this.y > 0)
                {
                    if ((Math.abs(pieces[i].getX() - this.x) == Math.abs(pieces[i].getY() - this.y))&&
                        (pieces[i].getX() < x && pieces[i].getX() > this.x) &&
                        (pieces[i].getY() < y && pieces[i].getY() > this.y))
                    {
                        notBetween = false;
                    }
                }
                //if moving up and to the left
                else if(x - this.x < 0 && y - this.y < 0)
                {
                    if ((Math.abs(pieces[i].getX() - this.x) == Math.abs(pieces[i].getY() - this.y))&&
                        (pieces[i].getX() > x && pieces[i].getX() < this.x) &&
                        (pieces[i].getY() > y && pieces[i].getY() < this.y))
                    {
                        notBetween = false;
                    }
                }
                //if moving down and to the left
                else if(x - this.x < 0 && y - this.y > 0)
                {  
                    if ((Math.abs(pieces[i].getX() - this.x) == Math.abs(pieces[i].getY() - this.y))&&
                        (pieces[i].getX() > x && pieces[i].getX() < this.x) &&
                        (pieces[i].getY() < y && pieces[i].getY() > this.y))
                    {
                        notBetween = false;
                    }
                }
                //if space is occupied, store that piece's index
                if(pieces[i].getX() == x && pieces[i].getY() == y)
                    occupyingPiece = i;
            }
        }
        //if nothing between and no other of your piece are in the square and in the correct diagonal
        if(notBetween && occupyingPiece < 0 && (Math.abs(x - this.x) == Math.abs(y - this.y)))
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
            }
        }
        else if (occupyingPiece >= 0 && notBetween && pieces[occupyingPiece].getPlayer() != player)
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                capturePiece(occupyingPiece, pieces);
            }
        }
        else
            throw new InvalidMoveException("Bishops move along the diagonal, "
                +"and can't pass through other pieces.");
        //if move method wasn't called by another piece for checking
        //if the king is in check, check if the king is in check (prevents recursive call)
        if(!performingCheck)
            kingCheckLogic(pieces, oldX, oldY);
    }
    //used to move Rooks, located here so that Queens can also use it
    public void rookMove(int x, int y, boolean performingCheck, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        boolean notBetween = true;
        int occupyingPiece = -1, oldX = this.x, oldY = this.y;
        //check vertically for pieces between
        if((this.x == x && this.y != y) || (this.x != x && this.y == y))
        {
            for(int i = 0; i < pieces.length; i++)
            {
                //check vertically for pieces between
                if(pieces[i].getX() == x)
                {
                    if((pieces[i].getY() < this.y && pieces[i].getY() > y) ||
                        pieces[i].getY() < y && pieces[i].getY() > this.y)
                    {
                        notBetween = false;
                    }
                }
                //check horizontally for pieces between
                else if(pieces[i].getY() == y)
                {
                    if((pieces[i].getX() < this.x && pieces[i].getX() > x) ||
                        pieces[i].getX() < x && pieces[i].getX() > this.x)
                    {
                        notBetween = false;
                    }
                }
                //check if player is attempting to move ontop of any piece
                if(pieces[i].getX() == x && pieces[i].getY() == y)
                    occupyingPiece = i;
            }
        }
<<<<<<< HEAD
=======
        else
            throw new InvalidMoveException("Rooks move horizontally and vertically, "
                +"and can't pass through other pieces.");
        if(notBetween && occupyingPiece < 0 && (this.x == x || this.y == y))
        {
            this.x = x;
            this.y = y;
        }
        else if (occupyingPiece >= 0 && notBetween && pieces[occupyingPiece].getPlayer() != player)
        {
            this.x = x;
            this.y = y;
            capturePiece(occupyingPiece, pieces);
        }
>>>>>>> c3a9e5b (Fixed Bug with Rook)
        else
            throw new InvalidMoveException("Rooks move horizontally and vertically, "
                +"and can't pass through other pieces.");
        if(notBetween && occupyingPiece < 0 && (this.x == x || this.y == y))
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
            }
        }
        else if (occupyingPiece >= 0 && notBetween && pieces[occupyingPiece].getPlayer() != player)
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                capturePiece(occupyingPiece, pieces);
            }
        }
        else
            throw new InvalidMoveException("Rooks move horizontally and vertically, "
                +"and can't pass through other pieces.");
        //if move method wasn't called by another piece for checking
        //if the king is in check, check if the king is in check (prevents recursive call)
        if(!performingCheck)
            kingCheckLogic(pieces, oldX, oldY);
    }    
}

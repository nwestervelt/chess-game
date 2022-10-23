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
    protected void setX(int x)
    {
        this.x = x;
    }
    //used to set the y position of this piece
    protected void setY(int y)
    {
        this.y = y;
    }
    //used to move this piece according to it's movement rules
    public abstract void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException;
    //used to capture a piece
    protected void capturePiece(int occupyingPiece, PieceAbstract[] pieces)
    {
        //sets the captured piece offscreen so it is no longer visible
        pieces[occupyingPiece].setX(-100);
        pieces[occupyingPiece].setY(-100);
    }
    //used to move the Bishop, located here so that Queens can also use it
    public void bishopMove(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        boolean notBetween = true;
        int occupyingPiece = -1;
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
            this.x = x;
            this.y = y;
        }
        else if (occupyingPiece >= 0 && notBetween && pieces[occupyingPiece].getPlayer() != player)
        {
            this.x = x;
            this.y = y;
            capturePiece(occupyingPiece, pieces);
        }
        else
            throw new InvalidMoveException("Bishops move along the diagonal, "
                +"and can't pass through other pieces.");
    }
    //used to move Rooks, located here so that Queens can also use it
    public void rookMove(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        boolean notBetween = true;
        int occupyingPiece = -1;
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
        else
            throw new InvalidMoveException("Rooks move horizontally and vertically, "
                +"and can't pass through other pieces.");
    }    
}
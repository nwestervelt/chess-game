// Abstract lass file providing default method implementations.
import java.awt.image.*;
import java.io.*;
public abstract class PieceAbstract
{
    int x;
    int y;
    char player;
    
    public PieceAbstract()
    {
        player = ' ';
        x = 0;
        y = 0;
    }
    public PieceAbstract(int x, int y, char player)
        throws NoSuchPlayerException
    {
        setPlayer(player);
        this.x = x;
        this.y = y;
    }
    public void setPlayer(char player)
        throws NoSuchPlayerException
    {
        if(player == 'W' || player == 'B')
            this.player = player;
        else
            throw new NoSuchPlayerException(player+" is not a valid value for player.");
    }
    public char getPlayer()
    {
        return player;
    }
    public abstract BufferedImage getImage()
        throws IOException;
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    protected void setX(int x)
    {
        this.x = x;
    }
    protected void setY(int y)
    {
        this.y = y;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        this.x = x;
        this.y = y;
    }
    public void move(int x, int y, PieceAbstract[] pieces, int W_KING, int B_KING)
        throws InvalidMoveException
    {
        this.x = x;
        this.y = y;
    }
    protected void capturePiece(int occupyingPiece, PieceAbstract[] pieces)
    {
        //sets the captured piece offscreen so it is no longer visible
        pieces[occupyingPiece].setX(-100);
        pieces[occupyingPiece].setY(-100);
    }
    
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
                {
                    occupyingPiece = i;
                }
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
                {
                    occupyingPiece = i;
                }
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
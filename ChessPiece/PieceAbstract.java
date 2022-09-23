// Abstract lass file providing default method implementations.
package ChessPiece;

public abstract class PieceAbstract implements Piece
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
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        this.x = x;
        this.y = y;
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
                //if in same diagonal and if in between original position and candidate position
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
                //if in same diagonal and if in between original position and candidate position
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
                //if in same diagonal and if in between original position and candidate position
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
                //if in same diagonal and if in between original position and candidate position
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
                //check if player is attempting to move ontop of their own piece
                if(pieces[i].getPlayer() == this.getPlayer() &&
                    pieces[i].getX() == x && pieces[i].getY() == y)
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
        else
            throw new InvalidMoveException("Bishops move along the diagonal, "
                +"and can't pass through other pieces.");
    }
}
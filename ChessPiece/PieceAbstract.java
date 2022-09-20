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
    public void move(int x, int y)
        throws InvalidMoveException
    {
        this.x = x;
        this.y = y;
    }
}
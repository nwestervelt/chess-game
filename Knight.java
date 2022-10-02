// Class file for Knight chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Knight extends PieceAbstract
{
    public Knight()
    {
        super();
    }
    public Knight(int x, int y, char player)
        throws NoSuchPlayerException
    {
        super(x, y, player);
    }
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("images/"+player+"Knight.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        int occupyingPiece = -1;

        for(int i = 0; i < pieces.length; i++)
        {
            //if space is occupied, store that piece's index
            if(pieces[i].getX() == x && pieces[i].getY() == y)
            {
                occupyingPiece = i;
            }
        }
        if(occupyingPiece < 0 && ((Math.abs(x - this.getX()) == 2 && Math.abs(y - this.getY()) == 1) ||
            (Math.abs(x - this.getX()) == 1 && Math.abs(y - this.getY()) == 2)))
        {
            this.x = x;
            this.y = y;
        }
        else if (occupyingPiece >= 0 && pieces[occupyingPiece].getPlayer() != player &&
            ((Math.abs(x - this.getX()) == 2 && Math.abs(y - this.getY()) == 1) ||
            (Math.abs(x - this.getX()) == 1 && Math.abs(y - this.getY()) == 2)))
        {
            this.x = x;
            this.y = y;
            capturePiece(occupyingPiece, pieces);
        }
        else
            throw new InvalidMoveException("Knights move in L shapes, 2 spaces in one direction and 1 space in"+
                "a perpendicular direction.");
    }
}
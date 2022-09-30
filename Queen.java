// Class file for Queen chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Queen extends PieceAbstract
{
    public Queen()
    {
        super();
    }
    public Queen(int x, int y, char player)
        throws NoSuchPlayerException
    {
        super(x, y, player);
    }
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("images/"+player+"Queen.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        //if diagonal movement call bishopMove method
        //else call rookMove method
        if (Math.abs(x - this.x) == Math.abs(y - this.y))
        {
            bishopMove(x, y, pieces); 
        }
        else 
        {
            rookMove(x, y, pieces);
        }
    }
}
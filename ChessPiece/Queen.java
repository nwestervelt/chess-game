// Class file for Queen chess pieces.
package ChessPiece;

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
        image = ImageIO.read(new File("ChessPiece/images/"+player+"Queen.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        //will use InvalidMoveException to prevent illegal moves in the future
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
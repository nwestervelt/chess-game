// Class file for Bishop chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Bishop extends PieceAbstract
{
    //use constructor in AbstractPiece
    public Bishop(int x, int y, char player)
    {
        super(x, y, player);
    }
    //return the image associated with this Bishop
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("images/"+player+"Bishop.png"));
        return image;
    }
    //move this Bishop according to the appropriate rules
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        bishopMove(x, y, pieces);
    }
}

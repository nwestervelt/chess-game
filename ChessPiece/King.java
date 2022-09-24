// Class file for King chess pieces.
package ChessPiece;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class King extends PieceAbstract
{
    private boolean notMoved = false;

    public King()
    {
        super();
    }
    public King(int x, int y, char player)
        throws NoSuchPlayerException
    {
        super(x, y, player);
    }
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("ChessPiece/images/"+player+"King.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        //will use InvalidMoveException to prevent illegal moves in the future
        this.x = x;
        this.y = y;
    }
    public void castle(String direction)
    {

    }
    public boolean check()
    {
        return false;
    }
}

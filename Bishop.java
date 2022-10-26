// Class file for Bishop chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Bishop extends PieceAbstract
{
    //use constructor in AbstractPiece
    public Bishop(int x, int y, char player, MainFrame mainFrame)
    {
        super(x, y, player, mainFrame);
    }
    //return the image associated with this Bishop
    public BufferedImage getImage()
        throws IOException
    {
        return ImageIO.read(new File("images/"+player+"Bishop.png"));
    }
    //move this Bishop according to the appropriate rules
    public void move(int x, int y, boolean check)
        throws InvalidMoveException
    {
        bishopMove(x, y, check);
    }
}

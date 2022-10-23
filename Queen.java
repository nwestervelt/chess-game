// Class file for Queen chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Queen extends PieceAbstract
{
    //use constructor in PieceAbstract
    public Queen(int x, int y, char player)
    {
        super(x, y, player);
    }
    //get the image associated with this Queen
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("images/"+player+"Queen.png"));
        return image;
    }
    //move this Queen, following the appropriate rules
    public void move(int x, int y, boolean check, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        //if diagonal move use Bishop's move method
        if (Math.abs(x - this.x) == Math.abs(y - this.y))
            bishopMove(x, y, check, pieces); 
        //if horizontal move use Rook's move method
        else 
            rookMove(x, y, check, pieces);
    }
}
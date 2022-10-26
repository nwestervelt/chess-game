// Class file for Rook chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Rook extends PieceAbstract
{
    //variable to track if this Rook has moved
    private boolean notMoved = true;

    //use constructor in PieceAbstract
    public Rook(int x, int y, char player, MainFrame mainFrame)
    {
        super(x, y, player, mainFrame);
    }
    //return the image associated with this Rook
    public BufferedImage getImage()
        throws IOException
    {
        return ImageIO.read(new File("images/"+player+"Rook.png"));
    }
    //move this Rook according to the appropriate rules
    public void move(int x, int y, boolean check)
        throws InvalidMoveException
    {
        rookMove(x, y, check);
        notMoved = false;
    }
    //return if this Rook has moved yet
    public boolean getNotMoved()
    {
        return notMoved;
    }
    //set the notMoved variable, used when castling to ensure this Rook can't castle again
    public void setNotMoved(boolean notMoved)
    {
        this.notMoved = notMoved;
    }
}
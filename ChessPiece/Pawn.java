// Class file for Pawn chess pieces.
package ChessPiece;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Pawn extends PieceAbstract
{
    public Pawn()
    {
        super();
    }
    public Pawn(int x, int y, char player)
        throws NoSuchPlayerException
    {
        super(x, y, player);
    }
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("ChessPiece/images/"+player+"Pawn.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        //will use InvalidMoveException to prevent illegal moves in the future
        this.x = x;
        this.y = y;
    }
    public void enPassant(int x, int y)
        throws InvalidMoveException
    {
        this.x = x;
        this.y = y;
    }
    public void promotion(String piece)
    {

    }
}
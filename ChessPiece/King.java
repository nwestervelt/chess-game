// Class file for King chess pieces.
package ChessPiece;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class King extends PieceAbstract
{
    private boolean notMoved = true;

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
        int occupyingPiece = -1;

        //if moving one space
        if(Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
        {
            for(int i = 0; i < pieces.length; i++)
            {
                //check if player is attempting to move ontop of their own piece
                if(pieces[i].getX() == x && pieces[i].getY() == y)
                {
                    occupyingPiece = i;
                }
            }
        }
        if(occupyingPiece < 0 && Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
        {
            this.x = x;
            this.y = y;
            notMoved = false;
        }
        else if (pieces[occupyingPiece].getPlayer() != player)
        {
            this.x = x;
            this.y = y;
            capturePiece(occupyingPiece, pieces);
        }
        else
            throw new InvalidMoveException("Kings can only move one space at a time, in any direction.");
    }
    public void castle(String direction)
    {

    }
    public boolean check()
    {
        return false;
    }
}

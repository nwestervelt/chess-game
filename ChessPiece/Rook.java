// Class file for Rook chess pieces.
package ChessPiece;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Rook extends PieceAbstract
{
    private boolean moved;

    public Rook()
    {
        super();
    }
    public Rook(int x, int y, char player)
        throws NoSuchPlayerException
    {
        super(x, y, player);
    }
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("ChessPiece/images/"+player+"Rook.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        boolean notBetween = true;
        //check horizontally for pieces between
        if(this.x == x && this.y != y)
        {
            for(int i = 0; i < pieces.length; i++)
            {
                if(pieces[i].getX() == x)
                {
                    if((pieces[i].getY() < this.y && pieces[i].getY() > y) ||
                        pieces[i].getY() < y && pieces[i].getY() > this.y)
                    {
                        notBetween = false;
                    }
                }
            }
        }
        //check vertically for pieces between
        else if(this.x != x && this.y == y)
        {
            for(int i = 0; i < pieces.length; i++)
            {
                if(pieces[i].getY() == y)
                {
                    if((pieces[i].getX() < this.x && pieces[i].getX() > x) ||
                        pieces[i].getX() < x && pieces[i].getX() > this.x)
                    {
                        notBetween = false;
                    }
                }
            }
        }
        if(notBetween && (this.x == x || this.y == y))
        {
            this.x = x;
            this.y = y;
        }
        else
            throw new InvalidMoveException("Rooks move horizontally and vertically, "
                +"and can't pass through other pieces.");
    }
}
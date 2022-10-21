// Class file for King chess pieces.
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
        image = ImageIO.read(new File("images/"+player+"King.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces, int W_KING, int B_KING)
        throws InvalidMoveException
    {
        int occupyingPiece = -1;
        boolean notBetween = true;
        //if moving one space
        if(Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
        {
            for(int i = 0; i < pieces.length; i++)
            {
                //if space is occupied, store that piece's index
                if(pieces[i].getX() == x && pieces[i].getY() == y)
                {
                    occupyingPiece = i;
                }
            }
        }
        //if King has not moved and is moving two spaces horizontally
        if(notMoved && Math.abs(x - this.x) == 2 && y == this.y)
        {
            //get the King's index
            int index = 0;
            if (this.player == 'W')
            {
                index = W_KING;
            }
            else  
            {
                index = B_KING;
            }   
            //if king is moving left and left rook has not moved
            if(x - this.x == -2 && ((Rook)pieces[index - 4]).getNotMoved())
            {
                //searching for pieces between king and rook
                for (int j = 0; j < pieces.length; j++)
                {
                    if(pieces[j].getX() < this.x && pieces[j].getX() > this.x - 4 &&
                        pieces[j].getY() == this.y)
                    {
                        notBetween = false;
                    }
                }
                //if nothing between king and rook, move king and rook
                if (notBetween)
                {
                    this.x = x;
                    pieces[index - 4].setX(this.x + 1);
                    notMoved = false;
                    ((Rook)pieces[index - 4]).setNotMoved(false);
                }
            }
            //if king is moving right and right rook has not moved 
            else if(x - this.x == 2 && ((Rook)pieces[index + 3]).getNotMoved())
            {
                //searching for pieces between king and rook
                for (int j = 0; j < pieces.length; j++)
                {
                    if(pieces[j].getX() > this.x && pieces[j].getX() < this.x + 3 &&
                        pieces[j].getY() == this.y)
                    {
                        notBetween = false;
                    }
                }
                //if nothing between king and rook, move king and rook
                if (notBetween)
                {
                    this.x = x;
                    pieces[index + 3].setX(this.x - 1);
                    notMoved = false;
                    ((Rook)pieces[index + 3]).setNotMoved(false);
                }
            }
        }
        if(occupyingPiece < 0 && Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
        {
            this.x = x;
            this.y = y;
            this.notMoved = false;
        }
        else if (occupyingPiece >= 0 && pieces[occupyingPiece].getPlayer() != player)
        {
            this.x = x;
            this.y = y;
            capturePiece(occupyingPiece, pieces);
            this.notMoved = false;
        }
        else
            throw new InvalidMoveException("Kings can only move one space at a time, in any direction.");
    }
    public boolean check()
    {
        return false;
    }
}

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
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        int occupyingPiece = -1;
        boolean notBetween = true;
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
        //if King has not moved and is trying to move two across the same x for castling
        if(notMoved && (Math.abs(x - this.x) == 2 && y == this.y))
        {
            //iterating through the array to find the king piece
            for(int i = 0; i < pieces.length; i++)
            {
                //if finding king
                if(pieces[i].getX() == this.x && pieces[i].getY() == this.y)
                {
                    //if king is moving left and the left rook has not moved
                    if(x - this.x == -2 && ((Rook)(pieces[i - 3])).getNotMoved())
                    {
                        //iterating through array checking if pieces are between rook and king
                        for (int j = 0; j < pieces.length; j++)
                        {
                            if(pieces[j].getX() < this.x && pieces[j].getX() > this.x - 3 &&
                            pieces[j].getY() == this.y)
                            {
                                notBetween = false;
                            }
                        }
                        //if nothing is between move king over 2 spaces and rook next to it on the opposite side
                        if (notBetween)
                        {
                            this.x = x;
                            pieces[i - 3].setX(this.x + 1);
                            this.notMoved = false;
                        }
                    }
                    //if king is moving right and right rook has not moved 
                    else if(x - this.x == 2 && ((Rook)(pieces[i - 3])).getNotMoved())
                    {
                        //iterating through array checking if pieces are between rook and king
                        for (int j = 0; j < pieces.length; j++)
                        {
                            if(pieces[j].getX() > this.x && pieces[j].getX() < this.x + 4 &&
                                pieces[j].getY() == this.y)
                            {
                                notBetween = false;
                            }
                        }
                        //if nothing is between move king over 2 spaces and rook next to it on the opposite side
                        if (notBetween)
                        {
                            this.x = x;
                            pieces[i + 4].setX(this.x - 1);
                            this.notMoved = false;
                        }
                    }
                }
            }
        }
        else if(occupyingPiece < 0 && Math.abs(x - this.x) < 2 && Math.abs(y - this.y) < 2)
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
        }
        else
            throw new InvalidMoveException("Kings can only move one space at a time, in any direction.");
    }
    public boolean check()
    {
        return false;
    }
}

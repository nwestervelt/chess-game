// Class file for Pawn chess pieces.
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Pawn extends PieceAbstract
{
    private boolean notMoved = true, enPassant = false;

    public Pawn()
    {
        super();
    }
    public Pawn(int x, int y, char player)
        throws NoSuchPlayerException
    {
        super(x, y, player);
    }
    public boolean isEnPassant()
    {
        return enPassant;
    }
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("images/"+player+"Pawn.png"));
        return image;
    }
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException
    {
        int occupyingPiece = -1;
        boolean notBetween = true, movingForward = false;

        //if player is moving their piece forwards
        if((player == 'W' && y - this.y < 0) ||
            (player == 'B' && y - this.y > 0))
        {
            for(int i = 0; i < pieces.length; i++)
            {
                //if pieces[i] is this object
                if(pieces[i] == this) continue;

                //check for pieces between when moving two spaces forward
                if(x == this.x && Math.abs(y - this.y) == 2)
                {
                    //if pieces[i] occupies the space behind the ending space
                    if(pieces[i].getX() == x && pieces[i].getY() == ((this.y + y) / 2))
                        notBetween = false;
                }
                //if space is occupied, store that piece's index
                if(pieces[i].getX() == x && pieces[i].getY() == y)
                {
                    occupyingPiece = i;
                }
                //if pieces[i] is a pawn, en passant can be used, and position is behind move location
                else if(pieces[i] instanceof Pawn && ((Pawn)pieces[i]).isEnPassant() && (pieces[i].getX() == x))
                {
                    if((pieces[i].getY() - y == -1 && player == 'B') ||
                        (pieces[i].getY() - y == 1 && player == 'W'))
                        occupyingPiece = i;
                }
            }
            //set flag for moving forwards
            movingForward = true;
        }
        //if nothing between start and end, not occupied by friendly piece, and
        //moving in the same column
        if(notBetween && occupyingPiece < 0 && movingForward && x == this.x)
        {
            //if moving one space
            if(Math.abs(y - this.y) == 1)
            {
                this.x = x;
                this.y = y;
                notMoved = false;
                enPassant = false;
            }
            //if moving two spaces
            else if(Math.abs(y - this.y) == 2 && notMoved)
            {
               this.x = x;
               this.y = y;
               notMoved = false;
               enPassant = true; 
            }
        }
        //if occupied and pawn is moving forward and opposing player is occupying 
        //and it is 1 space to the diagonal of the pawn
        else if (occupyingPiece >= 0 && movingForward && pieces[occupyingPiece].getPlayer() != player 
            && (x == this.x + 1 || x == this.x - 1) && Math.abs(y - this.y) == 1)
        {
            this.x = x;
            this.y = y;
            capturePiece(occupyingPiece, pieces);
            enPassant = false;
        }
        //throw exception if didn't move
        else
            throw new InvalidMoveException("Pawns can only move towards the opposing side, and "+
                "they can only move two spaces forwards if they haven't moved yet.");
    }
}
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
        int occupyingPiece = -1;
        boolean notBetween = true, movingForward = false;

        //if player is moving their piece forwards
        if((player == 'W' && y - this.y > 0) ||
            (player == 'B' && y - this.y < 0))
        {
            for(int i = 0; i < pieces.length; i++)
            {
                //check for pieces between when moving two spaces forward
                if(x == this.x && Math.abs(y - this.y) == 2)
                {
                    //if pieces[i] occupies the space behind the ending space
                    if(pieces[i].getX() == x && pieces[i].getY() == ((this.y + y) / 2))
                        notBetween = false;
                }
                //check if player is attempting to move ontop of their own piece
                if(pieces[i].getX() == x && pieces[i].getY() == y)
                {
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
        }
        //throw exception if didn't move
        else
            throw new InvalidMoveException("Pawns can only move towards the opposing side, and "+
                "they can only move two spaces forwards if they haven't moved yet.");
    }
    public void promotion(String piece)
    {

    }
}
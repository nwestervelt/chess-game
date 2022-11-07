//class file for Pawn chess pieces
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Pawn extends PieceAbstract
{
    //variables to track if this Pawn has moved and if en passant can be used on it
    private boolean notMoved = true, enPassant = false;

    //variable storing this pawn's old x position
    private int startX;

    //reference to newpiece from a possible promotion
    PieceAbstract newPiece;

    //use constructor in PieceAbstract
    public Pawn(int x, int y, char player, MainFrame mainFrame, PieceAbstract[] pieces)
    {
        super(x, y, player, mainFrame, pieces);
    }
    //return if en passant can be used on this Pawn
    public boolean isEnPassant()
    {
        return enPassant;
    }
    //return the image associated with this Pawn
    public BufferedImage getImage()
        throws IOException
    {
        return ImageIO.read(new File("images/"+player+"Pawn.png"));
    }
    //move this Pawn according to the appropriate rules
    public void move(int x, int y, boolean performingCheck)
        throws InvalidMoveException
    {
        int occupyingPiece = -1;
        oldX = this.x;
        oldY = this.y;
        boolean notBetween = true, movingForward = false, isPromoting = false;

        startX = this.x;

        //if player is moving their piece forwards
        if((player == 'W' && y - this.y < 0) ||
            (player == 'B' && y - this.y > 0))
        {
            for(int i = 0; i < pieces.length; i++)
            {
                //if piece is this object
                if(pieces[i] == this) continue;

                //check for pieces between when moving two spaces forward
                if(x == this.x && Math.abs(y - this.y) == 2)
                {
                    //if piece occupies the space behind the ending space
                    if(pieces[i].getX() == x && pieces[i].getY() == ((this.y + y) / 2))
                        notBetween = false;
                }
                //if space is occupied, store that piece's index
                if(pieces[i].getX() == x && pieces[i].getY() == y)
                {
                    occupyingPiece = i;
                }
                //if the piece is a pawn, en passant can be used, and position is behind move location
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
        //if nothing between start and end, not occupied by friendly piece,
        //moving in the same column, and not trying to move two spaces after already moving
        if(notBetween && occupyingPiece < 0 && movingForward && x == this.x &&
            (Math.abs(y - this.y) == 1 || (Math.abs(y - this.y) == 2 && notMoved)))
        {
            //if moving one space
            if(Math.abs(y - this.y) == 1)
            {
                //only perform move if not checking check status of other player's king
                if(!performingCheck)
                {
                    this.x = x;
                    this.y = y;
                    moveType = HistoryPanel.NORMAL;
                    notMoved = false;
                    enPassant = false;
                }
            }
            //if moving two spaces
            else if(Math.abs(y - this.y) == 2 && notMoved)
            {
               //only perform move if not checking check status of other player's king
               if(!performingCheck)
               {
                    this.x = x;
                    this.y = y;
                    moveType = HistoryPanel.NORMAL;
                    notMoved = false;
                    enPassant = true; 
               }
            }
        }
        //if occupied and pawn is moving forward and opposing player is occupying 
        //and it is 1 space to the diagonal of the pawn
        else if (occupyingPiece >= 0 && movingForward && pieces[occupyingPiece].getPlayer() != player 
            && (x == this.x + 1 || x == this.x - 1) && Math.abs(y - this.y) == 1)
        {
            //set checkCaptured to true
            if(player == 'W')
                ((King)pieces[MainFrame.W_KING]).setCheckCaptured(true);
            else
                ((King)pieces[MainFrame.B_KING]).setCheckCaptured(true);
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                capturePiece(occupyingPiece);
                enPassant = false;
                moveType = HistoryPanel.CAPTURE; 
            }
        }
        //throw exception if didn't move
        else
            throw new InvalidMoveException("Pawns can only move towards the opposing side, and "+
                "they can only move two spaces forwards if they haven't moved yet.");

        //check if this pawn can promote
        if(this.y == 0 || this.y == 7)
            isPromoting = true;
        
        //if not checking for check status of other player's king
        if(!performingCheck)
        {
            //check if this player's King is in check after their move
            moveBack(oldX, oldY,occupyingPiece);

            //add this move to move history
            mainFrame.addMove(this, moveType, startX, isPromoting);
        }
    }
}
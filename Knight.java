//class file for Knight chess pieces
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Knight extends PieceAbstract
{
    //use constructor in PieceAbstract
    public Knight(int x, int y, char player, MainFrame mainFrame)
    {
        super(x, y, player, mainFrame);
    }
    //return the image associated with this Knight
    public BufferedImage getImage()
        throws IOException
    {
        return ImageIO.read(new File("images/"+player+"Knight.png"));
    }
    //move this Knight according to the appropriate rules
    public void move(int x, int y, boolean performingCheck)
        throws InvalidMoveException
    {
        int occupyingPiece = -1, oldX = this.x, oldY = this.y;

        for(int i = 0; i < mainFrame.pieces.length; i++)
        {
            //if space is occupied, store that piece's index
            if(mainFrame.pieces[i].getX() == x && mainFrame.pieces[i].getY() == y)
                occupyingPiece = i;
        }
        if(occupyingPiece < 0 && ((Math.abs(x - this.getX()) == 2 && Math.abs(y - this.getY()) == 1) ||
            (Math.abs(x - this.getX()) == 1 && Math.abs(y - this.getY()) == 2)))
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
            }
        }
        else if (occupyingPiece >= 0 && mainFrame.pieces[occupyingPiece].getPlayer() != player &&
            ((Math.abs(x - this.getX()) == 2 && Math.abs(y - this.getY()) == 1) ||
            (Math.abs(x - this.getX()) == 1 && Math.abs(y - this.getY()) == 2)))
        {
            //only perform move if not checking check status of other player's king
            if(!performingCheck)
            {
                this.x = x;
                this.y = y;
                capturePiece(occupyingPiece);
            }
        }
        else
            throw new InvalidMoveException("Knights move in L shapes, 2 spaces in one direction and 1 space in"+
                "a perpendicular direction.");

        //if not checking for check status of other player's king
        if(!performingCheck)
        {
            //check if this player's King is in check after their move
            kingCheckLogic(oldX, oldY);

            //update the move history (starting x not used in this case)
            mainFrame.updateHistory(this, moveType, -1, false);
        }
    }
}
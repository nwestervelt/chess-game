// Class file for Bishop chess pieces.
package ChessPiece;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class Bishop implements Piece
{
    private char player;
    private int x, y;

    Bishop()
    {
        //create another constructor for use in non-default locations
        //in the future
        player = ' ';
        x = 0;
        y = 0;
    }
    public BufferedImage getImage()
        throws IOException
    {
        BufferedImage image;
        image = ImageIO.read(new File("images/"+player+"Bishop.png"));
        return image;
    }
    public void setPlayer(char newPlayer)
        throws NoSuchPlayerException
    {
        if(newPlayer == 'W' || newPlayer == 'B')
            player = newPlayer;
        else
            throw new NoSuchPlayerException(newPlayer+" is not a valid value for player.");
    }
    public char getPlayer()
    {
        return player;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void move(int newX, int newY)
        throws InvalidMoveException
    {
        //will use InvalidMoveException to prevent illegal moves in the future
        x = newX;
        y = newY;
    }
}

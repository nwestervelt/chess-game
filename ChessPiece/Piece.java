// Interface used by all chesspiece classes.
package ChessPiece;

import java.io.*;
import java.awt.image.*;

interface Piece
{
    public void move(int x, int y, PieceAbstract[] pieces)
        throws InvalidMoveException;

    public void setPlayer(char newPlayer)
        throws NoSuchPlayerException;

    public char getPlayer();

    public int getX();

    public int getY();

    public BufferedImage getImage()
        throws IOException;
}

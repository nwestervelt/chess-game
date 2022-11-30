//class file for Bishop chess pieces.

public class Bishop extends PieceAbstract
{
    //use constructor in AbstractPiece
    public Bishop(int x, int y, char player, MainFrame mainFrame, PieceAbstract[] pieces)
    {
        super(x, y, player, mainFrame, pieces);
    }
    //move this Bishop according to the appropriate rules
    public void move(int x, int y, boolean check)
        throws InvalidMoveException
    {
        bishopMove(x, y, check);
    }
}

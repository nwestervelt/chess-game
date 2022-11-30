// Class file for Queen chess pieces.

public class Queen extends PieceAbstract
{
    //use constructor in PieceAbstract
    public Queen(int x, int y, char player, MainFrame mainFrame, PieceAbstract[] pieces)
    {
        super(x, y, player, mainFrame, pieces);
    }
    //move this Queen, following the appropriate rules
    public void move(int x, int y, boolean check)
        throws InvalidMoveException
    {
        //if diagonal move use Bishop's move method
        if (Math.abs(x - this.x) == Math.abs(y - this.y) && (x != this.x && y != this.y))
            bishopMove(x, y, check);

        //if horizontal move use Rook's move method
        else if((x == this.x && y != this.y) || (x != this.x && y == this.y))
            rookMove(x, y, check);

        else
            throw new InvalidMoveException("Invalid move for Queen.");
    }
}
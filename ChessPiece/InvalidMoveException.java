package ChessPiece;

public class InvalidMoveException extends Exception
{
    InvalidMoveException(String message)
    {
        super(message);
    }
}

//JUnit Testing for each pieces move methods

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class MoveTests
{
    PieceAbstract[] pieces;
    //Starting positions set up for each test 
    @BeforeEach
    public void setUp()
    {
        pieces = new PieceAbstract[32];
        for(int i = 0; i < pieces.length; i++)
        {
            // white pieces
            if(i == MainFrame.W_ROOK1 || i == MainFrame.W_ROOK2)
                pieces[i] = new Rook(i%8, 7, 'W', null, pieces);
            else if(i == MainFrame.W_KNIGHT1 || i == MainFrame.W_KNIGHT2)
                pieces[i] = new Knight(i%8, 7, 'W', null, pieces);
            else if(i == MainFrame.W_BISHOP1 || i == MainFrame.W_BISHOP2)
                pieces[i] = new Bishop(i%8, 7, 'W', null, pieces);
            else if(i == MainFrame.W_QUEEN)
                pieces[i] = new Queen(i%8, 7, 'W', null, pieces);
            else if(i == MainFrame.W_KING)
                pieces[i] = new King(i%8, 7, 'W', null, pieces);
            else if(i >= MainFrame.W_PAWN_MIN && i <= MainFrame.W_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 6, 'W', null, pieces);

            // black pieces
            else if(i >= MainFrame.B_PAWN_MIN && i <= MainFrame.B_PAWN_MAX)
                pieces[i] = new Pawn(i%8, 1, 'B', null, pieces);
            else if(i == MainFrame.B_ROOK1 || i == MainFrame.B_ROOK2)
                pieces[i] = new Rook(i%8, 0, 'B', null, pieces);
            else if(i == MainFrame.B_KNIGHT1 || i == MainFrame.B_KNIGHT2)
                pieces[i] = new Knight(i%8, 0, 'B', null, pieces);
            else if(i == MainFrame.B_BISHOP1 || i == MainFrame.B_BISHOP2)
                pieces[i] = new Bishop(i%8, 0, 'B', null, pieces);
            else if(i == MainFrame.B_QUEEN)
                pieces[i] = new Queen(i%8, 0, 'B', null, pieces);
            else if(i == MainFrame.B_KING)
                pieces[i] = new King(i%8, 0, 'B', null, pieces);
        }
    }
    //White Knight invalid 1
    @Test
    public void testMoveKnightInvalidW1()
    {
        try
        {
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.W_KNIGHT1].move(3,6,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Knight invalid 2
    @Test
    public void testMoveKnightInvalidW2()
    {
        try
        {
            //new postion of knight
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.W_KNIGHT1] = new Knight(5,5,'W',null,pieces);
            pieces[MainFrame.W_KNIGHT1].move(7,6,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Knight invalid 1
    @Test
    public void testMoveKnightInvalidB1()
    {
        try
        {
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.B_KNIGHT1].move(3,1,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Knight invalid 2
    @Test
    public void testMoveKnightInvalidB2()
    {
        try
        {
            //new postion of knight
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.B_KNIGHT1] = new Knight(5,2,'B',null,pieces);
            pieces[MainFrame.B_KNIGHT1].move(6,0,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Knight valid moves
    @Test
    public void testMoveKnightValidW()
    {
        try
        {
            //valid moves in open squares
            pieces[MainFrame.W_KNIGHT1].move(2,5,true);
            pieces[MainFrame.W_KNIGHT1].move(0,5,true);
            //new position of knight with capturing moves
            pieces[MainFrame.W_KNIGHT1] = new Knight(3,3,'W',null,pieces);
            pieces[MainFrame.W_KNIGHT1].move(2,1,true);
            pieces[MainFrame.W_KNIGHT1].move(4,1,true);
            assertTrue(true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
    //Black Knight valid moves
    @Test
    public void testMoveKnightValidB()
    {
        try
        {
            //valid moves in open squares
            pieces[MainFrame.B_KNIGHT1].move(2,2,true);
            pieces[MainFrame.B_KNIGHT1].move(0,2,true);
            //new position of knight with capturing moves
            pieces[MainFrame.B_KNIGHT1] = new Knight(4,4,'B',null,pieces);
            pieces[MainFrame.B_KNIGHT1].move(3,6,true);
            pieces[MainFrame.B_KNIGHT1].move(5,6,true);
            assertTrue(true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
    //White Bishop invalid 1
    @Test
    public void testMoveBishopInvalidW1()
    {
        try
        {
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.W_BISHOP1].move(3,6,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Bishop invalid 2
    @Test
    public void testMoveBishopInvalidW2()
    {
        try
        {
            //should be invalid move because of moving through pieces
            pieces[MainFrame.W_BISHOP1].move(4,5,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Bishop invalid 3
    @Test
    public void testMoveBishopInvalidW3()
    {
        try
        {
            //should be invalid move because of moving up
            pieces[MainFrame.W_BISHOP1] = new Bishop(2,5,'W',null,pieces);
            pieces[MainFrame.W_BISHOP1].move(2,3,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Bishop invalid 4
    @Test
    public void testMoveBishopInvalidW4()
    {
        try
        {
            //should be invalid move because of moving right
            pieces[MainFrame.W_BISHOP1] = new Bishop(2,5,'W',null,pieces);
            pieces[MainFrame.W_BISHOP1].move(5,5,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Bishop invalid 1
    @Test
    public void testMoveBishopInvalidB1()
    {
        try
        {
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.B_BISHOP1].move(3,1,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Bishop invalid 2
    @Test
    public void testMoveBishopInvalidB2()
    {
        try
        {
            //should be invalid move because of moving through pieces
            pieces[MainFrame.B_BISHOP1].move(4,2,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Bishop invalid 3
    @Test
    public void testMoveBishopInvalidB3()
    {
        try
        {
            //should be invalid move because of moving down
            pieces[MainFrame.B_BISHOP1] = new Bishop(2,2,'B',null,pieces);
            pieces[MainFrame.B_BISHOP1].move(2,5,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Bishop invalid 4
    @Test
    public void testMoveBishopInvalidB4()
    {
        try
        {
            //should be invalid move because of moving right
            pieces[MainFrame.B_BISHOP1] = new Bishop(2,2,'B',null,pieces);
            pieces[MainFrame.B_BISHOP1].move(5,2,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Bishop valid moves 
    @Test
    public void testMoveBishopValidW()
    {
        try
        {
            //new position to test valid moves
            pieces[MainFrame.W_BISHOP1] = new Bishop(2,5,'W',null,pieces);
            //valid moves in open squares and nothing in between
            pieces[MainFrame.W_BISHOP1].move(4,3,true);
            pieces[MainFrame.W_BISHOP1].move(0,3,true);
            //valid moves with capturing
            pieces[MainFrame.W_BISHOP1].move(6,1,true);
            assertTrue(true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
    //Black Bishop valid moves 
    @Test
    public void testMoveBishopValidB()
    {
        try
        {
            //new position to test valid moves
            pieces[MainFrame.B_BISHOP1] = new Bishop(2,2,'B',null,pieces);
            //valid moves in open squares and nothing in between
            pieces[MainFrame.B_BISHOP1].move(4,4,true);
            pieces[MainFrame.B_BISHOP1].move(0,4,true);
            //valid moves with capturing
            pieces[MainFrame.B_BISHOP1].move(6,6,true);
            assertTrue(true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
    //White Rook invalid 1
    @Test
    public void testMoveRookInvalidW1()
    {
        try
        {
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.W_ROOK1].move(0,6,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Rook invalid 2
    @Test
    public void testMoveRookInvalidW2()
    {
        try
        {
            //should be invalid move because of moving through a piece
            pieces[MainFrame.W_ROOK1].move(0,5,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Rook invalid 3
    @Test
    public void testMoveRookInvalidW3()
    {
        try
        {
            //should be invalid move because of moving diagonally
            pieces[MainFrame.W_ROOK1] = new Rook(5,5,'W',null,pieces);
            pieces[MainFrame.W_ROOK1].move(6,6,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Rook invalid 1
    @Test
    public void testMoveRookInvalidB1()
    {
        try
        {
            //should be invalid move because of moving on to the same colored piece
            pieces[MainFrame.B_ROOK1].move(0,1,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Rook invalid 2
    @Test
    public void testMoveRookInvalidB2()
    {
        try
        {
            //should be invalid move because of moving through a piece
            pieces[MainFrame.B_ROOK1].move(0,2,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Rook invalid 3
    @Test
    public void testMoveRookInvalidB3()
    {
        try
        {
            //should be invalid move becuase of moving diagonally
            pieces[MainFrame.B_ROOK1] = new Rook(5,5,'B',null,pieces);
            pieces[MainFrame.B_ROOK1].move(6,6,true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Rook valid moves
    @Test
    public void testMoveRookValidW()
    {
        try
        {
            //new position to test valid moves
            pieces[MainFrame.W_ROOK1] = new Rook(5,5,'W',null,pieces);
            //valid moves in open squares and nothing in between
            pieces[MainFrame.W_ROOK1].move(3,5,true);
            pieces[MainFrame.W_ROOK1].move(5,3,true);
            //valid moves with capturing
            pieces[MainFrame.W_ROOK1].move(5,1,true);
            assertTrue(true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
    //Black Rook valid moves
    @Test
    public void testMoveRookValidB()
    {
        try
        {
            //new position to test valid moves
            pieces[MainFrame.B_ROOK1] = new Rook(5,5,'B',null,pieces);
            //valid moves in open squares and nothing in between
            pieces[MainFrame.B_ROOK1].move(3,5,true);
            pieces[MainFrame.B_ROOK1].move(5,3,true);
            //valid moves with capturing
            pieces[MainFrame.B_ROOK1].move(5,6,true);
            assertTrue(true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
    //White Pawn invalid 1
    @Test
    public void testMovePawnInvalidW1()
    {
        try
        {
            //move a white pawn in front of another white pawn
            pieces[MainFrame.W_PAWN_MAX].setX(pieces[MainFrame.W_PAWN_MIN].getX());
            pieces[MainFrame.W_PAWN_MAX].setY(pieces[MainFrame.W_PAWN_MIN].getY() - 1);
            //should be invalid move because of moving onto same colored piece, forwards
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MAX].getX(), pieces[MainFrame.W_PAWN_MAX].getY(), true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Pawn invalid 2
    @Test
    public void testMovePawnInvalidW2()
    {
        try
        {
            //move a white pawn in front of another white pawn
            pieces[MainFrame.W_PAWN_MAX].setX(pieces[MainFrame.W_PAWN_MIN].getX());
            pieces[MainFrame.W_PAWN_MAX].setY(pieces[MainFrame.W_PAWN_MIN].getY() - 1);
            //should be invalid move because of moving past another piece
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MAX].getX(), pieces[MainFrame.W_PAWN_MAX].getY() - 1, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Pawn invalid 3
    @Test
    public void testMovePawnInvalidW3()
    {
        try
        {
            //move a white pawn 2 spaces in front of another white pawn
            pieces[MainFrame.W_PAWN_MAX].setX(pieces[MainFrame.W_PAWN_MIN].getX());
            pieces[MainFrame.W_PAWN_MAX].setY(pieces[MainFrame.W_PAWN_MIN].getY() - 2);
            //should be invalid move because of moving onto same colored piece
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MAX].getX(), pieces[MainFrame.W_PAWN_MAX].getY(), true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Pawn invalid 4
    @Test
    public void testMovePawnInvalidW4()
    {
        try
        {
            //Move a white pawn to a front diagonal of another white pawn
            pieces[MainFrame.W_PAWN_MAX].setX(pieces[MainFrame.W_PAWN_MIN].getX() + 1);
            pieces[MainFrame.W_PAWN_MAX].setY(pieces[MainFrame.W_PAWN_MIN].getY() - 1);
            //should be invalid move because of moving onto same colored piece, diagonally
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MAX].getX(), pieces[MainFrame.W_PAWN_MAX].getY(), true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Pawn invalid 5
    @Test
    public void testMovePawnInvalidW5()
    {
        try
        {
            //should be invalid move because of moving diagonally with no piece to capture
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MIN].getX() + 1, pieces[MainFrame.W_PAWN_MIN].getY() - 1, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Pawn invalid 6
    @Test
    public void testMovePawnInvalidW6()
    {
        try
        {
            //should be invalid move because of moving more than two spaces forwards
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MIN].getX(), pieces[MainFrame.W_PAWN_MIN].getY() - 3, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Pawn invalid 7
    @Test
    public void testMovePawnInvalidW7()
    {
        try
        {
            //set notMoved variable as if the pawn has moved
            ((Pawn)pieces[MainFrame.W_PAWN_MIN]).setNotMoved(false);
            //should be invalid move because of moving two spaces forwards after already moving
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MIN].getX(), pieces[MainFrame.W_PAWN_MIN].getY() - 2, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //White Pawn valid moves
    @Test
    public void testMovePawnValidW()
    {
        try
        {
            //setup
            pieces[MainFrame.B_PAWN_MIN].setX(pieces[MainFrame.W_PAWN_MIN].getX() + 1);
            pieces[MainFrame.B_PAWN_MIN].setY(pieces[MainFrame.W_PAWN_MIN].getY() - 1);
            pieces[MainFrame.B_PAWN_MAX].setX(pieces[MainFrame.W_PAWN_MAX].getX() - 1);
            pieces[MainFrame.B_PAWN_MAX].setY(pieces[MainFrame.W_PAWN_MAX].getY() - 1);
            ((Pawn)pieces[MainFrame.B_PAWN_MAX]).setEnPassant(true);
            pieces[MainFrame.W_PAWN_MAX].setY(pieces[MainFrame.W_PAWN_MAX].getY() - 1);

            //move forwards one space
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MIN].getX(), pieces[MainFrame.W_PAWN_MIN].getY() - 1, true);
            //move forwards two spaces
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MIN].getX(), pieces[MainFrame.W_PAWN_MIN].getY() - 2, true);
            //capture a black piece
            pieces[MainFrame.W_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MIN].getX(), pieces[MainFrame.B_PAWN_MIN].getY(), true);
            //perform enPassant
            pieces[MainFrame.W_PAWN_MAX].move(pieces[MainFrame.B_PAWN_MAX].getX(), pieces[MainFrame.B_PAWN_MAX].getY() - 1, true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
    //Black Pawn invalid 1
    @Test
    public void testMovePawnInvalidB1()
    {
        try
        {
            //move a black pawn in front of another black pawn
            pieces[MainFrame.B_PAWN_MAX].setX(pieces[MainFrame.B_PAWN_MIN].getX());
            pieces[MainFrame.B_PAWN_MAX].setY(pieces[MainFrame.B_PAWN_MIN].getY() + 1);
            //should be invalid move because of moving onto same colored piece, forwards
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MAX].getX(), pieces[MainFrame.B_PAWN_MAX].getY(), true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Pawn invalid 2
    @Test
    public void testMovePawnInvalidB2()
    {
        try
        {
            //move a black pawn in front of another black pawn
            pieces[MainFrame.B_PAWN_MAX].setX(pieces[MainFrame.B_PAWN_MIN].getX());
            pieces[MainFrame.B_PAWN_MAX].setY(pieces[MainFrame.B_PAWN_MIN].getY() + 1);
            //should be invalid move because of moving past another piece
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MAX].getX(), pieces[MainFrame.B_PAWN_MAX].getY() + 1, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Pawn invalid 3
    @Test
    public void testMovePawnInvalidB3()
    {
        try
        {
            //move a black pawn 2 spaces in front of another black pawn
            pieces[MainFrame.B_PAWN_MAX].setX(pieces[MainFrame.B_PAWN_MIN].getX());
            pieces[MainFrame.B_PAWN_MAX].setY(pieces[MainFrame.B_PAWN_MIN].getY() + 2);
            //should be invalid move because of moving onto same colored piece
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MAX].getX(), pieces[MainFrame.B_PAWN_MAX].getY(), true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Pawn invalid 4
    @Test
    public void testMovePawnInvalidB4()
    {
        try
        {
            //Move a black pawn to a front diagonal of another black pawn
            pieces[MainFrame.B_PAWN_MAX].setX(pieces[MainFrame.B_PAWN_MIN].getX() + 1);
            pieces[MainFrame.B_PAWN_MAX].setY(pieces[MainFrame.B_PAWN_MIN].getY() + 1);
            //should be invalid move because of moving onto same colored piece, diagonally
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MAX].getX(), pieces[MainFrame.B_PAWN_MAX].getY(), true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Pawn invalid 5
    @Test
    public void testMovePawnInvalidB5()
    {
        try
        {
            //should be invalid move because of moving diagonally with no piece to capture
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MIN].getX() + 1, pieces[MainFrame.B_PAWN_MIN].getY() + 1, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Pawn invalid 6
    @Test
    public void testMovePawnInvalidB6()
    {
        try
        {
            //should be invalid move because of moving more than two spaces forwards
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MIN].getX(), pieces[MainFrame.B_PAWN_MIN].getY() + 3, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Pawn invalid 7
    @Test
    public void testMovePawnInvalidB7()
    {
        try
        {
            //set notMoved variable as if the pawn has moved
            ((Pawn)pieces[MainFrame.B_PAWN_MIN]).setNotMoved(false);
            //should be invalid move because of moving two spaces forwards after already moving
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MIN].getX(), pieces[MainFrame.B_PAWN_MIN].getY() + 2, true);
            assertTrue(false);
        }
        catch(InvalidMoveException ime) {assertTrue(true);}
    }
    //Black Pawn valid moves
    @Test
    public void testMovePawnValidB()
    {
        try
        {
            //setup
            pieces[MainFrame.W_PAWN_MIN].setX(pieces[MainFrame.B_PAWN_MIN].getX() + 1);
            pieces[MainFrame.W_PAWN_MIN].setY(pieces[MainFrame.B_PAWN_MIN].getY() + 1);
            pieces[MainFrame.W_PAWN_MAX].setX(pieces[MainFrame.B_PAWN_MAX].getX() + 1);
            pieces[MainFrame.W_PAWN_MAX].setY(pieces[MainFrame.B_PAWN_MAX].getY() + 1);
            ((Pawn)pieces[MainFrame.W_PAWN_MAX]).setEnPassant(true);
            pieces[MainFrame.B_PAWN_MAX].setY(pieces[MainFrame.B_PAWN_MAX].getY() + 1);

            //move forwards one space
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MIN].getX(), pieces[MainFrame.B_PAWN_MIN].getY() + 1, true);
            //move forwards two spaces
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.B_PAWN_MIN].getX(), pieces[MainFrame.B_PAWN_MIN].getY() + 2, true);
            //capture a white piece
            pieces[MainFrame.B_PAWN_MIN].move(pieces[MainFrame.W_PAWN_MIN].getX(), pieces[MainFrame.W_PAWN_MIN].getY(), true);
            //perform enPassant
            pieces[MainFrame.B_PAWN_MAX].move(pieces[MainFrame.W_PAWN_MAX].getX(), pieces[MainFrame.W_PAWN_MAX].getY() + 1, true);
        }
        catch(InvalidMoveException ime) {assertTrue(false);}
    }
}
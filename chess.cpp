//terminal chess game
//Created by Noah Westervelt on 06/24/2020

#include<iostream>
#include<string>
#include<fstream>

//******Function Definitions******
void boardInit(std::string board[8][8]) //function for initializing the board
{
    for(int col = 0; col < 8; col += 1) //initialize the white side
    {
        if (col == 0 || col == 7)
            board[7][col] = "Wr";
        if (col == 1 || col == 6)
            board[7][col] = "Wk";
        if (col == 2 || col == 5)
            board[7][col] = "Wb";
        if (col == 3)
            board[7][col] = "WK";
        if (col == 4)
            board[7][col] = "WQ";
        board[6][col] = "Wp";
    }
    
    for (int col = 0; col < 8; col += 1) //initialize the black side
    {
        if (col == 0 || col == 7)
            board[0][col] = "Br";
        if (col == 1 || col == 6)
            board[0][col] = "Bk";
        if (col == 2 || col == 5)
            board[0][col] = "Bb";
        if (col == 3)
            board[0][col] = "BK";
        if (col == 4)
            board[0][col] = "BQ";
        board[1][col] = "Bp";
    }
    
    for (int row = 2; row < 6; row += 1) //fill empty space with ##
    {
        for (int col = 0; col < 8; col += 1)
            board[row][col] = "##";
    }
} //end boardInit

void display(std::string board[8][8]) //function for displaying the board
{
    std::cout << std::endl << "   0   1   2   3   4   5   6   7" << std::endl;
    for (int row = 0; row < 8; row += 1)
    {
        std::cout << row << "  ";
        for (int col = 0; col < 8; col += 1)
            std::cout << board[row][col] << "  ";
	std::cout << std::endl;
    }
    std::cout << std::endl; //create space between displayed board and next line
} //end display

int topCheck(int stRow, int stCol, int edRow, int edCol, std::string board[8][8]) //top two move angle checks, returns 0 when no errors found
{
    if (stRow > edRow)
    {
        if (stCol > edCol)
        {
            for (int x = 1; x < abs(stRow - edRow); x += 1)
            {
                if (board[stRow - x][stCol - x] != "##" && board[stRow - x][stCol - x] != "!b" && board[stRow - x][stCol - x] != "!w")
                    return 1;
	    }
        }
        if (stCol < edCol)
        {
            for (int x = 1; x < abs(stRow - edRow); x += 1)
            {
                if (board[stRow - x][stCol + x] != "##" && board[stRow - x][stCol + x] != "!b" && board[stRow - x][stCol + x] != "!w")
                    return 1;
	    }
        }
    }
    return 0;
} //end topCheck

int botCheck(int stRow, int stCol, int edRow, int edCol, std::string board[8][8]) //bottom two move angle checks, returns 0 when no errors found
{
    if (stRow < edRow)
    {
        if (stCol > edCol)
        {
            for (int x = 1; x < abs(stRow - edRow); x += 1)
            {
                if (board[stRow + x][stCol - x] != "##" && board[stRow + x][stCol - x] != "!b" && board[stRow + x][stCol - x] != "!w")
                    return 1;
            }
        }
        if (stCol < edCol)
        {
            for (int x = 1; x < abs(stRow - edRow); x += 1)
            {
                if (board[stRow + x][stCol + x] != "##" && board[stRow + x][stCol + x] != "!b" && board[stRow + x][stCol + x] != "!w")
                    return 1;
            }
        }
    }
    return 0;
} //end botCheck

int horCheck(int stRow, int stCol, int edRow, int edCol, std::string board[8][8]) //horizontal move check, returns 0 when no errors found
{
    if (stRow == edRow)
    {
        if (stCol > edCol)
        {
            for (int x = 1; x < stCol - edCol; x += 1)
            {
                if (board[edRow][stCol - x] != "##" && board[edRow][stCol - x] != "!b" && board[edRow][stCol - x] != "!w")
                    return 1;
            }
        }
        if (stCol < edCol)
        {
            for (int x = 1; x < edCol - stCol; x += 1)
            {
                if (board[edRow][edCol - x] != "##" && board[edRow][edCol - x] != "!b" && board[edRow][edCol - x] != "!w")
                    return 1;
            }
        }
    }
    return 0;
} //end horCheck

int verCheck(int stRow, int stCol, int edRow, int edCol, std::string board[8][8]) //vertical move check, returns 0 when no errors found
{
    if (stCol == edCol)
    {
        if (stRow > edRow)
        {
            for (int x = 1; x < stRow - edRow; x += 1)
            {
                if (board[stRow - x][edCol] != "##" && board[stRow - x][edCol] != "!b" && board[stRow - x][edCol] != "!w")
                    return 1;
            }
        }
        if (stRow < edRow)
        {
            for (int x = 1; x < edRow - stRow; x += 1)
            {
                if (board[edRow - x][edCol] != "##" && board[edRow - x][edCol] != "!b" && board[edRow - x][edCol] != "!w")
                    return 1;
            }
        }
    }
    return 0;
} //end verCheck

void promote(int edRow, int edCol, std::string board[8][8]) //function for promoting pawns
{
    if(edRow == 0) //white player promotion
    {
        int choice = 0;
        while(choice < 1 || choice > 4)
        {
            std::cout << std::endl << "You may promote this pawn, enter the corresponding number to make your choice:" << std::endl;
            std::cout << "     1 = WQ" << std::endl << "     2 = Wr" << std::endl << "     3 = Wb" << std::endl << "     4 = Wk" << std::endl;
            std::cout << "Your choice: ";
            std::cin >> choice;
        
            if(choice < 1 || choice > 4)
                std::cout << "Enter a valid number to make your choice." << std::endl;
        }
        
        if(choice == 1)
            board[edRow][edCol] = "WQ";
        if(choice == 2)
            board[edRow][edCol] = "Wr";
        if(choice == 3)
            board[edRow][edCol] = "Wb";
        if(choice == 4)
            board[edRow][edCol] = "Wk";
    } //end white player promotion
    
    if(edRow == 7) //black player promotion
    {
        int choice = 0;
        while(choice < 1 || choice > 4)
        {
            std::cout << std::endl << "You may promote this pawn, enter the corresponding number to make your choice:" << std::endl;
            std::cout << "     1 = BQ" << std::endl << "     2 = Br" << std::endl << "     3 = Bb" << std::endl << "     4 = Bk" << std::endl;
            std::cout << "Your choice: ";
            std::cin >> choice;
        
            if(choice < 1 || choice > 4)
                std::cout << "Enter a valid number to make your choice." << std::endl;
        }
        
        if(choice == 1)
            board[edRow][edCol] = "BQ";
        if(choice == 2)
            board[edRow][edCol] = "Br";
        if(choice == 3)
            board[edRow][edCol] = "Bb";
        if(choice == 4)
            board[edRow][edCol] = "Bk";
    } //end black player promotion
} //end promote function
            
void replace(int stRow, int stCol, int edRow, int edCol, std::string board[8][8]) //replaces the ending point with the piece at the start point
{
    board[edRow][edCol] = board[stRow][stCol];
    board[stRow][stCol] = "##";
} //end replace

int move(int stRow, int stCol, int edRow, int edCol, int wCheck, int bCheck, bool wKingMv,
         bool wLeftRookeMv, bool wRightRookeMv, bool bKingMv, bool bLeftRookeMv, bool bRightRookeMv, std::string board[8][8]) //function for moving pieces, returns 0 when no move errors found
{
    int error = 0;

    //check for friendly fire, true = no friendly fire
    if ((std::string(board[stRow][stCol]).find("W") == 0 && std::string(board[edRow][edCol]).find("W") != 0) || (std::string(board[stRow][stCol]).find("B") == 0 && std::string(board[edRow][edCol]).find("B") != 0))
    {
        if (board[stRow][stCol] == "Wp") //White pawn
        {
            if (stCol == edCol) //forward
            {
                if (stRow - 1 == edRow && board[edRow][edCol] == "##") //normal move
                    replace(stRow, stCol, edRow, edCol, board);
                
                if (stRow == 6 && edRow == 4 && board[5][edCol] == "##" && board[4][edCol] == "##") //special start move
                {
                    replace(stRow, stCol, edRow, edCol, board);
                    board[edRow + 1][edCol] = "!w";
                }
            } //end forward
            
            if (abs(stCol - edCol) == 1 && stRow - 1 == edRow) //attack
            {
                if (std::string(board[edRow][edCol]).find("B") == 0)
                    replace(stRow, stCol, edRow, edCol, board);
                
                if(board[edRow][edCol] == "!b") //en passant
                {
                    replace(stRow, stCol, edRow, edCol, board);
                    board[edRow + 1][edCol] = "##";
                }// end en passant
            } //end attack
            
            if (edRow == 0)
                promote(edRow, edCol, board);
            
        } //end White pawn
    
        if (board[stRow][stCol] == "Bp") //Black pawn
        {
            if (stCol == edCol) //forward
            {
                if (stRow + 1 == edRow && board[edRow][edCol] == "##") //normal move
                    replace(stRow, stCol, edRow, edCol, board);
                
                if (stRow == 1 && edRow == 3 && board[2][edCol] == "##" && board[3][edCol] == "##") //special start move
                {
                    replace(stRow, stCol, edRow, edCol, board);
                    board[edRow - 1][edCol] = "!b";
                }
            } //end forward
            
            if (abs(stCol - edCol) == 1 && stRow + 1 == edRow) //attack
            {
                if (std::string(board[edRow][edCol]).find("W") == 0)
                    replace(stRow, stCol, edRow, edCol, board);
                
                if(board[edRow][edCol] == "!w") //en passant
                {
                    replace(stRow, stCol, edRow, edCol, board);
                    board[edRow - 1][edCol] = "##";
                } //end en passant
            } //end attack
            
            if(edRow == 7)
                promote(edRow, edCol, board);
            
        } //end Black pawn
    
        if (board[stRow][stCol] == "Wk") //White knight
        {   
            if (abs(stRow - edRow) == 2 && abs(stCol - edCol) == 1)
            {
                replace(stRow, stCol, edRow, edCol, board);
            }
            if (abs(stRow - edRow) == 1 && abs(stCol - edCol) == 2)
            {
                replace(stRow, stCol, edRow, edCol, board);
           	 }
        } //end White knight
        
        if (board[stRow][stCol] == "Bk") //Black knight
        {
        if (abs(stRow - edRow) == 2 && abs(stCol - edCol) == 1)
        {
            replace(stRow, stCol, edRow, edCol, board);
        }
        if (abs(stRow - edRow) == 1 && abs(stCol - edCol) == 2)
        {
            replace(stRow, stCol, edRow, edCol, board);
        } 
        } //end Black knight
    
        if (board[stRow][stCol] == "Wr" && (stRow == edRow || stCol == edCol)) //White rook
        {
            if (horCheck(stRow, stCol, edRow, edCol, board) == 0 && verCheck(stRow, stCol, edRow, edCol, board) == 0)
            {
                replace(stRow, stCol, edRow, edCol, board);
            }
            else
                error = -1;
        } //end White rook
    
        if (board[stRow][stCol] == "Br" && (stRow == edRow || stCol == edCol)) //Black rook
        {
            if (horCheck(stRow, stCol, edRow, edCol, board) == 0 && verCheck(stRow, stCol, edRow, edCol, board) == 0)
            {
                replace(stRow, stCol, edRow, edCol, board);
            }
            else
                error = -1;
        } //end Black rook
        
        if (board[stRow][stCol] == "Wb" && abs(stRow - edRow) == abs(stCol - edCol)) //White bishop
        {
            if (topCheck(stRow, stCol, edRow, edCol, board) == 0 && botCheck(stRow, stCol, edRow, edCol, board) == 0)
            {
                replace(stRow, stCol, edRow, edCol, board);
            }
            else
                error = -1;
        } //end White bishop
        
        if (board[stRow][stCol] == "Bb" && abs(stRow - edRow) == abs(stCol - edCol)) //Black bishop
        {
            if (topCheck(stRow, stCol, edRow, edCol, board) == 0 && botCheck(stRow, stCol, edRow, edCol, board) == 0)
            {
                replace(stRow, stCol, edRow, edCol, board);
            }
            else
                error = -1;
        } //end Black bishop
        
        if (board[stRow][stCol] == "WQ" && ((abs(stRow - edRow) == abs(stCol - edCol)) || (stRow == edRow || stCol == edCol))) //White Queen
        {
            if (horCheck(stRow, stCol, edRow, edCol, board) == 0 && verCheck(stRow, stCol, edRow, edCol, board) == 0 && topCheck(stRow, stCol, edRow, edCol, board) == 0 && botCheck(stRow, stCol, edRow, edCol, board) == 0)
            {
                replace(stRow, stCol, edRow, edCol, board);
            }
            else
                error = -1;
        } //end White Queen
        
        if (board[stRow][stCol] == "BQ" && ((abs(stRow - edRow) == abs(stCol - edCol)) || (stRow == edRow || stCol == edCol))) //Black Queen
        {
            if (horCheck(stRow, stCol, edRow, edCol, board) == 0 && verCheck(stRow, stCol, edRow, edCol, board) == 0 && topCheck(stRow, stCol, edRow, edCol, board) == 0 && botCheck(stRow, stCol, edRow, edCol, board) == 0)
            {
                replace(stRow, stCol, edRow, edCol, board);
            }
            else
                error = -1;
        } //end Black Queen
        
        if (board[stRow][stCol] == "WK" && abs(stRow - edRow) < 2 && abs(stCol - edCol) < 2) //White King
        {
            replace(stRow, stCol, edRow, edCol, board);
        } //end White King
        
        if (board[stRow][stCol] == "BK" && abs(stRow - edRow) < 2 && abs(stCol - edCol) < 2) //Black King
        {
            replace(stRow, stCol, edRow, edCol, board);
        } //end Black King
        
        if (board[stRow][stCol] == "WK" && stRow == 7 && stCol == 3) //White castleing
        {
            if (edRow == 7 && edCol == 1 && wKingMv == false && wLeftRookeMv == false && wCheck == 0 && board[7][0] == "Wr") //left side
            {
                if (board[7][1] == "##" && board[7][2] == "##")
                {
                    board[edRow][edCol] = "WK";
                    board[7][2] = "Wr";
                    board[7][0] = "##";
                    board[7][3] = "##";
                }
            }
            
            if (edRow == 7 && edCol == 5 && wKingMv == false && wRightRookeMv == false && wCheck == 0 && board[7][7] == "Wr") //right side
            {
                if (board[7][4] == "##" && board[7][5] == "##" && board[7][6] == "##")
                {
                    board[edRow][edCol] = "WK";
                    board[7][4] = "Wr";
                    board[7][7] = "##";
                    board[7][3] = "##";
                }
            }
        } //end White castleing
        
        if (board[stRow][stCol] == "BK" && stRow == 0 && stCol == 3) //Black castleing
        {
            if (edRow == 0 && edCol == 1 && bKingMv == false && bLeftRookeMv == false && bCheck == 0 && board[0][0] == "Br") //left side
            {
                if (board[0][1] == "##" && board[0][2] == "##")
                {
                    board[edRow][edCol] = "BK";
                    board[0][2] = "Br";
                    board[0][0] = "##";
                    board[0][3] = "##";
                }
            }
            
            if (edRow == 0 && edCol == 5 && bKingMv == false && bRightRookeMv == false && bCheck == 0 && board[0][7] == "Br") //right side
            {
                if (board[0][4] == "##" && board[0][5] == "##" && board[0][6] == "##")
                {
                    board[edRow][edCol] = "BK";
                    board[0][4] = "Br";
                    board[0][7] = "##";
                    board[0][3] = "##";
                }
            }
        } //end Black castleing
        
    } //end true path for friendly fire check
    else
        error = -1; //end false path for friendly fire check
        
    return error;
} //end move

bool kingCheck(std::string player,int checkCount, int & wCheck, int & bCheck, std::string board[8][8])
{
    int kRow = 0;
    int kCol = 0;
    int counter = 0;
    bool check = false;
    
    //array search---------------------------------------------------------------------
    for(int row = 0; row < 8; row += 1)
    {
        for(int col = 0; col < 8; col += 1)
        {
            if(board[row][col] == player + "K") //check if player's king has been found
            {
                kRow = row;
                kCol = col;
            }
        }
    } //end array search---------------------------------------------------------------
    
    //left side horizontal check-------------------------------------------------------
    if(kCol - 1 > -1)
    {
        counter = 1;
        while(kCol - counter > -1)
        {
            if(board[kRow][kCol - counter] != "##" && board[kRow][kCol - counter] != "!b" && board[kRow][kCol - counter] != "!w")
                break;
            counter += 1;
        }
    
        if(player == "W")
        {
            if(board[kRow][kCol - counter] == "BQ" || board[kRow][kCol - counter] == "Br")
                check = true;
        }
    
        if(player == "B")
        {
            if(board[kRow][kCol - counter] == "WQ" || board[kRow][kCol - counter] == "Wr")
                check = true;
        }
    } //end left side horizontal check-------------------------------------------------
    
    //right side horizontal check------------------------------------------------------
    if(kCol + 1 < 8)
    {
        counter = 1;
        while(kCol + counter < 8)
        {
            if(board[kRow][kCol + counter] != "##" && board[kRow][kCol + counter] != "!b" && board[kRow][kCol + counter] != "!w")
                break;
            counter += 1;
        }
    
        if(player == "W")
        {
            if(board[kRow][kCol + counter] == "BQ" || board[kRow][kCol + counter] == "Br")
                check = true;
        }
    
        if(player == "B")
        {
            if(board[kRow][kCol + counter] == "WQ" || board[kRow][kCol + counter] == "Wr")
                check = true;
        }
    } //end right side horizontal check------------------------------------------------
    
    //up side vertical check-----------------------------------------------------------
    if(kRow - 1 > -1)
    {
        counter = 1;
        while(kRow - counter > -1)
        {
            if(board[kRow - counter][kCol] != "##" && board[kRow - counter][kCol] != "!b" && board[kRow - counter][kCol] != "!w")
                break;
            counter += 1;
        }
    
        if(kRow - counter > -1)
        {
            if(player == "W")
            {
                if(board[kRow - counter][kCol] == "BQ" || board[kRow - counter][kCol] == "Br")
                    check = true;
            }
    
            if(player == "B")
            {
                if(board[kRow - counter][kCol] == "WQ" || board[kRow - counter][kCol] == "Wr")
                    check = true;
            }
        }
    } //end up side vertical check-----------------------------------------------------
    
    //down side vertical check---------------------------------------------------------(segmentation fault here)
    if(kRow + 1 < 8)
    {
        counter = 1;
        while(kRow + counter < 8)
        {
            if(board[kRow + counter][kCol] != "##" && board[kRow + counter][kCol] != "!b" && board[kRow + counter][kCol] != "!w")
                break;
            counter += 1;
        }
        
        if(kRow + counter < 8)
        {
            if(player == "W")
            {
                if(board[kRow + counter][kCol] == "BQ" || board[kRow + counter][kCol] == "Br")
                    check = true;
            }
    
            if(player == "B")
            {
                if(board[kRow + counter][kCol] == "WQ" || board[kRow + counter][kCol] == "Wr")
                    check = true;
            }
        }
    } //end down side vertical check---------------------------------------------------
    
    //top diagonal check---------------------------------------------------------------
    if(kRow - 1 > -1 && kCol - 1 > -1)
    {
        counter = 1;
        while(kRow - counter > -1 && kCol - counter > -1)
        {
            if(board[kRow - counter][kCol - counter] != "##" && board[kRow - counter][kCol - counter] != "!b" && board[kRow - counter][kCol - counter] != "!w")
                break;
            counter += 1;
        }
        
        if(kRow - counter > -1 && kCol - counter > -1)
        {
            if(player == "W")
            {
                if(board[kRow - counter][kCol - counter] == "BQ" || board[kRow - counter][kCol - counter] == "Bb")
                    check = true;
            }
    
            if(player == "B")
            {
                if(board[kRow - counter][kCol - counter] == "WQ" || board[kRow - counter][kCol - counter] == "Wb")
                    check = true;
            }
        }
    }
    
    if(kRow - 1 > -1 && kCol + 1 < 8)
    {
        counter = 1;
        while(kRow - counter > -1 && kCol + counter < 8)
        {
            if(board[kRow - counter][kCol + counter] != "##" && board[kRow - counter][kCol + counter] != "!b" && board[kRow - counter][kCol + counter] != "!w")
                break;
            counter += 1;
        }
        
        if(kRow - counter > -1 && kCol + counter < 8)
        {
            if(player == "W")
            {
                if(board[kRow - counter][kCol + counter] == "BQ" || board[kRow - counter][kCol + counter] == "Bb")
                    check = true;
            }
    
            if(player == "B")
            {
                if(board[kRow - counter][kCol + counter] == "WQ" || board[kRow - counter][kCol + counter] == "Wb")
                    check = true;
            }
        }
    } //end top diagonal check---------------------------------------------------------
    
    //bottom diagonal check------------------------------------------------------------
    if(kRow + 1 < 8 && kCol - 1 > -1)
    {
        counter = 1;
        while(kRow + counter < 8 && kCol - counter > -1)
        {
            if(board[kRow + counter][kCol - counter] != "##" && board[kRow + counter][kCol - counter] != "!b" && board[kRow + counter][kCol - counter] != "!w")
                break;
            counter += 1;
        }
        
        if(kRow + counter < 8 && kCol - counter > -1)
        {
            if(player == "W")
            {
                if(board[kRow + counter][kCol - counter] == "BQ" || board[kRow + counter][kCol - counter] == "Bb")
                    check = true;
            }
    
            if(player == "B")
            {
                if(board[kRow + counter][kCol - counter] == "WQ" || board[kRow + counter][kCol - counter] == "Wb")
                    check = true;
            }
        }
    }
    
    if(kRow + 1 < 8 && kCol + 1 < 8)
    {
        counter = 1;
        while(kRow + counter < 8 && kCol + counter < 8)
        {
            if(board[kRow + counter][kCol + counter] != "##" && board[kRow + counter][kCol + counter] != "!b" && board[kRow + counter][kCol + counter] != "!w")
                break;
            counter += 1;
        }
    
        if(kRow + counter < 8 && kCol + counter < 8)
        {
            if(player == "W")
            {
                if(board[kRow + counter][kCol + counter] == "BQ" || board[kRow + counter][kCol + counter] == "Bb")
                    check = true;
            }
    
            if(player == "B")
            {
                if(board[kRow + counter][kCol + counter] == "WQ" || board[kRow + counter][kCol + counter] == "Wb")
                    check = true;
            }
        }
    } //end bottom diagonal check------------------------------------------------------
    //knight check---------------------------------------------------------------------
    if(kRow - 2 > -1)
    {
        if(kCol - 1 > -1)
        {
            if(player == "W" && board[kRow - 2][kCol - 1] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow - 2][kCol - 1] == "Wk")
                check = true;
        }
        
        if(kCol + 1 < 8)
        {
            if(player == "W" && board[kRow - 2][kCol + 1] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow - 2][kCol + 1] == "Wk")
                check = true;
        }
    }
    
    if(kRow - 1 > -1)
    {
        if(kCol - 2 > -1)
        {
            if(player == "W" && board[kRow - 1][kCol - 2] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow - 1][kCol - 2] == "Wk")
                check = true;
        }
        
        if(kCol + 2 < 8)
        {
            if(player == "W" && board[kRow - 1][kCol + 2] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow - 1][kCol + 2] == "Wk")
                check = true;
        }
    }
    
    if(kRow + 1 < 8)
    {
        if(kCol - 1 > -1)
        {
            if(player == "W" && board[kRow + 1][kCol - 2] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow + 1][kCol - 2] == "Wk")
                check = true;
        }
        
        if(kCol + 1 < 8)
        {
            if(player == "W" && board[kRow + 1][kCol + 2] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow + 1][kCol + 2] == "Wk")
                check = true;
        }
    }
    
    if(kRow + 2 < 8)
    {
        if(kCol - 1 > -1)
        {
            if(player == "W" && board[kRow + 2][kCol - 1] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow + 2][kCol - 1] == "Wk")
                check = true;
        }
        
        if(kCol + 1 < 8)
        {
            if(player == "W" && board[kRow + 2][kCol + 1] == "Bk")
                check = true;
            
            if(player == "B" && board[kRow + 2][kCol + 1] == "Wk")
                check = true;
        }
    } //end knight check---------------------------------------------------------------
    
    //pawn check-----------------------------------------------------------------------
    if(kRow + 1 < 8)
    {
        if(kCol - 1 > -1 || kCol + 1 < 8)
        {
            if(player == "W" && (board[kRow + 1][kCol - 1] == "Bp" || board[kRow + 1][kCol + 1] == "Bp"))
                check = true;
        }
    }
    
    if(kRow - 1 > -1)
    {
        if(kCol - 1 > -1 || kCol + 1 < 8)
        {
            if(player == "B" && (board[kRow - 1][kCol - 1] == "Wp" || board[kRow - 1][kCol + 1] == "Wp"))
                check = true;
        }
    
    } //end pawn check-----------------------------------------------------------------
    
    if(checkCount == 0 && check == true)
    {
        if(player == "W")
            wCheck += 1;
        if(player == "B")
            bCheck += 1;
    }
    
    return check;
} //end kingCheck

int load(int & alternator, int & checkCount, int & turnCount, int & wCheck, int & bCheck, bool & wKingMv, bool & wLeftRookeMv,
          bool & wRightRookeMv, bool & bKingMv, bool & bLeftRookeMv, bool & bRightRookeMv, std::string board[8][8]) //function for loading from the "Save-Game.txt" file
{
    int choice = 0;
    std::ifstream loadFile;
    loadFile.open("Save-Game.txt", std::ios::in);
    
    while(loadFile.is_open() == false)
    {
        std::cout << "File failed to load; Enter 0 to retry, 1 to start a new game, or 2 to quit: ";
        std::cin >> choice;
        
        if(choice < 0 || choice > 2)
            std::cout << "You entered an invalid value, please enter a valid value." << std::endl;
        
        if(choice == 0)
            loadFile.open("Save-Game.txt", std::ios::in);
            
        if(choice == 1 || choice == 2)
            break;
    }
    
    if(loadFile.is_open() == true) //assign the variables and elements of the array the appropriate values
    {
        //load the board array
        for(int row = 0; row < 8; row += 1)
        {
            for(int col = 0; col < 8; col += 1)
                loadFile >> board[row][col];
        }
        
        //load the variables
        loadFile.ignore(100);
        loadFile >> alternator;
        loadFile.ignore(100);
        loadFile >> checkCount;
        loadFile.ignore(100);
        loadFile >> turnCount;
        loadFile.ignore(100);
        loadFile >> wCheck;
        loadFile.ignore(100);
        loadFile >> bCheck;
        loadFile.ignore(100);
        loadFile >> wKingMv;
        loadFile.ignore(100);
        loadFile >> wLeftRookeMv;
        loadFile.ignore(100);
        loadFile >> wRightRookeMv;
        loadFile.ignore(100);
        loadFile >> bKingMv;
        loadFile.ignore(100);
        loadFile >> bLeftRookeMv;
        loadFile.ignore(100);
        loadFile >> bRightRookeMv;
    }
    
    loadFile.close();
    return choice;
}

int save(int alternator, int checkCount, int turnCount, int wCheck, int bCheck, bool wKingMv, bool wLeftRookeMv,
          bool wRightRookeMv, bool bKingMv, bool bLeftRookeMv, bool bRightRookeMv, std::string board[8][8]) //function for saving the game in the "Save-Game.txt" file
{
    int choice = 0;
    std::ofstream saveFile;
    saveFile.open("Save-Game.txt", std::ios::out);
    
    while(saveFile.is_open() == false)
    {
        std::cout << "Save failed; Enter 0 to retry, 1 to continue playing, or 2 to quit (progress will be lost): ";
        std::cin >> choice;
        
        if(choice == 0)
            saveFile.open("Save-Game.txt", std::ios::out);
        
        if(choice == 1 || choice == 2)
            break;
    }
    
    if(saveFile.is_open() == true) //save appropriate values to the file
    {
        saveFile << "Board" << std::endl; //board array being saved
        for(int row = 0; row < 8; row += 1)
        {
            for(int col = 0; col < 8; col += 1)
                saveFile << board[row][col] << " ";
            saveFile << std::endl;
        }
    
        saveFile << std::endl; //space between board values and other variable values
    
        //variables being saved
        saveFile << "alternator:\t" << alternator << std::endl;
        saveFile << "checkCount:\t" << checkCount << std::endl;
        saveFile << "turnCount:\t" << turnCount << std::endl;
        saveFile << "wCheck:\t" << wCheck << std::endl;
        saveFile << "bCheck:\t" << bCheck << std::endl;
        saveFile << "wKingMv:\t" << wKingMv << std::endl;
        saveFile << "wLeftRookeMv:\t" << wLeftRookeMv << std::endl;
        saveFile << "wRightRookeMv:\t" << wRightRookeMv << std::endl;
        saveFile << "bKingMv:\t" << bKingMv << std::endl;
        saveFile << "bLeftRookeMv:\t" << bLeftRookeMv << std::endl;
        saveFile << "bRightRookeMv:\t" << bRightRookeMv << std::endl;
    }
    saveFile.close();
    return choice;
}

//******Start Main Function******
int main()
{
    //*****Variable Declarations*****
    std::string board[8][8] = {{""}};
    std::string player = "";
    int turnCount = 0;
    int alternator = 0; //used to alternate between white and black players
    int error = 0;
    bool check = false;
    int checkCount = 0;
    int wCheck = 0; //counters used for players individually, used for castleing
    int bCheck = 0;
    bool wKingMv = false; //used to tell if these pieces have moved
    bool wLeftRookeMv = false;
    bool wRightRookeMv = false;
    bool bKingMv = false;
    bool bLeftRookeMv = false;
    bool bRightRookeMv = false;
    bool checkMate = false;
    int stRow = 0;
    int stCol = 0;
    int edRow = 0;
    int edCol = 0;
    std::string tempSt = "";
    std::string tempEd = "";
    int ldSv = 0;
    
    turnCount = 1;
    boardInit(board);
    
    std::cout << "In this version of chess (all other rules are normal rules) if you get put in check, you get three chances to get out of check." << std::endl;
    std::cout << "If you're attempt to get out of check fails, the move is reverted and you have one less attempt left. Use up all three attempts, and you're in check mate." << std::endl;
    
    std::cout << "Enter 0 to start a new game, 1 to load the saved game, or 2 to quit: ";
    std::cin >> ldSv;

    if(ldSv == 1)
        ldSv = load(alternator, checkCount, turnCount, wCheck, bCheck, wKingMv, wLeftRookeMv, wRightRookeMv, bKingMv, bLeftRookeMv, bRightRookeMv, board);
    
    if(ldSv !=2) //checks if user decided to close the program
    {
    std::cout << std::endl << "Entering 100 for both the beginning and ending row and column will save and quit the game." << std::endl;
    std::cout << "Entering -100 for both the beginning and ending row and column will quit the game without saving." << std::endl;
        while(checkMate == false) //checks if check mate has been reached before starting the next player's turn
        {
            display(board);
	    ldSv = -1;
            if(alternator == 0)
                player = "W";
            else
                player = "B";
            do //check counter loop
            {
                do //outer input error loop
                {
                    do //inner input error loop
                    {
                        error = 0;
                        if(player == "W")
                            std::cout << "White's turn #" << turnCount << "." << std::endl;
                        if(player == "B")
                            std::cout << "Black's turn #" << turnCount << "." << std::endl;
                        std::cout << "Enter the row and column of the piece to move, seperated by a space: ";
                        std::cin >> stRow;
                        std::cin >> stCol;
                        std::cout << "Enter the row and column of the destination, seperated by a space: ";
                        std::cin >> edRow;
                        std::cin >> edCol;
                        
                        if(stRow == 100 && stCol == 100 && edRow == 100 && edCol == 100)
                            ldSv = save(alternator, checkCount, turnCount, wCheck, bCheck, wKingMv, wLeftRookeMv, wRightRookeMv, bKingMv, bLeftRookeMv, bRightRookeMv, board);
                        
                        if(stRow == -100 && stCol == -100 && edRow == -100 && edCol == -100)
                            ldSv = 2;
                        
                        if(ldSv == 0 || ldSv == 2) //one of a series of breaks to exit the game
                            break;
                        
                        if(stRow > 7 || stRow < 0 || stCol > 7 || stCol < 0 || edRow > 7 || edRow < 0 || edCol > 7 || edCol < 0) //checks if user's input is over 7 or below 0
                        {
                            std::cout << std::endl << "Please enter valid values (integers between the values of 0 and 7) for the row and column numbers requested." << std::endl;
                            std::cout << "If you're trying to save and quit, enter 100 for all of the requested values. Entering -100 will quit without saving." << std::endl << std::endl;
                            error = -1;
                        }
                    } while(error == -1); //end inner input error loop
                    
                    if(ldSv == 0 || ldSv == 2) //one of a series of breaks to exit the game
                        break;
        
                    tempSt = board[stRow][stCol];
                    tempEd = board[edRow][edCol];
        
                    error = move(stRow, stCol, edRow, edCol, wCheck, bCheck, wKingMv, wLeftRookeMv, wRightRookeMv, bKingMv, bLeftRookeMv, bRightRookeMv, board); //perform the move
        
                    if(error == -1 || std::string(board[edRow][edCol]).find(player) == -1) //checks if player attempted to move the opponent's piece or move was invalid
                    {
                        board[stRow][stCol] = tempSt; //reverts the move
                        board[edRow][edCol] = tempEd;
                        std::cout << std::endl << "Your move was invalid, please try again with a different move." << std::endl << std::endl;
                    }
                } while(error == -1 || tempSt == board[stRow][stCol]); //end outer input error loop
                
                if(ldSv == 0 || ldSv == 2) //one of a series of breaks to exit the game
                    break;
            
                check = kingCheck(player, checkCount, wCheck, bCheck, board);
                if(check == true) //check if block
                {
                    checkCount += 1;
                    board[stRow][stCol] = tempSt; //reverts the move
                    board[edRow][edCol] = tempEd;
                    std::cout << std::endl << "You are in check, you're previous move was undone. Get out of check to proceed to the next turn." << std::endl << std::endl;
                    
                    //remove created en passant marks--------------------------------------------------
                    if(player == "W")
                    {
                        for(int row = 0; row < 8; row += 1)
                        {
                            for(int col = 0; col < 8; col += 1)
                            {
                                if(board[row][col] == "!w")
                                    board[row][col] = "##";
                            }
                        }
                    }
                    
                    if(player == "B")
                    {
                        for(int row = 0; row < 8; row += 1)
                        {
                            for(int col = 0; col < 8; col += 1)
                            {
                                if(board[row][col] == "!b")
                                    board[row][col] = "##";
                            }
                        }
                    } //end remove en passant marks----------------------------------------------------
                }
                
                else
                {
                    if(player == "W") //remove opponent's en passant marks-----------------------------
                    {
                        for(int row = 0; row < 8; row += 1)
                        {
                            for(int col = 0; col < 8; col += 1)
                            {
                                if(board[row][col] == "!b")
                                    board[row][col] = "##";
                            }
                        }
                    }
                    
                    if(player == "B")
                    {
                        for(int row = 0; row < 8; row += 1)
                        {
                            for(int col = 0; col < 8; col += 1)
                            {
                                if(board[row][col] == "!w")
                                    board[row][col] = "##";
                            }
                        }
                    } //end remove opponent's en passant marks-----------------------------------------
                    
                    checkCount = 0;
                } //end check if block
                
                if(checkCount == 3)
                    checkMate = true;
            } while(checkCount > 0 && checkCount < 3); //end check counter loop
            
            if(ldSv == 0 || ldSv == 2) //last of a series of breaks to exit the game
                break;
            
            if(board[edRow][edCol] == "Wr" && stRow == 7) //moved status of king and rooke pieces------
            {
                if(stCol == 0)
                    wLeftRookeMv = true;
                if(stCol == 7)
                    wRightRookeMv = true;
            }
            if(board[edRow][edCol] == "Br" && stRow == 0)
            {
                if(stCol == 0)
                    bLeftRookeMv = true;
                if(stCol == 7)
                    bRightRookeMv = true;
            }
            if(board[edRow][edCol] == "WK")
                wKingMv = true;
            if(board[edRow][edCol] == "BK")
                bKingMv = true; //end moved status of king and rooke pieces----------------------------
            
            if(checkMate == false)
            {
                alternator += 1; //update alternator variable
            
                if(player == "B") //update the turn counter and reset alternator variable
                {
                    turnCount += 1;
                    alternator = 0;
                }
            }
        } //end checkMate loop
        
        if(checkMate == true)
        {
            std::cout << "Check Mate, ";
            if(player == "W")
                std::cout << "Black Wins." << std::endl;
            if(player == "B")
                std::cout << "White Wins." << std::endl;
        }
    }
    return 0;
} //end main

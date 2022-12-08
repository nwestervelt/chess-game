# Let's Play Chess

A chess game written by me and CalebStadler for our Washburn University Capstone Project.

## Features

 - Local Multiplayer
 - Online Multiplayer (Peer-to-Peer)
 - Captured Piece Relative Values
 - Move History in Algebraic Notation
 - Standard Chess Rules Apply (Stalemate Not Implemented)

## Compiling From Source

Install a JDK to use for compiling the source code. OpenJDK 17 was used during development, but most versions should work.

Clone this repository, then compile the MainFrame class either from an IDE or from the commandline.  
This will compile all the classes in the program necessary for running it.

Commandline:

    git clone https://github.com/nwestervelt/chess-game
    cd chess-game
    javac MainFrame.java

After all the classes have compiled, run the MainFrame class to open the program.

Commandline:

    java MainFrame

## Using A Released Version

Install a JRE or JDK version 8 or higher.

Download a release from the "Releases" section of this [repository](https://github.com/nwestervelt/chess-game),
 seen on the right side of the page.

After the download has completed, run the JAR file the same way you would any other program.  
Depending on your platform, you may need to modify the permissions on the file to make it executable.

## Information and Image Sources

- Chess Piece Images From [WikiMedia](https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent)
- Move Notation Described On [iChess.net](https://www.ichess.net/blog/chess-notation)
- Rules Described On [Chess.com](https://www.chess.com/learn-how-to-play-chess)
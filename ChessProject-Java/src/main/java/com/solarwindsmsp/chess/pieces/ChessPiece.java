package com.solarwindsmsp.chess.pieces;

import com.solarwindsmsp.chess.ChessBoard;
import com.solarwindsmsp.chess.MovementType;
import com.solarwindsmsp.chess.PieceColor;

public interface ChessPiece {
    void setChessBoard(ChessBoard.Reference chessBoardReference);
    void setX(int i);
    void setY(int i);
    int getX();
    int getY();
    PieceColor getPieceColor();
    Integer GetMaxInstances();
    boolean IsAt(int x, int y);
    boolean IsMoveLegal(MovementType movementType, int x, int y);
    boolean Move(MovementType movementType, int x, int y);
}

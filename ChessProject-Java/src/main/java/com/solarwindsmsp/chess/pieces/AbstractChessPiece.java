package com.solarwindsmsp.chess.pieces;

import com.solarwindsmsp.chess.ChessBoard;
import com.solarwindsmsp.chess.MovementType;
import com.solarwindsmsp.chess.PieceColor;

public abstract class AbstractChessPiece implements ChessPiece {
    private PieceColor pieceColor;
    private ChessBoard.Reference chessBoard;
    private int x = -1;
    private int y = -1;

    public AbstractChessPiece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    ChessBoard.Reference getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard.Reference chessBoard) {
        this.chessBoard = chessBoard;
    }

    public int getX() {
        return x;
    }

    public void setX(int value) {
        this.x = value;
    }

    public int getY() {
        return y;
    }

    public void setY(int value) {
        this.y = value;
    }

    public PieceColor getPieceColor() {
        return this.pieceColor;
    }

    public boolean Move(MovementType movementType, int x, int y) {
        return getChessBoard().Move(this, movementType, x, y);
    }

    public abstract boolean IsMoveLegal(MovementType movementType, int newX, int newY);

    public abstract Integer GetMaxInstances();

    @Override
    public String toString() {
        return CurrentPositionAsString();
    }

    protected String CurrentPositionAsString() {
        String eol = System.lineSeparator();
        return String.format("Current X: {1}{0}Current Y: {2}{0}Piece Color: {3}", eol, x, y, pieceColor);
    }

    public boolean IsAt(int x, int y) {
        return x == getX() && y == getY();
    }
}

package com.solarwindsmsp.chess.pieces;

import com.solarwindsmsp.chess.ChessBoard;
import com.solarwindsmsp.chess.MovementType;
import com.solarwindsmsp.chess.PieceColor;

public class Pawn extends AbstractChessPiece {
    private static final Integer MAX_INSTANCES = ChessBoard.MAX_BOARD_WIDTH;

    private boolean initialMove;

    public Pawn(PieceColor pieceColor) {
        this(pieceColor, true);
    }

    public Pawn(PieceColor pieceColor, boolean initialMove) {
        super(pieceColor);
        this.initialMove = initialMove;
    }

    @Override
    public boolean Move(MovementType movementType, int x, int y) {
        if (super.Move(movementType, x, y)) {
            initialMove = false;
            return true;
        }
        return false;
    }

    public boolean IsMoveLegal(MovementType movementType, int newX, int newY) {
        // Pawns can only move towards the opposite side
        PieceColor positiveDirectionColor = getChessBoard().GetPositiveDirectionColor();
        int moveDirection = positiveDirectionColor.equals(getPieceColor()) ? 1 : -1;

        // Calculate Y displacement
        int yDispacement = (newY - getY()) * moveDirection;

        // Pawn behaviour depends on movement type
        switch (movementType) {
            case MOVE:
                // Pawns can only move in a straight line unless capturing
                if (newX != getX())
                    break;

                // Pawns can only move forward
                if (yDispacement <= 0)
                    break;

                // Pawns can only move 1 space, or 2 on their first move
                int maxYDisplacement = initialMove ? 2 : 1;
                return yDispacement <= maxYDisplacement;
            case CAPTURE:
                // Pawns can only capture one space diagonally left or right
                int xDisplacement = newX - getX();
                if (xDisplacement != 1 && xDisplacement != -1)
                    break;

                return yDispacement == 1;
        }
        return false;
    }

    public Integer GetMaxInstances() {
        return MAX_INSTANCES;
    }
}

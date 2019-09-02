package com.solarwindsmsp.chess;

import com.solarwindsmsp.chess.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoard {
    // This class is the reference given to any added piece to provide
    // the chessboard interface required by piece (otherwise ChessBoard.Move
    // would have to be public)
    public class Reference {
        public PieceColor GetPositiveDirectionColor() {
            return ChessBoard.this.GetPositiveDirectionColor();
        }

        public boolean Move(ChessPiece piece, MovementType movementType, int newX, int newY) {
            return ChessBoard.this.Move(piece, movementType, newX, newY);
        }
    }

    public static int MAX_BOARD_WIDTH = 8;
    public static int MAX_BOARD_HEIGHT = 8;

    private PieceColor positiveDirectionColor;

    private List<ChessPiece> pieces = new ArrayList<ChessPiece>();
    private Map<PieceColor, List<ChessPiece>> capturedPieces = new HashMap<PieceColor, List<ChessPiece>>() {{
        put(PieceColor.WHITE, new ArrayList<ChessPiece>());
        put(PieceColor.BLACK, new ArrayList<ChessPiece>());
    }};

    public ChessBoard() {
        // By default, white pieces move "up" the board (+ve Y), and black down (-ve Y)
        this(PieceColor.WHITE);
    }

    public ChessBoard(PieceColor positiveDirectionColor) {
        this.positiveDirectionColor = positiveDirectionColor;
    }

    public PieceColor GetPositiveDirectionColor() {
        return positiveDirectionColor;
    }

    public boolean Add(ChessPiece piece, int x, int y) {
        // Can't add the same piece twice
        if (Contains(piece))
            return false;

        // Must not exceed piece limit
        Integer pieceLimit = piece.GetMaxInstances();
        if (pieceLimit != null && GetPieceCount(piece.getClass(), piece.getPieceColor()) >= pieceLimit)
            return false;

        // New position must be valid
        if (!IsLegalBoardPosition(x, y))
            return false;

        // Must be unoccupied
        ChessPiece targetPiece = GetPieceAt(x, y);
        if (!IsTargetOccupationLegal(MovementType.MOVE, piece, targetPiece))
            return false;

        // Perform add
        piece.setChessBoard(new Reference());
        piece.setX(x);
        piece.setY(y);
        pieces.add(piece);
        return true;
    }

    private boolean Move(ChessPiece piece, MovementType movementType, int newX, int newY) {
        // Can't move a piece that's not on the board
        if (!Contains(piece))
            return false;

        // New position must be valid
        if (!IsLegalBoardPosition(newX, newY))
            return false;

        // Check target space occupation is valid
        ChessPiece targetPiece = GetPieceAt(newX, newY);
        if (!IsTargetOccupationLegal(movementType, piece, targetPiece))
            return false;

        // Apply piece specific rules
        if (!piece.IsMoveLegal(movementType, newX, newY))
            return false;

        // Perform move
        if (targetPiece != null) {
            Remove(targetPiece);
            capturedPieces.get(piece.getPieceColor()).add(targetPiece);
        }
        piece.setX(newX);
        piece.setY(newY);
        return true;
    }

    public boolean Remove(ChessPiece piece) {
        // Can't remove a piece that's not on the board
        if (!Contains(piece))
            return false;

        piece.setX(-1);
        piece.setY(-1);
        return pieces.remove(piece);
    }

    public boolean Contains(ChessPiece piece) {
        return pieces.contains(piece);
    }

    public int GetPieceCount(Class<? extends ChessPiece> aClass, PieceColor pieceColor) {
        int count = 0;
        for (ChessPiece piece : pieces) {
            if (aClass.isInstance(piece) && piece.getPieceColor().equals(pieceColor))
                count++;
        }
        return count;
    }

    public ChessPiece GetPieceAt(int x, int y) {
        for (ChessPiece piece : pieces) {
            if (piece.IsAt(x, y))
                return piece;
        }
        return null;
    }

    private boolean IsTargetOccupationLegal(MovementType movementType, ChessPiece piece, ChessPiece targetPiece) {
        switch (movementType) {
            case MOVE:
                // MOVE must be to an empty space
                if (targetPiece != null)
                    return false;
                break;
            case CAPTURE:
                // Capture must be to an occupied space
                if (targetPiece == null)
                    return false;

                // Capture must be of opposite color
                if (targetPiece.getPieceColor().equals(piece.getPieceColor()))
                    return false;
                break;
        }
        return true;
    }

    public boolean IsLegalBoardPosition(int x, int y) {
        boolean xOkay = x >= 0 && x < MAX_BOARD_WIDTH;
        boolean yOkay = y >= 0 && y < MAX_BOARD_HEIGHT;
        return xOkay && yOkay;
    }
}

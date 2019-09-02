package com.solarwindsmsp.chess;

import com.solarwindsmsp.chess.pieces.AbstractChessPiece;
import com.solarwindsmsp.chess.pieces.ChessPiece;
import com.solarwindsmsp.chess.pieces.Pawn;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PawnTest {

    private ChessBoard chessBoard;
    private AbstractChessPiece testSubject;

    @Before
    public void setUp() {
        this.chessBoard = new ChessBoard();
        this.testSubject = new Pawn(PieceColor.BLACK);
    }

    private void assertPosition(ChessPiece piece, int x, int y) {
        assertEquals(x, piece.getX());
        assertEquals(y, piece.getY());
    }

    @Test
    public void testChessBoard_Add_Sets_XCoordinate() {
        assertTrue(chessBoard.Add(testSubject, 6, 3));
        assertEquals(6, testSubject.getX());
    }

    @Test
    public void testChessBoard_Add_Sets_YCoordinate() {
        assertTrue(chessBoard.Add(testSubject, 6, 3));
        assertEquals(3, testSubject.getY());
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Right_DoesNotMove() {
        chessBoard.Add(testSubject, 6, 3);
        assertFalse(testSubject.Move(MovementType.MOVE, 7, 3));
        assertPosition(testSubject,6, 3);
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Left_DoesNotMove() {
        chessBoard.Add(testSubject, 6, 3);
        assertFalse(testSubject.Move(MovementType.MOVE, 4, 3));
        assertEquals(6, testSubject.getX());
        assertEquals(3, testSubject.getY());
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Backward_DoesNotMove() {
        chessBoard.Add(testSubject, 6, 3);
        assertFalse(testSubject.Move(MovementType.MOVE, 6, 3));
        assertPosition(testSubject,6, 3);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward_UpdatesCoordinates() {
        chessBoard.Add(testSubject, 6, 3);
        assertTrue(testSubject.Move(MovementType.MOVE, 6, 2));
        assertPosition(testSubject,6, 2);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward2_FirstMove_UpdatesCoordinates() {
        chessBoard.Add(testSubject, 6, 3);
        assertTrue(testSubject.Move(MovementType.MOVE, 6, 1));
        assertPosition(testSubject,6, 1);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward2_SecondMove_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.BLACK);
        chessBoard.Add(testSubject, 6, 3);
        assertTrue(testSubject.Move(MovementType.MOVE, 6, 2));
        assertFalse(testSubject.Move(MovementType.MOVE, 6, 0));
        assertPosition(testSubject,6, 2);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward2_SecondMove_Init_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.BLACK, false);
        chessBoard.Add(testSubject, 6, 3);
        assertFalse(testSubject.Move(MovementType.MOVE, 6, 1));
        assertPosition(testSubject,6, 3);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward2_SecondMove_Init_White_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.WHITE, false);
        chessBoard.Add(testSubject, 6, 3);
        assertFalse(testSubject.Move(MovementType.MOVE, 6, 5));
        assertPosition(testSubject,6, 3);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward2_SecondMove_White_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        assertTrue(testSubject.Move(MovementType.MOVE, 6, 4));
        assertFalse(testSubject.Move(MovementType.MOVE, 6, 6));
        assertPosition(testSubject,6, 4);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward2_FirstMove_White_UpdatesCoordinates() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        assertTrue(testSubject.Move(MovementType.MOVE, 6, 5));
        assertPosition(testSubject,6, 5);
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Backward_White_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        assertFalse(testSubject.Move(MovementType.MOVE, 6, 2));
        assertPosition(testSubject, 6, 3);
    }

    @Test
    public void testPawn_Move_LegalCoordinates_Forward_White_UpdatesCoordinates() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        assertTrue(testSubject.Move(MovementType.MOVE, 6, 4));
        assertPosition(testSubject, 6, 4);
    }

    @Test
    public void testPawn_Move_InvalidCoordinates_DoesNotMove() {
        chessBoard.Add(testSubject, 7, 3);
        assertFalse(testSubject.Move(MovementType.MOVE, 8, 3));
        assertPosition(testSubject,7, 3);
    }

    @Test
    public void testPawn_Move_Removed_Piece() {
        chessBoard.Add(testSubject, 7, 3);
        chessBoard.Remove(testSubject);
        assertFalse(testSubject.Move(MovementType.MOVE, 8, 3));
    }

    @Test
    public void testPawn_Move_SpaceOccupied_DoesNotMove() {
        Pawn pawn1 = new Pawn(PieceColor.WHITE);
        Pawn pawn2 = new Pawn(PieceColor.WHITE);
        assertTrue(chessBoard.Add(pawn1, 0, 0));
        assertTrue(chessBoard.Add(pawn2, 0, 1));
        assertFalse(pawn1.Move(MovementType.MOVE, 0, 1));
    }

    @Test
    public void testPawn_Capture_LegalCoordinates_DiagonalRight_UpdatesCoordinates() {
        Pawn targetPiece = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 7,2);
        assertTrue(testSubject.Move(MovementType.CAPTURE, 7, 2));
        assertPosition(testSubject,7, 2);
        assertFalse(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_LegalCoordinates_DiagonalLeft_UpdatesCoordinates() {
        Pawn targetPiece = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 5,2);
        assertTrue(testSubject.Move(MovementType.CAPTURE, 5, 2));
        assertPosition(testSubject,5, 2);
        assertFalse(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_IllegalCoordinates_Backward_DoesNotMove() {
        Pawn targetPiece = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 7,4);
        assertFalse(testSubject.Move(MovementType.CAPTURE, 7, 4));
        assertPosition(testSubject,6, 3);
        assertTrue(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_IllegalCoordinates_Forward_DoesNotMove() {
        Pawn targetPiece = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 6,4);
        assertFalse(testSubject.Move(MovementType.CAPTURE, 6, 4));
        assertPosition(testSubject,6, 3);
        assertTrue(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_IllegalCoordinates_Forward_White_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        Pawn targetPiece = new Pawn(PieceColor.BLACK);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 6,2);
        assertFalse(testSubject.Move(MovementType.CAPTURE, 6, 2));
        assertPosition(testSubject,6, 3);
        assertTrue(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_LegalCoordinates_DiagonalRight_White_UpdatesCoordinates() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        Pawn targetPiece = new Pawn(PieceColor.BLACK);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 7,4);
        assertTrue(testSubject.Move(MovementType.CAPTURE, 7, 4));
        assertPosition(testSubject,7, 4);
        assertFalse(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_LegalCoordinates_DiagonalLeft_White_UpdatesCoordinates() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        Pawn targetPiece = new Pawn(PieceColor.BLACK);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 5,4);
        assertTrue(testSubject.Move(MovementType.CAPTURE, 5, 4));
        assertPosition(testSubject,5, 4);
        assertFalse(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_IllegalCoordinates_Backward_White_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        Pawn targetPiece = new Pawn(PieceColor.BLACK);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 7,2);
        assertFalse(testSubject.Move(MovementType.CAPTURE, 7, 2));
        assertPosition(testSubject,6, 3);
        assertTrue(chessBoard.Contains(targetPiece));
    }

    @Test
    public void testPawn_Capture_UnoccupiedCoordinates_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        assertFalse(testSubject.Move(MovementType.CAPTURE, 7, 2));
        assertPosition(testSubject,6, 3);
    }

    @Test
    public void testPawn_Capture_SameColor_DoesNotMove() {
        Pawn testSubject = new Pawn(PieceColor.WHITE);
        Pawn targetPiece = new Pawn(PieceColor.WHITE);
        chessBoard.Add(testSubject, 6, 3);
        chessBoard.Add(targetPiece, 7,2);
        assertFalse(testSubject.Move(MovementType.CAPTURE, 7, 2));
        assertPosition(testSubject,6, 3);
        assertTrue(chessBoard.Contains(targetPiece));
    }
}

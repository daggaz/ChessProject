package com.solarwindsmsp.chess;

import com.solarwindsmsp.chess.pieces.Pawn;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChessBoardTest extends TestCase {

    private ChessBoard testSubject;

    @Before
    public void setUp() throws Exception {
        testSubject = new ChessBoard();
    }

    private void assertNoPosition(Pawn pawn) {
        assertPosition(pawn, -1, -1);
    }

    private void assertPosition(Pawn pawn, int x, int y) {
        assertEquals(x, pawn.getX());
        assertEquals(y, pawn.getY());
    }

    @Test
    public void testHas_MaxBoardWidth_of_8() {
        assertEquals(8, ChessBoard.MAX_BOARD_WIDTH);
    }

    @Test
    public void testHas_MaxBoardHeight_of_8() {
        assertEquals(8, ChessBoard.MAX_BOARD_HEIGHT);
    }

    @Test
    public void testIsLegalBoardPosition_True_X_equals_0_Y_equals_0() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(0, 0);
        assertTrue(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_True_X_equals_5_Y_equals_5() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(5, 5);
        Assert.assertTrue(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_11_Y_equals_5() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(11, 5);
        assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_0_Y_equals_9() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(0, 9);
        assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_11_Y_equals_0() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(11, 0);
        assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_For_Negative_Y_Values() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(5, -1);
        Assert.assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_True_X_equals_7_Y_equals_7() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(7, 7);
        Assert.assertTrue(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_7_Y_equals_8() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(7, 8);
        Assert.assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_8_Y_equals_7() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(8, 7);
        Assert.assertFalse(isValidPosition);
    }

    @Test
    public void testIsLegalBoardPosition_False_X_equals_8_Y_equals_8() {
        boolean isValidPosition = testSubject.IsLegalBoardPosition(8, 8);
        Assert.assertFalse(isValidPosition);
    }

    @Test
    public void Avoids_Duplicate_Positioning() {
        Pawn firstPawn = new Pawn(PieceColor.BLACK);
        Pawn secondPawn = new Pawn(PieceColor.BLACK);
        assertTrue(testSubject.Add(firstPawn, 6, 3));
        assertFalse(testSubject.Add(secondPawn, 6, 3));
        assertPosition(firstPawn, 6, 3);
        assertNoPosition(secondPawn);
    }

    @Test
    public void testLimits_The_Number_Of_Pawns() {
        // cannot add more than one row's worth of pawns
        for (int i = 0; i < 10; i++) {
            Pawn pawn = new Pawn(PieceColor.BLACK);
            int row = i / ChessBoard.MAX_BOARD_WIDTH;
            if (row < 1) {
                assertTrue(testSubject.Add(pawn, 6 + row, i % ChessBoard.MAX_BOARD_WIDTH));
                assertPosition(pawn, 6 + row, i % ChessBoard.MAX_BOARD_WIDTH);
            } else {
                assertFalse(testSubject.Add(pawn, 6 + row, i % ChessBoard.MAX_BOARD_WIDTH));
                assertNoPosition(pawn);
            }
        }

        // can still add a pawn of a different color
        Pawn pawn = new Pawn(PieceColor.WHITE);
        assertTrue(testSubject.Add(pawn, 0, 0));
    }

    @Test
    public void testAdd_Existing_Piece_Invalid() {
        Pawn pawn = new Pawn(PieceColor.WHITE);
        assertTrue(testSubject.Add(pawn, 0, 0));
        assertFalse(testSubject.Add(pawn, 0, 1));
    }

    @Test
    public void testAdd_InvalidCoordinates() {
        Pawn pawn = new Pawn(PieceColor.WHITE);
        assertFalse(testSubject.Add(pawn, 8, 8));
        assertFalse(testSubject.Add(pawn, -1, -1));
        assertFalse(testSubject.Add(pawn, -1, 0));
        assertFalse(testSubject.Add(pawn, 0, -1));
        assertFalse(testSubject.Add(pawn, 7, 8));
        assertFalse(testSubject.Add(pawn, 8, 7));
    }

    @Test
    public void testAdd_Space_Occupied_Invalid() {
        Pawn pawn1 = new Pawn(PieceColor.WHITE);
        Pawn pawn2 = new Pawn(PieceColor.WHITE);
        assertTrue(testSubject.Add(pawn1, 0, 0));
        assertFalse(testSubject.Add(pawn2, 0, 0));
        assertFalse(testSubject.Contains(pawn2));
    }

    @Test
    public void testRemove() {
        Pawn pawn = new Pawn(PieceColor.WHITE);
        assertTrue(testSubject.Add(pawn, 0, 0));
        assertTrue(testSubject.Remove(pawn));
        assertFalse(testSubject.Contains(pawn));
    }

    @Test
    public void testRemove_NotAdded() {
        Pawn pawn = new Pawn(PieceColor.WHITE);
        assertFalse(testSubject.Remove(pawn));
    }
}

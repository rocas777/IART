import UI.View;
import board.Board;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void Test1(){
        String board =
                        "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        Board toSolve = new Board(6,6, h , v);
        toSolve.readBoard(board);
        Board solution = Solver.solve(toSolve);


        Board original = new Board(6,6, h , v);
        original.readBoard(board);
        original.setSol("1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");

        assertEquals(original,solution);
    }

    @Test
    void Test2(){
        String board =
                "1 1 2 2 5 5 7 7 7 7 7 8 8 8 8;" +
                        "3 1 2 2 4 5 5 5 5 9 10 11 12 12 13;" +
                        "3 3 3 2 4 6 6 6 6 9 10 11 11 12 13;" +
                        "2 2 2 2 2 14 14 6 6 9 10 10 11 11 13;" +
                        "15 16 16 16 16 16 14 19 19 18 18 20 13 13 13;" +
                        "15 16 16 17 17 16 19 19 19 19 19 20 21 22 22;" +
                        "15 16 17 17 17 17 19 23 26 26 25 21 21 27 27;" +
                        "28 28 28 24 24 23 23 23 23 26 26 27 27 27 27;" +
                        "29 24 24 24 23 23 23 26 26 26 26 37 27 38 38;" +
                        "29 29 30 33 34 34 34 26 26 36 36 37 27 27 38;" +
                        "31 30 30 33 33 34 35 35 36 36 37 37 27 27 38 ;" +
                        "31 30 45 45 45 34 35 44 37 37 37 37 40 38 38;" +
                        "31 30 45 30 45 34 44 44 41 41 41 41 40 38 38;" +
                        "32 30 30 30 45 45 45 44 41 42 41 40 40 38 38;" +
                        "32 45 45 45 45 45 43 43 41 42 40 40 39 39 38;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        Board toSolve = new Board(15,15, h , v);
        toSolve.readBoard(board);
        Board solution = Solver.solve(toSolve);

        new View(1000,1000,toSolve);
        while (1==1){}
    }
}
//state.history.size() + state.manDist = A* Heuristic

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class astar {

    @SuppressWarnings("unused")
    public static void main(String args[]) throws FileNotFoundException, IOException, didntWorkException {

        String line = null;
        int[] test = {3, 3, 1, 2, 0, 5, 7, 3, 4, 8, 6};
        int[] testCoords = {0, 2};
        int[] possMoves = new int[4];
        int currManDist = -1;

        try {

            BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
            StringBuilder sb = new StringBuilder();
            FileWriter myWriter = new FileWriter(new File(args[1]));

            while((line = in.readLine()) != null) {
                sb.append(line);
            }

            String contents = sb.toString();
            in.close();
            String[] strArr = contents.split(" ");
            int[] intArr = new int[strArr.length];

            if (strArr.length <= 2) {
                throw new didntWorkException("Uh oh! The input was too short!");
            }

            for (int z = 0; z < strArr.length; z++) {
                intArr[z] = Integer.parseInt(strArr[z]);
            }

            int[] board = intArr;
            int[] possibleMoves = manhattan.calcPossibleMoves(intArr);
            int manDist = manhattan.calculateManhattan(intArr);
            State initialState = new State(board, possibleMoves, manDist, true);

            long startTime = System.nanoTime();

            LinkedList<Integer> solution = manhattan.solve(initialState, new PriorityQueue<State>(), true);
            
            long stopTime = System.nanoTime();
            System.out.println(stopTime - startTime);

            for (int r = 0; r < solution.size(); r++) {
                myWriter.write(Integer.toString(solution.get(r)));
                myWriter.write(" ");
            }
            myWriter.close();

            /*
            State testState = move(board, 3);
            for (int b = 0; b < board.length; b++) {
                System.out.println(board[b]);
            }
            */

        } catch (didntWorkException e) {

            throw new didntWorkException("Ooopsie! There was somethnig wrong with the input! Is it formatted correctly?");

        }
    }
}
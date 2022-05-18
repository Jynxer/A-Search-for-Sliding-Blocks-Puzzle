import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class idbnb {

    public static LinkedList<Integer> solveBNBDF(State state, LinkedList<State> search, int bound) throws didntWorkException {

        LinkedList<Integer> temp = new LinkedList<Integer>();
        LinkedList<State> visited = new LinkedList<State>();
        State currState = state;
        State bestState = state;
        boolean solved = false;
        int[] possMoves = new int[4];

        if (state.manDist == 0) {
            //System.out.println("Puzzle already solved!");
            temp.add(1);
            return(temp);
        }

        search.add(state);

        int counter = 0;

        while (!search.isEmpty()) {

            if (counter > 100000) {
                temp.add(0);
                return(temp);
            }

            counter++;

            currState = search.removeFirst();
            //System.out.println(search.size());
            
            //if (bound == 24) {
            //    System.out.println(currState.manDist);
            //}

            //System.out.println(currState.manDist);
            //System.out.println(currState.history);

            if (currState.manDist == 0) {
                bestState = currState;
                solved = true;
            } else {

                possMoves = manhattan.calcPossibleMoves(currState.board);

                for (int i = 0; i < 4; i++) {
                    if (possMoves[i] != -1) {
                        State newState = manhattan.move(currState, possMoves[i], true);
                        if (newState != null) {
                            if ((currState.history.size() + currState.manDist) < bound) {
                                if (!visited.contains(newState)) {
                                    visited.add(newState);
                                    search.addFirst(newState);
                                }
                            }
                        }
                    }
                }

            }

        }

        if (solved) {
            bestState.history.addFirst(1);
            return(bestState.history);
        } else {
            temp.add(-1);
            return(temp);
        }

    }

    public static LinkedList<Integer> solveID(State state) throws didntWorkException {

        LinkedList<Integer> temp = new LinkedList<Integer>();
        int depth = state.manDist;
        boolean solutionFound = false;

        while (!solutionFound) {

            if (depth > 1000) {
                //System.out.println("Ok wtf?");
                break;
            }

            //System.out.println(depth);

            temp = solveBNBDF(state, new LinkedList<State>(), depth);

            switch (temp.get(0)) {
                case -1:
                    depth = depth + 2;
                    break;
                case 1:
                    solutionFound = true;
                    break;
                case 0:
                    //System.out.println("Something was impossible???");
                    solutionFound = true;
                    break;
            }

        }

        return(temp);

    }

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

            LinkedList<Integer> solution = solveID(initialState);

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
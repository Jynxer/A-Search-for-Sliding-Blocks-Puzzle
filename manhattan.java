import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class manhattan {

    enum xPosition {
        LEFT,
        CENTRE,
        RIGHT
    }

    enum yPosition {
        TOP,
        CENTRE,
        BOTTOM
    }

    // Gets goal co-ordinates of a block given the number of puzzle columns and the square number
    // Input: (int) puzzle rows, (int) puzzle columms, (int) block number
    // Output: (str) row, column starting at 0
    public static String getGoalCoordinates(int _m, int _x) {

        int valA = Math.floorDiv(_x - 1, _m);
        int valB = (_x - 1) % _m;
        String strValA = Integer.toString(valA);
        String strValB = Integer.toString(valB);
        String tuple = strValA + "," + strValB;
        return(tuple);

    }

    // Gets co-ordinates of a block given the puzzle input and the square number
    // Input: (int[]) puzzle input as integer array, (int) square number
    // Output: (str) row, column starting at 0
    public static String getCoordinates(int[] _arr, int _x) throws didntWorkException {
        int index = -1;
        int m = _arr[1];

        for (int i = 2; i < _arr.length; i++) {
            if (_arr[i] == _x) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new didntWorkException("Nooooo! _x wasn't found in the int array!");
        }

        index = index - 2;
        int temp = Math.floorDiv(index, m);
        int tempTwo = index % m;
        String strTemp = Integer.toString(temp);
        String strTempTwo = Integer.toString(tempTwo);
        String tuple = strTemp + "," + strTempTwo;
        return(tuple);

    }

    public static int calculateManhattan(int[] intArr) throws didntWorkException {

        String tupleOne;
        String tupleTwo;
        String[] strsOne = new String[2];
        String[] strsTwo = new String[2]; 
        Hashtable<String, String> dict = new Hashtable<String, String>();
        int manhattanDistance;
        String manhattanDistanceStr;
        String square;
        int n = intArr[0];
        int m = intArr[1];

        for (int i = 1; i < (n*m); i++) {
            tupleOne = getGoalCoordinates(m, i);
            tupleTwo = getCoordinates(intArr, i);
            strsOne = tupleOne.split(",");
            strsTwo = tupleTwo.split(",");
            manhattanDistance = Math.abs((Integer.parseInt(strsOne[0]) - Integer.parseInt(strsTwo[0]))) + (Math.abs(Integer.parseInt(strsOne[1]) - Integer.parseInt(strsTwo[1])));
            square = Integer.toString(i);
            manhattanDistanceStr = Integer.toString(manhattanDistance);
            dict.put(square, manhattanDistanceStr);
        }

        String strG;
        int totalManDist = 0;

        for (int g = 1; g < intArr.length - 2; g++) {
            strG = Integer.toString(g);
            totalManDist = totalManDist + Integer.parseInt(dict.get(strG));
        }

        return(totalManDist);

    }

    public static int coordsToIndex(int y, int x, int m) {

        int index = 2;
        index = index + (y * m);
        index = index + x;
        return(index);

    }

    // ------------------------------------------------------------------------------------------------------------------
    // USE COORDSTOINDEX()
    public static int[] getAdjacentSquares(int[] intArr, int[] coords, yPosition y, xPosition x) {

        int[] adjacent = {-1, -1, -1, -1}; // [UP, DOWN, LEFT, RIGHT]
        int m = intArr[1];

        if ((y == yPosition.CENTRE) && (x == xPosition.CENTRE)) {
            adjacent[0] = intArr[coordsToIndex(coords[0]-1, coords[1],  m)];//UP
            adjacent[1] = intArr[coordsToIndex(coords[0]+1, coords[1],  m)];//DOWN
            adjacent[2] = intArr[coordsToIndex(coords[0], coords[1]-1,  m)];//LEFT
            adjacent[3] = intArr[coordsToIndex(coords[0], coords[1]+1,  m)];//RIGHT
        } else if ((y == yPosition.TOP) && (x == xPosition.LEFT)) {
            adjacent[1] = intArr[coordsToIndex(coords[0]+1, coords[1],  m)];//DOWN
            adjacent[3] = intArr[coordsToIndex(coords[0], coords[1]+1,  m)];//RIGHT
        } else if ((y == yPosition.TOP) && (x == xPosition.CENTRE)) {
            adjacent[1] = intArr[coordsToIndex(coords[0]+1, coords[1],  m)];//DOWN
            adjacent[2] = intArr[coordsToIndex(coords[0], coords[1]-1,  m)];//LEFT
            adjacent[3] = intArr[coordsToIndex(coords[0], coords[1]+1,  m)];//RIGHT
        } else if ((y == yPosition.TOP) && (x == xPosition.RIGHT)) {
            adjacent[1] = intArr[coordsToIndex(coords[0]+1, coords[1],  m)];//DOWN
            adjacent[2] = intArr[coordsToIndex(coords[0], coords[1]-1,  m)];//LEFT
        } else if ((y == yPosition.CENTRE) && (x == xPosition.RIGHT)) {
            adjacent[0] = intArr[coordsToIndex(coords[0]-1, coords[1],  m)];//UP
            adjacent[1] = intArr[coordsToIndex(coords[0]+1, coords[1],  m)];//DOWN
            adjacent[2] = intArr[coordsToIndex(coords[0], coords[1]-1,  m)];//LEFT
        } else if ((y == yPosition.BOTTOM) && (x == xPosition.RIGHT)) {
            adjacent[0] = intArr[coordsToIndex(coords[0]-1, coords[1],  m)];//UP
            adjacent[2] = intArr[coordsToIndex(coords[0], coords[1]-1,  m)];//LEFT
        } else if ((y == yPosition.BOTTOM) && (x == xPosition.CENTRE)) {
            adjacent[0] = intArr[coordsToIndex(coords[0]-1, coords[1],  m)];//UP
            adjacent[2] = intArr[coordsToIndex(coords[0], coords[1]-1,  m)];//LEFT
            adjacent[3] = intArr[coordsToIndex(coords[0], coords[1]+1,  m)];//RIGHT
        } else if ((y == yPosition.BOTTOM) && (x == xPosition.LEFT)) {
            adjacent[0] = intArr[coordsToIndex(coords[0]-1, coords[1],  m)];//UP
            adjacent[3] = intArr[coordsToIndex(coords[0], coords[1]+1,  m)];//RIGHT
        } else if ((y == yPosition.CENTRE) && (x == xPosition.LEFT)) {
            adjacent[0] = intArr[coordsToIndex(coords[0]-1, coords[1],  m)];//UP
            adjacent[1] = intArr[coordsToIndex(coords[0]+1, coords[1],  m)];//DOWN
            adjacent[3] = intArr[coordsToIndex(coords[0], coords[1]+1,  m)];//RIGHT
        }

        return(adjacent);

    }

    public static int[] calcPossibleMoves(int[] intArr) throws didntWorkException {

        int[] possibleMoves = new int[4];
        int n = intArr[0];
        int m = intArr[1];
        yPosition y;
        xPosition x;
        String emptySquare;
        String[] temp = new String[2];
        int[] coords = new int[2];

        emptySquare = getCoordinates(intArr, 0);
        temp = emptySquare.split(",");

        for (int u = 0; u < 2; u++) {
            coords[u] = Integer.parseInt(temp[u]);
        }

        if (coords[0] == 0) {
            y = yPosition.TOP;
        } else if (coords[0] == (n - 1)) {
            y = yPosition.BOTTOM;
        } else if ((coords[0] > 0) && (coords[0] < (n - 1))) {
            y = yPosition.CENTRE;
        } else {
            throw new didntWorkException("Peaaak! Y coordinate of empty space out of bounds!");
        }

        if (coords[1] == 0) {
            x = xPosition.LEFT;
        } else if (coords[1] == (m - 1)) {
            x = xPosition.RIGHT;
        } else if ((coords[1] > 0) && (coords[1] < (m - 1))) {
            x = xPosition.CENTRE;
        } else {
            throw new didntWorkException("Daaamn! X coordinate of empty space out of bounds!");
        }

        possibleMoves = getAdjacentSquares(intArr, coords, y, x);
        return(possibleMoves);

    }

    public static State move(State currState, int squareToMove, boolean astar) throws didntWorkException {

        int[] newIntArr = currState.board.clone();
        int squareIndex = -1;
        int zeroIndex = -1;

        for (int i = 2; i < currState.board.length; i++) {
            if (squareToMove == currState.board[i]) {
                squareIndex = i;
                break;
            }
        }

        for (int j = 0; j < currState.board.length; j++) {
            if (currState.board[j] == 0) {
                zeroIndex = j;
            }
        }
        if (squareIndex == -1) {
            throw new didntWorkException("SH*T! Wtf is the square index?");
        }

        if (zeroIndex == -1) {
            throw new didntWorkException("How terrilby strange! zeroIndex wasn't found!");
        }

        newIntArr[zeroIndex] = newIntArr[squareIndex];
        newIntArr[squareIndex] = 0;

        int[] newPossMoves = calcPossibleMoves(newIntArr);
        int newManDist = calculateManhattan(newIntArr);
        State newState = new State(newIntArr, newPossMoves, newManDist, astar);
        for (int f = 0; f < currState.history.size(); f++) { //-----------------------USE ADD(0, OBJ) TO PUT SOLVED STATE IN AFTER!!!!!!!!!
            newState.history.add(currState.history.get(f));
        }
        newState.history.add(squareToMove);

        return(newState);
    }
    
    public static LinkedList<Integer> solve(State state, PriorityQueue<State> search, boolean astar) throws didntWorkException {

        int[] possMoves = new int[4];
        LinkedList<Integer> temp = new LinkedList<Integer>();

        if (state.manDist == 0) {
            //System.out.println("Puzzle already solved!");
            temp.add(1);
            return(temp);
        }

        search.add(state);

        int counter = 0;

        while (true) {

            if (counter > 1000000) {
                //System.out.println("UNSOLVABLE!");

                break;
            }

            counter++;
            
            if (search.isEmpty()) {
                temp.add(0);
                return(temp);
            }

            State currState = search.poll();
            int[] board = currState.board;
            possMoves = calcPossibleMoves(board);

            /*
            for (int u = 0; u < currState.board.length; u++) {
                System.out.println(currState.board[u]);
            }
            */

            //System.out.println("-------------------------------");
            //System.out.println(currState.manDist);
            //System.out.println("-------------------------------");

            for (int i = 0; i < 4; i++) {
                if (possMoves[i] != -1) {
                    State newState = move(currState, possMoves[i], astar);
                    if (newState != null) {
                        if (newState.manDist == 0) {
                            newState.history.addFirst(1);
                            return(newState.history);
                        }
                        search.add(newState);
                    }
                }
            }
            //System.out.println(counter);
        }

        temp.add(-1);
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
            int[] possibleMoves = calcPossibleMoves(intArr);
            int manDist = calculateManhattan(intArr);
            State initialState = new State(board, possibleMoves, manDist, false);

            long startTime = System.nanoTime();

            LinkedList<Integer> solution = solve(initialState, new PriorityQueue<State>(), false);

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
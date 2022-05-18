import java.util.*;

public class State implements Comparable<State> {

    public int[] board;
    public LinkedList<Integer> history = new LinkedList<Integer>();
    public int[] possibleMoves;
    public int manDist;
    public boolean astar;

    public State(int[] _board, int[] _possibleMoves, int _manDist, boolean _astar) {
        board = _board;
        possibleMoves = _possibleMoves;
        manDist = _manDist;
        astar = _astar;
    }

    @Override
    public int compareTo(State state) {
        if (astar) {
            return Integer.compare((this.manDist + this.history.size()), (state.manDist + state.history.size()));
        } else {
            return Integer.compare(this.manDist, state.manDist);
        }
    }
}
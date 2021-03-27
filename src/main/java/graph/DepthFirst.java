package graph;

import board.State;

public class DepthFirst implements Order{
    @Override
    public int compare(State o1, State o2) {
        return o2.getDepth() - o1.getDepth();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.util.ArrayList;
import static projectai.Principal.map;

/**
 *
 * @author jesus
 */
final class Operator {
    
    private final int movement;
    
    public static final int MOVE_INT_UP = 0;
    public static final int MOVE_INT_RIGHT = 1;
    public static final int MOVE_INT_DOWN = 2;
    public static final int MOVE_INT_LEFT = 3;
    
    Operator(int m) {
        this.movement = m;
    }
    
    /**
     *
     * @param player
     * @param nextState
     * @return
     */
    public boolean isAplicable(Player player, State nextState) {
        if (nextState.getX() <= 0 || nextState.getX() > map.getWidth() || nextState.getY() <= 0 || nextState.getY() > map.getHeight()) {
            return false;
        }
        
        ArrayList<Weight> weights = player.getWeights();
        Terrain terrain = map.getTerrain(nextState);
        
        return weights.stream().anyMatch((Weight weight) -> (weight.getTerrainID() == terrain.getId()));
    }
    
    /**
     *
     * @param cs
     * @return
     */
    public State getNextState(State cs) {
        switch (this.movement) {
            case Operator.MOVE_INT_UP:
                return new State(cs.getX(), cs.getY()-1);
            case Operator.MOVE_INT_RIGHT:
                return new State(cs.getX()+1, cs.getY());
            case Operator.MOVE_INT_DOWN:
                return new State(cs.getX(), cs.getY()+1);
            default:
                return new State(cs.getX()-1, cs.getY());
        }
    }
    
    public int getMovementID() {
    	return movement;
    }
}

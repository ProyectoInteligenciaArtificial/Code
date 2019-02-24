/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;


public class Operator extends Map {
    
    private final int movement;
    
    public static final int MOVE_INT_UP = 0;
    public static final int MOVE_INT_RIGHT = 1;
    public static final int MOVE_INT_DOWN = 2;
    public static final int MOVE_INT_LEFT = 3;
    
    Operator(int m) {
        this.movement = m;
    }
    
    public boolean isAplicable(int playerId) {
        Player player = this.getPlayer(playerId);
        State nextState = this.getNextState(this.currentState);
        Terrain terrain = this.getTerrain(nextState);
        Weight [] weights = player.getWeights();
        
        for (Weight weight : weights) {
            if (weight.getTerrain() == terrain.getId()) {
                return true;
            }
        }
        
        return false;
    }
    
    private State getNextState(State cs) {
        if (this.movement == this.MOVE_INT_UP) {
            return new State(cs.getX(), cs.getY()-1);
        }
        else if (this.movement == this.MOVE_INT_RIGHT) {
            return new State(cs.getX()+1, cs.getY());
        }
        else if (this.movement == this.MOVE_INT_DOWN) {
            return new State(cs.getX(), cs.getY()+1);
        }
        else {
            return new State(cs.getX()-1, cs.getY());
        }
    }

    public Player getPlayer(int playerId) {
        for (Player player : this.players) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        
        return null;
    }
    
    public Terrain getTerrain(State nextState) {
        return this.cells.get(nextState.getY()).get(nextState.getX()).getTerrain();
    }
}

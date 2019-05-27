/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

/**
 *
 * @author jesus
 */
final class State {
    private int x;
    private int y;

    State() {
        this.x = 1;
        this.y = 1;
    }
    
    State(State state) {
        this.setState(state.getX(), state.getY());
    }
    
    State(int i, int j) {
        setState(i, j);
    }
    
    State(char c, int j) {
        setState(c, j);
    }
    
    public void setState(int i, int j) {
        this.x = i;
        this.y = j;
    }
    
    public void setState(char c, int j) {
        this.x = c - 'A' + 1;
        this.y = j;
    }
    
    public int getX() {
        return this.x;
    }
    
    public String getXString() {
        return String.valueOf('A' + (x - 1));
    }
    
    public int getY() {
        return this.y;
    }
    
    @Override
    public String toString() {
        return "(" + (char) ('A' + x - 1) + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        return x == ((State)obj).x && y == ((State)obj).y;
    }
}

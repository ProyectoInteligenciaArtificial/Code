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
class State {
    private int x;
    private int y;

    State() {
        this.x = 0;
        this.y = 0;
    }
    
    State(int i, int j) {
        setState(i, j);
    }
    
    public void setState(int i, int j) {
        this.x = i;
        this.y = j;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
}

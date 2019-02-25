/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.util.ArrayList;

/**
 *
 * @author jesus
 */
class Cell extends Map {
    private Terrain terrain;
    private ArrayList<Integer> visitCounter;
    
    Cell(Cell cell) {
        this.terrain = cell.terrain;
        this.visitCounter = cell.visitCounter;
    }
    
    Cell(Terrain _terrain) {
        setTerrain(_terrain);
    }
    
    Cell(int _terrain) {
        setTerrain(_terrain);
    }
    
    public void setTerrain(Terrain _terrain) {
        this.terrain = _terrain;
    }
    
    public void setTerrain(int _terrain) {
        this.terrain = this.terrains.get(this.terrains.indexOf(new Terrain(_terrain, this.cellSize)));
    }
    
    public Terrain getTerrain() {
        return terrain;
    }
    
    public void addVisit(int i) {
        visitCounter.add(i);
    }
    
    public ArrayList<Integer> getVisitCounter() {
        return this.visitCounter;
    }
    
    public void resetVisitCounter() {
        this.visitCounter.clear();
    }
}

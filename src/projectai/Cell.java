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
final class Cell {
    private int terrainId;
    private ArrayList<Integer> visitCounter;
    
    Cell(Cell cell) {
        setTerrainId(cell.getTerrainId());
        setVisitCounter(cell.getVisitCounter());
    }
    
    Cell(Terrain _terrain) {
        setTerrainId(_terrain);
        visitCounter = new ArrayList();
    }
    
    Cell(int _terrain, ArrayList<Terrain> terrains) {
        setTerrainId(_terrain, terrains);
        visitCounter = new ArrayList();
    }
    
    public void setTerrainId(int _terrain) {
        terrainId = _terrain;
    }
    
    public void setTerrainId(Terrain _terrain) {
        this.terrainId = _terrain.getId();
    }
    
    public void setTerrainId(int _terrain, ArrayList<Terrain> terrains) {
        for (Terrain t : terrains) {
            if (t.getId() == _terrain) {
                terrainId = t.getId();
                return;
            }
        }
   }
    
    public int getTerrainId() {
        return terrainId;
    }
    
    public void addVisit(int i) {
        visitCounter.add(i);
    }
    
    public void setVisitCounter(ArrayList vc) {
        visitCounter = new ArrayList<>(vc);
    }
    
    public ArrayList<Integer> getVisitCounter() {
        return this.visitCounter;
    }
    
    public String getStringVC() {
        String str = "";
        for (int i = 0; i < this.visitCounter.size(); i++) {
            str += this.visitCounter.get(i);
            if (i != this.visitCounter.size()) {
                str += ',';
            }
        }
        return str;
    }
    
    public void resetVisitCounter() {
        this.visitCounter.clear();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import static projectai.Principal.map;

/**
 *
 * @author jesus
 */
final class Weight {
    private Double weight;
    private int terrainID;
    
    Weight(Weight w) {
        setWeight(w.getWeight());
        setTerrainID(w.getTerrainID());
    }

    Weight(double w, int tid) {
        setWeight(w);
        setTerrainID(tid);
    }
    
    Weight(double w, Terrain terrain) {
        setWeight(w);
        setTerrainID(terrain.getId());
    }
    
    public void setWeight(double w) {
        weight = Math.floor(w * 100) / 100;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setTerrainID(Terrain terrain) {
        terrainID = terrain.getId();
    }
    
    public void setTerrainID(int tid) {
        terrainID = tid;
    }
    
    public int getTerrainID() {
        return terrainID;
    }
    
    @Override
    public String toString() {
        return "(" + map.getTerrain(terrainID).getName() + "," + weight + ")";
    }
}

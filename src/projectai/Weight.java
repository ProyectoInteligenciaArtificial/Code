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
final class Weight {
    private Double weight;
    private int terrainId;
    
    Weight(Weight w) {
        setWeight(w.getWeight());
        setTerrainId(w.getTerrainId());
    }

    Weight(double w, int tId) {
        setWeight(w);
        setTerrainId(tId);
    }
    
    Weight(double w, Terrain terrain) {
        setWeight(w);
        setTerrainId(terrain.getId());
    }
    
    public void setWeight(double w) {
        weight = Math.floor(w * 100) / 100;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setTerrainId(Terrain terrain) {
        terrainId = terrain.getId();
    }
    
    public void setTerrainId(int tid) {
        terrainId = tid;
    }
    
    public int getTerrainId() {
        return terrainId;
    }
}

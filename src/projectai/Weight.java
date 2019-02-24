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
class Weight {
    private Double weight;
    private int terrainId;

    Weight() {
        this.weight = null;
    }
    
    public void setWeight(double w) {
        this.weight = w;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public void setTerrain(int tid) {
        this.terrainId = tid;
    }
    
    public void setTerrain(Terrain terrain) {
        this.terrainId = terrain.getId();
    }
    
    public int getTerrain() {
        return terrainId;
    }
}

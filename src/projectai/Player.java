/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author jesus
 */
final class Player {
    private String name;
    private BufferedImage img;
    
    private final ArrayList<Weight> weights;

    Player() {
        name = "default name";
        weights = new ArrayList();
    }
    
    Player(Player player) {
        weights = new ArrayList();
        setName(player.getName());
        setImage(player.getImage());
        setWeights(player.getWeights());
    }
    
    Player(String _name) {
        setName(_name);
        weights = new ArrayList();
    }
    
    Player(String _name, BufferedImage image) {
        setName(_name);
        setImage(image);
        weights = new ArrayList();
    }
    
    public void setName(String _name) {
        if (_name.matches("^\\s*$")) {
            throw new RuntimeException("'" + _name + "' is not a valid name.");
        }
        name = _name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setImage(BufferedImage image) {
        img = ImageManipulator.copyImage(image);
    }
    
    public BufferedImage getImage() {
        return img;
    }
    
    public void addWeight(Weight weight) {
        if (weights.stream().anyMatch((Weight w) -> {
            return w.getTerrainID() == weight.getTerrainID();
        })) {
            weights.stream().filter((Weight w) -> {
                return w.getTerrainID() == weight.getTerrainID();
            }).forEach((Weight w1) -> {
                w1.setWeight(weight.getWeight());
            });
            return;
        }
        
        weights.add(weight);
    }
    
    public void removeWeight(int terrainId) {
        weights.removeIf((Weight w) -> {
            return w.getTerrainID() == terrainId;
        });
    }
    
    public void setWeights(ArrayList<Weight> wL) {
        wL.stream().forEach((w) -> {
            addWeight(w);
        });
    }
    
    public ArrayList<Weight> getWeights() {
        return weights;
    }
}

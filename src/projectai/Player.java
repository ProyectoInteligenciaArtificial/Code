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
    
    private ArrayList<Weight> weights;

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
    
    public void addWeight(Weight w) {
        weights.add(w);
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

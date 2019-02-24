/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.image.BufferedImage;

/**
 *
 * @author jesus
 */
class Player {
    private int id;
    private String name;
    private BufferedImage img;
    
    private final int [] position;
    Weight[] weights;

    Player() {
        this.position = new int[]{0, 0};
    }
    
    public int getId() {
        return this.id;
    }
    
    public Weight[] getWeights() {
        return this.weights;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author jesus
 */
final class Terrain {
    private int id;
    private Integer color;
    private String name = null;
    private BufferedImage image = null;
    
    Terrain(int terrainId) {
        setId(terrainId);
    }
    
    Terrain(int terrainId, int rgb, int size) {
        setId(terrainId);
        setImage(color = rgb, size);
    }
    
    Terrain(int terrainId, BufferedImage img) {
        setId(terrainId);
        setImage(img);
    }

    Terrain(Terrain t) {
        setId(t.getId());
        setName(t.getName());
        setImage(t.getImage());
    }
    
    public void setId(int terrainId) {
        this.id = terrainId;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setName(String terrainName) {
        if (terrainName.matches("^\\s*$")) {
            throw new RuntimeException("'" + terrainName + "' is not a valid name.");
        }
        this.name = terrainName;
    }
    
    public String getName() {
        return name != null ? name : Integer.toString(id);
    }
    
    public void setImage(BufferedImage img) {
        image = ImageManipulator.copyImage(img);
    }
    
    public void setImage(int rgb, int size) {
        if (rgb == Color.WHITE.getRGB() || rgb == Color.BLACK.getRGB()) {
            throw new RuntimeException("White and Black are not allowed colors");
        }
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                this.image.setRGB(x, y, rgb);
            }
        }
        color = rgb;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public Integer getColorRGB() {
        return color;
    }
}

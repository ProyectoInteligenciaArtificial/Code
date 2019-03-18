/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 *
 * @author jesus
 */
final class Terrain {
    private int id;
    private String name;
    private BufferedImage image = null;
    
    Terrain(int terrainId, int size) {
        setId(terrainId);
        size = size == 0 ? 50 : size;
        
        switch (terrainId) {
            case 1:
                setImage(Color.GREEN.getRGB(), size);
                break;
            case 2:
                setImage(Color.BLUE.getRGB(), size);
                break;
            case 3:
                setImage(Color.WHITE.getRGB(), size);
                break;
            default:
                setImage(Color.RED.getRGB(), size);
                break;
        }
    }
    
    Terrain(int terrainId, int rgb, int size) {
        setId(terrainId);
        setImage(rgb, size);
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
        this.name = terrainName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setImage(BufferedImage img) {
        image = ImageManipulator.copyImage(img);
    }
    
    public void setImage(int rgb, int size) {
        this.image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                this.image.setRGB(x, y, rgb);
            }
        }
    }
    
    public BufferedImage getImage() {
        return image;
    }
}

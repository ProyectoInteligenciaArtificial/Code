/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static projectai.Select.size;

/**
 *
 * @author jesus
 */
final class ImageManipulator {
	private static BufferedImage originalImage;
    private static BufferedImage manipulatedImage;
    
    protected static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Toolkit toolkit =  Toolkit.getDefaultToolkit ();
        Dimension dim = toolkit.getScreenSize();
        
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d;
        g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    
    protected static BufferedImage copyImage(BufferedImage img) {
        ColorModel cm = img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = img.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public static void setImage(BufferedImage img, JLabel LBL) {
    	originalImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        manipulatedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        ImageIcon icon = new ImageIcon(img);
        LBL.setIcon(icon);
    }
    
    public static void setImage(int rgb, JLabel LBL) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getHeight(); x++) {
                img.setRGB(x, y, rgb);
            }
        }
        
        ImageIcon icon = new ImageIcon(img);
        LBL.setIcon(icon);
    }
    
    protected static BufferedImage getManipulatedImage() {
        if (manipulatedImage == null)
            return null;
        
        return manipulatedImage;
    }
}

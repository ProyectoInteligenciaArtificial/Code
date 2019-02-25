/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Exception;

/**
 *
 * @author jesus
 */
public class Map {
    protected State currentState;
    
    private int mapSize = 0;
    protected int cellSize = 50;
    protected ArrayList<ArrayList<Cell>> cells = new ArrayList();
    protected ArrayList<Terrain> terrains = new ArrayList();
    protected ArrayList<Player> players = new ArrayList();
    
    public void loadMap(FileReader mapFile) throws IOException {
        String code = "";
        int character;
        int cols = 0;
        int rows = 0;
        
        ArrayList<Cell> rowCell = new ArrayList();
        Cell cell;
        
        boolean eof = false;
        
        while (!eof) {
            eof = (character = mapFile.read()) == -1;
            
            if ((('0' <= character) && (character <= '9'))) {
                code += (char)character;
            }
            
            if (character == ',') {
                addTerrain(Integer.parseInt(code));
                cell = createCell(Integer.parseInt(code));
                rowCell.add(cell);
                code = "";
                cols++;
            }
            
            if (character == '\n') {
                addTerrain(Integer.parseInt(code));
                cell = createCell(Integer.parseInt(code));
                rowCell.add(cell);
                cells.add(rowCell);
                rowCell = new ArrayList();
                setSize(++cols);
                code = "";
                cols = 0;
                rows++;
            }
            
            else if (character == -1) {
                eof = true;
            }
        }
        
        if (mapSize != ++rows) {
            //throw Exception
        }
    }
    
    public Cell createCell(int code) {
        for (Terrain terrain : terrains) {
            if (terrain.getId() == code) {
                return new Cell(new Cell(terrain));
            }
        }
        
        return null;
    }
    
    public BufferedImage getMap() {
        BufferedImage img = new BufferedImage(this.mapSize * this.cellSize, this.mapSize * this.cellSize,  BufferedImage.TYPE_INT_RGB);
        System.out.println(mapSize);
        System.out.println(cellSize);
        
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                for (int y = 0; y < this.cellSize; y++) {
                    for (int x = 0; x < this.cellSize; x++) {
                        //System.out.println(x + " " + y);
                        int rgb = cells.get(i).get(j).getTerrain().getImage().getRGB(x, y);
                        img.setRGB((this.cellSize * j) + x, (this.cellSize * i) + y, rgb);
                    }
                }
            }
        }
        
        return img;
    }
    
    private void setSize(int size) {
        if (this.mapSize == 0) {
            this.mapSize = size;
        }
        else if (this.mapSize != size) {
            //throw Exception
        }
    }
    
    public int getSize() {
        return this.mapSize;
    }
    
    public void setCellSize(int viewPortHeight) {
        this.cellSize = viewPortHeight / this.mapSize;
        
        for (ArrayList<Cell> cellRow : cells) {
            for (Cell cell : cellRow) {
                Terrain terrain = cell.getTerrain();
                BufferedImage img = terrain.getImage();
                terrain.setImage(ImageManipulator.resize(img, cellSize, cellSize));
            }
        }
    }
    
    public int getCellSize() {
        return this.cellSize;
    }
    
    private void addTerrain(int terrainId) {
        for (Terrain terrain : terrains) {
            if (terrain.getId() == terrainId) {
                return;
            }
        }
        
        Terrain terrain = new Terrain(terrainId, cellSize);
        this.terrains.add(terrain);
    }
    
    private void addTerrain(int terrainId, int rgb) {
        for (Terrain terrain : terrains) {
            if (terrain.getId() == terrainId) {
                return;
            }
        }
        
        Terrain terrain = new Terrain(terrainId, rgb, 100);
        this.terrains.add(terrain);
    }
    
    public ArrayList<Terrain> getTerrains() {
        return this.terrains;
    }
    
    public ArrayList<ArrayList<Cell>> getCells() {
        return this.cells;
    }
    
    public void addPlayer(Player player) {
        if (players.size() < 4) {
            players.add(player);
            return;
        }
        //throw Exception
    }
    
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
}

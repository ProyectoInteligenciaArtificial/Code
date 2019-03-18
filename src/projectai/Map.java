/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static projectai.ImageManipulator.resize;

/**
 *
 * @author jesus
 */
public class Map {
    
    private State initialState;
    private State finalState;
    private State currentState;
    
    private final ArrayList<State> route;
    
    private int visitCounter;

    private int mapWidth = 0;
    private int mapHeight = 0;
    private int cellSize;
    private final ArrayList<ArrayList<Cell>> cells;
    private final ArrayList<Terrain> terrains;
    private final ArrayList<Player> players;
    private int selectedPlayer;

    public Map() {
        this.cells = new ArrayList();
        route = new ArrayList();
        players = new ArrayList();
        terrains = new ArrayList();
        selectedPlayer = -1;
        visitCounter = 0;
        initialState = null;
        currentState = null;
    }

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

            switch (character) {
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    code += (char) character;
                    break;

                case ',':
                    addTerrain(Integer.parseInt(code));
                    cell = createCell(Integer.parseInt(code));
                    rowCell.add(cell);
                    code = "";
                    cols++;
                    break;

                case '\n':
                    addTerrain(Integer.parseInt(code));
                    cell = createCell(Integer.parseInt(code));
                    rowCell.add(cell);
                    cells.add(rowCell);
                    rowCell = new ArrayList();
                    setSize(++cols);
                    code = "";
                    cols = 0;
                    rows++;
                    break;

                case 13:
                    continue;

                case -1:
                    eof = true;
                    break;

                default:
                    throw new RuntimeException("Parse Error: invalid symbols found");
            }
        }

        mapHeight = rows;
    }

    /**
     *
     * @param code
     * @return
     */
    public Cell createCell(int code) {
        for (Terrain t : terrains) {
            if (t.getId() == code) {
                return new Cell(t);
            }
        }

        return null;
    }

    public BufferedImage getMap() {
        BufferedImage img = new BufferedImage((mapWidth + 1) * cellSize, (mapHeight + 1) * cellSize, BufferedImage.TYPE_INT_RGB);
        int rgb;
        int factor = 4;
        int fontSize = cellSize / factor;
        String str;

        Font font_2 = new Font("TimesRoman", Font.PLAIN, fontSize - 5);

        Graphics2D g2d = img.createGraphics();

        for (int i = 1; i < mapHeight + 1; i++) {
            for (int j = 1; j < mapWidth + 1; j++) {
                for (int y = 0; y < cellSize - 1; y++) {
                    for (int x = 0; x < cellSize - 1; x++) {
                        rgb = getTerrain(cells.get(i - 1).get(j - 1).getTerrainId()).getImage().getRGB(x, y);
                        img.setRGB((cellSize * j) + x, (cellSize * i) + y, rgb);
                    }
                }
                str = cells.get(i - 1).get(j - 1).getStringVC();
                g2d.setFont(font_2);

                for (int k = 0; k < str.length(); k++) {
                    g2d.drawString(str.substring(k, k + 1), j * cellSize + ((k % (factor * 2)) * (fontSize / 2)), i * (cellSize) + fontSize + ((int) (k / (factor * 2)) * fontSize));
                }
            }
        }
        
        drawCoords(g2d);
        drawStateMarks(g2d);
        drawPlayer(g2d, img);

        return img;
    }

    public void drawCoords(Graphics2D g2d) {
        Font font_1 = new Font("TimesRoman", Font.PLAIN, (cellSize / 2));

        g2d.setFont(font_1);
        for (int i = 0; i < mapHeight;) {
            g2d.drawString(String.valueOf((char) ('1' + i)), (int) (cellSize * 0.25), ++i * cellSize + (int) (cellSize * 0.75));
        }
        for (int i = 0; i < mapWidth;) {
            g2d.drawString(String.valueOf((char) ('A' + i)), ++i * cellSize + (int) (cellSize * 0.25), (int) (cellSize * 0.75));
        }
    }
    
    public void drawStateMarks(Graphics g2d) {
        Font font = new Font("TimesRoman", Font.PLAIN, cellSize);
        g2d.setColor(Color.black);
        g2d.drawString("I", initialState.getX() * cellSize + (int)(cellSize * 0.375), initialState.getY() * cellSize + (int)(cellSize * 0.75));
        g2d.drawString("F", finalState.getX() * cellSize + (int)(cellSize * 0.375), finalState.getY() * cellSize + (int)(cellSize * 0.75));
    }

    public void drawPlayer(Graphics g2d, BufferedImage img) {
        int rgb;
        State cs = getCurrentState();
        BufferedImage playerImg = resize(players.get(selectedPlayer).getImage(), cellSize / 2, cellSize / 2);

        for (int y = 0; y < (int) (cellSize / 2); y++) {
            for (int x = 0; x < (int) (cellSize / 2); x++) {
                rgb = playerImg.getRGB(x, y);
                if (rgb != Color.BLACK.getRGB()) {
                    img.setRGB(cellSize * cs.getX() + ((int) (cellSize * 0.25) + x), cellSize * cs.getY() + ((int) (cellSize * 0.25) + y), rgb);
                }

            }
        }
    }

    private void setSize(int size) {
        if (mapWidth == 0) {
            mapWidth = size;
        } else if (this.mapWidth != size) {
            throw new RuntimeException("Parse error: to many columns!");
        }
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return mapWidth > mapHeight ? mapWidth : mapHeight;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return mapWidth;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return mapHeight;
    }

    /**
     *
     * @param viewPortHeight
     */
    public void setCellSize(int viewPortHeight) {
        this.cellSize = viewPortHeight / (mapWidth > mapHeight ? mapWidth : mapHeight);

        terrains.stream().forEach((Terrain terrain) -> {
            BufferedImage img = terrain.getImage();
            terrain.setImage(resize(img, cellSize, cellSize));
        });
    }

    /**
     *
     * @return
     */
    public int getCellSize() {
        return cellSize;
    }

    private void addTerrain(int terrainId) {
        for (Terrain terrain : terrains) {
            if (terrain.getId() == terrainId) {
                return;
            }
        }

        Terrain terrain = new Terrain(terrainId, cellSize);
        terrains.add(terrain);
    }
    
    private void addTerrain(Terrain t) {
        for (Terrain terrain : terrains) {
            if (terrain.getId() == t.getId()) {
                return;
            }
        }

        Terrain terrain = new Terrain(t);
        terrains.add(terrain);
    }

    private void addTerrain(int terrainId, int rgb) {
        for (Terrain terrain : terrains) {
            if (terrain.getId() == terrainId) {
                return;
            }
        }

        Terrain terrain = new Terrain(terrainId, rgb, 100);
        terrains.add(terrain);
    }
    
    private void addTerrain(int terrainId, BufferedImage img) {
        for (Terrain terrain : terrains) {
            if (terrain.getId() == terrainId) {
                return;
            }
        }

        Terrain terrain = new Terrain(terrainId, img);
        terrains.add(terrain);
    }

    /**
     *
     * @return
     */
    public ArrayList<Terrain> getTerrains() {
        return terrains;
    }

    /**
     *
     * @param code
     * @return
     */
    public Terrain getTerrain(int code) {
        for (Terrain t : terrains) {
            if (t.getId() == code) {
                return t;
            }
        }

        return null;
    }

    /**
     *
     * @param state
     * @return
     */
    public Terrain getTerrain(State state) {
        int terrainId = cells.get(state.getY() - 1).get(state.getX() - 1).getTerrainId();
        for (Terrain t : terrains) {
            if (t.getId() == terrainId) {
                return t;
            }
        }

        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<ArrayList<Cell>> getCells() {
        return cells;
    }
    
    /**
     *
     * @param c
     * @param i
     * @return
     */
    public Cell getCell(char c, int i) {
        return new Cell(cells.get(i-1).get(c - 'A'));
    }

    /**
     *
     * @param player
     */
    public void addPlayer(Player player) {
        if (players.size() < 4) {
            selectedPlayer = players.isEmpty() ? 0 : selectedPlayer;
            players.add(player);
            return;
        }
        throw new RuntimeException("The maximun number of players is 4!");
    }
    
    public void removePlayer(Player player) {
        players.stream().filter((p) -> (p.getName().equals(player.getName()))).forEach((p) -> {
            players.remove(p);
        });
    }

    /**
     *
     * @param sp
     */
    public void setSelectedPlayer(int sp) {
        selectedPlayer = sp;
    }
    
    public void setSelectedPlayer(Player player) {
        players.stream().filter((p) -> (p.getName().equals(player.getName()))).forEach((p) -> {
            selectedPlayer = players.indexOf(p);
        }); 
    }

    /**
     *
     * @return
     */
    public Player getSelectedPlayer() {
        return players.isEmpty() ? null : players.get(selectedPlayer);
    }

    /**
     *
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    /**
     *
     * @param state
     */
    public void setInitialState(State state) {
        if (!stateReachable(state)) {
            throw new RuntimeException("Selected State is not reachable for the current player");
        }
        initialState = state;
        if (currentState == null) {
            setCurrentState(initialState);
        }
    }
    
    /**
     *
     * @param state
     */
    public void setFinalState(State state) {
        if (!stateReachable(state)) {
            throw new RuntimeException("Selected State is not reachable for the current player");
        }
        finalState = state;
    }

    /**
     *
     * @param state
     */
    public void setCurrentState(State state) {
        if (!stateReachable(state)) {
            throw new RuntimeException("Selected State is not reachable for the current player");
        }
        currentState = state;
        addRouteState(currentState);
        cells.get(state.getY() - 1).get(state.getX() - 1).addVisit(++visitCounter);
    }
    
    /**
     *
     * @return
     */
    public State getInitialState() {
        if (initialState == null) {
            throw new RuntimeException("Initial State had not been set.");
        }
        return new State(initialState);
    }
    
    /**
     *
     * @return
     */
    public State getFinalState() {
        if (finalState == null) {
            throw new RuntimeException("Final State had not been set.");
        }
        return new State(finalState);
    }

    /**
     *
     * @return
     */
    public State getCurrentState() {
        return currentState != null ? new State(currentState) : getInitialState();
    }
    
    /**
     *
     * @param state
     * @return
     */
    private boolean stateReachable(State state) {
        return getSelectedPlayer().getWeights().stream().anyMatch((w) -> (getTerrain(state).getId() == w.getTerrainId()));
    }
    
    /**
     *
     * @param state
     */
    private void addRouteState(State state) {
        route.add(state);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<State> getRoute() {
        return new ArrayList<>(route);
    }
}
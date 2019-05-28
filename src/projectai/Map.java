/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
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
    
    private int visitCounter;
    private int mapWidth;
    private int mapHeight;
    private int cellSize;
    private int selectedPlayer;
    
    private boolean finished;
    
    private final ArrayList<ArrayList<Cell>> cells;
    private final ArrayList<Terrain> terrains;
    private final ArrayList<Player> players;
    private final ArrayList<Node> route;
    private static ArrayList<State> solution;

    /**
     *
     */
    public Map() {
        initialState = null;
        finalState = null;
        currentState = null;
        
        visitCounter = 0;
        mapWidth = 0;
        mapHeight = 0;
        cellSize = 50;
        selectedPlayer = -1;
        
        finished = false;
        
        cells = new ArrayList();
        terrains = new ArrayList();
        players = new ArrayList();
        route = new ArrayList();
    }

    /**
     *
     * @param mapFile
     * @throws IOException
     */
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
                    checkForVoid(code);
                    addTerrain(Integer.parseInt(code));
                    cell = createCell(Integer.parseInt(code));
                    rowCell.add(cell);
                    code = "";
                    cols++;
                    break;

                case '\n':
                    checkForVoid(code);
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
        
        if (mapWidth == 0 || mapHeight == 0) {
            throw new RuntimeException("The map is empty.");
        }
    }
    
    private void checkForVoid(String code) {
        if (code.equals("")) {
            throw new RuntimeException("Parse Error: the file have void id's");
        }
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
    
    public BufferedImage getMapPrev(boolean is, boolean fs) {
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
        
        if (is && fs) {
            currentState = initialState;
            drawStateMarks(g2d);
        } else {
            for (int i = 0; i < cells.size(); i++) {
                for (int j = 1; j < cells.get(i).size(); j++) {
                    if (stateReachable(currentState = new State((char) ('A' + i), j))) {
                        break;
                    }
                }
            }
        }
        
        drawCoords(g2d);
        drawPlayer(g2d, img);
        
        return img;
    }
    
    /**
     *
     * @return
     */
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
            	if (isDiscovered(j-1,i-1) || getFinalState().equals(new State(j, i))) {
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
        }
        
        if (finished) {
        	drawSolution(g2d);
        }
        
        drawCoords(g2d);
        drawPlayer(g2d, img);
        drawStateMarks(g2d);

        return img;
    }
    
    public boolean isDiscovered(int x, int y) {
    	return !(cells.get(y).get(x).getVisitCounter().isEmpty() &&
    			(y <= 0 ? true : cells.get(y-1).get(x).getVisitCounter().isEmpty()) &&
    			(x+1 >= mapWidth ? true : cells.get(y).get(x+1).getVisitCounter().isEmpty()) &&
    			(y+1 >= mapHeight ? true : cells.get(y+1).get(x).getVisitCounter().isEmpty()) &&
    			(x <= 0 ? true : cells.get(y).get(x-1).getVisitCounter().isEmpty()));
    }

    /**
     *
     * @param g2d
     */
    public void drawCoords(Graphics2D g2d) {
        Font font_1 = new Font("TimesRoman", Font.PLAIN, (cellSize / 2));

        g2d.setFont(font_1);
        g2d.setColor(Color.white);
        for (int i = 0; i < mapHeight;) {
            g2d.drawString(Integer.toString(1 + i), (int) (cellSize * 0.25), ++i * cellSize + (int) (cellSize * 0.75));
        }
        for (int i = 0; i < mapWidth;) {
            g2d.drawString(String.valueOf((char) ('A' + i)), ++i * cellSize + (int) (cellSize * 0.25), (int) (cellSize * 0.75));
        }
    }
    
    /**
     *
     * @param g2d
     */
    public void drawStateMarks(Graphics g2d) {
        if (!stateReachable(getInitialState())) {
            throw new RuntimeException("Initial State is not reachable for the current player.");
        }
        if (!stateReachable(getFinalState())) {
            throw new RuntimeException("Final State is not reachable for the current player.");
        }
        
        Font font = new Font("TimesRoman", Font.PLAIN, cellSize / 2 );
        g2d.setFont(font); 
        g2d.setColor(Color.black);
        g2d.drawString("I", initialState.getX() * cellSize + (int)(cellSize * 0.375), initialState.getY() * cellSize + (int)(cellSize * 0.75));
        g2d.drawString("F", finalState.getX() * cellSize + (int)(cellSize * 0.375), finalState.getY() * cellSize + (int)(cellSize * 0.75));
    }

    /**
     *
     * @param g2d
     * @param img
     */
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
    
    private void drawSolution(Graphics2D g2d) {
    	g2d.setColor(Color.yellow);
    	g2d.setStroke(new BasicStroke(2));
    	for (State s : solution) {
    		g2d.drawLine(s.getX() * cellSize, s.getY() * cellSize, s.getX() * cellSize + cellSize, s.getY() * cellSize);
    		g2d.drawLine(s.getX() * cellSize, s.getY() * cellSize, s.getX() * cellSize, s.getY() * cellSize + cellSize);
    		g2d.drawLine(s.getX() * cellSize + cellSize, s.getY() * cellSize + cellSize, s.getX() * cellSize + cellSize, s.getY() * cellSize);
    		g2d.drawLine(s.getX() * cellSize + cellSize, s.getY() * cellSize + cellSize, s.getX() * cellSize, s.getY() * cellSize + cellSize);
    	}
    	g2d.setColor(Color.red);
    	g2d.setStroke(new BasicStroke(1));
    	for (State s : solution) {
    		g2d.drawLine(s.getX() * cellSize, s.getY() * cellSize, s.getX() * cellSize + cellSize, s.getY() * cellSize);
    		g2d.drawLine(s.getX() * cellSize, s.getY() * cellSize, s.getX() * cellSize, s.getY() * cellSize + cellSize);
    		g2d.drawLine(s.getX() * cellSize + cellSize, s.getY() * cellSize + cellSize, s.getX() * cellSize + cellSize, s.getY() * cellSize);
    		g2d.drawLine(s.getX() * cellSize + cellSize, s.getY() * cellSize + cellSize, s.getX() * cellSize, s.getY() * cellSize + cellSize);
    	}
    }
    
    public void setSolution(ArrayList<State> s) {
    	solution = s;
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

        Terrain terrain = new Terrain(terrainId);
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
     * @param name
     * @return
     */
    public Terrain getTerrain(String name) {
        for (Terrain t : terrains) {
            if (t.getName().equals(name)) {
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
        for (Player p : players) {
            if (p.getName() != null && p.getName().equals(player.getName())) {
                p.setImage(player.getImage());
                return;
            }
        }
        
        if (players.size() < 4) {
            selectedPlayer = players.isEmpty() ? 0 : selectedPlayer;
            players.add(player);
            return;
        }
        
        throw new RuntimeException("The maximun number of players is 4!");
    }
    
    /**
     *
     * @param playerName
     */
    public void removePlayer(String playerName) {
        players.removeIf((Player p) -> (p.getName().equals(playerName)));
    }

    /**
     *
     * @param sp
     */
    public void setSelectedPlayer(int sp) {
        selectedPlayer = sp;
    }
    
    /**
     *
     * @param player
     */
    public void setSelectedPlayer(Player player) {
        players.stream().filter((Player p) -> {
            return p.getName().equals(player.getName());
        }).forEach((Player p) -> {
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
     * @param playerName
     * @return
     */
    public Player getPlayer(String playerName) {
        return players.stream().filter((Player p) -> {
            return p.getName().equals(playerName);
        }).findFirst().get();
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
            //setCurrentState(initialState);
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
    	if (state == null) {
    		currentState = state;
    		return;
    	}
        if (!stateReachable(state)) {
            throw new RuntimeException("Selected State is not reachable for the current player");
        }
        currentState = state;
        cells.get(state.getY() - 1).get(state.getX() - 1).addVisit(++visitCounter);
        addRouteState(currentState);
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
    public boolean stateReachable(State state) {
        for (Weight w : getSelectedPlayer().getWeights()) {
            if (w.getTerrainID() == getTerrain(state).getId()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     *
     * @param state
     */
    private void addRouteState(State state) {
        route.add(new Node(state, visitCounter, initialState != null && state.equals(initialState), finalState != null && state.equals(finalState)));
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Node> getRoute() {
        return new ArrayList<>(route);
    }
    
    public void reset(FileReader fr) throws IOException {
        initialState = null;
        finalState = null;
        currentState = null;
        
        visitCounter = 0;
        mapWidth = 0;
        mapHeight = 0;
        cellSize = 50;
        selectedPlayer = -1;
        
        finished = false;
        
        cells.clear();
        terrains.clear();
        players.clear();
        route.clear();
        
        loadMap(fr);
    }
    
    public void reset() {
        initialState = null;
        finalState = null;
        currentState = null;
        
        visitCounter = 0;
        mapWidth = 0;
        mapHeight = 0;
        cellSize = 50;
        selectedPlayer = -1;
        
        finished = false;
        
        cells.clear();
        terrains.clear();
        players.clear();
        route.clear();
    }
    
    public void resetVisitCounter() {
    	cells.forEach((r) -> {
    		r.forEach((c) -> {
    			c.resetVisitCounter();
    		});
    	});
        visitCounter = 0;
    }
    
    public void setFinish(boolean f) {
        finished = f;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    public int manhatan(State stt) {
    	return Math.abs(finalState.getX() - stt.getX()) + Math.abs(finalState.getY() - stt.getY());
    }
}
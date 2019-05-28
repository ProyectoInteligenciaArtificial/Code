/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTable;
import static projectai.Principal.map;

/**
 *
 * @author jesus
 */
public class PlayerDataTable extends DataTable {
    public PlayerDataTable() {
        columnNamnes = new String [] {"name", "terreno", "peso"};
        buildTable();
    }
    
    public void add(Player p) {
        Object[] objs = {};
        if (p.getWeights().size() > 0) {
            for (Weight w : p.getWeights()) {
                objs = new Object [] {p.getName(), w.getTerrainID(), w.getWeight()};
                tableModel.addRow(objs);
            }
        } else {
            objs = new Object [] {p.getName(), null, null};
            tableModel.addRow(objs);
        }
    }
    
    public JTable add(ArrayList<Player> pL) {
        pL.forEach(this::add);
        
        updateTable();
        return table;
    }
}
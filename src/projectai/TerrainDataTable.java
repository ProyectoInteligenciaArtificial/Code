/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author jesus
 */
public class TerrainDataTable extends DataTable {
    public TerrainDataTable() {
        columnNamnes = new String [] {"id", "name", "color"};
        buildTable();
    }
    
    public void add(Terrain t) {
        Object[] objs = new Object [] {t.getId(), t.getName() == null ? "" : t.getName(), null};
        tableModel.addRow(objs);
    }
    
    public JTable add(ArrayList<Terrain> tL) {
        tL.forEach(this::add);
        updateTable();
        table.getColumnModel().getColumn(2).setCellRenderer(new projectai.ColorColumnCellRenderer());
        return table;
    } 
}
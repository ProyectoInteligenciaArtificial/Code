/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Point;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;


public class DataTable {
    protected DefaultTableModel tableModel;
    protected String columnNamnes [];
    protected JTable table;

    public DataTable () {
        buildTable();
    }

    public void buildTable() {
        tableModel = new DefaultTableModel(columnNamnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
    }

    public JTable clear() {
        tableModel.setRowCount(0);
        return table;
    }

    public void updateTable() {
        table = new JTable(tableModel);
    }

    public JTable getTable() {
        return table;
    }
    
}

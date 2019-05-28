/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectai;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import static projectai.Principal.map;

/**
 *
 * @author jesus
 */
public class ColorColumnCellRenderer extends DefaultTableCellRenderer {@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        
        if (!map.getTerrains().isEmpty() && map.getTerrains().get(row).getColorRGB() != null) {
            l.setBackground(new Color(map.getTerrains().get(row).getColorRGB()));
        }
        else {
            l.setBackground(Color.white);
        }
        
        return l;
    }
}

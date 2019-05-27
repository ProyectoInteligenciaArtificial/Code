package projectai;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JTable;

public class OperatorsDataTable extends DataTable {
	public OperatorsDataTable() {
        columnNamnes = new String [] {"Operator"};
        buildTable();
    }
    
    public void add(Operator op) {
    	final Object [] objs = {null};
    	switch (op.getMovementID()) {
    		case 0:
    			objs[0] = "Move Up";
    			break;
    		case 1:
    			objs[0] = "Move Right";
    			break;
    		case 2:
    			objs[0] = "Move Down";
    			break;
    		case 3:
    			objs[0] = "Move Left";
    			break;
    	}
    	tableModel.addRow(objs);
    }
    
    public JTable add(Operator [] ops) {
    	for (Operator op : ops) {
    		add(op);
    	}
    	
    	return table;
    }
}

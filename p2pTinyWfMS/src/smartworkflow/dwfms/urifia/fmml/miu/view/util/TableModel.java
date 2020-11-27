package smartworkflow.dwfms.urifia.fmml.miu.view.util;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Ndadji Maxime
 */
public class TableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	protected Object[][] data;
    protected Object[] title;
   
    public TableModel(Object[][] data, Object[] title){
        this.data = data;
        this.title = title;
    }
    
    public TableModel(Object[] title){
        this.data = new Object[0][0];
        this.title = title;
    }
    
    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return this.title.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }

    @Override
    public void fireTableCellUpdated(int row, int column) {
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.data[0][columnIndex].getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return this.title[column].toString();
    }
    
    public void removeRow(int position){
        int indice = 0, indice2 = 0, nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
        Object temp[][] = new Object[nbRow][nbCol];

        for(Object[] value : this.data){
            if(indice != position)
                temp[indice2++] = value;
            indice++;
        }
        this.data = temp;
        temp = null;
        this.fireTableDataChanged();
    }
    
    public void removeAll(){
        this.data = new Object[0][0];
    }

    public void addRow(Object[] data){
        int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
        Object temp[][] = this.data;
        this.data = new Object[nbRow+1][nbCol];
        for(Object[] value : temp)
            this.data[indice++] = value;

        this.data[indice] = data;
        temp = null;
        this.fireTableDataChanged();
    }
    
    public static class ComponentRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("rawtypes")
		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton)
                return (JButton) value;
            
            if (value instanceof JComboBox)
                return (JComboBox) value;
            
            if (value instanceof JCheckBox)
                return (JCheckBox) value;
            
            if (value instanceof JTextField)
                return (JTextField) value;
            
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    
    public static class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("");
            return this;
        }
        
    }
    
    public static ComponentRenderer getComponentRenderer(){
        return new ComponentRenderer();
    }
    
    public static CheckBoxRenderer getCheckBoxRenderer(){
        return new CheckBoxRenderer();
    }
}

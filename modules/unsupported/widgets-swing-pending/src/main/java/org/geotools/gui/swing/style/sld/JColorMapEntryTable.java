/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.gui.swing.style.sld;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import org.geotools.gui.swing.style.StyleElementEditor;
import org.geotools.map.MapLayer;
import org.geotools.styling.ColorMapEntry;


/**
 * Color map entry table.
 * 
 * @author Johann Sorel
 *
 * @source $URL$
 */
public class JColorMapEntryTable extends javax.swing.JPanel implements StyleElementEditor<ColorMapEntry[]> {

    private MapLayer layer = null;
    private final EntryModel model = new EntryModel(new ColorMapEntry[]{});
    private final EntryEditor editor = new EntryEditor();

    /** Creates new form JFontsPanel */
    public JColorMapEntryTable() {
        initComponents();
        init();
    }

    private void init() {
        tabFonts.setTableHeader(null);
        tabFonts.setModel(model);
        tabFonts.getColumnModel().getColumn(0).setCellEditor(editor);
        tabFonts.setDefaultRenderer(ColorMapEntry.class, new EntryRenderer());
        tabFonts.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setLayer(MapLayer layer) {
        editor.setLayer(layer);
        this.layer = layer;
    }

    public MapLayer getLayer() {
        return layer;
    }

    public void setEdited(ColorMapEntry[] fonts) {
        model.setEntrys(fonts);
    }

    public ColorMapEntry[] getEdited() {
        return model.getEntrys();
    }

    public void apply() {
    }

    public Component getComponent() {
        return this;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabFonts = new javax.swing.JTable();

        jScrollPane1.setViewportView(tabFonts);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabFonts;
    // End of variables declaration//GEN-END:variables
}
class EntryModel extends AbstractTableModel {

    private List<ColorMapEntry> entrys = new ArrayList<ColorMapEntry>();

    EntryModel(ColorMapEntry[] entrys) {
        for (ColorMapEntry entry : entrys) {
            this.entrys.add(entry);
        }
    }

    public void setEntrys(ColorMapEntry[] entrys) {
        this.entrys.clear();

        if (entrys != null) {
            for (ColorMapEntry entry : entrys) {
                this.entrys.add(entry);
            }
        }
        fireTableDataChanged();
    }

    public ColorMapEntry[] getEntrys() {
        return entrys.toArray(new ColorMapEntry[entrys.size()]);
    }

    public int getRowCount() {
        return entrys.size();
    }

    public int getColumnCount() {
        return 1;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ColorMapEntry.class;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return entrys.get(rowIndex);
    }
}

class EntryRenderer extends DefaultTableCellRenderer {

    private static final String text = "";

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);

        ColorMapEntry f = (ColorMapEntry) value;
        lbl.setText(f.getLabel());
        return lbl;
    }
}

class EntryEditor extends AbstractCellEditor implements TableCellEditor {
    private MapLayer layer = null;
    private JColorMapEntryPane editpane = new JColorMapEntryPane();
    private JButton but = new JButton("");
    private ColorMapEntry entry = null;

    public EntryEditor() {
        super();
        but.setBorderPainted(false);

        but.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (entry != null) {
                    JDialog dia = new JDialog();

                    //panneau d'edition           
                    editpane.setEdited(entry);

                    dia.setContentPane(editpane);
                    dia.setLocationRelativeTo(but);
                    dia.pack();
                    dia.setModal(true);
                    dia.setVisible(true);

                    entry = editpane.getEdited();
                }
            }
        });
    }

    public void setLayer(MapLayer layer) {
        this.layer = layer;
    }

    public MapLayer getLayer() {
        return layer;
    }

    public Object getCellEditorValue() {
        return entry;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        if (value != null && value instanceof ColorMapEntry) {
            entry = (ColorMapEntry) value;
            but.setText(entry.getLabel());
        } else {
            entry = null;
        }
        return but;
    }
}
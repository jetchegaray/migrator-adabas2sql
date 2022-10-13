/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelFieldsTable.java
 *
 * Created on 11-nov-2010, 15:34:47
 */

package views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javimetal
 */
public class JPanelFieldsTable extends javax.swing.JDialog {

    private ViewTable viewTable;
    private List viewForeignKeysOfTableToShow;
    //seria las foreign key de la tabla a mostar

    public JPanelFieldsTable(java.awt.Frame parent,ViewTable viewTable,List viewForeignKeys) {
        super(parent, true);
        this.viewTable = viewTable;
        this.viewForeignKeysOfTableToShow = new ArrayList();
        CreateFKOfTable(viewForeignKeys);
        putInFieldsIsForeignKeys();
        initComponents();
    }

    /**
     * @return the viewForeignKeysOfTableToShow
     */
    public List getViewForeignKeysOfTableToShow() {
        return viewForeignKeysOfTableToShow;
    }




    //es medio fruta , pero el tiempo no dio para hacerlo de otra manera
    //se tendria que hacer un modelo de vistas a partir de las clases viewIndex
    //y viewField que representan cada fila de las tablas.

            public class ViewTableFieldsAndIndexListener implements TableModelListener{
                private JPanelFieldsTable jpanel;

                public void setJPanel(JPanelFieldsTable jpanel){
                    this.jpanel = jpanel;
                }

                public void tableChanged(TableModelEvent e) {

                   if(e.getType() == TableModelEvent.UPDATE)
                   {
                        jpanel.saveFieldsChanges();
                        jpanel.saveIndexsChanges();
                    }else if(e.getType() == TableModelEvent.INSERT){

                    }

                }
            }

            public class ViewTableForeignKeyListener implements TableModelListener{
                private JPanelFieldsTable jpanel;

                public void setJPanel(JPanelFieldsTable jpanel){
                    this.jpanel = jpanel;
                }

                public void tableChanged(TableModelEvent e) {

                   if(e.getType() == TableModelEvent.UPDATE)
                   {
                        jpanel.saveForeignKeysChanges();
                    }else if(e.getType() == TableModelEvent.INSERT){

                    }

                }
            }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPaneDataTable = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        ViewTableFieldsAndIndexListener viewTableFieldsAndIndexListener= new ViewTableFieldsAndIndexListener();
        viewTableFieldsAndIndexListener.setJPanel(this);
        jTableFields = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        ViewTableForeignKeyListener viewTableForeignKeyListener = new ViewTableForeignKeyListener();
        viewTableForeignKeyListener.setJPanel(this);
        jTableForeignKeys = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableIndexs = new javax.swing.JTable();
        jButtonEdit = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPaneDataTable.setToolTipText("Campos");
        jTabbedPaneDataTable.setName("Campos"); // NOI18N

        jTableFields.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Primary Key", "Foreign Key", "Nombre", "Tipo", "Tamanio", "Not Null", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableFields.setDropMode(javax.swing.DropMode.ON_OR_INSERT_ROWS);
        jTableFields.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTableFields.setSelectionForeground(new java.awt.Color(0, 0, 0));
        this.LoadFieldRows();
        this.jTableFields.getModel().addTableModelListener(viewTableFieldsAndIndexListener);
        jScrollPane1.setViewportView(jTableFields);
        jTableFields.getColumnModel().getColumn(0).setMinWidth(70);
        jTableFields.getColumnModel().getColumn(0).setMaxWidth(70);
        jTableFields.getColumnModel().getColumn(1).setMinWidth(70);
        jTableFields.getColumnModel().getColumn(1).setMaxWidth(70);
        jTableFields.getColumnModel().getColumn(5).setMinWidth(40);
        jTableFields.getColumnModel().getColumn(5).setMaxWidth(50);

        jTabbedPaneDataTable.addTab("Campos", jScrollPane1);

        jTableForeignKeys.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Campo Clave Foreanea", "Tabla Referenciada", "Campo referenciado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableForeignKeys.setDropMode(javax.swing.DropMode.ON_OR_INSERT_ROWS);
        jTableForeignKeys.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTableForeignKeys.setSelectionForeground(new java.awt.Color(0, 0, 0));
        this.LoadForeignRows();
        this.jTableForeignKeys.getModel().addTableModelListener(viewTableForeignKeyListener);
        jScrollPane3.setViewportView(jTableForeignKeys);

        jTabbedPaneDataTable.addTab("Claves Foraneas", jScrollPane3);

        jTableIndexs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Campos", "Unique"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableIndexs.setDropMode(javax.swing.DropMode.ON_OR_INSERT_ROWS);
        jTableIndexs.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTableIndexs.setSelectionForeground(new java.awt.Color(0, 0, 0));
        this.LoadIndexRows();
        this.jTableIndexs.getModel().addTableModelListener(viewTableFieldsAndIndexListener);
        jScrollPane2.setViewportView(jTableIndexs);
        jTableIndexs.getColumnModel().getColumn(1).setMinWidth(400);
        jTableIndexs.getColumnModel().getColumn(1).setMaxWidth(400);
        jTableIndexs.getColumnModel().getColumn(2).setMinWidth(60);
        jTableIndexs.getColumnModel().getColumn(2).setMaxWidth(60);

        jTabbedPaneDataTable.addTab("Indices", jScrollPane2);

        jButtonEdit.setText("Editar Texto");
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Eliminar");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonAdd.setText("Agregar");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPaneDataTable, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneDataTable, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 1, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 2, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed


        int row = this.jTableFields.getSelectedRow();
        int column = this.jTableFields.getSelectedColumn();

        if (column == 6){
            DefaultTableModel model = ((DefaultTableModel)this.jTableFields.getModel());
            String text = (String) model.getValueAt(row, column);

            JDialogText panelTextZoom = new JDialogText(null);

            panelTextZoom.getjTextPaneZoom().setText(text);
            panelTextZoom.setLocation(160, 160);
            panelTextZoom.setTitle("Editor de Texto");
            panelTextZoom.setVisible(true);

            model.setValueAt(panelTextZoom.getjTextPaneZoom().getText(),row, column);

        }else{
            DialogOnlyAccept dialog = new DialogOnlyAccept(null, true,"Debe Seleccionar una Celda de Comentarios");
            dialog.setVisible(true);
            dialog.dispose();
        }
    }//GEN-LAST:event_jButtonEditActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed

         Object rows[];

         if (this.jTableFields.isShowing()){
                DefaultTableModel model = ((DefaultTableModel)this.jTableFields.getModel());
                rows = new Object[7];
                rows[0] = Boolean.valueOf(false);
                rows[1] = Boolean.valueOf(false);
                rows[2] = String.valueOf(" ");
                rows[3] = String.valueOf("VARCHAR");
                rows[4] = Float.valueOf(0);
                rows[5] = Boolean.valueOf(false);
                rows[6] = null;

                model.addRow(rows);

        }else if (this.jTableForeignKeys.isShowing()){
                DefaultTableModel model = ((DefaultTableModel)this.jTableForeignKeys.getModel());

                rows = new Object[4];
                rows[0] = String.valueOf(" ");
                rows[1] = String.valueOf(" ");
                rows[2] = String.valueOf(" ");
                rows[3] = String.valueOf(" ");
                model.addRow(rows);
                this.getViewForeignKeysOfTableToShow().add(new ViewForeignKey());
        }else if (this.jTableIndexs.isShowing()){
                DefaultTableModel model = ((DefaultTableModel)this.jTableIndexs.getModel());

                rows = new Object[3];

                rows[0] = String.valueOf(" ");
                rows[1] = String.valueOf(" ");
                rows[2] = Boolean.valueOf(false);
                model.addRow(rows);
        }

    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed

        DefaultTableModel model;
        int[] rows;

        if (this.jTableFields.isShowing() && this.jTableFields.getSelectedRows() != null){
            model = ((DefaultTableModel)this.jTableFields.getModel());

            rows = this.jTableFields.getSelectedRows();
            for (int i =rows[0];i <= rows[rows.length-1];i++){
                model.removeRow(rows[0]);
                this.viewTable.getFields().remove(rows[0]);
                //seimpre remuevo el mismo al remover uno el que sigue toma su pos en la lista
            }
        }else if (this.jTableIndexs.isShowing() && this.jTableIndexs.getSelectedRows() != null){
            model = ((DefaultTableModel)this.jTableIndexs.getModel());
            rows = this.jTableIndexs.getSelectedRows();
            for (int i =rows[0];i <= rows[rows.length-1];i++){
                model.removeRow(rows[0]);
                this.viewTable.getIndexs().remove(rows[0]);
            }
        }else if (this.jTableForeignKeys.isShowing() && this.jTableForeignKeys.getSelectedRows() != null){
            model = ((DefaultTableModel)this.jTableForeignKeys.getModel());
            rows = this.jTableForeignKeys.getSelectedRows();
            for (int i =rows[0];i <= rows[rows.length-1];i++){
                model.removeRow(rows[0]);
                this.getViewForeignKeysOfTableToShow().remove(rows[0]);
            }
        }

    }//GEN-LAST:event_jButtonDeleteActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPaneDataTable;
    private javax.swing.JTable jTableFields;
    private javax.swing.JTable jTableForeignKeys;
    private javax.swing.JTable jTableIndexs;
    // End of variables declaration//GEN-END:variables


     private void LoadFieldRows(){

        if (this.viewTable != null && this.viewTable.getFields() != null){
            Iterator itFields = this.viewTable.getFields().iterator();
            ViewField viewField = null;
            int row = 0;
            Object rows[] = new Object[7];
            DefaultTableModel model = ((DefaultTableModel)this.jTableFields.getModel());

            while (itFields.hasNext()){
                viewField = (ViewField)itFields.next();
                rows[0] = Boolean.valueOf(viewField.isPrimaryKey());
                rows[1] = Boolean.valueOf(viewField.isForeignKey());
                rows[2] = (Object)viewField.getName();
                rows[3] = viewField.getType();
                
                if (!viewField.getType().toLowerCase().contains("decimal")
                    && String.valueOf(viewField.getSize()).contains(".0"))
                    rows[4] = Float.valueOf(viewField.getSize()).intValue();
                else
                    rows[4] = Float.valueOf(viewField.getSize()).floatValue();

                rows[5] = Boolean.valueOf(viewField.isNotNull());
                rows[6] = this.viewTable.getCommentsOnField().get(viewField.getName());

                model.addRow(rows);

                row++;
            }
        }
    }




     private void LoadForeignRows(){

        if (this.getViewForeignKeysOfTableToShow() != null){
            Iterator itFKeys = this.getViewForeignKeysOfTableToShow().iterator();
            ViewForeignKey viewForeignKey = null;
            int row = 0;
            Object rows[] = new Object[4];
            DefaultTableModel model = ((DefaultTableModel)this.jTableForeignKeys.getModel());

            while (itFKeys.hasNext()){
                viewForeignKey = (ViewForeignKey) itFKeys.next();
                rows[0] = viewForeignKey.getName();
                rows[1] = viewForeignKey.getFieldName();
                rows[2] = viewForeignKey.getTableReferences();
                rows[3] = viewForeignKey.getFieldReferences();
                model.addRow(rows);

                row++;
            }
        }
    }

     


      private void LoadIndexRows(){


        if (this.viewTable != null && this.viewTable.getIndexs() != null){

            Iterator itIndexs = this.viewTable.getIndexs().iterator();
            ViewIndex viewIndex = null;

            int row = 0;
            Object rows[] = new Object[3];
            DefaultTableModel model = ((DefaultTableModel)this.jTableIndexs.getModel());

            while (itIndexs.hasNext()){
                viewIndex = (ViewIndex)itIndexs.next();
                rows[0] = (Object)viewIndex.getName();

                //lista de campos que componen la clave, uno por lo menos
                Iterator itCompoused = viewIndex.getCompousedKey().iterator();
                String stringRow = "";
                while (itCompoused.hasNext()){
                   stringRow += (String)itCompoused.next();

                    if (itCompoused.hasNext()){
                        stringRow +=",";
                    }
                }
                rows[1] = (Object)stringRow;
                rows[2] = Boolean.valueOf(viewIndex.isUnique());
                model.addRow(rows);

                row++;
            }
        }
    }




 

    public void saveFieldsChanges(){

        List newFields = new ArrayList();

        DefaultTableModel model = ((DefaultTableModel)this.jTableFields.getModel());
        Vector rows = model.getDataVector();

        for (int i=0;i < rows.size();i++){

            Vector column = (Vector)rows.get(i);
            ViewField viewField = new ViewField();

            this.viewTable.getCommentsOnField().remove(viewField.getName());
    //        if (column.get(1) != null && column.get(2) != null && column.get(3) != null && column.get(4) != null)
    //        {
                viewField.setPrimaryKey(((Boolean)column.get(0)).booleanValue());
                viewField.setForeignKey(((Boolean)column.get(1)).booleanValue());
                viewField.setName((String) column.get(2));
                viewField.setType((String) column.get(3));
                viewField.setSize(Float.valueOf(column.get(4).toString()).floatValue());
                viewField.setNotNull(((Boolean)column.get(5)).booleanValue());
                if (column.get(6) != null){
                    String comment = ((String) column.get(6));
                    this.viewTable.getCommentsOnField().put(viewField.getName(), comment);
                }
                newFields.add(viewField);
    //        }
        }
        this.viewTable.setFields(newFields);
    }




    
     private void saveForeignKeysChanges() {
   
        DefaultTableModel model = ((DefaultTableModel)this.jTableForeignKeys.getModel());
        Vector rows = model.getDataVector();

        for (int i=0;i < rows.size();i++){

            Vector column = (Vector)rows.get(i);
            ViewForeignKey viewForeignKey = (ViewForeignKey) this.getViewForeignKeysOfTableToShow().get(i);

            viewForeignKey.setName((String)column.get(0));
            viewForeignKey.setFieldName((String)column.get(1));
            viewForeignKey.setTableReferences((String) column.get(2));
            viewForeignKey.setFieldReferences((String) column.get(3));
            viewForeignKey.setOwnerTable(this.viewTable.getName());
        }
    }




     

    public void saveIndexsChanges(){

       DefaultTableModel model = ((DefaultTableModel)this.jTableIndexs.getModel());
       Vector rows = model.getDataVector();
       List newIndexs = new ArrayList();

       for (int i=0;i < rows.size();i++){

            Vector column = (Vector)rows.get(i);
            List fieldsCompoused= new ArrayList();

            ViewIndex viewIndex = new ViewIndex();

            viewIndex.setName((String) column.get(0));
            viewIndex.setUnique(((Boolean)column.get(2)).booleanValue());

            viewIndex.setOnTable(viewTable.getName());

            //por el momento el indice apunta a uno campo, pero con lo siguiente
            //s extiende a varios , indices cxompuestos
            StringTokenizer tokenizer = new StringTokenizer((String) column.get(1),",");

            while (tokenizer.hasMoreElements()){
                fieldsCompoused.add(tokenizer.nextToken());
            }
            viewIndex.setCompousedKey(fieldsCompoused);
            newIndexs.add(viewIndex);
        }

        viewTable.setIndexs(newIndexs);
    }



 
   
     private void CreateFKOfTable(List viewForeignKeys) {
        Iterator itFkeys = viewForeignKeys.iterator();
        ViewForeignKey viewForeignKey = null;

        while (itFkeys.hasNext()){
            viewForeignKey = (ViewForeignKey) itFkeys.next();

            if (viewForeignKey.getOwnerTable().equals(this.viewTable.getName().trim())){
                this.getViewForeignKeysOfTableToShow().add(viewForeignKey);
                itFkeys.remove();
            }
        }
     }


    private void putInFieldsIsForeignKeys(){
        Iterator itFkeys = viewForeignKeysOfTableToShow.iterator();
        ViewForeignKey viewForeignKey = null;

        while (itFkeys.hasNext()){
            viewForeignKey = (ViewForeignKey) itFkeys.next();

            Iterator itFields = this.viewTable.getFields().iterator();
            ViewField viewField = null;
            
            while (itFields.hasNext()){
                 viewField = (ViewField) itFields.next();
                if (viewField.getName().equals(viewForeignKey.getFieldName()))
                    viewField.setForeignKey(true);
            }
        }
    }

}

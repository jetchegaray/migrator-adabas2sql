/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelTables.java
 *
 * Created on 11-nov-2010, 15:20:00
 */

package views;



import controllers.HandlePrefixesOracle;
import controllers.HandleViewTablesInFile;
import controllers.TransformViewTable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.Table;

/**
 *
 * @author javimetal
 */
public class JPanelTables extends javax.swing.JDialog{

    private List viewTables;
    private HandleViewTablesInFile handleFile;
    private List otherCases;
    private List viewForeignKeys;
    private HandlePrefixesOracle handlePrefix;
    /** Creates new form JPanelTables */
    private String type;

    public JPanelTables(java.awt.Frame parent,HandleViewTablesInFile loadViewTables,HandlePrefixesOracle handlePrefix,String type) {
        super(parent, true);
        loadViewTables.processSQLAndGetViewTables();
        this.viewTables = loadViewTables.getViewTables();
        this.otherCases = loadViewTables.getOtherCases();
        this.viewForeignKeys = loadViewTables.getForeignKeys();
        this.handlePrefix = handlePrefix;
        this.type = type;
        createForeignsKeysFromScript(handlePrefix);
        this.handleFile = loadViewTables;
        initComponents();
    }



   
      public class ViewTableModelListener implements TableModelListener{
                private JPanelTables jpanel;

                public void setJPanel(JPanelTables jpanel){
                    this.jpanel = jpanel;
                }

                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.UPDATE){
                        jpanel.saveTablesChanges();
                    }
                }
            }

       public class ViewCasesModelListener implements TableModelListener{
                private JPanelTables jpanel;

                public void setJPanel(JPanelTables jpanel){
                    this.jpanel = jpanel;
                }

                public void tableChanged(TableModelEvent e) {
                    jpanel.saveCasesChanges();
                }
            }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonFields = new javax.swing.JButton();
        jButtonProcessSQL = new javax.swing.JButton();
        jTabbedPaneTablesAndSentencies = new javax.swing.JTabbedPane();
        jScrollPaneTableoFTables = new javax.swing.JScrollPane();
        ViewTableModelListener viewTableModelListener= new ViewTableModelListener();
        viewTableModelListener.setJPanel(this);
        jTableOfTables = new javax.swing.JTable();
        jScrollPaneTableOfSentencies = new javax.swing.JScrollPane();
        ViewCasesModelListener viewCasesModelListener= new ViewCasesModelListener();
        viewCasesModelListener.setJPanel(this);
        jTableCases = new javax.swing.JTable();
        jButtonTransformToOracle = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonEdit = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione una accion");

        jButtonFields.setText("Ver Campos");
        jButtonFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFieldsActionPerformed(evt);
            }
        });

        jButtonProcessSQL.setText("Salvar en Archivo");
        jButtonProcessSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProcessSQLActionPerformed(evt);
            }
        });

        jTabbedPaneTablesAndSentencies.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneTablesAndSentenciesStateChanged(evt);
            }
        });

        jTableOfTables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Seleccionar", "Nombre", "Descripcion", "Volumetria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableOfTables.setRowSelectionAllowed(false);
        jTableOfTables.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTableOfTables.setSelectionForeground(new java.awt.Color(0, 0, 0));
        LoadRows();
        this.jTableOfTables.getModel().addTableModelListener(viewTableModelListener);
        jScrollPaneTableoFTables.setViewportView(jTableOfTables);
        jTableOfTables.getColumnModel().getColumn(0).setMinWidth(80);
        jTableOfTables.getColumnModel().getColumn(0).setMaxWidth(80);

        jTabbedPaneTablesAndSentencies.addTab("Tablas", jScrollPaneTableoFTables);

        jTableCases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sentencia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        LoadCases();
        this.jTableCases.getModel().addTableModelListener(viewCasesModelListener);
        jScrollPaneTableOfSentencies.setViewportView(jTableCases);

        jTabbedPaneTablesAndSentencies.addTab("Sentencias", jScrollPaneTableOfSentencies);

        jButtonTransformToOracle.setText("Convertir nombres a Oracle");
        if (!this.type.equals("oracle")){
            this.jButtonTransformToOracle.setEnabled(false);
        }
        jButtonTransformToOracle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTransformToOracleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPaneTablesAndSentencies, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonFields)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonTransformToOracle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonProcessSQL)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jButtonProcessSQL, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jButtonTransformToOracle, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jButtonFields, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPaneTablesAndSentencies, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButtonAdd.setText("Agregar");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Borrar");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonEdit.setText("Editar Texto");
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });

        jButton1.setText("Seleccionar todo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonEdit)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFieldsActionPerformed

        DefaultTableModel model = ((DefaultTableModel)this.jTableOfTables.getModel());
        boolean isOneSelected = false;
        String name = null;


        for (int i = 0;i<this.jTableOfTables.getRowCount();i++){
            if (((Boolean)model.getValueAt(i, 0)).equals(Boolean.TRUE)){
                name = (String) model.getValueAt(i, 1);
                isOneSelected = (isOneSelected == true) ? false : true;
            }
        }

        if (!isOneSelected){
            DialogOnlyAccept dialog = new DialogOnlyAccept(null, true,"Debe Seleccionar una sola tabla");
            dialog.setVisible(true);
            dialog.dispose();
        }else{
            JPanelFieldsTable panelFields = new JPanelFieldsTable(null,findViewTable(name),this.viewForeignKeys);
            panelFields.setTitle("Editor de Campos");
            panelFields.setLocation(140, 140);
            panelFields.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            panelFields.setVisible(true);

            List FKInTable = panelFields.getViewForeignKeysOfTableToShow();
            this.viewForeignKeys.addAll(FKInTable);
           
        }
    }//GEN-LAST:event_jButtonFieldsActionPerformed

    private void jButtonProcessSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProcessSQLActionPerformed

        DefaultTableModel model = ((DefaultTableModel)this.jTableOfTables.getModel());
        List viewTablesPersistanceInSQL = new ArrayList();

        for (int i = 0;i < this.jTableOfTables.getRowCount();i++ ){

            if (((Boolean)model.getValueAt(i, 0)).booleanValue()){ //esta seleccionada 
                viewTablesPersistanceInSQL.add(this.viewTables.get(i));
            }
        }
   
        this.handleFile.SaveViewTablesAnOtherCasesToFile(viewTablesPersistanceInSQL,this.viewForeignKeys,this.otherCases);
        //ya se guardaroin los cambios.
        DialogOnlyAccept dialog = new DialogOnlyAccept(null, true,"ReGeneracion XML terminada satisfactoriamente");
       dialog.setVisible(true);
       dialog.dispose();
}//GEN-LAST:event_jButtonProcessSQLActionPerformed

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed

        if (this.jTableOfTables.isShowing())
        {

            int row = this.jTableOfTables.getSelectedRow();
            int column = this.jTableOfTables.getSelectedColumn();

            if (column == 2 || column == 3){
                DefaultTableModel model = ((DefaultTableModel)this.jTableOfTables.getModel());
                String text = (String) model.getValueAt(row, column);

                JDialogText panelTextZoom = new JDialogText(null);

                panelTextZoom.getjTextPaneZoom().setText(text);
                panelTextZoom.setTitle("Editor de Texto");
                panelTextZoom.setLocation(140, 140);
                panelTextZoom.setVisible(true);

                model.setValueAt(panelTextZoom.getjTextPaneZoom().getText(),row, column);
            }else{
                DialogOnlyAccept dialog = new DialogOnlyAccept(null, true,"Debe Seleccionar una Celda de Comentarios");
                dialog.setVisible(true);
                dialog.dispose();
            }
            
        }else if (this.jTableCases.isShowing()){
            int row = this.jTableCases.getSelectedRow();

            DefaultTableModel model = ((DefaultTableModel)this.jTableCases.getModel());
            String text = (String) model.getValueAt(row, 0);

            JDialogText panelTextZoom = new JDialogText(null);

            panelTextZoom.getjTextPaneZoom().setText(text);
            panelTextZoom.setTitle("Editor de Texto");
            panelTextZoom.setLocation(140, 140);
            panelTextZoom.setVisible(true);

            model.setValueAt(panelTextZoom.getjTextPaneZoom().getText(),row, 0);
        }
    }//GEN-LAST:event_jButtonEditActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
       DefaultTableModel model = ((DefaultTableModel)this.jTableCases.getModel());
         Object rows[];

         if (this.jTableCases.isShowing()){
                rows = new Object[1];
                rows[0] = String.valueOf(" ");
                this.otherCases.add(String.valueOf(" "));
                model.addRow(rows);

        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        DefaultTableModel model;
        int[] rows;

        if (this.jTableCases.isShowing()){
            model = ((DefaultTableModel)this.jTableCases.getModel());
            rows = this.jTableCases.getSelectedRows();
            for (int i =rows[0];i <= rows[rows.length-1];i++){
                model.removeRow(rows[0]);
                this.otherCases.remove(rows[0]);
            }
        }

    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jTabbedPaneTablesAndSentenciesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneTablesAndSentenciesStateChanged
          if (!this.jTableCases.isShowing()){
                this.jButtonAdd.setEnabled(false);
                this.jButtonDelete.setEnabled(false);
          }else{
              this.jButtonAdd.setEnabled(true);
              this.jButtonDelete.setEnabled(true);
          }
    }//GEN-LAST:event_jTabbedPaneTablesAndSentenciesStateChanged

    private void jButtonTransformToOracleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTransformToOracleActionPerformed
        TransformViewTable transform = new TransformViewTable();

        Iterator itViewTables = this.viewTables.iterator();
        ViewTable viewTable = null;
        List newViewTables = new ArrayList();

        while(itViewTables.hasNext()){
            viewTable = (ViewTable) itViewTables.next();
            viewTable = transform.tableAttunity2Oracle(viewTable, this.handlePrefix);
            viewTable.setCommentsOnField(transform.commentFields(viewTable.getCommentsOnField()));
            newViewTables.add(viewTable);
        }
        this.viewTables.clear();
        this.viewTables.addAll(newViewTables);

        if (this.viewForeignKeys != null){

            Iterator itViewForeignKeys = this.viewForeignKeys.iterator();
            ViewForeignKey viewForeignKey = null;

            while(itViewForeignKeys.hasNext()){
                viewForeignKey = (ViewForeignKey) itViewForeignKeys.next();
                viewForeignKey = transform.foreignKey2Oracle(viewForeignKey,this.handlePrefix);
            }
        }

        
        DefaultTableModel model = ((DefaultTableModel)this.jTableOfTables.getModel());
        int length = model.getRowCount();
        for (int i = 0;i < length;i++){
            model.removeRow(0);
        }
        LoadRows();
    }//GEN-LAST:event_jButtonTransformToOracleActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model;
        int count;
        model = ((DefaultTableModel)this.jTableOfTables.getModel());

        count = model.getRowCount();
        for (int i=0;i < count ;i++){
            model.setValueAt(Boolean.valueOf(true), i, 0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonFields;
    private javax.swing.JButton jButtonProcessSQL;
    private javax.swing.JButton jButtonTransformToOracle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPaneTableOfSentencies;
    private javax.swing.JScrollPane jScrollPaneTableoFTables;
    private javax.swing.JTabbedPane jTabbedPaneTablesAndSentencies;
    private javax.swing.JTable jTableCases;
    private javax.swing.JTable jTableOfTables;
    // End of variables declaration//GEN-END:variables


    // carga las filas en donde van las tablas
      private void LoadRows(){

        if (viewTables != null){

            Iterator itVTables = viewTables.iterator();
            ViewTable viewTable = null;
            int row = 0;
            Object rows[] = new Object[5];
            DefaultTableModel model = ((DefaultTableModel)this.jTableOfTables.getModel());

            while (itVTables.hasNext()){
                viewTable = (ViewTable)itVTables.next();
                rows[0] = Boolean.valueOf(false);
                rows[1] = viewTable.getName();
                rows[2] = viewTable.getComments();
                rows[4] = "";
                model.addRow(rows);

                row++;
            }
        }
    }



    private ViewTable findViewTable(String item) {
        Iterator itViewTables = this.viewTables.iterator();
        ViewTable viewTable = null;

        while(itViewTables.hasNext()){
            viewTable = (ViewTable) itViewTables.next();
            if (viewTable.getName().equals(item))
                break;
            viewTable = null;
        }
        return viewTable;
    }


    private void saveTablesChanges() {

        DefaultTableModel model = ((DefaultTableModel)this.jTableOfTables.getModel());
        Vector rows = model.getDataVector();
       
        for (int i=0;i < rows.size();i++){

            Vector column = (Vector)rows.get(i);
            ViewTable newTable = (ViewTable) this.viewTables.get(i);

            newTable.setName((String)column.get(1));
            newTable.setComments((String) column.get(2));
        }
    }


  
    private void LoadCases(){

        if (this.otherCases != null){

            Iterator itVCases = otherCases.iterator();
            String viewCases = null;
            Object rows[] = new Object[1];
            DefaultTableModel model = ((DefaultTableModel)this.jTableCases.getModel());

            while (itVCases.hasNext()){
                viewCases = (String)itVCases.next();
                if (!viewCases.trim().equals("")){
                    rows[0] = viewCases;
                    model.addRow(rows);
                }
            }
        }
    }


    private void saveCasesChanges() {
        DefaultTableModel model = ((DefaultTableModel)this.jTableCases.getModel());
        Vector rows = model.getDataVector();
       
        for (int i=0;i < rows.size();i++){

            Vector column = (Vector)rows.get(i);
            String newCase = (String) this.otherCases.get(i);

            newCase = (String)column.get(0);
            this.otherCases.set(i, newCase);
        }
    }



    private void createForeignsKeysFromScript(HandlePrefixesOracle handlePrefix) {

        Iterator itTables = this.viewTables.iterator();

        while (itTables.hasNext()){
            ViewTable viewTable = (ViewTable)itTables.next();

            Iterator itFields = viewTable.getFields().iterator();

            while (itFields.hasNext()){
                ViewField viewField = (ViewField)itFields.next();

                if (IsForeignKeyinViewTable(viewField.getName(),viewTable.getName(),viewTable.getNameApp(),handlePrefix)){
                    String tableReferences = searchTableReferences(handlePrefix,viewTable.getNameApp(),viewField.getName());
                   
                    //si esta en el script
                    if (findViewTable(tableReferences) != null)
                    {
                        String fieldReferences = searchFieldReferencesInListViewTables(viewField.getName(),viewTable.getName());

                        if (fieldReferences != null){
                            viewField.setForeignKey(true);

                            ViewForeignKey viewForeignKey = new ViewForeignKey();
                            viewForeignKey.setName("FK_"+viewField.getName());
                            viewForeignKey.setFieldReferences(fieldReferences);
                            viewForeignKey.setTableReferences(tableReferences);
                            viewForeignKey.setOwnerTable(viewTable.getName());
                            viewForeignKey.setFieldName(viewField.getName());
                            if (!this.viewForeignKeys.contains(viewForeignKey)){
                                this.viewForeignKeys.add(viewForeignKey);
                            }
                        }
                    }
                }
            }
        }
    }




    private boolean IsForeignKeyinViewTable(String fieldName, String tableName,String appName,HandlePrefixesOracle handlePrefix) {

        String fieldPrefix = fieldName.substring(0,fieldName.indexOf("_"));
        String prefixNameField = null;
  
        if (fieldPrefix.length() == 3){
            prefixNameField = handlePrefix.getPrefixFieldFromTableName(tableName);
            if (prefixNameField != null && !prefixNameField.equals(fieldPrefix)){
                //si existe el prefijo en el archivo y no igual al que me dio el campo
                //es una foreign key
                return true;
            }
        }else if (fieldPrefix.length() == 5){
            String appNameInField = fieldPrefix.substring(0,2);
            if (!appName.equals(appNameInField) && fieldName.charAt(4) == 'B'){
                return false;
                //el dia que se sepa de donde sacar la tabla pasarloa true
                //e implemtar por afuera lo que sea necesario en el listForeigmKey
                //es una foreign key pero no se de que tabla es.
            }
        }

        return false;
    }


    private String searchTableReferences(HandlePrefixesOracle handlePrefix,String appName, String fieldName) {
        String tableNameReferences = null;

        tableNameReferences = handlePrefix.getTableNameFromPrefixFieldName( appName,fieldName.substring(0,3));
        if (tableNameReferences != null)
            return tableNameReferences.replaceAll("-", "_");

        return tableNameReferences;
    }

    //busco el campo al que referencia una foreign key de una tabla, ya que al llamr a esta estoy recorriendo
    //todas las FK,para buscar el campo al que refercnian debo recorrer todos los campos de todas las tablas
    
    private String searchFieldReferencesInListViewTables(String nameField,String ownerNameTable) {
        Iterator itTables = this.viewTables.iterator();
        String nameFieldReferencesToReturn = null;
        String nameFieldToSearch = getNameWithoutPrefixAndSufix(nameField);

        while (itTables.hasNext()){
            ViewTable viewTable = (ViewTable)itTables.next();

            if (viewTable.getName().equals(ownerNameTable))
                continue;

            Iterator itFields = viewTable.getFields().iterator();

            while (itFields.hasNext()){
                ViewField viewField = (ViewField)itFields.next();
                int cant = 0;

                if (viewField.getName().contains(nameFieldToSearch)){
                    nameFieldReferencesToReturn = viewField.getName();
                    if (++cant == 2){
                        return null;
                    }
                }
            }
        }
        return nameFieldReferencesToReturn;
    }



    private String getNameWithoutPrefixAndSufix(String fieldName) {
         String name = "";
        StringTokenizer tokenizer = new StringTokenizer(fieldName.concat("_"),"_");
        int k = 0;

        while(tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();

            if (k != 0 && tokenizer.hasMoreTokens())
                name += token+"_";
            k++;
        }
        name = name.substring(0,name.lastIndexOf("_"));
        if (name.trim().equals(""))
            return null;

        return name;
    }



}






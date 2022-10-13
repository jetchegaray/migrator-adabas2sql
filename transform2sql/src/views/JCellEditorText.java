/*
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

public class EditorCellRenderer extends AbstractCellEditor implements
TableCellEditor, TableCellRenderer {

private EditorTextPane cellText = new EditorTextPane();

   protected EventListenerList listenerList = new EventListenerList();
   protected ChangeEvent changeEvent = new ChangeEvent(this);

public EditorCellRenderer(EditorTextPane secPane, JPanel tablePanel, JTable
table, JScrollPane tableScroll) {

 //set up the text pane with the correct document
 cellText.getDocument().addUndoableEditListener(new
EditorUndoableEditListener(Editor.undoAction, Editor.redoAction,
Editor.undo));
 SimpleAttributeSet attrs = new SimpleAttributeSet();
       StyleConstants.setFontFamily(attrs, "SansSerif");
       StyleConstants.setFontSize(attrs, 12);
       cellText.setCharacterAttributes(attrs, false);
 TrackChangesStyledDocument trackDoc = new
TrackChangesStyledDocument(cellText, secPane, tablePanel, table,
tableScroll);
 cellText.setStyledDocument(trackDoc);
 cellText.setBorder(BorderFactory.createLineBorder(Color.black));

}

   public void addCellEditorListener(CellEditorListener listener) {

      listenerList.add(CellEditorListener.class, listener);

   }

   public void removeCellEditorListener(CellEditorListener listener) {

      listenerList.remove(CellEditorListener.class, listener);

   }

   protected void fireEditingStopped() {

 CellEditorListener listener;
 Object[] listeners = listenerList.getListenerList();
 for (int i = 0; i < listeners.length; i++) {

  if (listeners[i] == CellEditorListener.class) {

     listener = (CellEditorListener) listeners[i + 1];
     listener.editingStopped(changeEvent);

  }

 }

   }

   protected void fireEditingCanceled() {

 CellEditorListener listener;
 Object[] listeners = listenerList.getListenerList();
 for (int i = 0; i < listeners.length; i++) {

  if (listeners[i] == CellEditorListener.class) {

     listener = (CellEditorListener) listeners[i + 1];
     listener.editingCanceled(changeEvent);

  }

 }

   }

   public void cancelCellEditing() {

      fireEditingCanceled();

   }

   public boolean stopCellEditing() {

 fireEditingStopped();
 return true;

   }

   public boolean isCellEditable(EventObject event) {

      return true;

   }

   public boolean shouldSelectCell(EventObject event) {

      return true;

   }

   public Object getCellEditorValue() {

      return cellText.getText();

   }

public Component getTableCellEditorComponent(JTable table, Object value,
boolean isSelected, int row, int column) {

       if (value != null) {

        cellText.setText(value.toString());

       } else {

        cellText.setText("");

       }
 return cellText;

}

public Component getTableCellRendererComponent(JTable table,
 Object value, boolean isSelected, boolean hasFocus, int row, int column) {

       if (value != null) {

        cellText.setText(value.toString());

       } else {

        cellText.setText("");

       }
 return cellText;

   }

}*/
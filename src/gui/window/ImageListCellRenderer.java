package gui.window;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

@SuppressWarnings("rawtypes")
public class ImageListCellRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList jList, Object value,
												  int cellIndex, boolean isSelected, boolean cellHasFocus) {
		
		if (value instanceof JPanel){
	      Component component = (Component) value;
	      component.setForeground (Color.white);
	      //component.setBackground (isSelected ? UIManager.getColor("Table.focusCellForeground") : Color.white);
	      component.setBackground (isSelected ? new Color(239,233,165) : new Color(200,233,165));
	      return component;
	    }
	    else
	    {
	      // TODO - I get one String here when the JList is first rendered; proper way to deal with this?
	      //System.out.println("Got something besides a JPanel: " + value.getClass().getCanonicalName());
	      return new JLabel("???");
	    }
	}

}

package xyz.chengzi.aeroplanechess.Winner;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WinnerProperty {
	protected PropertyChangeSupport propertyChangeSupport;
	private int win;

	public WinnerProperty () {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void set(int win) {
		int before = this.win;
		this.win = win;
		propertyChangeSupport.firePropertyChange("WinnerProperty", before, this.win);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
}


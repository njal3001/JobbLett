package jobblett.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

//TODO: Er litt rart at vi har en klasse som heter noe med
//med jobblett i core modulen.
public abstract class JobblettPropertyChangeSupporter {
  protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public void addListener(PropertyChangeListener pcl) {
    pcs.addPropertyChangeListener(pcl);
  }

  protected void removeListener(PropertyChangeListener pcl) {
    pcs.removePropertyChangeListener(pcl);
  }

  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    pcs.firePropertyChange(propertyName, oldValue, newValue);
  }

  // Trenger jo egentlig ikke oldValue
  protected void firePropertyChange(String propertyName, Object newValue) {
    firePropertyChange(propertyName, null, newValue);
  }

}

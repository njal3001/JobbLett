package jobblett.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//TODO: Er litt rart at vi har en klasse som heter noe med jobblett
// i core modulen.
/**
 * SuperClass for all List-classes in Jobblett. Type "k" is the class-type used for in the
 * identification. Type "T" is the class-type which is stored in the list.
 */
public abstract class JobblettList<K, T> extends JobblettPropertyChangeSupporter
    implements Iterable<T>, PropertyChangeListener {

  private List<T> list = new ArrayList<>();

  public T get(K k) {
    return stream().filter(a -> identifier(a).equals(k)).findAny().orElse(null);
  }

  //TODO: Denne metoden passer ikke med WorkspaceGroupList
  /**
   * Adds objects to the list.
   *
   * @param objects objects to be added to the list
   * @return if the objects were added or not
   */
  public boolean add(T... objects) {
    for (T o : objects) {
      if (get(identifier(o)) != null) {
        optionalAlreadyExists();
      }
      if (o instanceof JobblettPropertyChangeSupporter) {
        ((JobblettPropertyChangeSupporter) o).addListener(this);
      }
    }
    boolean result = list.addAll(Arrays.asList(objects));
    //TODO
    //firePropertyChange(simpleTypeName() + "List", list);
    if (optionalComparator() != null) {
      Collections.sort(list, optionalComparator());
    }
    return result;
  }

  /**
   * Adds everything in the Iterable to the list.
   *
   * @param jobblettList contains all objects to be added to this list.
   * @return if the objects were added or not.
   */
  public boolean addAll(Iterable<T> jobblettList) {
    Collection<T> helperList = new ArrayList<>();
    jobblettList.forEach(helperList::add);
    return add((T[]) helperList.toArray());
  }

  /**
   * Checks if list is empty.
   *
   * @return if list is empty or not.
   */
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /**
   * Removes the object from the list.
   *
   * @param o object to be removed from list.
   * @return if the object was removed or not.
   */
  public boolean remove(T o) {
    boolean removed = list.remove(o);
    if (removed) {
      if (o instanceof JobblettPropertyChangeSupporter) {
        ((JobblettPropertyChangeSupporter) o).removeListener(this);
      }
    }
    return removed;
  }

  //Trenger vi alle disse metodene?

  public boolean contains(T o) {
    return list.contains(o);
  }

  public Stream<T> stream() {
    return list.stream();
  }

  public List<T> filter(Predicate<T> predicate) {
    return stream().filter(predicate).collect(Collectors.toList());
  }

  public int size() {
    return list.size();
  }

  protected int indexOf(T t) {
    return list.indexOf(t);
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }

  @Override
  public String toString() {
    return simpleTypeName() + "List=" + list;
  }

  //TODO: fjernes?
  @Override
  public boolean equals(Object o) {
    if (o instanceof JobblettList) {
      JobblettList newList = (JobblettList) o;
      return list.equals(newList.list);
    } else {
      return super.equals(o);
    }
  }

  @Override
  public int hashCode() {
    assert false : "hashCode not designed";
    return 42; // any arbitrary constant will do
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    T source = (T) evt.getSource();
    String propertyName = "" + simpleTypeName() + "{" + identifier(source) + "}: " 
        + evt.getPropertyName();
    firePropertyChange(propertyName, evt.getOldValue(), evt.getNewValue());
  }

  protected String simpleTypeName() {
    ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    String fullTypeName = parameterizedType.getActualTypeArguments()[1].getTypeName();
    String[] splitTypeName = fullTypeName.split("\\.");
    String simpleTypeName = splitTypeName[splitTypeName.length - 1];
    return simpleTypeName;
  }

  protected abstract K identifier(T t);

  protected void optionalAlreadyExists() {

  }

  protected Comparator<T> optionalComparator() {
    return null;
  }


}

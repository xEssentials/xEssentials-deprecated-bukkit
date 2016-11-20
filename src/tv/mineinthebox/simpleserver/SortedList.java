package tv.mineinthebox.simpleserver;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * a List which work with a anonymous Comperator<E> inside the constructor
 *
 * @param <E> - the element type of the list
 */
public class SortedList<E> extends AbstractList<E> {
	
	private final Comparator<E> comp;
	private final ArrayList<E> list = new ArrayList<E>();
	
	public SortedList(Comparator<E> comp) {
		this.comp = comp;
	}
	
	/**
	 * adds a element to the list
	 * 
	 * @return boolean if succeed otherwise false
	 */
	@Override
	public boolean add(E e) {
		boolean bol = list.add(e);
		Collections.sort(list, (comp != null ? comp : null));
		return bol;
	}
	
	/**
	 * adds a element to the list
	 * 
	 * @param i the index where the element should be
	 * @param e the element type
	 */
	@Override
	public void add(int i, E e) {
		list.add(i, e);
		Collections.sort(list, (comp != null ? comp : null));
	}

	/**
	 * gets the element from the list
	 * 
	 * @return E the element object
	 */
	@Override
	public E get(int index) {
		return list.get(index);
	}
	
	/**
	 * removes a element from the list
	 * 
	 * @param index the number where the element should be
	 * @return E the removed element
	 */
	@Override
	public E remove(int index) {
		E type = list.remove(index);
		Collections.sort(list, (comp != null ? comp : null));
		return type;
	}
	
	/**
	 * removes a element from the list
	 * 
	 * @param obj the object which should be removed
	 * @return boolean true when removed otherwise false
	 */
	@Override
	public boolean remove(Object obj) {
		boolean bol = list.remove(obj);
		Collections.sort(list, (comp != null ? comp : null));
		return bol;
	}

	/**
	 * returns the size of elements holding by this list
	 * 
	 * @return int the size of elements
	 */
	@Override
	public int size() {
		return list.size();
	}
	
	/**
	 * clears the list till its being empty
	 */
	@Override
	public void clear() {
		list.clear();
	}
	
	
}

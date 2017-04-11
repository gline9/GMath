package gmath.sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * class that makes an ordered tuple of elements if it gets them from a type of
 * list. This will make a copy of the list so the type is immutable.
 * 
 * @author Gavin
 *
 * @param <R>
 *            type the tuple is over
 */
public class Tuple<R> extends Set<R> implements Iterable<R>{

	private final ArrayList<R> data;

	public Tuple(List<R> data) {
		// create a new array list
		this.data = new ArrayList<R>(data.size());

		// add all of the data to it
		this.data.addAll(data);
	}

	@Override
	public boolean contains(R x) {
		// return true if data contains x
		return data.contains(x);
	}

	/**
	 * used to get the size of the ordered tuple
	 * 
	 * @return size of the tuple
	 */
	public int size() {
		return data.size();
	}

	/**
	 * used to access individual elements of the tuple. If out of range this
	 * will return null.
	 * 
	 * @param index
	 *            index to look at, first index is 0
	 * @return element at that index
	 */
	public R getElement(int index) {
		try {
			return data.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public Iterator<R> iterator() {
		return data.iterator();
	}
	
	

}

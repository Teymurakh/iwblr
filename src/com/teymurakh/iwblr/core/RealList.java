package com.teymurakh.iwblr.core;

import java.util.Collection;
import java.util.Iterator;

public class RealList<E> implements Iterable<E>, Collection<E>{
	private transient Object[] elementData;
	
	private int last;
	private final int dataSize;
	
	private int modCount;
	
	
	public RealList(int size) {
		this.dataSize = size;
		this.last = 0;
		this.elementData = new Object[dataSize];
	}
	
	public void addEntity(E item) {
		elementData[last] = item;
		last++;
		modCount++;
	}
	
	public void removeEntity(E item) {
		for (int index = 0; index < last; index++) {
			if (item.equals(elementData[index])) {
				fastRemove(index);
			}
		}
		modCount++;
	}

	public void clear() {
		for (int index = 0; index < last; index++) {
			elementData[index] = null;
		}
		
		last = 0;
		modCount++;
	}
	
	private void fastRemove(int index) {
		int numMoved = last - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index+1, elementData, index, numMoved);
		elementData[--last] = null; // Let gc do its work
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr<E>();
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return last;
	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("hiding")
	private class Itr<E> implements Iterator<E> {
		private int cursor = 0;
		
		
		@Override
		public boolean hasNext() {
			return cursor != last && cursor < last;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			
			cursor++;
			return (E) elementData[cursor-1];
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
		}

	}
}

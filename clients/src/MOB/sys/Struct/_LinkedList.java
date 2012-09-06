package MOB.sys.Struct;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 重写LinkedList 主要是同步出入方法
 * @author max
 *
 * @param <E>
 */
public class _LinkedList<E> extends LinkedList<E> {

	public synchronized void addLast(E e){
		super.addLast(e);
	}
	
	public synchronized E getFirst(){
		return super.getFirst();
	}

	@Override
	public synchronized E getLast() {
		// TODO Auto-generated method stub
		return super.getLast();
	}

	@Override
	public synchronized E removeFirst() {
		// TODO Auto-generated method stub
		return super.removeFirst();
	}

	@Override
	public synchronized E removeLast() {
		// TODO Auto-generated method stub
		return super.removeLast();
	}

	@Override
	public synchronized void addFirst(E e) {
		// TODO Auto-generated method stub
		super.addFirst(e);
	}

	@Override
	public synchronized boolean contains(Object o) {
		// TODO Auto-generated method stub
		return super.contains(o);
	}

	@Override
	public synchronized int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	@Override
	public synchronized boolean add(E e) {
		// TODO Auto-generated method stub
		return super.add(e);
	}

	@Override
	public synchronized boolean remove(Object o) {
		// TODO Auto-generated method stub
		return super.remove(o);
	}

	@Override
	public synchronized boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return super.addAll(c);
	}

	@Override
	public synchronized boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return super.addAll(index, c);
	}

	@Override
	public synchronized void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	@Override
	public synchronized E get(int index) {
		// TODO Auto-generated method stub
		return super.get(index);
	}

	@Override
	public synchronized E set(int index, E element) {
		// TODO Auto-generated method stub
		return super.set(index, element);
	}

	@Override
	public synchronized void add(int index, E element) {
		// TODO Auto-generated method stub
		super.add(index, element);
	}

	@Override
	public synchronized E remove(int index) {
		// TODO Auto-generated method stub
		return super.remove(index);
	}

	@Override
	public synchronized int indexOf(Object o) {
		// TODO Auto-generated method stub
		return super.indexOf(o);
	}

	@Override
	public synchronized int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return super.lastIndexOf(o);
	}

	@Override
	public synchronized E peek() {
		// TODO Auto-generated method stub
		return super.peek();
	}

	@Override
	public synchronized E element() {
		// TODO Auto-generated method stub
		return super.element();
	}

	@Override
	public synchronized E poll() {
		// TODO Auto-generated method stub
		return super.poll();
	}

	@Override
	public synchronized E remove() {
		// TODO Auto-generated method stub
		return super.remove();
	}

	@Override
	public synchronized boolean offer(E e) {
		// TODO Auto-generated method stub
		return super.offer(e);
	}

	@Override
	public synchronized boolean offerFirst(E e) {
		// TODO Auto-generated method stub
		return super.offerFirst(e);
	}

	@Override
	public synchronized boolean offerLast(E e) {
		// TODO Auto-generated method stub
		return super.offerLast(e);
	}

	@Override
	public synchronized E peekFirst() {
		// TODO Auto-generated method stub
		return super.peekFirst();
	}

	@Override
	public synchronized E peekLast() {
		// TODO Auto-generated method stub
		return super.peekLast();
	}

	@Override
	public synchronized E pollFirst() {
		// TODO Auto-generated method stub
		return super.pollFirst();
	}

	@Override
	public synchronized E pollLast() {
		// TODO Auto-generated method stub
		return super.pollLast();
	}

	@Override
	public synchronized void push(E e) {
		// TODO Auto-generated method stub
		super.push(e);
	}

	@Override
	public synchronized E pop() {
		// TODO Auto-generated method stub
		return super.pop();
	}

	@Override
	public synchronized boolean removeFirstOccurrence(Object o) {
		// TODO Auto-generated method stub
		return super.removeFirstOccurrence(o);
	}

	@Override
	public synchronized boolean removeLastOccurrence(Object o) {
		// TODO Auto-generated method stub
		return super.removeLastOccurrence(o);
	}

	@Override
	public synchronized ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return super.listIterator(index);
	}

	@Override
	public synchronized Iterator<E> descendingIterator() {
		// TODO Auto-generated method stub
		return super.descendingIterator();
	}

	@Override
	public synchronized Object clone() {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public synchronized Object[] toArray() {
		// TODO Auto-generated method stub
		return super.toArray();
	}

	@Override
	public synchronized <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return super.toArray(a);
	}
	
	
}//end of class _LinkedList

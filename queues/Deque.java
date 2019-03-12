/* *****************************************************************************
 *  Name: Wei Sun
 *  Date: 3/2/2019
 *  Description: The class Deque implements a double-ended queue.
 *               
 **************************************************************************** */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n;

    // Using a doubly-linked list node as the internal class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
        assert check();
    }                          
    
    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }                 

    // return the number of items on the deque
    public int size() {
        return n;
    }                       

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Null item");
        }
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.prev = null;
        if (isEmpty()) {
            newFirst.next = null;
            last = newFirst;   
        } else {
            Node oldFirst = first;
            newFirst.next = oldFirst;
            oldFirst.prev = newFirst;
        }
        first = newFirst;
        n++;
        assert check();
    }         

    // add the item to the end
    public void addLast(Item item) {          
        if (item == null) throw new java.lang.IllegalArgumentException("Null item");
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        if (isEmpty()) {
            newLast.next = null;
            first = newLast;
        } else {
            Node oldLast = last;
            oldLast.next = newLast;
            newLast.prev = oldLast;
        }
        last = newLast;
        n++;
        assert check();
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Empty deque");
        Node node = first;
        if (node.next == null) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        n--;
        assert check();
        return node.item;
    }             

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Empty deque");
        Node node = last;
        if (node.prev == null) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        n--;
        assert check();
        return node.item;
    }

    // check internal invariants
    private boolean check() {
        // check a few properties of instance variable 'first'
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null) return false;
        }
        else if (n == 1) {
            if (first == null)      return false;
            if (first.next != null) return false;
        }
        else {
            if (first == null)      return false;
            if (first.next == null) return false;
        }

        // check internal consistency of instance variable n
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != n) return false;

        return true;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No more items in iteration.");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        
    }   
}

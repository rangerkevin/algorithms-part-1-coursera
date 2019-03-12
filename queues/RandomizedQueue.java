/* *****************************************************************************
 *  Name: Wei Sun
 *  Date: 3/2/2019
 *  Description: The class RandomizedQueue implements a randomized queue.
 *               The pop operation will remove an item randomly chosen in the data structure.
 *
 **************************************************************************** */
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n;
    private Item[] a;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }                

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }                
    
    // return the number of items on the randomized queue
    public int size() {                       
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("Null item");
        if (n == a.length) resize(a.length * 2);
        a[n++] = item;
    }           

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Empty RandomizedQueue");
        int index = StdRandom.uniform(n);
        Item item = a[index];
        a[index] = a[n - 1];
        a[n - 1] = null;
        n--;
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }                    

    // return a random item (but do not remove it)
    public Item sample() {                    
        if (isEmpty()) throw new java.util.NoSuchElementException("Empty RandomizedQueue");
        int index = StdRandom.uniform(n);
        return a[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }         

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] indices;
        private int m;

        private RandomizedQueueIterator() {
            indices = new int[n];
            m = 0;

            for (int i = 0; i < n; i++) {
                indices[i] = i;
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() { return m < n; }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No more items in interation");
            Item item = a[indices[m++]];
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
    
    }  
}

import java.util.Iterator;

/**
 * Created by thamilton on 11/24/14.
 */
public class AbstractTree<E extends Comparable<E>> implements Tree<E> {
    /**
     * @param e
     * @return true if the element is in the tree
     */
    public boolean search(E e) {
        return false;
    }

    /**
     * Insert element 0 into the binary tree
     *
     * @param e
     * @return true if the element is inserted successfully
     */
    public boolean insert(E e) {
        return false;
    }

    /**
     * Delete the specified element from the tree
     *
     * @param e
     * @return true if the element is deleted successfully
     */
    public boolean delete(E e) {
        return false;
    }

    /**
     * In-order traversal from the root
     */
    public void inorder() {

    }

    /**
     * Post-order traversal from the root
     */
    public void postorder() {

    }

    /**
     * Pre-order traversal from the root
     */
    public void preorder() {

    }

    /**
     * @return the number of nodes in the tree
     */
    public int getSize() {
        return 0;
    }

    /**
     * @return true if the tree is empty
     */
    public boolean isEmpty() {
        return getSize() == 0;
    }

    /**
     * @return an iterator to traverse the elements in the tree
     */
    public Iterator<E> iterator() {
        return null;
    }
}

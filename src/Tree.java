import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {

    /**
     *
     * @param e
     * @return true if the element is in the tree
     */
    public boolean search(E e);

    /**
     * Insert element 0 into the binary tree
     * @param e
     * @return true if the element is inserted successfully
     */
    public boolean insert(E e);

    /**
     * Delete the specified element from the tree
     * @param e
     * @return true if the element is deleted successfully
     */
    public boolean delete(E e);

    /**
     * In-order traversal from the root
     */
    public void inorder();

    /**
     * Post-order traversal from the root
     */
    public void postorder();

    /**
     * Pre-order traversal from the root
     */
    public void preorder();

    /**
     *
     * @return the number of nodes in the tree
     */
    public int getSize();

    /**
     *
     * @return true if the tree is empty
     */
    public boolean isEmpty();

    /**
     *
     * @return an iterator to traverse the elements in the tree
     */
    public Iterator<E> iterator();
}

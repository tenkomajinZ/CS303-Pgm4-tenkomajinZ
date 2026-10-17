
public class MyLinkedList<E> implements MyList<E> {
  private Node<E> head, tail;
  private int size = 0; // Number of elements in the list
  
  //Constructor:  Create a default list 
  public MyLinkedList() {
  }

  //Constructor: Create a list from an array of objects 
  public MyLinkedList(E[] objects) {
    for (int i = 0; i < objects.length; i++)
      add(objects[i]); 
  }

  //Retrieve the element at the head
  //PRE: none
  //POST:verify the list is not empty
  //     return the head element 
  public E getFirst() {
    if (size == 0) {
      return null;
    }
    else {
      return head.element;
    }
  }

  //Retrieve the element at the head
  //PRE: none
  //POST:verify the list is not empty
  //     return the tail element 
  public E getLast() {
    if (size == 0) {
      return null;
    }
    else {
      return tail.element;
    }
  }

  //Add the element to the head of the list
  //PRE: accepts the element to add
  //POST:creates the new node
  //     adds the element as the new 'head' element
  //     adjusts tail if the list was empty
  //     increases the size of the list
  public void addFirst(E e) {
    Node<E> newNode = new Node<>(e);  // Create a new node
    newNode.next = head;              // link the new node with the head
    head = newNode;                   // head points to the new node
    size++;                           // Increase list size

    if (tail == null)                 // the new node is the only node in list
      tail = head;
  }

  //Add an element to the end of the list
  //PRE: accepts the element to add
  //POST:creates the new node
  //     adds the element as the tail element
  //     adjusts head if the list was empty 
  //     increases the size of the list

  public void addLast(E e) {
    Node<E> newNode = new Node<>(e);  // Create a new for element e

    if (tail == null) {
      head = tail = newNode;          // The new node is the only node in list
    }
    else {
      tail.next = newNode;            // Link the new with the last node
      tail = newNode;                 // tail now points to the last node
    }

    size++;                           // Increase size
  }

  @Override 
  //TASK 1: ADD 
  
  //Add a new element at the specified index in this list
  //PRE: accepts the element to add & index location
  //POST:if index is 0 - addfirst
  //     if index is at or after size - addlast
  //     create node, get to index position
  //     adjust pointers, increase size

  public void add(int index, E e) {
    if (index < 0)
      throw new IndexOutOfBoundsException("Index: " + index);
    if (index == 0) {
      addFirst(e);
    }
    else if (index >= size) {
      addLast(e);
    }
    else {
      Node<E> current = head;
      for (int i = 0; i < index - 1; i++)
        current = current.next;
      Node<E> newNode = new Node<>(e);
      newNode.next = current.next;
      current.next = newNode;
      size++;
    }
  }


  //TASK 2: ADD BEFORE
  
  //PRE:  Accepts 2 values: the Prior value & the value to add 
  //POST: New node is added prior to the location of insertion

  public void addBefore(E prior, E data) {
    // PRE: prior is the value before which data should be inserted.
    // POST: inserts data before the first matching prior value, or throws if absent.
    if (head == null)
      throw new IllegalStateException("List is empty - prior not found");
    if (java.util.Objects.equals(head.element, prior)) {
      addFirst(data);
      return;
    }
    Node<E> previous = head;
    while (previous.next != null && !java.util.Objects.equals(previous.next.element, prior))
      previous = previous.next;
    if (previous.next == null)
      throw new IllegalStateException("Prior not found");
    Node<E> newNode = new Node<>(data);
    newNode.next = previous.next;
    previous.next = newNode;
    size++;
  }

  // Remove the head node and return the object
  //PRE:  None 
  //POST: Check if list is empty (return null if so)
  //      save head element, remove head value & adjust pointers
  //      return saved element 

  public E removeFirst() {
    if (size == 0) {
      return null;
    }
    else if (size == 1) {
      E temp = head.element;
      head = tail = null;
      size = 0;
      return temp;
    }
    else {
      E temp = head.element;
      head = head.next;
      size--;
      if (head == null) {
        tail = null;
      }
      return temp;
    }
  }

  /** Remove the last node and
   * return the object that is contained in the removed node. */
  public E removeLast() {
    if (size == 0) 
      return null;

    else if (size == 1) {
      E temp = head.element;
      head = tail = null;
      size = 0;
      return temp;
    }
    else {
      Node<E> current = head;

      for (int i = 0; i < size - 2; i++) {
        current = current.next;
      }

      E temp = tail.element;
      tail = current;
      tail.next = null;
      size--;
      return temp;
    }
  }

  //TASK 3: DELETE BEFORE
  
  //PRE:   Function accepts the element value immediately AFTER node to be deleted
  //POST:  The node prior to the value given is deleted 

  public E deleteBefore(E prior) {
    // PRE: prior is the value immediately after the node to delete.
    // POST: removes and returns the node before the first matching prior value.
    if (size < 2)
      throw new IllegalStateException("Delete before: unable to delete before");
    if (java.util.Objects.equals(head.element, prior))
      throw new IllegalStateException("Delete before: prior is the head");
    if (java.util.Objects.equals(head.next.element, prior))
      return removeFirst();
    Node<E> previous = head;
    while (previous.next != null && previous.next.next != null) {
      if (java.util.Objects.equals(previous.next.next.element, prior)) {
        E removed = previous.next.element;
        previous.next = previous.next.next;
        size--;
        return removed;
      }
      previous = previous.next;
    }
    throw new IllegalStateException("Delete before: prior not found");
  }

 
  @Override  
  //Remove the element at the specified position in this list
  //PRE: accepts the index value
  //POST:verifies the value (return null if so)
  //     use remove first & last if applicable
  //     else find index position & adjust pointers  
  //     decrement size
  //     return value
  public E remove(int index) {  
    if (index < 0 || index >= size) {
      return null;
    }
    else if (index == 0) {
      return removeFirst();
    }
    else if (index == size - 1) {
      return removeLast();
    }
    else {
      Node<E> previous = head;

      for (int i = 1; i < index; i++) {
        previous = previous.next;
      }

      Node<E> current = previous.next;
      previous.next = current.next;
      size--;
      return current.element;
    }
  }

  @Override 
  //Create a string that holds values in the array
  //PRE: none
  //POST:creates a string with array values & returns string
  public String toString() {
    if (isEmpty())
      return "[]";

    StringBuilder result = new StringBuilder("[");

    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      result.append(current.element);
      current = current.next;
      if (current != null) {
        result.append(", "); // Separate two elements with a comma
      }
      else {
        result.append("]"); // Insert the closing ] in the string
      }
    }
    return result.toString();
  }

  @Override 
  //Clear the list
  //PRE: none
  //POST:set head & tail to null & size to 0
  public void clear() {
    size = 0;
    head = tail = null;
  }

  @Override 
  //Return true if this list contains the element 
  //PRE: accepts the object
  //POST:checks data elements if found, returns true
  //     else returns false

  public boolean contains(Object e) {
    // PRE: e is the value to search for; null is allowed.
    // POST: returns true if a matching element is present; otherwise false.
    for (Node<E> current = head; 
      current != null; 
      current = current.next)

      if (java.util.Objects.equals(current.element, e))
        return true;
      
    return false;
  }

  @Override 
  //Retrieve the element at the index position
  //PRE: accepts the index
  //POST:verify the index & return null if invalid
  //     return the element 

  public E get(int index) {
    // PRE: index is a zero-based position.
    // POST: returns the element at index, or null when index is invalid.
    if (index < 0 || index >= size)
      return null;
    Node<E> current = head;
    for (int i = 0; i < index; i++)
      current = current.next;
    return current.element;
  }

  @Override 
  //Return the index of the first matching object or -1 if not found
  //PRE: accepts an object
  //POST:returns the index if found or -1 if not 
  
  public int indexOf(Object e) {
    // PRE: e is the value to find; null is allowed.
    // POST: returns the first matching index, or -1 when absent.
    int index = 0;
    for (Node<E> current = head; 
      current != null; 
      current = current.next, 
      index++)

      if (java.util.Objects.equals(current.element, e))
        return index;
    return -1;
  }

  @Override 
  //Returns the last index of the matching object or -1 if not found
  //PRE: accepts an object
  //POST:returns the last index if found or -1 if not 
 
  public int lastIndexOf(E e) {
    // PRE: e is the value to find; null is allowed.
    // POST: returns the last matching index, or -1 when absent.
    int index = 0;
    int lastIndex = -1;
    for (Node<E> current = head; 
      current != null; 
      current = current.next, 
      index++)

      if (java.util.Objects.equals(current.element, e))
        lastIndex = index;

    return lastIndex;
  }

  @Override 
  //Replace the element at the specified position with new element
  //PRE: accepts the index value & new element
  //POST:verifies the value (will throw an exception if invalid)
  //     saves old value at the index
  //     sets index value to new element  
  //     returns element
  

  public E set(int index, E e) {
    // PRE: index is a valid zero-based position and e is the replacement value.
    // POST: replaces the indexed element and returns its previous value.
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    Node<E> current = head;
    for (int i = 0; i < index; i++)
      current = current.next;
    E previous = current.element;
    current.element = e;
    return previous;
  }

  @Override
  public boolean containsAll(java.util.Collection<?> c) {
    // PRE: c is the collection of values to search for.
    // POST: returns true only when every value in c occurs in this list.
    for (Object value : c)
      if (!contains(value))
        return false;
    return true;
  }

  @Override
  
  public boolean addAll(java.util.Collection<? extends E> c) {
    // PRE: c is the collection of values to append.
    // POST: appends all values in iteration order and reports whether the list changed.
    Object[] values = new Object[c.size()];
    int count = 0;
    for (E value : c)
      values[count++] = value;
    for (int i = 0; i < count; i++) {
      @SuppressWarnings("unchecked") E value = (E) values[i];
      addLast(value);
    }
    return count > 0;
  }

  @Override
  //Reference: MyList.java collection-method exercise; remove values found in c.
  //PRE: c is the collection of values to remove.
  //POST: removes every matching list element and reports whether the list changed.
  public boolean removeAll(java.util.Collection<?> c) {
    boolean modified = false;
    for (int i = 0; i < size; i++) {
      if (c.contains(get(i))) {
        remove(i);
        modified = true;
        i--;
      }
    }
    return modified;
  }

  @Override 
  //Override iterator() defined in Iterable 
  //PRE: none
  //POST return a new array list iterator
  public java.util.Iterator<E> iterator() {
    return new LinkedListIterator();
  }
  
  @Override /** Return the number of elements in this list */
  public int size() {
    return size;
  }


  private class LinkedListIterator 
      implements java.util.Iterator<E> {
    private Node<E> current = head; // Current index 
    
    @Override
    public boolean hasNext() {
      return (current != null);
    }

    @Override
    public E next() {
      E e = current.element;
      current = current.next;
      return e;
    }

    @Override
    public void remove() {
      // This will not be implemented
    }
  }
  
  private static class Node<E> {
    E element;
    Node<E> next;

    public Node(E element) {
      this.element = element;
    }
  }
  

}


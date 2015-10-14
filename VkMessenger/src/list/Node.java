package list;

public class Node {
    public Node next;
    public Node prev;
    public Object container;

    public Node (Object o) {
        this.container = o;
    }
}

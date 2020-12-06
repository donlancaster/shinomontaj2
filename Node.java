class Node {
    public int data;
    public boolean leftIsThread;
    public Node left;
    public Node right;

    public Node(int data){
        this.data = data;
        leftIsThread = false;
        left  = null;
        right = null;
    }
    public void show() { System.out.print(data + " "); }
}
import java.util.Stack;

class BinTree {
    private Node root;

    public BinTree() {
        root = null;
    }

    public void insert(int data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
        } else {
            Node curr = root, prev;
            while (true) {
                prev = curr;
                if (data < curr.data) {
                    curr = curr.left;
                    if (curr == null) {
                        prev.left = newNode;
                        return;
                    }
                } else {
                    curr = curr.right;
                    if (curr == null) {
                        prev.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    public void insertToThreadedBinaryTree(int data) {
        getThreadingUpdated(root);
        insert(data);
        doThreading();
    }

    public void doTraversing() {
        Node curr = root;
        while (curr != null) {
            System.out.print(curr.data + "->");
            if (curr.left != null && !curr.leftIsThread) curr = curr.left;
            else if (curr.left == null) curr = curr.right;
            else if (curr.leftIsThread) curr = curr.left;
        }
        System.out.println("end");
    }

    public void doThreading() {
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        while (!stack.empty()) {
            Node extracted = stack.pop();
            if (extracted.right != null) stack.push(extracted.right);
            if (extracted.left != null && !extracted.leftIsThread) stack.push(extracted.left);
            if (stack.empty()) return;
            if (extracted.right == null && (extracted.leftIsThread || extracted.left == null)) {
                if (stack.peek() == root) {
                    extracted.left = null;
                    extracted.leftIsThread = false;
                } else {
                    extracted.left = stack.peek();
                    extracted.leftIsThread = true;
                }
            }
        }
    }

    private void getThreadingUpdated(Node n) {
        if (n != null) {
            if (n.leftIsThread) {
                n.leftIsThread = false;
                n.left = null;
            }
            getThreadingUpdated(n.left);
            getThreadingUpdated(n.right);
        }
    }


    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;
        while (current.data != key) {
            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null)
                return false;
        }
        //удаление листа ?
        if (current.left == null &&
                current.right == null) {
            if (current == root)
                root = null;
            else if (isLeftChild)
                parent.left = null;
            else
                parent.right = null;
        } else
            //у удаляемого узла есть только левый потомок
            if (current.right == null) {
                if (current == root)
                    root = current.left;
                else if (isLeftChild)
                    parent.left = current.left;
                else
                    parent.right = current.left;
            } else
                //у удаляемого узла есть только правый потомок
                if (current.left == null) {
                    if (current == root)
                        root = current.right;
                    else if (isLeftChild)
                        parent.left = current.right;
                    else
                        parent.right = current.right;
                } else
                //у удаляемого узла есть и левый, и правфй потомок
                {
                    //находим преемника. преемник - это узел со следующим по величине ключом.
                    Node successor = getSuccessor(current);
                    if (current == root)
                        root = successor;
                    else if (isLeftChild)
                        parent.left = successor;        //Удаляемый узел - левый потомок. Заменяем его преемником.
                    else
                        parent.right = successor;       //Аналогично в случае правого потомка.

                    successor.left = current.left;      //Переносим левое поддерево удаляемого узла.
                }
        return true;
    }

    //Преемник - либо (1) правый потомок delNode
    //           либо (2) "самый левый" потомок правого потомка delNode
    private Node getSuccessor(Node delNode) {
        //Становимся в правое поддерево удаляемого узла
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.right;
        //находим самый левый
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        //Если самый левый не является (1), а является (2), то
        //правый потомком преемника должно стать правое поддерево delNode
        if (successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }
        return successor;
    }

    public boolean deleteFromThreadedBinaryTree(int key) {
        getThreadingUpdated(root);
        boolean res = delete(key);
        doThreading();
        return res;
    }

}
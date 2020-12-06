import java.util.LinkedHashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random rand = new Random();
        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
        BinTree tree = new BinTree();
        for (int i = 0; i < 100; i++)
            set.add(rand.nextInt(10));
        System.out.println("Elements added:");
        for (Integer it : set){
            System.out.print(it + " ");
            tree.insert(it);
        }
        System.out.println();
        tree.doThreading();
        System.out.println("traversed preorder:");
        tree.doTraversing();
        tree.insertToThreadedBinaryTree(-1);
        tree.insertToThreadedBinaryTree(-500);
        tree.insertToThreadedBinaryTree(20);
        tree.insertToThreadedBinaryTree(999);
        tree.insertToThreadedBinaryTree(15);
        tree.insertToThreadedBinaryTree(30);
        tree.insertToThreadedBinaryTree(-45);

        System.out.println("traversed preorder after new elements added:");
        tree.doTraversing();
        int K = set.size() / 2;
        for (Integer it : set){
            tree.deleteFromThreadedBinaryTree(it);
            K--;
            if (K == 0) break;
        }
        System.out.println("traversed preorder after half elements removed:");
        tree.doTraversing();
    }
}
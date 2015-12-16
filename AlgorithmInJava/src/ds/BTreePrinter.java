package ds;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



class NodePT<T extends Comparable<?>> {
    NodePT<T> left, right;
    T data;

    public NodePT(T data) {
        this.data = data;
    }
}

class BTreePrinter {

    public static <T extends Comparable<?>> void printNode(NodePT<T> root) {
        int maxLevel = BTreePrinter.maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<NodePT<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<NodePT<T>> newNodes = new ArrayList<NodePT<T>>();
        for (NodePT<T> node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(NodePT<T> node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
    
    //testing methods
    
    private static NodePT<Integer> test1() {
        NodePT<Integer> root = new NodePT<Integer>(2);
        NodePT<Integer> n11 = new NodePT<Integer>(7);
        NodePT<Integer> n12 = new NodePT<Integer>(5);
        NodePT<Integer> n21 = new NodePT<Integer>(2);
        NodePT<Integer> n22 = new NodePT<Integer>(6);
        NodePT<Integer> n23 = new NodePT<Integer>(3);
        NodePT<Integer> n24 = new NodePT<Integer>(6);
        NodePT<Integer> n31 = new NodePT<Integer>(5);
        NodePT<Integer> n32 = new NodePT<Integer>(8);
        NodePT<Integer> n33 = new NodePT<Integer>(4);
        NodePT<Integer> n34 = new NodePT<Integer>(5);
        NodePT<Integer> n35 = new NodePT<Integer>(8);
        NodePT<Integer> n36 = new NodePT<Integer>(4);
        NodePT<Integer> n37 = new NodePT<Integer>(5);
        NodePT<Integer> n38 = new NodePT<Integer>(8);

        root.left = n11;
        root.right = n12;

        n11.left = n21;
        n11.right = n22;
        n12.left = n23;
        n12.right = n24;

        n21.left = n31;
        n21.right = n32;
        n22.left = n33;
        n22.right = n34;
        n23.left = n35;
        n23.right = n36;
        n24.left = n37;
        n24.right = n38;

        return root;
    }

    private static NodePT<Integer> test2() {
        NodePT<Integer> root = new NodePT<Integer>(2);
        NodePT<Integer> n11 = new NodePT<Integer>(7);
        NodePT<Integer> n12 = new NodePT<Integer>(5);
        NodePT<Integer> n21 = new NodePT<Integer>(2);
        NodePT<Integer> n22 = new NodePT<Integer>(6);
        NodePT<Integer> n23 = new NodePT<Integer>(9);
        NodePT<Integer> n31 = new NodePT<Integer>(5);
        NodePT<Integer> n32 = new NodePT<Integer>(8);
        NodePT<Integer> n33 = new NodePT<Integer>(4);

        root.left = n11;
        root.right = n12;

        n11.left = n21;
        n11.right = n22;

        n12.right = n23;
        n22.left = n31;
        n22.right = n32;

        n23.left = n33;

        return root;
    }
    
    /* 
     * change the name of this method to main so that you can 
     * see the test result
     */
    
    public static void test(String[] args) {

        BTreePrinter.printNode(test1());
        BTreePrinter.printNode(test2());

    }
    
}
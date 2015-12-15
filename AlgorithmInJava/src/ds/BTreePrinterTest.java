package ds;

import java.util.ArrayList;

import ds.SplayBST.Node;

public class BTreePrinterTest {

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
    
  
    public static void test(String[] args) {

        BTreePrinter.printNode(test1());
        BTreePrinter.printNode(test2());

    }
}
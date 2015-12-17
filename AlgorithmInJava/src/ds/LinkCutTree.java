package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <h1>Link-Cut Tree Data Structure</h1>
 * 
 * <p>This class implements the link-cut tree data structures. This data structure represents
 * a <b>directed unordered forest</b>. Each tree in the forest, called <b>represented tree</b>, is 
 * represented by a tree of <b>virtual trees</b>. Each non-root virtual tree is linked to another virtual tree
 * , called the <b>parent virtual tree</b> by a <b>path parent pointer</b>.  In Link-Cut Tree data structure, 
 * each virtual tree is structured (data structure representation) by a slay tree, where each node
 * of the splay tree is keyed by depth (in the original tree). Node that this <em>key</em> is implicit in
 * the splay tree, meaning that the actual value is not explicitly stored in a field of a node, but implicitly by
 * the position of that node in the splay tree.</p>
 * 
 * <div>In this implementation, each node has an id in {1,2,...,n}. The structure supports:
 *  <ul> <li>Make(v): Make a new tree consists of a singleton node v.</li>
 *  	<li>Cut(v): Remove the edge between a node v and its parent w in the represented tree.</li>
 *  	<li>Link(v,w): Make the vertex v become a child of a vertex w. To do this operation, it is user's responsibility
 *  to make sure that v and w belong to different (represented) trees and v is the root of the represented tree containing v.</li>
 *  	<li>FindRoot(v): find the root of the (represented) tree containing v.</li>
 *  	<li> Path(v): doing some aggregation along the path from $v$ to the root. This function has not been implemented yet
 *  and will be added in the future.</li>
 *  </ul>
 *  One of the major drawback of this implementation is we need to know the number of nodes in the represented forest
 *  beforehand, that should be initialized through the constructor with capacity. If the capacity is not initialized correctly,
 *  this implementation will breakdown. 
 * </div>
 * 
 * @author Hung Le
 * @version 1.0
 * @since 12/16/2016
 *   
 */
public class LinkCutTree {
	/*
	 * The data structure for each node consists of the id, the left, right and parent pointer.
	 * The pointer here is an index of the referred node in the array of nodes.
	 * 
	 * The parent pointer of a node here both represents a pointer to the parent in the splay tree 
	 * if the node is not the root of the splay tree and represents a path parent pointer
	 * if the node is the root of the splay tree.
	 */
	private class Node{
		public int id;
		public int l,r,p;
		
		public Node(int id){
			this.id = id;
			l = r = p = -1;
		}
	}
	private int n; // the number of nodes of the link cut tree.
	private Node[] nodes;
	public LinkCutTree(int capacity){
		this.n = capacity;
		nodes = new Node[capacity];
		for(int i =0 ; i < n; i++){
			nodes[i] = new Node(i);
		}
	}
	/*
	 * This function checks where there a node is a root by checking:
	 * 	if its parent pointer is -1 or
	 * 	if none of its parent is this node. 
	 */
	private boolean isRoot(Node p){
		return (p.p == -1 || (nodes[p.p].l != p.id)&&(nodes[p.p].r != p.id));
	}
	
	/*
	 * Left Rotation at node x
	 *         x							y
     *		  /  \						   /  \
     * 		 o    y			------>		  x    o	
     *		     / \					 / \   
     *	   		oo   o					o  oo  
 	 * 
 	 * User needs to make sure that x has a non-null right child
	 */
	private void leftRotate(Node x){
		int y = x.r;
		nodes[y].p = x.p;
		x.r = nodes[y].l;
		nodes[y].l = x.id;
		x.p = y;
	}
	
	/*
	 * Right Rotation at node x
	 *         x							y
     *		  /  \						   /  \
     * 		 y    o			------>		  o	   x	
     *		/ \					 		 	  / \   
     *	   o   oo   						 oo  o  
 	 * 
 	 * User needs to make sure that x has a non-null left child
	 */
	private Node rightRotate(Node x){
		int y = x.l;
		nodes[y].p = x.p;
		x.l = nodes[y].r;
		nodes[y].r = x.id;
		x.p = y;
		return nodes[y];
	}
	
	/*
	 * Splay x to the root of the splay tree that contains x
	 */
	private void splay(Node x){
		if(isRoot(x)) {
			return;
		}else { // x is not a root;
			int w = x.p;
			if(nodes[w].r == x.id){ // x is the right child of its parent w
				if(isRoot(nodes[w])) {// if w is the root of the splay tree
					leftRotate(nodes[w]);
				} else {
					int z  =  nodes[w].p;
					if(nodes[z].r == w){  // w is the right child of z
						// left roller-coaster at z;
						leftRotate(nodes[z]);
						leftRotate(nodes[w]);
					} else { // w is the left child of z
						// zig-zag at z
						leftRotate(nodes[w]);
						rightRotate(nodes[z]);
					}
				}
			} else {// x is the left child of its parent w
				if(isRoot(nodes[w])){// w is the root of the splay tree
					rightRotate(nodes[w]);
				} else {
					int z = nodes[w].p;
					if(nodes[z].r == w){ // w is the right child of z
						// zig-zag at z
						rightRotate(nodes[w]);
						leftRotate(nodes[z]);
					}else { // w is the left child of z
						// roller-coaster at z
						rightRotate(nodes[z]);
						rightRotate(nodes[w]);
					}
				}
			}
		}
	}
	
	private void access(Node x){
		splay(x);
		/* right child of x has larger depth than x in the rep. path
		 * we cut x's right child from x by simply set the right child of x
		 *  to be -1.
		 */
		x.r = -1;
		int y;
		Node v = x;
		while(v.p != -1){
			y  = v.p;
			/* recall here depth of y is smaller than x
			 * we splay to make y the root of the splay tree containing y and 
			 * then, we cut out the right child of y
			 */
			splay(nodes[y]);
			nodes[y].r = v.id;
			v = nodes[y];
		}
		// splay v for convenience
		splay(x);
	}
	/**
	 * This method implements the method FindRoot(v) described above. To find the root
	 * of v, first, we <em>access </em> v. Then, we find the left most node of the splay tree
	 * rooted at v, that is the root of the represented tree contaning v.
	 * 
	 * @param v the id of the node in question
	 * @return r the id of the root of the tree containing v 
	 */
	public int findRoot(int v){
		Node x = nodes[v];
		access(x);
		Node r = x;
		while(r.l != -1) r = nodes[r.l];
		splay(r);
		return r.id;
	}

	/**
	 * This method implements the method Cut(v) described above. To cut the edge between v
	 * and its parent in the represented tree, we first <em>access</em> v. Observe that all nodes
	 * in the left subtree of v are acestors of v in the represented tree. Thus, we only
	 * need to disconnect v and its left child.
	 * 
	 * @param v the id of the node in question
	 */
	public void cut(int v){
		access(nodes[v]);
//		nodes[nodes[v].l].p = -1;
		nodes[v].l = -1;
	}

	/**
	 * This method implements the method Link(v,w) described above. Here v must be the root of
	 * the represented tree containing v and must belong to different tree of w. To add 
	 * an edge between v and w, we first <em>access</em> v and w and then, set v to be 
	 * the right child of w. Note that after access, both v and w become roots of virtual 
	 * trees and their right children are null.
	 * 	  
	 * @param v the id of the node where the link comes from
	 * @param w the id of the node where the link comes to
	 */

	public void link(int v, int w){
		access(nodes[v]);
		access(nodes[w]);
		nodes[w].r = v;
		nodes[v].p = w;
	}
	
	private int aggregate(int v){
		//TODO
		return 0;
	}
	
	/**
	 * This method converts a virtual splay tree into a tree string.
	 * 	  
	 * @param v the id of the root of the splay tree.
	 * @return s the string reprsented the virtual splay tree.
	 * @exception IllegalArgumentException  the input node is not a root.
	 * @see IllegalArgumentException
	 */

		
	public String printTree(int v){
		   Node x = nodes[v];
		   if(!isRoot(x)){
			   throw new IllegalArgumentException("The input node is not a root");
		   }
	       int maxLevel = maxLevel(x);
	       return printNodeInternal(Collections.singletonList(x), 1, maxLevel);
	 
	}
	
    private String printNodeInternal(List<Node> nodelist, int level, int maxLevel) {
        if (nodelist.isEmpty() || isAllElementsNull(nodelist))
            return null;
        StringBuffer bf  = new StringBuffer();
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        bf.append(printWhitespaces(firstSpaces));

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodelist) {
            if (node != null) {
            	bf.append(node.id);
                if(node.l != -1) {
                	newNodes.add(nodes[node.l]);
                } else {
                	newNodes.add(null);
                }
                if(node.r != -1) {
                	newNodes.add(nodes[node.r]);
                }else {
                	newNodes.add(null);
                }
            } else {
                newNodes.add(null);
                newNodes.add(null);
                bf.append(" ");
            }
            bf.append(printWhitespaces(betweenSpaces));
        }
        bf.append("\n");
        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodelist.size(); j++) {
            	bf.append(printWhitespaces(firstSpaces - i));
                if (nodelist.get(j) == null) {
                	bf.append(printWhitespaces(endgeLines + endgeLines + i + 1));
                    continue;
                }

                if (nodelist.get(j).l != -1)
                	bf.append("/");
                else
                	bf.append(printWhitespaces(1));
                bf.append(printWhitespaces(i + i - 1));
                if (nodelist.get(j).r != -1)
                	bf.append("\\");
                else
                	bf.append(printWhitespaces(1));

                bf.append(printWhitespaces(endgeLines + endgeLines - i));
            }

            bf.append("\n");
        }

       bf.append(printNodeInternal(newNodes, level + 1, maxLevel));
       return bf.toString();
    }
    private boolean isAllElementsNull(List<Node> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }

    private int maxLevel(Node r){
		if(r == null) return 0;
		else {
			int leftlv = 0, rightlv = 0;
			if(r.l != -1) leftlv = maxLevel(nodes[leftlv]);
			if(r.r != -1) rightlv = maxLevel(nodes[rightlv]);
			return Math.max(leftlv,rightlv) + 1;
		}
	}
	
    private String printWhitespaces(int count) {
    	StringBuffer bf  = new StringBuffer();
        for (int i = 0; i < count; i++)
        	bf.append(" ");
        return bf.toString();
    }
   


    private static void test(String[] args){
		LinkCutTree lct = new LinkCutTree(5);
		lct.link(0,1);
		System.out.println(lct.printTree(1));
		lct.link(1, 2);
		System.out.println(lct.printTree(2));
		lct.link(3,2);
		System.out.println(lct.printTree(2));
		lct.link(4, 1);
		System.out.println(lct.printTree(1));
		System.out.println(lct.findRoot(0));
		System.out.println(lct.printTree(2));
		
	}
}

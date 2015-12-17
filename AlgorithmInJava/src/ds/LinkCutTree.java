package ds;

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
 *  to make sure that v and w belong to different (represented) trees.</li>
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
	 * This funciton check where there a node is a root by checking:
	 * 	if its parent pointer is -1 or
	 * 	if none of its parent is this node. 
	 */
	private boolean isRoot(Node p){
		return (p.p == -1 || (nodes[p.p].l != p.id)&&(nodes[p.p].r != p.id));
	}
	
	public void leftRotate(Node p){
		
	}
	
	public void rightRotate(Node p){
		
	}
	
}

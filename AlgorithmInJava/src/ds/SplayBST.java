package ds;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class SplayBST<Key extends Comparable<Key>, Value> {
	
	public Node root;
	private int size;
	
	public SplayBST(){
		root = null;
	}
	public SplayBST(Key K, Value V){
		root = new Node(K, V);
	}
	
	// insert to the splay tree
	public void add(Key K, Value V){
		if(root == null){
			root = new Node(K, V);
		}else {
			quickAdd(K, V);
		}
		size++;
	}
	
	// insert to the splay tree without checking null of the root
	private void quickAdd(Key k, Value v){
		root = splay(root, k);
		int cmp = root.getKey().compareTo(k);
		if(cmp > 0){
			Node n = new Node(k, v);
			n.lcld = root.lcld;
			root.lcld = null;
			n.rcld = root;
			root = n;
		}else if(cmp < 0){
			Node n = new Node(k, v);
			n.rcld = root.rcld;
			root.rcld = null;
			n.lcld = root;
			root = n;
		}else {
			size --;
			root.value  = v;
		}

	}

	// delete a node with a given key in the splay tree
	public void delete(Key K){
		if(root == null) return;
		else {
			root = splay(root, K);
			int cmp = root.getKey().compareTo(K);
			if(cmp == 0){
				if(root.lcld == null) root = root.rcld;
				else {
					Node x = root.rcld;
					root  = root.lcld;
					root = splay(root, K);
					root.rcld = x;
				}
				size--;
			}
		}		
	}
	
	public Value find(Key K){
		root = splay(root, K);
		if(root.getKey().compareTo(K) == 0) return root.getVal();
		else return null;
	}
	private Node splay(Node r, Key k){
		if(r == null) return null;
		int cmp1  = k.compareTo(r.getKey());
		if(cmp1 > 0){ // k belongs to the right subtree of r
			if(r.rcld == null) return r;
			else {
				int cmp2 = k.compareTo(r.rcld.getKey());
				if(cmp2 > 0){ // roller-coaster
					r.rcld.rcld = splay(r.rcld.rcld,k);
					r = rotateLeft(r);
				}else if(cmp2 < 0){// zig-zag
					r.rcld.lcld = splay(r.rcld.lcld, k);
					if (r.rcld.lcld != null){
						r.rcld = rotateRight(r.rcld);
					}
				}
				if(r.rcld == null) return r;
				else return rotateLeft(r);
			}
		}else if(cmp1 < 0){ // k belongs to the left subtree of r
			if(r.lcld == null) return r;
			else {
				int cmp2 =  k.compareTo(r.lcld.getKey());
				if(cmp2 > 0){// zig-zag
					r.lcld.rcld = splay(r.lcld.rcld, k);
					if(r.lcld.rcld != null){
						r.lcld = rotateLeft(r.lcld);
					}
				}else if(cmp2 < 0){// roller-coaster
					r.lcld.lcld = splay(r.lcld.lcld, k);
					r = rotateRight(r);
				}
				if(r.lcld == null) return r;
				else return rotateRight(r);
			}
		}else return r;
	}
	// rotate right at y's left child, say x, and return x
	public Node rotateRight(Node y){
		if(y == null || y.lcld == null) return y;
		else {
			Node x = y.lcld;
			y.lcld = x.rcld;
			x.rcld = y;
			return x;
		}
	}
	
	// rotate right at y's right child, say x, and return x
	public Node rotateLeft(Node y){
		if(y == null || y.rcld == null) return y;
		else {
			Node x = y.rcld;
			y.rcld = x.lcld;
			x.lcld = y;
			return x;
		}
	}
	
	// add many (key,value) pairs at one
	public void addAll(Collection<Entry<Key, Value>> entries){
		if(entries.isEmpty()) return ;	
		Iterator<Entry<Key, Value>> it  = entries.iterator();
		Entry<Key, Value> entry;
		if(root == null){
			 entry = it.next();
			root = new Node(entry.getKey(), entry.getValue());
		} 
		while(it.hasNext()){
			entry = it.next();
			quickAdd(entry.getKey(), entry.getValue());
		}
		size += entries.size();
	}
	
	public int size(){
		return size;
	}
	
	class Node{
		private Key key;
		private Value value;
		public Node lcld, rcld;
		
		public Node(Key K, Value V){
			this.key = K;
			this.value = V;
			this.lcld = null; rcld = null;
		}
		public Key getKey(){
			return key;
		}
		public Value getVal(){
			return value;
		}
		
	}
	
	// print the splay tree in tree form
    public String toString() {
        int maxLevel = maxLevel(root);
        return printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }
    private String printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return null;
        StringBuffer bf  = new StringBuffer();
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        bf.append(printWhitespaces(firstSpaces));

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
            	bf.append(node.getKey().toString());
                newNodes.add(node.lcld);
                newNodes.add(node.rcld);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                bf.append(" ");
            }

            bf.append(printWhitespaces(betweenSpaces));
        }
        bf.append("\n");
        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
            	bf.append(printWhitespaces(firstSpaces - i));
                if (nodes.get(j) == null) {
                	bf.append(printWhitespaces(endgeLines + endgeLines + i + 1));
                    continue;
                }

                if (nodes.get(j).lcld != null)
                	bf.append("/");
                else
                	bf.append(printWhitespaces(1));
                bf.append(printWhitespaces(i + i - 1));
                if (nodes.get(j).rcld != null)
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
			return Math.max(maxLevel(r.lcld), maxLevel(r.rcld)) + 1;
		}
	}
	
    private String printWhitespaces(int count) {
    	StringBuffer bf  = new StringBuffer();
        for (int i = 0; i < count; i++)
        	bf.append(" ");
        return bf.toString();
    }
   

	
	public static void main(String[] args){
		PrintStream StdOut = System.out;
		SplayBST<Integer, Integer> st1 = new SplayBST<Integer, Integer>();
		st1.add(5, 5);
		st1.add(9, 9);
		st1.add(13, 13);
		st1.add(11, 11);
		st1.add(1, 1);
		StdOut.println(st1.toString());
		
		SplayBST<String, String> st = new SplayBST<String, String>();
		st.add("www.cs.princeton.edu", "128.112.136.11");
		st.add("www.cs.princeton.edu", "128.112.136.12");
		st.add("www.cs.princeton.edu", "128.112.136.13");
		st.add("www.princeton.edu", "128.112.128.15");
		st.add("www.yale.edu", "130.132.143.21");
		st.add("www.simpsons.com", "209.052.165.60");
		StdOut.println("The size 0 is: " + st.size());
		st.delete("www.yale.edu");
		StdOut.println("The size 1 is: " + st.size());
		st.delete("www.princeton.edu");
		StdOut.println("The size 2 is: " + st.size());
		st.delete("non-member");
		StdOut.println("The size 3 is: " + st.size());
		StdOut.println(st.find("www.cs.princeton.edu"));
		StdOut.println("The size 4 is: " + st.size());
		StdOut.println(st.find("www.yale.com"));
		StdOut.println("The size 5 is: " + st.size());
		StdOut.println(st.find("www.simpsons.com"));
		StdOut.println("The size 6 is: " + st.size());
		StdOut.println();
	}
}

//============================================================================
// Author      : Hung Le
// Version     :
// Description : Java Code for http://codeforces.com/contest/605/problem/D
//============================================================================

/**
 * This programe solves the problem by using the priority search tree. 
 */

package c335div1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class C335Div1DBoaGam {
	static int N;
	static double EPS = 1e-7;
	static Int4Tuple[] cards;
	static Int4Tuple target; // the target magic card
	static HNode pstRoot; // the root of the priority search tree
	static Queue<Int4Tuple> bfsQueue = new LinkedList<>();
	static int[] P;
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			N = Integer.parseInt(br.readLine());
			P = new int[N];
			for(int i = 0; i < N; i++) P[i] = -1;
			cards = new Int4Tuple[N];
			String[] chunks;
			for(int i = 0; i < N; i++){
				chunks = br.readLine().split(" ");
				cards[i] = new Int4Tuple(Integer.parseInt(chunks[0]), 
						Integer.parseInt(chunks[1]), Integer.parseInt(chunks[2]),
						Integer.parseInt(chunks[3]), i);
			}
			target = cards[N-1];
			// sort all cards by Y coordinates
			Arrays.sort(cards, new YComparator()); 
			pstRoot = recursivePSTConst(0, N-1);
			Int4Tuple tmp = new Int4Tuple(0, 0, 0, 0, -1);
			query(tmp, pstRoot, null, false);
			if(bfsQueue.isEmpty()) {
				prln("-1");
				return;
			} 
			while(!bfsQueue.isEmpty()){			
				tmp = bfsQueue.remove();
				if(tmp == target){
					printOutput(tmp.index);
					return;
				}else{
					query(tmp, pstRoot, null, false);
				}
			}
			prln("-1");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
	}
	static HNode recursivePSTConst(int l, int r){
		if(r < l) return null;
		else if(l == r) return new HNode(cards[l].b, cards[l]);
		else if(r == l+1) {
			HNode u = new HNode(cards[l].b, cards[l]);
			HNode v = new HNode(cards[r].b, cards[r]);
			if(u.point.a < v.point.a){
				u.right = v;	
				return u;
			}else{
				v.left = u;
				return v;
			}
		}
		else {
			int k = -1;
			int min = Integer.MAX_VALUE;
			for(int i = l; i <= r; i++){
				if(cards[i].a < min) {
					min = cards[i].a;
					k = i;
				}
			}
			Int4Tuple tmp = cards[k];
			for(int j = k+1; j <= r; j++){
				cards[j-1] = cards[j];
			}
			cards[r] = tmp;
			int m = (l+r-1)>>1;
			HNode hroot = new HNode(cards[m].b, cards[r]);
			hroot.left = recursivePSTConst(l, m);
			hroot.right = recursivePSTConst(m+1, r-1);
			return hroot;
		}
	}
	
	/*
	 * We keep track of the parent of the node in question in the parameter parent
	 * The variable isLeft indicates that the node is the left child of its parent 
	 * or not (if not, it is the right child). 
	 */
	
	static void query(Int4Tuple key, HNode node, HNode parent, boolean isLeft){
		if(node == null)return;
		while(node != null && node.point.a <= key.c && node.point.b <= key.d ){
			bfsQueue.add(node.point);
			P[node.point.index] = key.index;
			node = delete(node);
		}
		if(parent != null){
			if(isLeft) parent.left = node;
			else parent.right = node;
		} else {
			pstRoot = node;
		}
		if(node == null) return;
		if(key.d < node.ymid){
			query(key,node.left, node, true);
		}else {
			node.left = report(key.c, key.index, node.left);
			query(key, node.right, node, false);
		}
	}
	
	// pid is the parent id, this is used to keep track of the path
	static HNode report(int x, int pid, HNode root){
			if(root == null) return null;
			while(root != null && root.point.a <= x){
				bfsQueue.add(root.point);
				P[root.point.index] = pid;
				root = delete(root);	
			}
			return root;
	}
	/*
	 *  Delete a node from the priority search tree
	 *  If a node is delete, we must replace that node by one of its children
	 *  the procedure upHeap will do the job.
	 */
	static HNode delete(HNode root){
		if(root.left == null) {
			root = root.right;
		}else if(root.right == null){
			root = root.left;			
		}else upHeap(root); 
		return root;
	}
	
	static void upHeap(HNode root){
		if(root.left == null || (root.right != null && 
				root.left.point.a >= root.right.point.a )) {
			root.point = root.right.point;
			if(isLeaf(root.right)){
				root.right = null;
			}else upHeap(root.right);
		}else if(root.right == null ||
				root.left.point.a < root.right.point.a){
			root.point = root.left.point;
			if(isLeaf(root.left)){
				root.left = null;
			}else upHeap(root.left);
		}
	}
	
	
	
	static boolean isLeaf(HNode node){
		return (node.left == null)&&(node.right == null);
	}

	static void printOutput(int k){
		LinkedList<Integer> path = new LinkedList<>();
		int i = k;
		while(P[i]!= -1){
			path.addFirst(i);
			i = P[i];
		}
		path.addFirst(i);
		prln(path.size() + "");
		StringBuilder output = new StringBuilder();
		for(Integer e : path){
			output.append((e+1) + " ");
		}
		prln(output.toString());
	}

	static void prln(String s){
		System.out.println(s);
	}
	
	/*
	 * Use this to debug the heap
	 */
	static void printHeap(HNode root){
		prln(root.toString());
		if(root.left != null) printHeap(root.left);
		if(root.right != null) printHeap(root.right);
	}
	
	static class HNode {
		public int ymid;
		public Int4Tuple point;
		
		public HNode left, right;
		
		public HNode(int ymid, Int4Tuple point) {
			this.ymid = ymid;
			this.point = point;
			this.left = null;
			this.right = null;
		}
		public String toString(){
			return "[" + ymid + ","  + point.toString() + "]\tleft:" + 
					(left!= null? left.point.toString() :"") + "\tright:"  +
					(right!= null? right.point.toString() :"");
		}
	}
	static class Int4Tuple{
		public int a,b,c,d;
		public int index;
		
		public Int4Tuple(int a, int b, int c, int d, int index) {
			this.a = a; this.b = b; this.c = c; this.d = d;
			this.index = index;
		}
		
		public String toString(){
			return  "(" + a + "," + b + "," + c + "," + d + "," + index + ")";
		}

	}
	
	static class YComparator implements Comparator<Int4Tuple>{

		@Override
		public int compare(Int4Tuple o1, Int4Tuple o2) {
			return Integer.compare(o1.b, o2.b);
		}
	}
}

//# one test case
//10
//8 17 12 24
//0 0 6 30
//0 0 26 27
//0 0 30 1
//26 4 8 11
//21 7 3 8
//29 19 5 24
//4 27 30 30
//23 23 24 23
//14 29 27 13
//
//
//output
//3
//2 8 10

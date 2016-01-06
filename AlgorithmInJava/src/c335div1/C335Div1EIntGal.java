package c335div1;
//============================================================================
// Author      : Hung Le
// Version     :
// Description : Java Code for http://codeforces.com/contest/605/problem/E
//============================================================================

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class C335Div1EIntGal {
	static int N;
	static int[][] P; // the probability array
	static boolean[][] RP; // the parent array
	static double[] D;
	static HNode[] nodes;
	static boolean[] Fix;
	static PriorityQueue<HNode> heap = new PriorityQueue<>();
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			N = Integer.parseInt(br.readLine());
			P  = new int[N][N];
			RP = new boolean[N][N];
			D = new double[N];
			nodes = new HNode[N];
			Fix = new boolean[N];
			String[] chunks;
			for(int i = 0; i < N; i++){
				chunks = br.readLine().split(" ");
				for(int j = 0; j < N; j++){
					P[i][j] = Integer.parseInt(chunks[j]);
					if(P[i][j] > 0 && i!= j) RP[j][i] = true;
				}
			}
			if(N == 1){
				System.out.println(0);
				return;
			}			
			for(int i = 0; i < N-1; i++){
				if(RP[N-1][i]){
					D[i] = (double)100/ (double)P[i][N-1];
					nodes[i] =  new HNode(D[i], i,1, (1.0- (double)P[i][N-1]/(double)100));
					heap.add(nodes[i]);
				} else {
					D[i] = Double.MAX_VALUE;
					nodes[i] =  new HNode(D[i], i,1, 1);
				}
			}
			HNode minHeap;
			while(!heap.isEmpty()){
				minHeap = heap.remove();
				if(minHeap.id == 0){
					System.out.println(minHeap.dis);
					break;
				}
				Fix[minHeap.id] = true;
				for(int j = 0; j < N-1; j++){
					if(RP[minHeap.id][j] && !Fix[j]){ // j is the parent of min heap
						double p = (double) P[j][minHeap.id]/ (double)100;
						nodes[j].a  = nodes[j].a + nodes[j].b*p*minHeap.dis;
						nodes[j].b = nodes[j].b*(1-p);
						double newDis = nodes[j].a / (1-nodes[j].b);
						heap.remove(nodes[j]);
						nodes[j].dis = newDis;
						heap.add(nodes[j]);
					}
				}
				
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	static class HNode implements Comparable<HNode>{
		public double dis;
		public int id;
		public double a;
		public double b;
		
		public HNode(double dis, int id, double a, double b) {
			this.dis = dis;
			this.id = id;
			this.a  = a;
			this.b = b;
		}
		@Override
		public int compareTo(HNode o) {
			return Double.compare(dis, o.dis);
		}
		
		@Override
		public boolean equals(Object o){
			HNode x = (HNode)o;
			if(x.id == this.id) return true;
			else return false;
		}
		
		public String toString(){
			return "(" + dis + "," + id + "," + a + "," + b+ ")";
		}
		
	}

}


//test
//6
//100 0 0 25 0 0
//0 100 0 0 0 75
//0 0 100 0 0 0
//0 50 0 100 0 0
//0 0 0 0 100 0
//0 0 0 0 0 100
//output
//7.3333333333

//============================================================================
// Author      : Hung Le
// Version     :
// Description : Java Code for http://codeforces.com/contest/607/problem/A
//============================================================================

package c336div1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class C336D1AChaRea {
	static int N;
	static IntPair[] D;
	public static void main(String[] args){
		
		BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		try{
			N  = Integer.parseInt(br.readLine());
			D = new IntPair[N];
			int[] F = new int[N];
			String[] chks;
			for(int i = 0; i < N; i++){
				chks = br.readLine().split(" ");
				D[i] = new IntPair(Integer.parseInt(chks[0]), Integer.parseInt(chks[1]));
			}
			Arrays.sort(D);
			F[0] = 1;
			for(int i = 1; i < N; i++){
				int j = Arrays.binarySearch(D,0,i,new IntPair(D[i].a-D[i].b, 0));
				if(j < 0) j = -j-2;
				if(j == -1) F[i] = 1;
				else{
					while(j >= 0 && D[j].a == D[i].a-D[i].b){
						j--;
					}
					F[i] = (j == -1? 0 :F[j]) + 1;
				}
								
			}
			int m  = 0;
			for(int i = 0; i < N; i++){
				m = Math.max(m, F[i]);
			}
			System.out.println(N-m);			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
	}
	
	static class IntPair implements Comparable<IntPair>{
		public int a;
		public int b;
		public IntPair(int a, int b) {
			this.a = a;
			this.b = b;
		}
		@Override
		public int compareTo(IntPair o) {
			return this.a - o.a;
		}
		
		public String toString(){
			return "(" + a + "," + b + ")";
		}
	}

}

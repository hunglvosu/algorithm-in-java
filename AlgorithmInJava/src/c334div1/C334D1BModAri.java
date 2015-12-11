//============================================================================
// Author      : Hung Le
// Version     :
// Description : Java Code for http://codeforces.com/contest/603/problem/B
//============================================================================

package c334div1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class C334D1BModAri {
	static int P, K;
	static long M = 1000000007l;
	static int[] A;
	public static void solve(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] chks  = br.readLine().split(" ");
			P =  Integer.parseInt(chks[0]);
			K = Integer.parseInt(chks[1]);
			A = new int[P];
			A[0] = 0;
			if(K == 0){
				prln(pw((long)P, (long) P-1, M)+"");
			}else if(K == 1){
				prln(pw((long)P, (long) P, M)+"");	
			}	else {
				for(int i =1 ; i < P; i++){
					A[i] = (int) (((long)K * (long)i)%(long)P);
				}
				Stack<Integer> S = new Stack<>();
				ArrayList<Integer> path = new ArrayList<>();
				boolean[][] vs = new boolean[P][2];
				for(int i = 1; i < P; i++){
					S.add(i);
				}
				int x = 0;
				int cmp = 0;
				while(!S.isEmpty()){
					x = S.pop();
					if(vs[x][0]){
						for(int i: path){
							vs[i][0] = true;
						}
						path.clear();
					} else if(vs[x][1]) {
						cmp++;
						for(int i: path){
							vs[i][0] = true;
						}
						path.clear();
					} else {
						vs[x][1]= true;
						S.push(A[x]);
						path.add(x);
					}
				}
				prln( pw(P, cmp, M)+  "");
			}
 		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	static void prln(String s){
		System.out.println(s);
	}
	static long pw(long a, long b, long m){
		if(b == 1) return a;
		else {
			long x = pw(a, b>>1, m);
			x  = (x*x)%m;
			if((b&1) == 0){
				return x;
			}else {
				return (x*a) %m;
			}
		}
	}
	
}

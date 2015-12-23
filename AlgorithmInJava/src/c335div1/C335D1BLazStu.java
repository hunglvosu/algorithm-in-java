//============================================================================
// Author      : Hung Le
// Version     :
// Description : Java Code for http://codeforces.com/contest/605/problem/B
//============================================================================


package c335div1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class C335D1BLazStu {
	
	static int N, M;
	static KKVTriple[] E;
	public static void submitted(String args[]){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] nm = br.readLine().split(" ");
			N  = Integer.parseInt(nm[0]);
			M = Integer.parseInt(nm[1]);
			E = new KKVTriple[M];
			String[] ln;
			for(int i =0 ;i < M; i++){
				ln = br.readLine().split(" ");
				E[i] = new KKVTriple(Integer.parseInt(ln[0]), Integer.parseInt(ln[1]), i);
			}
			int numNotInMST = 0;
			int numVt = 1;
			Arrays.sort(E);
			boolean valid = true;
			for(int i = 0; i < M; i++){
				if(E[i].key2 == 0){
					numNotInMST++;
					if((numVt*(numVt-3))/2 +1 < numNotInMST) {
						valid = false;
						break;
					}				
				}	else {
					numVt++;
				}
			}
			if(!valid){
				prln("-1");
			}else {
				int j  = 2;
				int k = 2, v = 3;
				String[] output = new String[M];
				for(int i = 0; i < M; i++){
					if(E[i].key2 == 1){
						output[E[i].val] = "1 " + j;
						j++;
					} else {
						output[E[i].val] = k + " " +v;
						if(k == v-1){
							v++; k = 2;
						} else {
							k++;
						}
					}
				}
				for(int i =0 ; i < M; i++){
					prln(output[i]);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	static void prln(String s){
		System.out.println(s);
	}

	
static class KKVTriple implements Comparable<KKVTriple>{
	public int key1,key2;
	int val;
	public KKVTriple(int key1, int key2, int val) {
		this.key1 = key1; 
		this.key2 = key2;
		this.val = val;
	}
	@Override
	public int compareTo(KKVTriple o) {
		int cmp = Integer.compare(this.key1, o.key1);
		if(cmp != 0) return cmp;
		else return Integer.compare(o.key2, this.key2);
	}
	
	public String toString(){
		return "(" + key1+ "," + key2 + "," + val + ")";
	}
}
}

// some test cases


//3 3
//4 1
//5 0
//7 1

//3 3
//4 1
//4 0
//4 1

//4 6
//2 1
//4 0
//3 0
//1 1
//4 1
//5 0


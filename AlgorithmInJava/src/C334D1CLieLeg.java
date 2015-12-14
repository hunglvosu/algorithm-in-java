

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class C334D1CLieLeg {
	static int N, K;
	static int[] A;
	
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] NKs = br.readLine().split(" ");
			N = Integer.parseInt(NKs[0]);
			K = Integer.parseInt(NKs[1]);
			A = new int[N];
			String[] chks = br.readLine().split(" ");
			for(int i = 0; i < chks.length; i++){
				A[i] = Integer.parseInt(chks[i]);
			}
			int rs;
			if((K&1) == 0){// K is even
				rs = (A[0] < 3? A[0] : ((A[0]&1) ==0? 1: 0));
				for(int i = 1; i < N; i++){
					rs ^= (A[i] < 3? A[i] : ((A[i]&1) ==0? 1: 0));
				}
			}else { // K is odd
				rs = grundyVal(A[0]);
				for(int i = 1; i < N; i++){
					rs ^= grundyVal(A[i]);
				}
			}
			if(rs == 0) prln("Nicky");
			else prln("Kevin");
		}catch(IOException e){
			e.printStackTrace();
		}		
	}
	static int grundyVal(int x){
		switch(x){
			case 0: 
				return 0;
			case 1:
				return 1;
			case 2:
				return 0;
			case 3: 
				return 1;
			default:
				if((x&1) != 0) return 0;
				int k = 0, t = x;
				while((t&1) == 0 &&t > 2){
					k ++; t >>= 1;
				}
				if((k&1)== 0){ // k is even
					if(t == 3 || t == 2) return 1;
					else return 2;
				}else {
					if(t == 3|| t == 2) return 2;
					else return 1;
				}
		}
	}
	static void prln(String s){
		System.out.println(s);
	}
}


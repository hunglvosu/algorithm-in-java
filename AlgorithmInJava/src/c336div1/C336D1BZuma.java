// Author      : Hung Le
// Version     :
// Description : Java Code for http://codeforces.com/contest/607/problem/B
//============================================================================

package c336div1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class C336D1BZuma {
	static int N;
	static int[] A;
	static int[][] F;
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			N = Integer.parseInt(br.readLine());
			F = new int[N][N];
			A = new int[N];
			String[] chnks = br.readLine().split(" ");
			for(int i = 0 ; i < N; i++){
				A[i] = Integer.parseInt(chnks[i]);
			}
			for(int i = 0; i < N; i++)F[i][i] = 1;
			for(int i = 0; i < N; i++){
				for(int j = i+1; j < N; j++)F[i][j] = Integer.MAX_VALUE;
			}
			for(int d = 1; d < N; d++){
				for(int j = 0; j < N-d; j++){
					int k = 0;
					while(A[j+k] == A[j+d-k] && j + k <= j+d-k){
						k++;
						F[j][j+d] = Math.min(F[j+k][j+d-k], F[j][j+d]);
					}
					if(d-2*k <= 0) F[j][j+d] = 1;
					else {
						for(k = j; k <= j+d-1; k++){
							F[j][j+d] = Math.min(F[j][j+d], F[j][k] + F[k+1][j+d]);
						}
					}
				}
			}
			System.out.println(F[0][N-1]);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

}

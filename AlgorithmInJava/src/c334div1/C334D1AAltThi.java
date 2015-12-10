
package c334div1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class C334D1AAltThi {
	static int N;
	static String S;
	static int[][] F;
	
	public static void main(String[] args){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			N = Integer.parseInt(br.readLine());
			S = br.readLine();
			F = new int[N][6];
			
			F[0][0] = (S.charAt(0) == '1'? 1: 0);
			F[0][1] = 1 - F[0][0]; 
			F[0][2] = 1 - F[0][0];
			F[0][3] = F[0][0];
			F[0][4] = 0;
			F[0][5] = 0;
			int m = 1;
			for(int i = 1; i < N; i++){
				if(S.charAt(i) == '1'){// S[i] change from 1 to 0
					F[i][0] = Math.max(Math.max(F[i-1][0], F[i-1][1] + 1), F[i-1][3]+ 1);
					F[i][1] = Math.max(F[i-1][1], F[i-1][3]);
					F[i][2] = F[i-1][2];
					F[i][3] = Math.max(F[i-1][2] + 1, F[i-1][3]);
					if(S.charAt(i-1) == '1'){ // 11
						F[i][4] = Math.max(F[i-1][4], F[i-1][0]);
						F[i][5] = Math.max(Math.max(F[i-1][5], F[i-1][0] + 1), F[i-1][4] + 1);
					}else { // 01
						F[i][4] = Math.max(F[i-1][0], F[i-1][4]);
						F[i][5] = Math.max(Math.max(F[i-1][0] + 1, F[i-1][5]), F[i-1][4] +1);					
					}
				}else { // S[i] change from 0 to 1
					F[i][0] = Math.max(F[i-1][0], F[i-1][2]);
					F[i][1] = Math.max(Math.max(F[i-1][1], F[i-1][0] + 1), F[i-1][2]+1);
					F[i][2] = Math.max(F[i-1][2], F[i-1][3] + 1);
					F[i][3] = F[i-1][3];
					if(S.charAt(i-1) == '1'){ // 10
						F[i][4] = Math.max(Math.max(F[i-1][1] + 1, F[i-1][0]),F[i-1][5] + 1);
						F[i][5] = Math.max(F[i-1][5], F[i-1][1]) ;
					}else { // 00
						F[i][4] = Math.max(Math.max(F[i-1][1]+1, F[i-1][4]),F[i-1][5] + 1);
						F[i][5] = Math.max(F[i-1][5], F[i-1][1]);					
					}
				}
			}
			m = Math.max(F[N-1][0], m);
			m = Math.max(F[N-1][1], m);
			m = Math.max(F[N-1][2], m);
			m = Math.max(F[N-1][3], m);
			m = Math.max(F[N-1][4], m);
			m = Math.max(F[N-1][5], m);
			prln(m + "");			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	static void prln(String s){
		System.out.println(s);
	}
}

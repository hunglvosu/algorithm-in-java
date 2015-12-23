package c335div1;

//============================================================================
// Author      : Hung Le
// Version     :
// Description : Java Code for http://codeforces.com/contest/605/problem/C
//============================================================================

/*
 * This code solves the problem by: finding the convex hull of the input and then, find
 * the intersection point of the optimal line with the hull.
 * 
 *  Another algorithm is writing this problem using linear program, dualizing and 
 *  then, using ternary search to find the opt.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class C335Div1CFreDre {
	static final double EPS = 1e-9;
	
	static int N;
	static double A,B;
	static DoublePoint[] P;
	static Line[] hullLines;
	
	public static void submitted(String args[]){
		BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			String[] nab = br.readLine().split(" ");
			N  = Integer.parseInt(nab[0]);
			A = Double.parseDouble(nab[1]);
			B = Double.parseDouble(nab[2]);
			P = new DoublePoint[N+2];
			double maxX = 0, maxY = 0;
			double x, y;
			String[] lns;
			for(int i = 0; i < N; i++){
				lns = br.readLine().split(" ");
				x =  Double.parseDouble(lns[0]);
				y = Double.parseDouble(lns[1]);
				maxX = Double.max(maxX, x);
				maxY = Double.max(maxY, y);
				P[i+1] = new DoublePoint(x, y);
			}
			P[0] = new DoublePoint(0, maxY + EPS/100);
			P[N+1] = new DoublePoint(maxX+EPS/100, 0);
			Arrays.sort(P);
			DoublePoint[] cvHull = new DoublePoint[N+2];
			int nHull = 0;
			cvHull[0] = P[0];
			int k = 2;
			for(; k < N+1; k++) if(Math.abs(P[k].x - P[1].x) > EPS) break;
			k--;
			cvHull[1]= P[k];
			nHull = 2;
			hullLines = new Line[N+1];
			hullLines[0] = new Line(cvHull[nHull-1].x, cvHull[nHull-1].y, cvHull[nHull-2].x, cvHull[nHull-2].y);			
			int nlines = 1;			
			int pos;			
			int i = k+1;
			while(i < N+1){
				int j = i+1;
				for(; j < N+1; j++)if(Math.abs(P[j].x-P[i].x) > EPS)break;
				j--;
				if(P[j].y > cvHull[nHull-1].y + EPS) {
					pos = hullLines[nlines -1].relativePos(P[j].x, P[j].y);
					if(pos > 0){
						int lineNo = searchLine(P[j], nlines-1,0);
						cvHull[lineNo+2] = P[j];
						nHull = lineNo+3;
						hullLines[lineNo+1] = new Line(P[j].x, P[j].y, cvHull[lineNo+1].x, cvHull[lineNo+1].y);
						nlines = lineNo+2;
					}else {
							cvHull[nHull] = P[j];
							nHull++;
							hullLines[nlines] = new Line(cvHull[nHull-1].x, cvHull[nHull-1].y, cvHull[nHull-2].x, cvHull[nHull-2].y);
							nlines++;
						}
				}
				i = j +1;
			}
			cvHull[nHull] = P[N+1];
			nHull++;
			Line optLine = new Line(0, 0, A, B);
			int tmp, prev;
			prev = optLine.relativePos(cvHull[0].x, cvHull[0].y);
			i = 0;
			for(i = 1; i < nHull; i++){
				tmp = optLine.relativePos(cvHull[i].x, cvHull[i].y);
				if(tmp != prev) break;
				else prev = tmp;
			}
			DoublePoint intersect = optLine.intersect(new Line(cvHull[i-1].x, cvHull[i-1].y, cvHull[i].x, cvHull[i].y));
			double time = A/intersect.x;
			prln(time + "");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	/*
	 *	Linear search is enough for accepted. This code can be improved by
	 *	adopting binary search. 	 
	 *  return the largest k  where P is below hullLinek[k] 
	 */
	
	static int searchLine(DoublePoint P, int h, int l){
		int pos = 1;
		while(pos > 0){
			pos = hullLines[h].relativePos(P.x, P.y);
			h--;
		}
		return h+1;
	}
	static void prln(String s){
		System.out.println(s);
	}

	
	static class Line{
		private double xa,xb;
		private double ya,yb;
		
		public Line(double xa, double ya, double xb, double yb) {
			this.xa = xa; this.xb  = xb;
			this.ya = ya; this.yb = yb;
		}
		
		public int relativePos(double x, double y){
			if(Math.abs(xa -xb) < EPS){
				return (Math.abs(x-xa) < EPS ? 0: (x < xa ? -1: 1));
			} else if(Math.abs(ya -yb) < EPS){
				return (Math.abs(y-ya) < EPS ? 0: (y < ya ? -1: 1));
			} else {
				double rs = (y-ya)/(ya-yb) - (x-xa)/(xa-xb);
				return (Math.abs(rs) < EPS? 0: (rs < 0? -1 : 1));
			}
		}
		
		public boolean isParallelX(){
			if(Math.abs(ya - yb) < EPS) return true;
			else return false;
		}
		
		public boolean isPrallelY(){
			if(Math.abs(xa - xb) < EPS) return true;
			else return false;
		}
		
		public DoublePoint intersect(Line l){
			if(l.isParallelX()){
				return new DoublePoint(xa + (xa - xb)*(l.ya-ya)/(ya-yb), l.ya);
			}else if(l.isPrallelY()){
				return new DoublePoint(l.xa, ya + (ya-yb)*(l.xa -xa)/(xa-xb));
			}else {
				double a1 = (ya-yb)/(xa-xb);
				double b1 = ya - (ya-yb)/(xa-xb)*xa;
				double a2 = (l.ya-l.yb)/(l.xa-l.xb);
				double b2  = l.ya - (l.ya-l.yb)/(l.xa-l.xb)*l.xa;
				return new DoublePoint((b2-b1)/(a1-a2), a1*(b2-b1)/(a1-a2) + b1);
			}
		}
		
		public String toString(){
			return "[(" + xa + "," + ya + ")," + "(" + xb + "," + yb + ")]";
		}
		
	}
	
	static class DoublePoint implements Comparable<DoublePoint>{
		private double x; 
		private double y;
		
		public DoublePoint(double x, double y) {
			this.x  = x;
			this.y  = y;
		}

		@Override
		public int compareTo(DoublePoint o) {
			if(Math.abs(o.x-x) < EPS){
				return Double.compare(y, o.y);
			}else return Double.compare(o.x, x);
		}
		
		public String toString(){
			return "(" + x + "," + y + ")";
		}
		
		
	}
}

//10 630 764
//679 16
//34 691
//778 366
//982 30
//177 9
//739 279
//992 89
//488 135
//7 237
//318 318
//
//output: 1.472265278375486


//10 123457 123459
//5 2
//11 4
//1 8
//11 1
//7 1
//10 2
//8 1
//11 3
//3 4
//11 8

//output: 15432.375000000000000
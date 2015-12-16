package ds;


public class LinkCutTree<Value> {
	
	/**
	 *  
	 */
	private class Node{
		private int key; 
		private Value v;
		private boolean mflip;
		public Node lcld, rcld, pr;
		
		public Node(int k, Value v){
			this.key = k;
			this.v = v;
			this.lcld = null; rcld = null;
			this.pr = null;
			
		}
		public int getKey(){
			return key;
		}
		public Value getVal(){
			return v;
		}
	}

}

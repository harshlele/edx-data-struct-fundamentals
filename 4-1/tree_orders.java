import java.util.*;
import java.io.*;

public class tree_orders {
    class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
	
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}

	public class TreeOrders {
		int n;
		int[] key, left, right;
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			key = new int[n];
			left = new int[n];
			right = new int[n];
			for (int i = 0; i < n; i++) { 
				key[i] = in.nextInt();
				left[i] = in.nextInt();
				right[i] = in.nextInt();
			}
		}

		List<Integer> inOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
            inOrderTraverse(result, 0);
			return result;
		}
     
     	void inOrderTraverse(List<Integer> l, int i){
        	if(i >= 0 && i < n){
                inOrderTraverse(l, left[i]);
                l.add(key[i]);
                inOrderTraverse(l, right[i]);
            }
        }

		List<Integer> preOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
            preOrderTraverse(result, 0);
			return result;
		}

        void preOrderTraverse(List<Integer> l, int i){
        	if(i >= 0 && i < n){
                l.add(key[i]);
                preOrderTraverse(l, left[i]);
                preOrderTraverse(l, right[i]);
            }
        }

		List<Integer> postOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
            postOrderTraverse(result, 0);
			return result;
		}

        void postOrderTraverse(List<Integer> l, int i){
        	if(i >= 0 && i < n){
                postOrderTraverse(l, left[i]);
                postOrderTraverse(l, right[i]);
                l.add(key[i]);
            }
        }
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_orders().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}

	public void print(List<Integer> x) {
		for (Integer a : x) {
			System.out.print(a + " ");
		}
		System.out.println();
	}

	public void run() throws IOException {
		TreeOrders tree = new TreeOrders();
		tree.read();
		print(tree.inOrder());
		print(tree.preOrder());
		print(tree.postOrder());
	}
}

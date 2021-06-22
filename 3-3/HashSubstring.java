import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Random;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static double getHash(String s,int start, int len, int prime, int x){
        double hash = 0;
        int j = 0;
        for(int i = start; i < start + len; i++){
            hash += ((s.charAt(i) - 'a')*Math.pow(x,j)) % prime;
            j++;
        }
        return hash;
    }

    private static List<Integer> getOccurrences(Data input) {
        String p = input.pattern, t = input.text;
        List<Integer> occurrences = new ArrayList<Integer>();
        int m = p.length(), n = t.length();
        
        int q = 53;
        int x = new Random().nextInt(q - 1) + 1;
        double pInt = 0;
        double tInt = 0;

        //calc int values of pattern and first m chars in text
        pInt = getHash(p, 0, m, q, x);
        tInt = getHash(t, n-m,m, q, x);

        for(int j = n-m; j >= 0; j--){
            if(pInt == tInt){
                if(p.equals(t.substring(j,j+m))){
                    occurrences.add(0,j);
                }
            }
            if( j > 0 ){
                double adj = (tInt * x + (t.charAt(j - 1) - 'a') - (t.charAt(j + m - 1) - 'a')*(Math.pow(x,m) % q)) % q;
                if(adj < 0) {
                    adj = (adj + q) % q; 
                }
                tInt = adj; 
            }
        }
    


        /*
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        for (int i = 0; i + m <= n; ++i) {
            boolean equal = true;
            for (int j = 0; j < m; ++j) {
                if (s.charAt(j) != t.charAt(i + j)) {
                    equal = false;
                    break;
                }
            }
            if (equal)
                occurrences.add(i);
	    }
        */
        return occurrences;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

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

    private static List<Integer> getOccurrences(Data input) {
        String p = input.pattern, t = input.text;
        List<Integer> occurrences = new ArrayList<Integer>();
        int m = p.length(), n = t.length();
        
        //int q = 500009;
        double pInt = 0;
        double tInt = 0;

        //double h = Math.pow(10, m-1) % q;

        //calc int values of pattern and first m chars in text
        for(int i = 0; i < m; i++){
            pInt = (pInt * 10 + (int)p.charAt(i))/* % q*/;
            tInt = (tInt * 10 + (int)t.charAt(i))/* % q*/; 
        }

        for(int j = 0; j <= n - m; j++){
            if(pInt == tInt){
                if(p.equals(t.substring(j,j+m))){
                    occurrences.add(j);
                }
            }
            if( j < n-m )
                //tInt = (10*(tInt - (t.charAt(j) * h)) + (int)t.charAt(j + m)) % q; //fix!!!!
                tInt = 10* (tInt - t.charAt(j)*Math.pow(10,m-1)) + (int)t.charAt(j + m);
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


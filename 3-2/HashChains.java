import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    private Node[] items;  
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        //out.flush();
    }

    private void processQuery(Query query) {
        int key;
        switch (query.type) {
            case "add":
                /*
                if (!elems.contains(query.s))
                    elems.add(0, query.s);
                */
                key = hashFunc(query.s);
                if(items[key] == null){
                    items[key] = new Node(query.s,key);
                }
                else{
                    Node curr = items[key];
                    boolean added = false;
                    while(curr != null){
                        if(curr.s.equals(query.s)){
                            added = true;
                            break;
                        }
                        curr = curr.next;
                    }
                    if(!added && curr == null){
                        Node l = new Node(query.s,key);
                        l.next = items[key];
                        items[key] = l;
                    }
                }
                break;
            case "del":
                key = hashFunc(query.s);
                if(items[key] != null){
                    if(items[key].s.equals(query.s)){
                        items[key] = items[key].next;
                    }
                    else{
                        Node c1 = items[key];
                        Node c2 = c1.next;
                        while(c2 != null){
                            if(c2.s.equals(query.s)){
                                c2 = c2.next;
                                c1.next = c2;
                                break;
                            }
                            c1 = c2;
                            c2 = c2.next;
                        }
                    }
                }
                break;
            case "find":
                //writeSearchResult(elems.contains(query.s));
                key = hashFunc(query.s);
                if(items[key] == null) writeSearchResult(false);
                else{
                    if(items[key].s.equals(query.s)){
                        writeSearchResult(true);
                    }
                    else{
                        Node curr = items[key];
                        while(curr != null){
                           if(curr.s.equals(query.s)){
                               writeSearchResult(true);
                               break;
                           }
                           curr = curr.next;
                        }
                        if(curr == null){
                            writeSearchResult(false);
                        } 
                    }
                }
                break;
            case "check":
                /*
                for (String cur : elems)
                    if (hashFunc(cur) == query.ind)
                        out.print(cur + " ");
                */
                if(items[query.ind] == null){
                    out.print("");
                }
                else if(items[query.ind].next == null){
                    out.print(items[query.ind].s + " ");
                }
                else{
                    Node curr = items[query.ind];
                    while(curr != null){
                        out.print(curr.s + " ");
                        curr = curr.next;
                    }
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                //out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        items = new Node[bucketCount];
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class Node {
        Node next,prev;
        String s;
        int key;

        public Node(String s,int key){
            this.s = s;
            this.key = key;
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

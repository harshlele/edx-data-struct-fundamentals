import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;  // Import the File class
import java.io.FileWriter;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
        this.timeElapsed = 0;
    }

    public Response Process(Request request) {
        while(this.finish_time_.size() > 0 && this.finish_time_.get(0) <= request.arrival_time){
            this.timeElapsed = request.arrival_time;
            this.finish_time_.remove(0);
        }
        
        if(this.finish_time_.size() == 0) {
            this.finish_time_.add(this.timeElapsed + request.process_time);
            return new Response(false, request.arrival_time);
        }   

        if(this.finish_time_.size() >= this.size_) return new Response(true, -1);    
        else{
        
            int start_time = this.finish_time_.get(this.finish_time_.size() - 1); 
            this.finish_time_.add(start_time + request.process_time);
            return new Response(false,start_time);
    
        }
    
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
    private int timeElapsed;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        
        Scanner scanner = new Scanner(System.in);
        
        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
        
        
        /*
        Buffer buffer;
        ArrayList<Request> requests = new ArrayList<Request>();
        try{
            File myObj = new File("19");
            Scanner s = new Scanner(myObj);
            String[] f = s.nextLine().split(" ");
            
            int buffer_max_size = Integer.valueOf(f[0]);
            buffer = new Buffer(buffer_max_size);
            
            int inputs = Integer.valueOf(f[1]);
            for(int i = 0; i < inputs; i++){
                String[] line = s.nextLine().split(" ");
                requests.add(new Request( Integer.valueOf(line[0]), Integer.valueOf(line[1]) ));
            }
            s.close();
            ArrayList<Response> responses = ProcessRequests(requests, buffer);
            //PrintResponses(responses);
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write("\n");
            for(Response r: responses){
                myWriter.write(r.start_time + "\n");
            }
            myWriter.close();
        }
        catch(Exception f){
            System.out.println(f.toString());
        }
        */
    }
}

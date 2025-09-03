package KFrequestUsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
    public static List<Map.Entry<String, Integer>> topKusers(List<String> logins, int k){
        Map<String, Integer> freq = new HashMap<>();
        for(String login: logins){
            freq.put(login, freq.getOrDefault(login, 0)+1);
        }
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            pq.offer(entry);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return new ArrayList<>(pq);
    }
    public static void main(String[] args) {
        List<String> logs = Arrays.asList("u1", "u2", "u1", "u3", "u2", "u2", "u4", "u1");
        System.out.println(topKusers(logs, 2));
    }
}

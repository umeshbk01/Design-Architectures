import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main{
    public static void main(String[] args){
        SharedResource sharedResource = new SharedResource(10);
        //read data from a txt file and produce it and later consume and print the data
        try (BufferedReader br = new BufferedReader(new FileReader("ProducerConsumer/src/MultiThreadingConcept/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String item = line;
                new Thread(() -> {
                    try {
                        sharedResource.producer(item);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Consumer thread
        new Thread(() -> {
            while (true) {
                try {
                    String item = sharedResource.consumer();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
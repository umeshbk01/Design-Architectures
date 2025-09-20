package FizzBuzzProblem;
import FizzBuzzProblem.FizzBuzz;
public class Main {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);
        Thread threadA = new Thread(()->{
            try {
                fizzBuzz.fizz(()-> System.out.println("fizz"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadB = new Thread(()->{
            try {
                fizzBuzz.buzz(()-> System.out.println("buzz"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadC = new Thread(()->{
            try {
                fizzBuzz.fizzbuzz(()-> System.out.println("fizzbuzz"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadD = new Thread(()->{
            try {
                fizzBuzz.number((x)-> System.out.println(x));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}

package FizzBuzzProblem;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class FizzBuzz {
    private int n;
    Semaphore fizzBuzzSemaphore;
    Semaphore fizzSemaphore;
    Semaphore buzzSemaphore;
    Semaphore numberSemaphore;

    public FizzBuzz(int n) {
        this.n = n;
        numberSemaphore = new Semaphore(1);
        fizzSemaphore = new Semaphore(0);
        buzzSemaphore = new Semaphore(0);
        fizzBuzzSemaphore = new Semaphore(0);
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for(int i=1; i<=n; i++){
            if(i%3==0 && i%5!=0){
                fizzSemaphore.acquire();
                printFizz.run();
                numberSemaphore.release();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for(int i=1; i<=n; i++){
            if(i%3!=0 && i%5==0){
                buzzSemaphore.acquire();
                printBuzz.run();
                numberSemaphore.release();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for(int i=1; i<=n; i++){
            if(i%3==0 && i%5==0){
                fizzBuzzSemaphore.acquire();
                printFizzBuzz.run();
                numberSemaphore.release();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for(int i=1; i<=n; i++){
            numberSemaphore.acquire();
            if(i%3==0 && i%5==0){
                fizzBuzzSemaphore.release();
            }else if(i%3==0){
                fizzSemaphore.release();
            }else if(i%5 == 0){
                buzzSemaphore.release();
            }else{
                printNumber.accept(i);
                numberSemaphore.release();
            }
        }
    }
}

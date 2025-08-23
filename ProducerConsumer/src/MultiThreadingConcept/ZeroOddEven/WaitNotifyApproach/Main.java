package ZeroOddEven.WaitNotifyApproach;

public class Main {
    public static void main(String[] args) {
        ZeroOddEvenWaitNotify zeroOddEven = new ZeroOddEvenWaitNotify(10);
        Thread t1 = new Thread(() -> {
            try {
                zeroOddEven.printZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                zeroOddEven.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                zeroOddEven.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}

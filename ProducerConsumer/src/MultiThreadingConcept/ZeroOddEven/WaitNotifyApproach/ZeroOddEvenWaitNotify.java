package ZeroOddEven.WaitNotifyApproach;

public class ZeroOddEvenWaitNotify {
        private int n;
    private int state = 0; // 0 -> zero's turn, 1 -> odd's turn, 2 -> even's turn
    private int current = 1;

    public ZeroOddEvenWaitNotify(int n) {
        this.n = n;
    }

    public synchronized void printZero() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (state != 0) wait();
            System.out.print(0 + " ");
            if (current % 2 == 1) state = 1; // odd’s turn
            else state = 2;                 // even’s turn
            notifyAll();
            Thread.sleep(3000);
        }
    }

    public synchronized void printOdd() throws InterruptedException {
        while (current <= n) {
            while (state != 1) wait();
            System.out.print(current + " ");
            current++;
            state = 0; // back to zero
            notifyAll();
            Thread.sleep(3000);
        }
    }

    public synchronized void printEven() throws InterruptedException {
        while (current <= n) {
            while (state != 2) wait();
            System.out.print(current + " ");
            current++;
            state = 0; // back to zero
            notifyAll();
            Thread.sleep(3000);
        }
    }
}

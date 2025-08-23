package ZeroOddEven.ConditionLockApproach;

import java.util.concurrent.locks.*;

public class ConditionLockApproach {
    int n;
    int state;
    private int current;
    Lock lock = new ReentrantLock();
    Condition zeroCondition = lock.newCondition();
    Condition oddCondition = lock.newCondition();
    Condition evenCondition = lock.newCondition();

    public ConditionLockApproach(int n) {
        this.n = n;
        this.state = 0; // 0 -> zero's turn, 1 -> odd's turn, 2 -> even's turn
        this.current=1;
    }

    public void printZero() throws InterruptedException{
        for(int i=0; i<=n; i++){
            lock.lock();
            try{
                while(state !=0){
                    zeroCondition.await();
                }
                System.out.print(0 + " ");
               if (current % 2 == 1) {
                    state = 1; // odd’s turn
                    oddCondition.signal();
                } else {
                    state = 2; // even’s turn
                    evenCondition.signal();
                }
            }finally{
                lock.unlock();
            }
        }
    }

    public void printOdd() throws InterruptedException{
        while (true) {
            lock.lock();
            try {
                while (state != 1) {
                    if (current > n) return;
                    oddCondition.await();
                }
                if (current > n) return;
                System.out.print(current + " ");
                current++;
                state = 0;
                zeroCondition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printEven() throws InterruptedException{
         while (true) {
            lock.lock();
            try {
                while (state != 2) {
                    if (current > n) return;
                    evenCondition.await();
                }
                if (current > n) return;
                System.out.print(current + " ");
                current++;
                state = 0;
                zeroCondition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

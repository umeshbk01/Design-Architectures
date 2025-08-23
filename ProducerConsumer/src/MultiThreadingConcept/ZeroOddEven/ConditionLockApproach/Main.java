package ZeroOddEven.ConditionLockApproach;

public class Main {
    public static void main(String[] args) {
        ConditionLockApproach obj = new ConditionLockApproach(10);
        Thread t1 = new Thread(() -> {
            try {
                obj.printZero();
            } catch (Exception e) {
                // TODO: handle exception
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                obj.printOdd();
            } catch (Exception e) {
            }
        });
        
        Thread t3 = new Thread(() -> {
            try {
                obj.printEven();
            } catch (Exception e) {
            }
        });
        t1.start();
        t2.start();
        t3.start();
    
    }

}

public class MyApp {

    public static class MyRunableTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0 ; i<10;i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" +i);

            }
        }
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0 ; i<10;i++) {
                System.out.println(Thread.currentThread().getName() + ":" +i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyRunableTask());

        Thread t2 = new Thread(new MyRunableTask());

        Thread t3 = new Thread(new MyRunableTask());

        Thread t4 = new Thread(new MyRunableTask());

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t4.join();
        Thread t5 = new MyThread();
        t5.start();

        Thread t6 = new Thread(() -> {
            for (int i = 0 ; i<11;i++) {
                System.out.println(Thread.currentThread().getName() + ":" +i);
            }
        });
        t6.start();

        Counter counter = new Counter();
        System.out.println(counter.getValue());
        Thread t7 = new Thread(() ->{
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });


        Thread t8 = new Thread(() ->{
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });

        t7.start();
        t8.start();

        t7.join();
        t8.join();

        System.out.println(counter.getValue());


    }

    public static class Counter {
       private int value;

        public int getValue() {
            return value;
        }

        public synchronized void increment() {
            value++;
        }

        public synchronized void decrement() {
            value--;
        }
    }
}

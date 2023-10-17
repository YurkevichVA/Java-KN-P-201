package step.learning.async;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
public class TaskHW {
    private Object sumLocker = new Object();
    private Object atcLocker = new Object();
    private double sum = 100;
    private int activeThreadCount;
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        List<Future<Double>> results = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            Callable<Double> task = new InflationCalculator(month);
            Future<Double> result = executorService.submit(task);
            results.add(result);
        }

        executorService.shutdown();

        for (Future<Double> result : results) {
            try {
                double inflationRate = result.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class InflationCalculator implements Callable<Double> {
        private int month;

        public InflationCalculator(int month) {
            this.month = month;
        }

        @Override
        public Double call() throws Exception {
            try {
                Thread.sleep(1000); // імітація запиту
            } catch (InterruptedException ignored) {}

            double localSum;
            double p = 0.1; // імітація результату запиту

            synchronized (sumLocker) {
                localSum = sum = sum * (1 + p);
            }

            System.out.printf(Locale.US, "місяць: %02d, відсоток: %.2f, сума: %.2f%n", month, p, localSum);

            synchronized (atcLocker) {
                activeThreadCount--;
                if (activeThreadCount == 0) {
                    System.out.printf(Locale.US, "----------------%nзагальна сума: %.2f%n", sum);
                }
            }

            return sum;
        }
    }
}

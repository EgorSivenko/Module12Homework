package solutions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeCounter {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        final long startTime = System.currentTimeMillis();

        executor.scheduleAtFixedRate(() -> {
            // Calculation of seconds, as well as minutes and hours, if they have passed
            long currentTime = System.currentTimeMillis();
            long elapsedSeconds = (currentTime - startTime) / 1000;

            long elapsedMinutes = elapsedSeconds / 60;
            elapsedSeconds %= 60;

            long elapsedHours = elapsedMinutes / 60;
            elapsedMinutes %= 60;

            // Output of elapsed time data
            displayTimeInfo(elapsedHours, elapsedMinutes, elapsedSeconds);
        }, 1, 1, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(() -> {
            System.out.println("5 seconds have passed.");
        }, 5100, 5000, TimeUnit.MILLISECONDS);

        Thread.sleep(31_000);
        executor.shutdown();
    }

    private static void displayTimeInfo(long elapsedHours, long elapsedMinutes, long elapsedSeconds) {
        System.out.print("Time elapsed: ");
        if (elapsedHours >= 1)
            System.out.print(elapsedHours + " hour(s), ");

        if (elapsedMinutes >= 1)
            System.out.print(elapsedMinutes + " minute(s), ");

        System.out.println(elapsedSeconds + " second(s)");
    }
}

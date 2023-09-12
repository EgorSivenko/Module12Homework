package solutions;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {
    private final List<String> sequence = new ArrayList<>();
    private volatile int number = 1;
    private final int maxValue;

    public FizzBuzz(int maxValue) {
        this.maxValue = maxValue;
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(30);

        Thread fizzThread = new Thread(fizzBuzz::fizz);
        Thread buzzThread = new Thread(fizzBuzz::buzz);
        Thread fizzbuzzThread = new Thread(fizzBuzz::fizzbuzz);
        Thread numberThread = new Thread(fizzBuzz::number);

        fizzThread.start();
        buzzThread.start();
        fizzbuzzThread.start();
        numberThread.start();

        try {
            numberThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(fizzBuzz.getSequence());
    }

    public synchronized void fizz() {
        while (number <= maxValue) {
            if (number % 3 == 0 && number % 5 != 0) {
                sequence.add("Fizz");
                number++;
                notifyAll();
            }
            else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void buzz() {
        while (number <= maxValue) {
            if (number % 3 != 0 && number % 5 == 0) {
                sequence.add("Buzz");
                number++;
                notifyAll();
            }
            else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void fizzbuzz() {
        while (number <= maxValue) {
            if (number % 3 == 0 && number % 5 == 0) {
                sequence.add("FizzBuzz");
                number++;
                notifyAll();
            }
            else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void number() {
        while (number <= maxValue) {
            if (number % 3 != 0 && number % 5 != 0) {
                sequence.add(String.valueOf(number++));
                notifyAll();
            }
            else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public String getSequence() {
        return String.join(", ", sequence);
    }
}

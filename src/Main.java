import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

    public static AtomicInteger count3 = new AtomicInteger();
    public static AtomicInteger count4 = new AtomicInteger();
    public static AtomicInteger count5 = new AtomicInteger();

    public static void main(String[] args) {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String word : texts) {
                if (word.contentEquals(new StringBuilder(word).reverse())) {
                    if (!isSame(word)) {
                        addToCount(word);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (String word : texts) {
                if (isSame(word)) {
                    addToCount(word);
                }
            }
        }).start();

        new Thread(() -> {
            for (String word : texts) {
                boolean isIncreace = true;
                for (int i = 0; i < word.length() - 1; i++) {
                    if (word.charAt(i) > word.charAt(i + 1)) {
                        isIncreace = false;
                        break;
                    }
                }
                if (isIncreace && !isSame(word)) {
                    addToCount(word);
                }
            }

        }).start();
        System.out.printf("Красивых слов с длиной 3: %d шт\n", count3.get());
        System.out.printf("Красивых слов с длиной 4: %d шт\n", count4.get());
        System.out.printf("Красивых слов с длиной 5: %d шт\n", count5.get());
    }

    public static boolean isSame(String text) {

        char first = text.charAt(0);
        for (char c : text.toCharArray()) {
            if (first != c) {
                return false;
            }
        }
        return true;
    }

    public static void addToCount(String word) {
        int size = word.length();

        switch (size) {
            case 3:
                count3.getAndIncrement();
                break;
            case 4:
                count4.getAndIncrement();
                break;
            case 5:
                count5.getAndIncrement();
                break;
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
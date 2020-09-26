import java.io.*;
import java.util.*;

public class utility {
    private static final String input_file_name = "addresses.txt";
    private static final String disk_name = "BACKING_STORE.txt";


    public static StringBuilder decToBinary(int dec) {
        String binaryNum = "";

        // counter for binary array
        int i = 0;
        while (dec > 0) {
            // storing remainder in binary array
            binaryNum = binaryNum.concat(String.valueOf(dec % 2));
            dec = dec / 2;
            i++;
        }
        StringBuilder result = new StringBuilder(binaryNum);
        while (result.length() < 32) {
            result.append(0);
        }

        return result.reverse();
    }

    public static int getDecimal(int binary) {
        int decimal = 0;
        int n = 0;
        while (true) {
            if (binary == 0) {
                break;
            } else {
                int temp = binary % 10;
                decimal += temp * Math.pow(2, n);
                binary = binary / 10;
                n++;
            }
        }
        return decimal;
    }

    public static void generate_random_input_file() throws FileNotFoundException {
        File file = new File(input_file_name);
        PrintStream printStream = new PrintStream(new FileOutputStream(file));
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int rand = random.nextInt(65536);
            printStream.println(rand);
        }
    }

    public static List<Integer> readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(input_file_name));
        List<Integer> numbers = new ArrayList<>();
        while (scanner.hasNext()) {
            numbers.add(scanner.nextInt());
        }
        return numbers;
    }

    public static void generate_random_bin_file(){
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(disk_name,"rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[]bytes = new byte[65536];
        new Random().nextBytes(bytes);
        try {
            assert randomAccessFile != null;
            randomAccessFile.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

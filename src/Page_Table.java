import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Page_Table {
    private static final int pageTableSize = 256;
    private static final int page_size = 256;
    private static int number_of_page_faults = 0;
    private static final Entry[] table = new Entry[pageTableSize];
    private static final String disk_name = "BACKING_STORE.txt";


    public static int getNumber_of_page_faults() {
        return number_of_page_faults;
    }

    static {
        for (int i = 0; i < table.length; i++) {
            table[i] = new Entry(false, null);
        }
    }

    private Page_Table() {

    }


    public static Entry getEntry(int page_number) {
        return table[page_number];
    }


    private static List<Byte> load_from_Disk(int page_number) {

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(disk_name, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] values = new byte[Page_Table.page_size];
        try {
            assert randomAccessFile != null;

            randomAccessFile.seek(page_number * Page_Table.page_size);
            randomAccessFile.read(values);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Byte> result = new ArrayList<>();
        for (byte value : values) {
            result.add(value);
        }

        return result;

    }

    private static void Page_table_update(int frame_number, int page_number) {
        // ban previous virtual address which points to this frame
        for (Entry entry : table) {
            if (entry.getValid() && entry.getFrame_number().equals(String.valueOf(utility.decToBinary(frame_number))))
                entry.setValid(false);
        }
        table[page_number].setFrame_number(String.valueOf(utility.decToBinary(frame_number)));
        table[page_number].setValid(true);
    }


    public static Entry page_fault_handler(int page_number) {
        number_of_page_faults++;
        List<Byte> values = load_from_Disk(page_number);

        // writing to RAM
        int frame_number = RAM.insertTo(values);
        Page_table_update(frame_number, page_number);
        return table[page_number];
    }

    public static byte test(Virtual_address virtual_address) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(disk_name, "r");
            randomAccessFile.seek(virtual_address.getDecimal_value());
            return (byte) randomAccessFile.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


}

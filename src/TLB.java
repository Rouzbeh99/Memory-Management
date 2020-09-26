import java.util.HashMap;
import java.util.Map;

public class TLB {
    private static final int tlb_size = 16;
    private static int filled = 0;
    private static final int page_size = 256;
    private static final Map<Integer, Entry> tlb = new HashMap<>();
    private static int numberOfMisses = 0;


    private TLB() {
    }

    public static int getNumberOfMisses() {
        return numberOfMisses;
    }

    public static boolean entryExists(int page_number) {
        if (tlb.containsKey(page_number)) { // need to update all LUs
            RAM.update_LUs_Hit(Integer.parseInt(getEntry(page_number).getFrame_number()));
        }

        return tlb.containsKey(page_number);
    }

    public static Entry getEntry(int page_number) {
        return tlb.get(page_number);
    }

    public static void miss_Handler(int page_number) {

        numberOfMisses++;

        // check if page has been loaded to RAM
        Entry entry = Page_Table.getEntry(page_number);
        if (!entry.getValid()) { // needs to be loaded
            // page fault
            entry = Page_Table.page_fault_handler(page_number);
        }
        if (filled < tlb_size) {
            tlb.put(page_number, entry);
            filled++;
        } else {
            tlb.remove(tlb.keySet().iterator().next()); // remove first item in TLB (FIFO)
            tlb.put(page_number, entry);
        }
    }

}

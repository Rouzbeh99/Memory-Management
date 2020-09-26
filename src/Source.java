import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Source {


    public static Virtual_address makeVirtualAddress(int number) {
        StringBuilder bin = utility.decToBinary(number);
        String offset = bin.substring(bin.length() - 8, bin.length());
        String page_number = bin.substring(bin.length() - 16, bin.length() - 8);
        return new Virtual_address(offset, page_number);
    }


    public static List<Virtual_address> extractVirtualAddresses(List<Integer> inputs) {
        List<Virtual_address> result = new ArrayList<>();
        for (Integer input : inputs) {
            result.add(makeVirtualAddress(input));
        }
        return result;
    }

    private static Physical_address extractPhysicalAddresses(Virtual_address virtualAddress) {
        int page_number = utility.getDecimal( Integer.parseInt(virtualAddress.getPage_number()));
        if (!TLB.entryExists(page_number)) {
            TLB.miss_Handler(page_number);
        }

        Entry entry = TLB.getEntry(page_number);
        String frame_number = entry.getFrame_number();
        return new Physical_address(virtualAddress.getOffset(), frame_number);

    }

    public static void main(String[] args) throws FileNotFoundException {

//        utility.generate_random_input_file();
//        utility.generate_random_bin_file();
        List<Integer> inputs = utility.readInput();
        List<Virtual_address> virtualAddresses = extractVirtualAddresses(inputs);
        int counter = 0;
        for (Virtual_address virtualAddress : virtualAddresses) {
            Physical_address physical_address = extractPhysicalAddresses(virtualAddress);
            int frame_number = Integer.parseInt(physical_address.getPage_number());
            int offset = Integer.parseInt(physical_address.getOffset());
            System.out.println("Virtual Address is : " + virtualAddress.getDecimal_value());
            System.out.println("Physical Address is : " + physical_address.getDecimal_value());
            System.out.println("Signed byte value stored at the translated physical address : " + RAM.get_value(utility.getDecimal(frame_number), utility.getDecimal(offset)));
            System.out.println("Tested value is  : " + Page_Table.test(virtualAddress));
            counter++;
            System.out.println("---------------------------------------------------");
        }
        System.out.println("Total number Of Page Faults : " + Page_Table.getNumber_of_page_faults());
        System.out.println("Page Fault rate : " + (Page_Table.getNumber_of_page_faults() / 19.0) *100);
        System.out.println("Total number Of TLB misses : " + TLB.getNumberOfMisses());
        System.out.println("Page Fault rate : " + (TLB.getNumberOfMisses() / 19.0) * 100);

    }


}




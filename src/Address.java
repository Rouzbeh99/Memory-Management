
public class Address implements Comparable<Address> {
    private String offset;
    private String page_number;
    private String address;
    private int decimal_value;

    public Address(String offset, String page_number) {
        this.offset = offset;
        this.page_number = page_number;
        int page_size;
        if (this instanceof Virtual_address)
            page_size = 256;
        else
            page_size = 128;
        this.decimal_value = utility.getDecimal(Integer.parseUnsignedInt(page_number)) * page_size + utility.getDecimal(Integer.parseUnsignedInt(offset));
        this.address = this.page_number.concat(this.offset);
    }

    public String getOffset() {
        return offset;
    }

    public String getPage_number() {
        return page_number;
    }

    public String getAddress() {
        return address;
    }

    public int getDecimal_value() {
        return decimal_value;
    }

    @Override
    public String toString() {
        return "Address{" +
                "offset='" + offset + '\'' +
                ", page_frame='" + page_number + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int compareTo(Address o) {
        return Integer.parseInt(this.address) - Integer.parseInt(o.address);
    }
}

class Virtual_address extends Address {

    public Virtual_address(String offset, String page_number) {
        super(offset, page_number);
    }
}

class Physical_address extends Address {

    public Physical_address(String offset, String page_frame) {
        super(offset, page_frame);
    }
}
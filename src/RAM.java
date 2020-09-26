import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class RAM {
    private static final int number_of_frames = 128;
    private static final int frame_size = 256;
    private static int nextEmpty = 0;
    private static final Byte[][] RAM = new Byte[number_of_frames][frame_size];
    private static Queue<Pair> last_use = new PriorityQueue<>();


    private RAM() {
    }

    public static byte get_value(int frame_number, int offset) {
        return RAM[frame_number][offset];
    }

    /**
     * @param values
     * @return frame number of the RAM in which values is saved
     */
    public static int insertTo(List<Byte> values) {
        int frame_number = getNextEmptyFrame();
        for (int i = 0; i < values.size(); i++) {
            RAM[frame_number][i] = values.get(i);
        }
        update_LUs();
        last_use.add(new Pair(frame_number, 0));
        return frame_number;
    }

    private static void update_LUs() {
        last_use.forEach(pair -> pair.setLast_use(pair.getLast_use() + 1));
    }

    // update all LUs after a hit
    public static void update_LUs_Hit(int frame_number) {
        update_LUs();
        last_use.stream().filter(pair -> pair.getFrame_number() == frame_number).forEach(pair -> pair.setLast_use(0));
    }

    // LRU
    private static int getNextEmptyFrame() {
        if (nextEmpty < number_of_frames - 1) {
            return nextEmpty++;
        }
        Pair pair = last_use.poll();
        assert pair != null;
        return pair.getFrame_number();

    }

}

class Pair implements Comparable<Pair> {
    private int frame_number;
    private int last_use;

    public Pair(int frame_number, int last_use) {
        this.frame_number = frame_number;
        this.last_use = last_use;
    }

    public int getFrame_number() {
        return frame_number;
    }

    public void setFrame_number(int frame_number) {
        this.frame_number = frame_number;
    }

    public int getLast_use() {
        return last_use;
    }

    public void setLast_use(int last_use) {
        this.last_use = last_use;
    }


    @Override
    public int compareTo(Pair o) {
        return this.last_use - o.last_use;
    }
}

package testing;

public class Entry {
    int data;

    Entry next;

    public static void main(String[] args) {
        System.out.println(ObjectSizeFetcher.getObjectSize(new Entry()));
    }
}

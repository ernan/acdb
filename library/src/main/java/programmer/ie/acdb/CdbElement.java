package programmer.ie.acdb;

public final class CdbElement {
    private final byte[] key;
    private final byte[] data;


    /**
     * Creates an instance of the CdbElement class and initializes it
     * with the given key and data values.
     *
     * @param key  The key value for this element.
     * @param data The data value for this element.
     */
    public CdbElement(byte[] key, byte[] data) {
        this.key = key;
        this.data = data;
    }

    public final byte[] getKey() {
        return key;
    }

    public final byte[] getData() {
        return data;
    }
}

package programmer.ie.acdb;

public final class CdbElement {
    private byte[] key_ = null;
    private byte[] data_ = null;


    /**
     * Creates an instance of the CdbElement class and initializes it
     * with the given key and data values.
     *
     * @param key  The key value for this element.
     * @param data The data value for this element.
     */
    public CdbElement(byte[] key, byte[] data) {
        key_ = key;
        data_ = data;
    }

    public final byte[] getKey() {
        return key_;
    }

    public final byte[] getData() {
        return data_;
    }
}

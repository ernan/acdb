# acdb
======

An android implementation of CDB database

This is an implementation of [CDB] (https://cr.yp.to/cdb.html)

CDB is useful if you have a static or relatively static database.

The key concepts of CDB

1. Fast lookups
2. Low overhead
3. No size limits
4. Fast replacement

I used the source code from [strangeGizmo.com](http://www.strangegizmo.com/products/sg-cdb/)

For me the testing went very well.
I am seeing five to ten times improvement in speed over sqlite.

The sample application loads 500 objects into a cdb database and aan SQLList database form comparison.

## Speed Comparison

This test is very simple so I would advise downloading the code and testing the performance for yourself.
All these were run on and android studio vm.

| Action              | Sqlite    | Cdb    |
| ------------------- |:---------:|-------:|
| Insert 500 objects  | 5490 ms   | 139 ms |
| Select 500 objects  | 1498 ms   | 116 ms |
| Optimised Select    |           |  52 ms |

![Example application results](/sc.png "Test acdb App results")


## Initialization

To create a new database we select a file and use CDBMake to create the database

#### Create a  database

    CdbMake make = new CdbMake();
    try {
        make.start(filepath);
        for (int i = 0; i < itemCount; ++i) {
            Product product = new Product(i, "Product " + i, i);
            make.add(ByteArrayUtil.toByteArray(product.getID()), ByteArrayUtil.toByteArray(product));
        }
        make.finish();
    } catch (IOException e) {
        e.printStackTrace();
    }


#### Inserting objects

The database is simply a key value store with bye arrays for keys and values.
So no matter what you are storing in it you will be creating the keys as arrays and  the values as arrays.

There is a util class included for handling some of the conversions.

For android you may store Parcel objects here is an example

#### convert the parcel to and from a byte array

    public static byte[] toByteArray(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    public static <T> T unmarshall(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = unmarshall(bytes);
        return creator.createFromParcel(parcel);
    }

    public static final byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

#### insert the parcel
	
	Product product = new Product(i, "Product " + i, i);
    make.add(ByteArrayUtil.toByteArray(product.getID()), ByteArrayUtil.toByteArray(product));

#### Unmarshal the parcel

   Product product = ByteArrayUtil.unmarshall(db.find(ByteArrayUtil.toByteArray(i)), Product.CREATOR);
   
   









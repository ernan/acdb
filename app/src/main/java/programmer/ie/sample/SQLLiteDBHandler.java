package programmer.ie.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLLiteDBHandler extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCT_NAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";

    public SQLLiteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCT_NAME
                + " TEXT," + COLUMN_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Product getProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE _id=?";
        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        Product result = null;
        if (c.moveToFirst()) {
            int pid = c.getInt(0);
            String name = c.getString(1);
            int quantity = c.getInt(2);
            result = new Product(pid, name, quantity);
        }
        c.close();
        db.close();
        return result;
    }


    public boolean deleteProduct(String productName) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCT_NAME + " =  \"" + productName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                    new String[]{cursor.getString(0)});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
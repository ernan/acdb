package programmer.ie.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import programmer.ie.acdb.Cdb;
import programmer.ie.acdb.CdbMake;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listview = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLLiteDBHandler handler = new SQLLiteDBHandler(getApplicationContext());

                int itemCount = 500;
                ArrayList<Product> testSet = new ArrayList<>();
                for (int i = 0; i < itemCount; ++i) {
                    testSet.add(new Product(i, "Product " + i, i));
                }
                ArrayList<Product> readSet = new ArrayList<>();

                long begin = System.currentTimeMillis();
                list.add("     \t\t" + "starting");
                for (int i = 0; i < itemCount; ++i) {
                    Product product = new Product(i, "Product " + i, i);
                    handler.addProduct(product);
                }
                long start = System.currentTimeMillis();
                list.add("" + (start - begin) + "(ms)\t\t" + "saved " + itemCount + " products to sqlite");
                begin = start;

                String filepath = getCacheDir() + "\\" + "test.cdb";

                CdbMake make = new CdbMake();
                try {
                    make.start(filepath);
                    for (int i = 0; i < itemCount; ++i) {
                        Product product = new Product(i, "Product " + i, i);
                        make.add(Util.toByteArray(product.getID()), Util.toByteArray(product));
                    }
                    make.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                start = System.currentTimeMillis();
                list.add("" + + (start - begin) + "(ms)\t\t" + "saved " + itemCount + " products to cdb");
                begin = start;

                for (int i = 0; i < itemCount; ++i) {
                    Product product = handler.getProduct(i);
                }
                start = System.currentTimeMillis();
                list.add("" + (start - begin) + "(ms)\t\t" + "got " + itemCount + " products from sqlite");
                begin = start;

                try {
                    Cdb db = new Cdb(filepath);
                    for (int i = 0; i < itemCount; ++i) {
                        Product product = Util.unmarshall(db.find(Util.toByteArray(i)), Product.CREATOR);
                        Log.i("Product: ", product.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                start = System.currentTimeMillis();
                list.add("" + + (start - begin) + "(ms)\t\t" + "got " + itemCount + " products from cdb");
                begin = start;

                ArrayList<byte[]> ints = new ArrayList<>();
                for (int i = 0; i < itemCount; ++i) {
                    ints.add(Util.toByteArray(i));
                }
                try {
                    Cdb db = new Cdb(filepath);

                    start = System.currentTimeMillis();
                    list.add("" + + (start - begin) + "(ms)\t\t" + "Stating optimised");
                    begin = start;

                    for (byte[] b: ints) {
                        byte[] c = db.find(b);
                    }
                    start = System.currentTimeMillis();
                    list.add("" + + (start - begin) + "(ms)\t\t" + "finished optimised");
                    begin = start;

                } catch (IOException e) {
                    e.printStackTrace();
                }


                adapter.notifyDataSetChanged();
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

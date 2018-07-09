package ex.niit.dev.mylistview3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    /*
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemQts = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();
    */
    private ArrayList<HashMap<String,String>> items = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView itemsView = (ListView)findViewById(R.id.listView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.shopping_app);
        actionBar.setSubtitle(getString(R.string.sub_title));


        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w("Index",position+"");
                Intent intent = new Intent(MainActivity.this,AddEditActivity.class);
                intent.putExtra(Util.INDEX,position);
                intent.putExtra(Util.ITEM_NAME,items.get(position).get(Util.ITEM_NAME));
                intent.putExtra(Util.ITEM_QTY,items.get(position).get(Util.ITEM_QTY));
                intent.putExtra(Util.ITEM_PRICE,items.get(position).get(Util.ITEM_PRICE));
                startActivityForResult(intent,Util.REQ_CODE);
            }
        });

        itemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                displayDeleteAlert(position);

                return true;
            }
        });

        adapter = new SimpleAdapter(this,items,R.layout.item_row,
                new String[]{Util.ITEM_NAME,Util.ITEM_PRICE,Util.ITEM_QTY},
                new int[]{R.id.itemName,R.id.itemPrice,R.id.itemQty});
         itemsView.setAdapter(adapter);

    }

    private void displayDeleteAlert(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.wait_txt);
        builder.setMessage(R.string.warning_msg);
        builder.setPositiveButton(R.string.del, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete
                items.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(R.string.cancel,null);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add){
            Intent intent = new Intent(this,AddEditActivity.class);
            intent.putExtra(Util.INDEX,Util.NEW_ITEM);
            startActivityForResult(intent,Util.REQ_CODE);
        }else if (item.getItemId() == R.id.action_register){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Util.REQ_CODE){
            if (resultCode == Util.RES_CODE){
                Bundle bundle = data.getExtras();
                int index = bundle.getInt(Util.INDEX);
                Log.w("ItemName",bundle.getString(Util.ITEM_NAME));
                HashMap<String,String> map = new HashMap<String, String>();
                map.put(Util.ITEM_NAME,bundle.getString(Util.ITEM_NAME));
                map.put(Util.ITEM_PRICE,bundle.getString(Util.ITEM_PRICE));
                map.put(Util.ITEM_QTY,bundle.getString(Util.ITEM_QTY));
                if (index == Util.NEW_ITEM){
                    //add
                    items.add(map);
                }else{
                    //edit
                    items.set(index,map);
                }
                adapter.notifyDataSetChanged();

            }
        }
    }
}

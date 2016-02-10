package billcounter.dk.com.billcounter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DatabaseHandler;
import model.Item;
import util.Utils;

public class DisplayItemsActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Item> dbItems=new ArrayList<>();
    private CustomListViewAdapter itemAdapter;
    private ListView listView;
    private Item myItem;
    private TextView totalDolls,TotalItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_items);

        listView=(ListView)findViewById(R.id.list);
        totalDolls=(TextView)findViewById(R.id.totalAmountTextView);
        TotalItems=(TextView)findViewById(R.id.totalItemsTextView);

        refreshData();


    }

    private void refreshData()
    {
       dbItems.clear();
        dba=new DatabaseHandler(getApplicationContext());

        ArrayList<Item> itemsFromDB=dba.getItems();

        int dollsValue=dba.totalDollars();
        int totalItems=dba.getTotalItems();
        String formattedValue= Utils.formatNumber(dollsValue);
        String formattedItems=Utils.formatNumber(totalItems);

        totalDolls.setText("Total money Spent : "+formattedValue);
        TotalItems.setText("Total Items : "+formattedItems);

        for(int i=0;i<itemsFromDB.size();i++)
        {
            String name=itemsFromDB.get(i).getItemName();
            String dateText=itemsFromDB.get(i).getRecordDate();
            int dols=itemsFromDB.get(i).getDollars();
            int itemID=itemsFromDB.get(i).getItemID();

            Log.v("Item ids : ",String.valueOf(itemID));

            myItem=new Item();
            myItem.setItemName(name);
            myItem.setItemID(itemID);
            myItem.setDollars(dols);
            myItem.setRecordDate(dateText);

            dbItems.add(myItem);



        }
        dba.close();
        //set adapter

        itemAdapter=new CustomListViewAdapter(DisplayItemsActivity.this,R.layout.list_row,dbItems);
        listView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
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

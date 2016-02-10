package billcounter.dk.com.billcounter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Item;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView itemName,dollar,dateTaken;
    private Button shareButton;
     private int itemId;
    private Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        itemName=(TextView)findViewById(R.id.detsItemName);
        dollar=(TextView)findViewById(R.id.detsMoneyValue);
        dateTaken=(TextView)findViewById(R.id.detsDateText);
        shareButton=(Button)findViewById(R.id.detsShareButton);
        delButton=(Button)findViewById(R.id.deleteButton);

        Item item=(Item)getIntent().getSerializableExtra("userObj");

        itemName.setText(item.getItemName());
        dollar.setText(String.valueOf(item.getDollars()));
        dateTaken.setText(item.getRecordDate());
        itemId=item.getItemID();


        dollar.setTextSize(34.9f);
        dollar.setTextColor(Color.RED);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDoll();
            }
        });



        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(ItemDetailActivity.this);
                alert.setTitle("Delete ?");
                alert.setMessage("Are you sure you wanna delete this item ?");
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                        dba.deleteItem(itemId);
                        Toast.makeText(getApplicationContext(), "Item Deleted ! ", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(ItemDetailActivity.this, DisplayItemsActivity.class));

                        //remove this activity from act stack
                        ItemDetailActivity.this.finish();

                    }
                });
                alert.show();
            }
        });


    }
    public void shareDoll()
    {
        StringBuilder dataString =new StringBuilder();
        String name=itemName.getText().toString();
        String dolls=dollar.getText().toString();
        String date=dateTaken.getText().toString();

        dataString.append(" Item : "+ name+"\n");
        dataString.append(" Dollars: "+dolls+"\n");
        dataString.append(" Bought on: "+date);

        Intent i=new Intent(Intent.ACTION_SEND);
        //
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Expenditure ");
        i.putExtra(Intent.EXTRA_EMAIL,new String[] {"kiit@hotmail.com"} );
        i.putExtra(Intent.EXTRA_TEXT,dataString.toString());

        try
        {
              startActivity(Intent.createChooser(i,"Send mail..."));
        }
        catch(ActivityNotFoundException a)
        {
            Toast.makeText(getApplicationContext(),"Please install an email client...",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteItem) {

            AlertDialog.Builder alert=new AlertDialog.Builder(ItemDetailActivity.this);
            alert.setTitle("Delete ?");
            alert.setMessage("Are you sure you wanna delete this item ?");
            alert.setNegativeButton("No", null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteItem(itemId);
                    Toast.makeText(getApplicationContext(), "Item Deleted ! ", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(ItemDetailActivity.this, DisplayItemsActivity.class));

                    //remove this activity from act stack
                    ItemDetailActivity.this.finish();

                }
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

}

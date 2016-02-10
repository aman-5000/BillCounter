package billcounter.dk.com.billcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Item;

public class MainActivity extends AppCompatActivity {
    private EditText itemName,itemDollars;
    private Button submitButton;
    private DatabaseHandler dba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba=new DatabaseHandler(MainActivity.this);
        itemName=(EditText)findViewById(R.id.itemEditText);
        itemDollars=(EditText)findViewById(R.id.dollarEditText);

        submitButton=(Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();
            }
        });

    }

    private void saveDataToDB()
    {
        Item item=new Item();
        String name=itemName.getText().toString().trim();
        String dollarString=itemDollars.getText().toString().trim();

        int doll=Integer.parseInt(dollarString);
        if (name.equals("") || dollarString.equals(""))
        {
            Toast.makeText(getApplicationContext(),"No empty fields allowed",Toast.LENGTH_LONG).show();
        }
        else
        {
            item.setItemName(name);
            item.setDollars(doll);

            dba.addItem(item);
            dba.close();

            //clear form data
            itemName.setText("");
            itemDollars.setText("");

            startActivity(new Intent(MainActivity.this,DisplayItemsActivity.class));

        }

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

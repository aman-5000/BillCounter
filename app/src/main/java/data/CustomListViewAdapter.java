package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import billcounter.dk.com.billcounter.ItemDetailActivity;
import billcounter.dk.com.billcounter.R;
import model.Item;

/**
 * Created by DK on 1/6/2016.
 */
public class CustomListViewAdapter extends ArrayAdapter<Item> {
    private int layoutResource;
    private Activity activity;
    private ArrayList<Item> itemList=new ArrayList<>();

    public CustomListViewAdapter(Activity act, int resource,ArrayList<Item> data)
    {
        super(act, resource,data);
        layoutResource=resource;
        activity=act;
        itemList=data;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Item getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public int getPosition(Item item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View row=convertView;
        ViewHolder holder=null;
        if(row==null ||(row.getTag()==null))
        {
            LayoutInflater lif=LayoutInflater.from(activity);

            //row holds list_row which we created
            row=lif.inflate(layoutResource,null);

            holder=new ViewHolder();
            holder.itemName=(TextView)row.findViewById(R.id.name);
            holder.itemDate=(TextView)row.findViewById(R.id.dateText);
            holder.itemCost=(TextView)row.findViewById(R.id.dollars);

            row.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)row.getTag();
        }

        holder.item=getItem(position);
        holder.itemName.setText(holder.item.getItemName());
        holder.itemDate.setText(holder.item.getRecordDate());
        holder.itemCost.setText(String.valueOf(holder.item.getDollars()));


        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(activity, ItemDetailActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userObj", finalHolder.item);
                //extra for small data int/string etc,  extras for large data like bundle
                i.putExtras(mBundle);

                //because we are not inside an activity so we pass following way / using context - which activity it is
                activity.startActivity(i);
            }
        });

        return row;
    }

    public class ViewHolder
    {
        Item item;
        TextView itemName;
        TextView itemCost;
        TextView itemDate;
     }
}

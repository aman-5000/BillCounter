package model;

import java.io.Serializable;

/**
 * Created by DK on 1/5/2016.
 */
public class Item implements Serializable
{
    private static final long serialVersionUID=10L;
    private String itemName;
    private int dollars;
    private int itemID;
    private String recordDate;

    public Item(String itemName, int dollars, int itemID, String recordDate)
    {
        this.itemName = itemName;
        this.dollars = dollars;
        this.itemID = itemID;
        this.recordDate = recordDate;
    }

    public Item()
    {

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}

package com.portfolio.contactlist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_list")
public class Contact
{

    @ColumnInfo(name = "contactId")
    @NonNull
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "contactName")
    private String contactName;

    @ColumnInfo(name = "contactPhone")
    private String contactPhone;

    @ColumnInfo(name = "contactEmail")
    private String contactEmail;

    //CONSTRUCTOR (Note: parameter names must match column names exactly)
    public Contact(String contactName, String contactPhone, String contactEmail)
    {
        this.id = id;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    //GETTERS
    public int getId() {return this.id; }
    public String getContactName() { return this.contactName; }
    public String getContactPhone() { return this.contactPhone; }
    public String getContactEmail() { return this.contactEmail; }

    //SETTERS
    public void setId(int id) { this.id = id; }
    public void setContactName(String name) { this.contactName = name; } //Unused
    public void setContactPhone(String phone) { this.contactPhone = phone; } //Unused
    public void getContactEmail(String email) { this.contactEmail = email; } //Unused
}
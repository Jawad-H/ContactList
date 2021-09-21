package com.portfolio.contactlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>
{
    private int contactItemLayout;
    private static List<Contact> contactCardList;
    OnDeleteClickListener deleteListener;

    //CONSTRUCTOR
    public ContactListAdapter(int layoutId)
    {
        contactItemLayout = layoutId;
    }

    public void setContactList(List<Contact> contacts)
    {
        contactCardList = contacts;
        notifyDataSetChanged();
    }

    //SORT
    public void sort(final boolean reverse)
    {
        Collections.sort(contactCardList, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs)
            {
                int dif = lhs.getContactName().toUpperCase().compareTo(rhs.getContactName().toUpperCase());
                if (reverse) return -1 * dif;
                else return dif;
            }
        });
        setContactList(contactCardList);
    }

    @Override
    public int getItemCount() { return contactCardList == null ? 0 : contactCardList.size(); }

    //VIEW HOLDER METHODS
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(contactItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int listPosition)
    {
        TextView nameView = holder.nameView;
        TextView phoneView = holder.phoneView;
        TextView emailView = holder.emailView;
        ImageButton deleteButton = holder.deleteButton;

        nameView.setText(contactCardList.get(listPosition).getContactName());
        phoneView.setText(contactCardList.get(listPosition).getContactPhone());
        emailView.setText(contactCardList.get(listPosition).getContactEmail());

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = contactCardList.get(listPosition).getContactName();
                deleteListener.onClick(name);
            }
        });


    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameView;
        TextView phoneView;
        TextView emailView;
        ImageButton deleteButton;

        ViewHolder(final View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_view);
            phoneView = itemView.findViewById(R.id.phone_view);
            emailView = itemView.findViewById(R.id.email_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }


    }

    //DELETE CLICK LISTENERS
    public void setOnDeleteClickListener(ContactListAdapter.OnDeleteClickListener deleteListener)
    {
        this.deleteListener = deleteListener;
    }
    public interface OnDeleteClickListener { void onClick(String name);}
}
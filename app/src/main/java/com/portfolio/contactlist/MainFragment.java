package com.portfolio.contactlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

public class MainFragment extends Fragment
{
    static MainViewModel mViewModel;
    static ContactListAdapter adapter = new ContactListAdapter(R.layout.card_layout);
    private TextInputLayout contactName;
    private TextInputLayout contactPhone;
    private TextInputLayout contactEmail;
    private Button addContact;
    private Pattern pattern;
    private Matcher matcher;

    //CONSTRUCTOR
    public static Fragment newInstance() { return new MainFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        contactName = getView().findViewById(R.id.contact_name);
        contactPhone = getView().findViewById(R.id.contact_phone);
        contactEmail =getView().findViewById(R.id.contact_email);
        recyclerSetup();
        observerSetup();

        addContact = getView().findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            String name = contactName.getEditText().getText().toString();
            String phone = contactPhone.getEditText().getText().toString();
            String email = contactEmail.getEditText().getText().toString();
            String id = getAlphaNumericString(10);

            String EMAIL_PATTERN =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if(!name.equals("")){
                System.out.println("name");
                contactName.setError(null);
                if(!phone.equals("")){
                    contactPhone.setError(null);
                    System.out.println("phone");
                    if(!email.equals("")){
                        contactEmail.setError(null);
                        System.out.println("email");
                        Pattern p = Pattern.compile(EMAIL_PATTERN);
                        Matcher m = p.matcher(email);
                        if(m.matches()){
                            Contact contact = new Contact(name, phone, email);
                            contact.setId(new Random().nextInt());
                            mViewModel.insertContact(contact);
                            clearFields();
                        }else
                            contactEmail.setError("*xyz@gmail.com");
                    } else{
                        contactEmail.setError("*xyz@gmail.com");
                    }
                }else{
                    contactPhone.setError(" ");
                }
            }else{
                contactName.setError(" ");
            }
        }
    });

        Button fab = getView().findViewById(R.id.searchButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name = contactName.getEditText().getText().toString();
                String phone = contactPhone.getEditText().getText().toString();
                String email = contactEmail.getEditText().getText().toString();

                if (!name.equals("")) { mViewModel.findName(name); }
                else if (!phone.equals("")) { mViewModel.findPhone(phone); }
                else if (!email.equals("")) { mViewModel.findEmail(email); }
                else {
                    Toast.makeText(getContext(), "You must enter criteria to search for", Toast.LENGTH_SHORT).show();
                }
                clearFields();
            }
        });
    }

    //CLEAR - Cannot call non-static MainActivity.clearFields() from static Fragment
    public void clearFields()
    {
        contactName.getEditText().setText("");
        contactPhone.getEditText().setText("");
        contactEmail.getEditText().setText("");
        contactName.requestFocus();
    }

    public void insertContact (Contact contact)
    {
        if (!contact.getContactName().equals("") && !contact.getContactPhone().equals(""))
        {
            mViewModel.insertContact(contact);
        }
    }

    //Sorts the display adapter, not the data
    public void sort(boolean reverse) { adapter.sort(reverse); }

    //OBSERVER SETUP
    private void observerSetup()
    {
        mViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts)
            {
                adapter.setContactList(contacts);
            }
        });

        mViewModel.getSearchResults().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts)
            {
                if (contacts.size() > 0)
                {
                    adapter.setContactList(contacts);
                }
                else
                {
                    mViewModel.findName("");
                    MainActivity.toaster(getContext(),"No matches found");
                }
            }
        });
    }

    //RECYCLER SETUP
    private void recyclerSetup()
    {
        RecyclerView recyclerView;
        recyclerView = getView().findViewById(R.id.contact_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener(new ContactListAdapter.OnDeleteClickListener()
        {
            public void onClick(String name)
            {
                mViewModel.deleteContact(name);
            }
        });
    }

     String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
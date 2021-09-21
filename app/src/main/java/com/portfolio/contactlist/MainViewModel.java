package com.portfolio.contactlist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel
{
    private static ContactRepository repository;
    private LiveData<List<Contact>> allContacts;
    private MutableLiveData<List<Contact>> searchResults;

    //CONSTRUCTOR
    public MainViewModel (Application application)
    {
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
        searchResults = repository.getSearchResults();
    }

    //GETTERS
    MutableLiveData<List<Contact>> getSearchResults() { return searchResults; }
    LiveData<List<Contact>> getAllContacts() { return allContacts; }

    //REPOSITORY OPERATIONS (insert, find, delete)
    public void insertContact(Contact contact) { repository.insertContact(contact); }
    public void findName(String name) { repository.findName(name); }
    public void findPhone(String phone) { repository.findPhone(phone); }
    public void findEmail(String email) { repository.findEmail(email); }
    public void deleteContact(String name) { repository.deleteContact(name); }
}
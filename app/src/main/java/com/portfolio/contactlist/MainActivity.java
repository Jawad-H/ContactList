package com.portfolio.contactlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    MainFragment fragment = new MainFragment();
    private EditText contactName;
    private EditText contactPhone;
    private EditText contactEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commitNow();
        }
    }


    //Calling fragment.clearFields() causes the application to crash
    private void clearFields()
    {
        contactName.setText("");
        contactPhone.setText("");
        contactEmail.setText("");
        contactName.requestFocus();
    }

    //TOASTER
    public static void toaster(Context context, String msg)
    {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View toastView = toast.getView();

        TextView toastMessage = (TextView)toastView.findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setCompoundDrawablePadding(16);
        toastMessage.setGravity(Gravity.LEFT);
        toastMessage.setPadding(25,0,25,05);
        toast.setGravity(Gravity.TOP, 0, 280);
        toast.show();
    }

}
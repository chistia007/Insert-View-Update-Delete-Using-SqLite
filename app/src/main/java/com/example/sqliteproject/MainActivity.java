package com.example.sqliteproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqliteproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Database d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        d =new Database(this);
        //d.SQLite getReadableDatabase(); //accessing database
        //Insert
        binding.btnInsert.setOnClickListener(view -> {
            String name1=binding.edtName.getText().toString();
            String userName=binding.edtUserName.getText().toString();
            String password1=binding.edtPassword.getText().toString();

            if(name1.equals("")|| userName.equals("") || password1.equals("")){
                Toast.makeText(MainActivity.this, "You can not leave any field empty", Toast.LENGTH_SHORT).show();
            }
            else{
                Boolean i=d.insert_data(name1,userName,password1);
                if(i){
                    Toast.makeText(MainActivity.this, "Successfully data Inserted", Toast.LENGTH_SHORT).show();
                }
                else{Toast.makeText(MainActivity.this, "Insertion failed", Toast.LENGTH_SHORT).show();}
                binding.edtName.setText("");
                binding.edtUserName.setText("");
                binding.edtPassword.setText("");
            }
        });

        // View
        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor t=d.getInfo();
                if(t.getCount()==0){
                    Toast.makeText(MainActivity.this, "No data founded", Toast.LENGTH_SHORT).show();
                }
                StringBuffer buffer=new StringBuffer();
                while(t.moveToNext()){
                    buffer.append("ID "+t.getString(0)+"\n");
                    buffer.append("Name: "+t.getString(1)+"\n");
                    buffer.append("User Name: "+t.getString(2)+"\n\n\n");
                    //buffer.append("password: "+t.getString(3)+"\n");
                }
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Signup users data\n");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        //Update
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2=binding.edtName.getText().toString();
                String userName2=binding.edtUserName.getText().toString();
                String password2=binding.edtPassword.getText().toString();
                Boolean i =d.updateData(name2,userName2,password2);
                if(i){
                    Toast.makeText(MainActivity.this, "Data updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Delete
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName3=binding.edtUserName.getText().toString();
                Boolean i =d.deleteData(userName3);
                if(i){
                    Toast.makeText(MainActivity.this, "Data updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
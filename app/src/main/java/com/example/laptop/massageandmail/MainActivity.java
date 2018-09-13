package com.example.laptop.massageandmail;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnEmail, btnSms, btnDialer, btnCall;
    private EditText number;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEmail = findViewById(R.id.sendEmail);
        btnSms = findViewById(R.id.btn_launch_sms);
        btnDialer = findViewById(R.id.btn_launch_call);
        btnCall = findViewById(R.id.btn_call);

        number = findViewById(R.id.txt_number);


        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail();

            }
        });


        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSms();

            }
        });

        btnDialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer();

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callNumber();

            }
        });

    }

    private String getPhoneNumber() {

        return number.getText().toString();
    }

    private void sendEmail() {

        String[] sendTo = {"mail@examle.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/*,plain/*");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, sendTo);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is demo Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "This is my demo email body");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Mail"));
            finish();
        } catch (ActivityNotFoundException e) {

            Toast.makeText(MainActivity.this, "There is no Email Clints", Toast.LENGTH_SHORT).show();

        }


    }

    private void sendSms() {


        Uri uri = Uri.parse("smsto:" + getPhoneNumber());
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsIntent.putExtra("sms_body", "This is My sms app");

        try {
            startActivity(smsIntent);
            finish();
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "SMS faild", Toast.LENGTH_SHORT).show();
        }
    }


    private void openDialer() {


        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + getPhoneNumber()));
        startActivity(dialIntent);
    }

    private void callNumber() {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + getPhoneNumber()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode){

            case  1:{

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    callNumber();
                }

                else {

                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}

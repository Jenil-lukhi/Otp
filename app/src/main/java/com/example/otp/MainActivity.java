package com.example.otp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{

    EditText enternumber;
    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         enternumber = findViewById(R.id.input_mobile_number);
         getotpbutton = findViewById(R.id.buttongetotp);

         ProgressBar progressbar = findViewById(R.id.progress_sending_otp);

         getotpbutton.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v) {
                 if (!enternumber.getText().toString().trim().isEmpty())
                 {
                     if ((enternumber.getText().toString().trim()).length() == 10)
                     {

                         progressbar.setVisibility(View.VISIBLE);
                         getotpbutton.setVisibility(View.INVISIBLE);

                         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                 "+91" + enternumber.getText().toString(),
                                 60,
                                 TimeUnit.SECONDS,
                                 MainActivity.this,
                                 new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                     @Override
                                     public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                         progressbar.setVisibility(View.GONE);
                                         getotpbutton.setVisibility(View.VISIBLE);

                                     }

                                     @Override
                                     public void onVerificationFailed(@NonNull FirebaseException e) {
                                         progressbar.setVisibility(View.GONE);
                                         getotpbutton.setVisibility(View.VISIBLE);
                                         Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                     }

                                     @Override
                                     public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                         progressbar.setVisibility(View.GONE);
                                         getotpbutton.setVisibility(View.VISIBLE);
                                         Intent intent = new Intent(getApplicationContext(), otpv.class);
                                         intent.putExtra("mobile",enternumber.getText().toString());
                                         intent.putExtra("backendotp",backendotp);
                                         startActivity(intent);
                                     }
                                 }
                         );

                     } else
                     {
                         Toast.makeText( MainActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                     }
                 } else
                 {
                     Toast.makeText(MainActivity.this,  "Enter Mobile number", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }
}

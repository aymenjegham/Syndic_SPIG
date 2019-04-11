package com.gst.socialcomponents.main.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.gst.socialcomponents.R;

public class PasswordResetActivity extends AppCompatActivity {

    EditText send_email;
    Button btn_reset ;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        send_email =findViewById(R.id.send_email);
        btn_reset=findViewById(R.id.btn_reset);

        firebaseAuth =FirebaseAuth.getInstance();
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= send_email.getText().toString();

                if(email.equals("")){
                    Toast.makeText(PasswordResetActivity.this, "Vous devez entrer une adresse email valide!", Toast.LENGTH_LONG).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(PasswordResetActivity.this, "Merci de consulter vos emails", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PasswordResetActivity.this,LoginActivity.class));
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(PasswordResetActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }
}

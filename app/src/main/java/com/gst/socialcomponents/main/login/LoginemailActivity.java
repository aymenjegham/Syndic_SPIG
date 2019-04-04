package com.gst.socialcomponents.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.editProfile.createProfile.CreateProfileActivity;
import com.gst.socialcomponents.main.main.MainActivity;

public class LoginemailActivity extends AppCompatActivity {


    EditText emailEt,passEt;
    Button cancel,confirm;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginemail);

        emailEt=findViewById(R.id.emailedittext);
        passEt=findViewById(R.id.passwordedittext);
        cancel=findViewById(R.id.annulebutton);
        confirm=findViewById(R.id.confirmbutton);


        mAuth = FirebaseAuth.getInstance();


    confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        boolean cancel = false;
        String email = emailEt.getText().toString();
        String mdp = passEt.getText().toString();

        if ((TextUtils.isEmpty(email))) {
            emailEt.setError(getApplicationContext().getString(R.string.error_field_required));
            cancel = true;
        }
        else if(TextUtils.isEmpty(mdp)){
            passEt.setError(getApplicationContext().getString(R.string.error_field_required));
            cancel = true;

        }

        if (!cancel) {
            mAuth.signInWithEmailAndPassword(email, mdp)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "connecté avec succés!", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginemailActivity.this, CreateProfileActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please try again later ! "+task.getException(), Toast.LENGTH_LONG).show();
                             }
                        }
                    });
        }
    }
    });

    }
}

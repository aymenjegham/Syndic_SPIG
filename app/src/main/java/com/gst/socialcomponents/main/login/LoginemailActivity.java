package com.gst.socialcomponents.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.editProfile.createProfile.CreateProfileActivity;
import com.gst.socialcomponents.main.main.MainActivity;
import com.gst.socialcomponents.main.profile.ProfileActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class LoginemailActivity extends AppCompatActivity {


    EditText emailEt,passEt;
    Button cancel,confirm;
    private FirebaseAuth mAuth;
    private TextView createAcc,forgetAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginemail);

        emailEt=findViewById(R.id.emailedittext);
        passEt=findViewById(R.id.passwordedittext);
        cancel=findViewById(R.id.annulebutton);
        confirm=findViewById(R.id.confirmbutton);
        createAcc=findViewById(R.id.createAccount);
        forgetAcc=findViewById(R.id.forgetPwd);


        mAuth = getInstance();



     createAcc.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent =new Intent(LoginemailActivity.this,RegisteremailActivity.class);
             startActivity(intent);
         }
     });


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
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Échec de la connexion! Veuillez réessayer plus tard ! "+task.getException(), Toast.LENGTH_LONG).show();
                             }
                        }
                    });
        }
    }
    });

    }
}
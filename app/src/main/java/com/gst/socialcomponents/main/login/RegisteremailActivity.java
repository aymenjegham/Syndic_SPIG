package com.gst.socialcomponents.main.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class RegisteremailActivity extends AppCompatActivity {


    EditText emailEt,passEt,pass2Et;
    Button cancel,confirm;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeremail);

        emailEt=findViewById(R.id.emailedittext);
        passEt=findViewById(R.id.passwordedittext);
        pass2Et=findViewById(R.id.password2edittext);
        cancel=findViewById(R.id.annulebutton);
        confirm=findViewById(R.id.confirmbutton);

        mAuth = FirebaseAuth.getInstance();



    confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        boolean cancel = false;
        String email = emailEt.getText().toString();
        String mdp = passEt.getText().toString();
        String mdp2 = pass2Et.getText().toString();

        if ((TextUtils.isEmpty(email))) {
            emailEt.setError(getApplicationContext().getString(R.string.error_field_required));
            cancel = true;
        }
        else if(TextUtils.isEmpty(mdp)){
            passEt.setError(getApplicationContext().getString(R.string.error_field_required));
            cancel = true;

        }else if(TextUtils.isEmpty(mdp2)){
            pass2Et.setError(getApplicationContext().getString(R.string.error_field_required));
            cancel = true;
        }

        if (!cancel && mdp.equals(mdp2)) {

            mAuth.createUserWithEmailAndPassword(email, mdp)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "inscription réussi!", Toast.LENGTH_LONG).show();

                               Intent intent = new Intent(RegisteremailActivity.this, CreateProfileActivity.class);
                                startActivity(intent);
                                finish();


                            }
                            else {
                                 Toast.makeText(getApplicationContext(), "inscription échoué! Veuillez réessayer plus tard"+task.getException().toString(), Toast.LENGTH_LONG).show();
                             }
                        }
                    });
        }
    }
    });

    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    });








    }
}

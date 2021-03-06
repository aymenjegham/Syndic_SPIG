package com.gst.socialcomponents.main.login;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.MainActivity;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.profile.ProfileActivity;
import com.gst.socialcomponents.model.Profile;

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

        emailEt=findViewById(R.id.send_email);
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

     forgetAcc.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent =new Intent(LoginemailActivity.this,PasswordResetActivity.class);
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
            ProgressDialog pd = new ProgressDialog(LoginemailActivity.this);
            pd.setMessage("Connexion");
            pd.show();
            mAuth.signInWithEmailAndPassword(email, mdp)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "connecté avec succés!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Échec de connecter! Vérifier votre email et mot de passe! ", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                                Log.v("checkingresultauth",task.getException().toString());
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

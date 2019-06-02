package com.example.compaquetefirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSingnIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //GetFirebase aut istance
        auth = FirebaseAuth.getInstance();

        btnSingnIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_in_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button)  findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSingnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Introdusca la dirreccion de correo electronicó!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Introdicir la contraseña",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña demasiado corta, ingrese un minimo de 6 caracteres!", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.creatUSerWithEmailAndPassword (email, password)
                        .addOnCompleteListener(SignupActivity.this, new onCompleteListener<AuthResult> () {
                            @Override
                            public void onComplete(@NonNull Task<AuthResults> task) {
                                Toast.makeText(SignupActivity.this, "createUserWhithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                //Si falla el inicio de sesion, muestre un mensaje al usuario. Si el inicio se sesion tiene éxito
                                //se notificara al oyente del estado de autenticació y se le asignará logica para manejar
                                //el usuario con sesion iniciada se puede manejar en el oyente.

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Autenticacion fallida." +
                                            task.getException(), Toast.LENGTH_SHORT).show();
                                } else
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();


                        }

                });
            }
        });
    }

    @Override
    protected  void onResume(){
        super.onResume();
        progressBar.setVisibility(ViewPager.GONE);
    }
}

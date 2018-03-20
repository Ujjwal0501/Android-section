package com.adid.rangilo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    Button skip;
    private FirebaseAuth mAuth;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText getmPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPasswordView = (EditText) findViewById(R.id.password);
        getmPasswordView = (EditText) findViewById(R.id.cpassword);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        Button mEmailSignInButton = (Button) findViewById(R.id.btnsignin);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                if (email.equals("")) {
                    mEmailView.setError("Email cannot be empty!");
                    return;
                }

                String password = mPasswordView.getText().toString();
                if (password.equals("")) {
                    mPasswordView.setError("Password cannot be empty!");
                    return;
                }
                attemptLogin(email, password);
            }
        });


        Button mSignUp = (Button) findViewById(R.id.btnsignup);
        mSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailView.getText().toString();
                if (email.equals("")) {
                    mEmailView.setError("Email cannot be empty!");
                    return;
                }

                String password = mPasswordView.getText().toString();
                if (password.equals("")) {
                    mPasswordView.setError("Password cannot be empty!");
                    return;
                }

                String cpassword = getmPasswordView.getText().toString();
                if (password.equals("")) {
                    mPasswordView.setError("Please renter password");
                    return;
                } else if (!(password.equals(cpassword))) {
                    mPasswordView.setError("Password do not match");
                    return;
                } else
                    attemptSignUp(email, password);
            }
        });

        skip = (Button) findViewById(R.id.btnskip);
        skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        // get auth instance
        mAuth = FirebaseAuth.getInstance();
    }

    // check user on start
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // if logged in go to main activity and pass username and password
        // else stay on login page
        if (currentUser != null) {
            Intent mIntent = new Intent(this, MainActivity.class);
            mIntent.putExtra("user", ""+currentUser);
        }
    }

    private void attemptLogin(String email, String password) {
        if (email.equals(null)) {
            Log.e("empty", "empty email");
            return;
        } else if (password.equals(null)) {
            Log.e("empty", "empty password");
            return;
        } else ;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void attemptSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUP success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUP failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    // switch sign up GUI
    public void signupgui(View v) {
        TextView tv = (TextView) findViewById(R.id.tvsignin);
        Button btn = (Button) findViewById(R.id.btnsignin);
        LinearLayout layout = (LinearLayout) findViewById(R.id.signup);
        v.setBackgroundColor(Color.parseColor("#cccccc"));
        btn.setVisibility(View.GONE);
        tv.setBackgroundColor(Color.parseColor("#eeeeee"));
        layout.setVisibility(View.VISIBLE);
    }

    // switch sign in GUI
    public void signingui(View v) {
        TextView tv = (TextView) findViewById(R.id.tvsignup);
        Button btn = (Button) findViewById(R.id.btnsignin);
        LinearLayout layout = (LinearLayout) findViewById(R.id.signup);
        v.setBackgroundColor(Color.parseColor("#cccccc"));
        btn.setVisibility(View.VISIBLE);
        tv.setBackgroundColor(Color.parseColor("#eeeeee"));
        layout.setVisibility(View.GONE);
    }

}


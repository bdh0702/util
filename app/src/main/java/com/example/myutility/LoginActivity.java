package com.example.myutility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 900;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private SignInButton buttonGoogle;
    private CallbackManager mCallbackManager;


    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public String Str_id;
    public String Str_password;
    public boolean count = false;
    EditText id,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        buttonGoogle = findViewById(R.id.googleLoginButton);

        // Google 로그인을 앱에 통합
        // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        id=(EditText)findViewById(R.id.idText);
        password=(EditText)findViewById(R.id.passwordText);


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("DataUsers");


        mCallbackManager = CallbackManager.Factory.create();
        LoginButton facebookloginButton = findViewById(R.id.facebook_login_button);
        facebookloginButton.setReadPermissions("email", "public_profile");
        facebookloginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                // ...
            }

            @Override
            public void onError(FacebookException error) {

                // ...
            }
        });// ...



        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                            for(DataSnapshot da: dataSnapshot.getChildren()){
                                Str_id=id.getText().toString();
                                Str_password=password.getText().toString();
                                String dbId = da.child("id").getValue(String.class);
                                String dbPassword = da.child("password").getValue(String.class);
                                if(Str_id.equals(dbId) && Str_password.equals(dbPassword)){
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    startToast("로그인 성공");
                                    count = true;
                                    break;
                                }
                                if(count == true) break;
                            }
                        }
                        if(!count){
                            startToast("아이디 또는 비밀번호가 일치하지 않습니다");
                            count=false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // 구글로그인 버튼 응답
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
    }
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                startToast("로그인 성공");
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    // 사용자가 정상적으로 로그인한 후에 GoogleSignInAccount 개체에서 ID 토큰을 가져와서
// Firebase 사용자 인증 정보로 교환하고 Firebase 사용자 인증 정보를 사용해 Firebase에 인증합니다.
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            startToast("로그인 성공");
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);

                        } else {
                            // 로그인 실패
                            startToast("로그인 실패");
                        }

                    }
                });
    }



    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}

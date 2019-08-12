package com.example.myutility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String UserId;
    public String Str_id;
    public String Str_nickname;
    public String Str_email;
    public String Str_password;
    public String Str_password2;
    public int clickId =0;
    public int clickNickname = 0;
    public int checkEmail = 0;

    EditText id,email,password,password2,nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        id=(EditText)findViewById(R.id.idText);
        email = (EditText)findViewById(R.id.emailText);
        password=(EditText)findViewById(R.id.passwordText);
        password2=(EditText)findViewById(R.id.passwordText2);
        nickname = (EditText)findViewById(R.id.nicknameText);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("DataUsers");
        UserId = mFirebaseDatabase.push().getKey();

        spinner = (Spinner)findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.major,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button checkId = findViewById(R.id.checkId);
        checkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    int clickId2 = 0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                            for(DataSnapshot da: dataSnapshot.getChildren()){
                                Str_id=id.getText().toString();
                                String dbId = da.child("id").getValue(String.class);
                                if(Str_id.equals(dbId)){
                                    clickId2= 1;
                                    break;
                                }
                            }
                            if(clickId2==1) break;
                        }
                        if(clickId2 != 1) clickId2=2;
                        if(!Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", Str_id)){
                            Toast.makeText(RegisterActivity.this,"형식이 바르지 않습니다\n영문,숫자 포함 5~12자.",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(clickId2==1) startToast("이미 사용 중인 아이디입니다");
                            else if(clickId2==2) startToast("사용 가능 한 아이디입니다");
                            clickId = clickId2;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        final Button checkNickname = findViewById(R.id.checkNickname);
        checkNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    int clickNickname2 = 0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Str_nickname=nickname.getText().toString();
                        for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                            for(DataSnapshot da: dataSnapshot.getChildren()){
                                String dbNickname = da.child("nickname").getValue(String.class);
                                if(Str_nickname.equals(dbNickname)){
                                    clickNickname2 = 1;
                                    break;
                                }
                            }
                            if(clickNickname2==1) break;
                        }
                        if(clickNickname2 != 1) clickNickname2=2;
                        if(Str_nickname.matches(""))
                        {
                            Toast.makeText(RegisterActivity.this,"빈 칸 일 수 없습니다",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(clickNickname2==1) startToast("이미 사용 중인 닉네임입니다");
                            else if(clickNickname2==2)startToast("사용 가능 한 닉네임입니다");
                            clickNickname=clickNickname2;
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count =0;
                Str_id = id.getText().toString();
                Str_password = id.getText().toString();
                Str_email = email.getText().toString();
                Str_nickname = nickname.getText().toString();
                Str_password = password.getText().toString();
                Str_password2 = password2.getText().toString();

                mFirebaseDatabase.child("Users").addListenerForSingleValueEvent((new ValueEventListener() {
                    int checkEmail2 = 0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                            for(DataSnapshot da: dataSnapshot.getChildren()){
                                String dbEmail = da.child("email").getValue(String.class);
                                if(Str_email.equals(dbEmail)) {
                                    checkEmail2=1;
                                    break;
                                }
                            }
                            if(checkEmail2==1) break;
                        }
                        if(checkEmail2 != 1) checkEmail2=2;
                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(Str_email).matches())
                        {
                            if(Pattern.matches("^^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", Str_password)){
                                if(Str_password2.equals(Str_password)){
                                    if(clickId==2 && clickNickname ==2)
                                    Toast.makeText(RegisterActivity.this,"이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else checkEmail = checkEmail2;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }));
            if(Str_nickname.matches("") ||Str_id.matches("")|| Str_email.matches("") || Str_password.matches("") || Str_password2.matches("")){
                    startToast("빈 칸 없이 입력해주세요");
            }
            else if(!Pattern.matches("^^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", Str_password))
                {
                    Toast.makeText(RegisterActivity.this,"비밀번호 형식이 아닙니다.\n숫자,문자,특수문자 포함 최소 8자 이상",Toast.LENGTH_SHORT).show();
                }
                else{

                    if(!Str_password2.equals(Str_password)){ //비밀번호 같지 않을 때
                        startToast("비밀번호가 일치하지 않습니다");
                    }
                    else if(clickId ==0){
                        startToast("아이디 중복 체크를 해주세요");
                    }
                    else if(clickNickname ==0){
                        startToast("닉네임 중복 체크를 해주세요");
                    }
                    else if(checkEmail ==1){
                        startToast("이미 사용 중인 이메일입니다");
                    }
                    else if((clickId == 2 ) && (clickId ==2) && (checkEmail ==2)){
                        addUser(Str_nickname.trim(),Str_id.trim(),Str_email.trim(),Str_password.trim());
                        if(!email.getText().toString().isEmpty()){
                            startToast("회원가입 성공");
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else startToast("이메일을 입력해주세요\nex)abc@gmail.com");

                    }
                    /*else if(count != 1){
                        startToast("빈 칸 없이 입력해주세요");
                     }*/
                }
            }

                //if(!Str_nickname.isEmpty() && !password.getText().toString().isEmpty() && !password2.getText().toString().isEmpty() && !Str_email.isEmpty() && !Str_id.isEmpty()){ //닉네임 , 비밀번호가 비어있지 않으면 카운트



        });
    }




    public void addUser(String nickname,String id,String email,String password){
        user users = new user(nickname,id,email,password);
        mFirebaseDatabase.child("Users").child(UserId).setValue(users);
    }

    public void updateUser(String nickname,String id,String email,String password){
        mFirebaseDatabase.child("Users").child(UserId).child("nickname").setValue(nickname);
        mFirebaseDatabase.child("Users").child(UserId).child("id").setValue(id);
        mFirebaseDatabase.child("Users").child(UserId).child("email").setValue(email);
        mFirebaseDatabase.child("Users").child(UserId).child("password").setValue(password);

    }

    public void insertData(View view){
        addUser(nickname.getText().toString().trim(),id.getText().toString().trim(),email.getText().toString().trim(),password.getText().toString().trim());


    }

    public void readData(View view){
        mFirebaseDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot da: dataSnapshot.getChildren()){
                        String dbNickname = da.child("nickname").getValue(String.class);
                        String dbId = da.child("id").getValue(String.class);
                        String dbEmail = da.child("email").getValue(String.class);
                        String dbPassword = da.child("password").getValue(String.class);
                        Log.d("Tag",dbNickname + "/" + dbId + "/" + dbEmail + "/" + dbPassword);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
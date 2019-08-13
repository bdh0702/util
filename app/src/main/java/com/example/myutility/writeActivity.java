package com.example.myutility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class writeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private EditText TitleText,contentText,nicknameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        TitleText = findViewById(R.id.titleText);
        contentText = findViewById(R.id.contentText);
        nicknameText = findViewById(R.id.write_nicknameText);

        findViewById(R.id.bt_upload).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Map<String, Object> post = new HashMap<>();
        post.put("id","");
        post.put("title",TitleText.getText().toString());
        post.put("contents",contentText.getText().toString());
        post.put("nickname",nicknameText.getText().toString());

        firebaseFirestore.collection("board").add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(writeActivity.this,"업로드 성공",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(writeActivity.this,"업로드 실패",Toast.LENGTH_LONG).show();
                    }
                });
    }
}

package com.example.myutility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FreeBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;

    private MainAdapter mainAdapter;
    private List<board> boardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board);

        recyclerView=findViewById(R.id.board_recycle_View);

        findViewById(R.id.bt_write).setOnClickListener(this);

        boardList = new ArrayList<>();
        boardList.add(new board("관리자","반갑습니다 여러분",null,null));
        boardList.add(new board(null,"hello",null,null));
        boardList.add(new board(null,"안녕하세요 ",null,null));
        boardList.add(new board(null,"ㅎㅎㅎ",null,null));

        mainAdapter = new MainAdapter(boardList);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,writeActivity.class);
        startActivity(intent);
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private List<board> boardList;

        public MainAdapter(List<board> boardList){
            this.boardList = boardList;
        }
        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            board data = boardList.get(position);
            holder.titleText.setText(data.getTitle());
            holder.nameText.setText(data.getNickname());
        }

        @Override
        public int getItemCount() {
            return boardList.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder{

            private TextView titleText;
            private TextView nameText;

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);

                titleText = itemView.findViewById(R.id.titleText);
                nameText = itemView.findViewById(R.id.nicknameText);
            }
        }
    }
}

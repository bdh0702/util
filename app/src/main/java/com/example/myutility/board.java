package com.example.myutility;

public class board {

    private String nickname;
    private String title;
    private String contents;
    private String id;

    public board(){

    }
    public board(String nickname,String title,String contents,String id){
        this.id = id;
        this.nickname = nickname;
        this.contents = contents;
        this.title = title;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContents(){
        return contents;
    }

    public void setContents(String contents){
        this.contents = contents;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "board{" +
                "nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

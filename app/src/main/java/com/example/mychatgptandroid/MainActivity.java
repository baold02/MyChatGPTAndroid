package com.example.mychatgptandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mychatgptandroid.adapter.MessageAdapter;
import com.example.mychatgptandroid.model.MessParamPost;
import com.example.mychatgptandroid.model.Message;
import com.example.mychatgptandroid.retrofit.ApiGpt;
import com.example.mychatgptandroid.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcv_chat;
    EditText edt_mess;
    ImageButton img_send;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiGpt apiGpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiGpt = RetrofitClient.getInstance().create(ApiGpt.class);
        initView();
        initControl();
    }

    public  void postData(String ques){
        messageList.add(new Message("...",1));
        MessParamPost  messParamPost = new MessParamPost();
        messParamPost.setModel("text-moderation-005");
        messParamPost.setMax_tokens(2048);
        messParamPost.setPrompt(ques);
        messParamPost.setTemperature(0);

        compositeDisposable.add(apiGpt.postQues(messParamPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messRespone -> {
                            messageList.remove(messageList.size()-1);
                            String result = messRespone.getChoicesList().get(0).getText();
                                addQuesToRecycle(result,1);
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void initControl() {
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ques = edt_mess.getText().toString().trim();
                addQuesToRecycle(ques,0);
                edt_mess.setText("");
                postData(ques);
            }
        });
    }

    private void addQuesToRecycle(String ques, int send_id) {
        messageList.add(new Message(ques,send_id));
        messageAdapter.notifyDataSetChanged();
        rcv_chat.smoothScrollToPosition(messageAdapter.getItemCount());
    }

    private void initView() {
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this,messageList);
        rcv_chat = findViewById(R.id.rcv_chat);
        edt_mess =findViewById(R.id.edt_mess);
        img_send =findViewById(R.id.Img_send);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rcv_chat.setLayoutManager(layoutManager);
        rcv_chat.setAdapter(messageAdapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
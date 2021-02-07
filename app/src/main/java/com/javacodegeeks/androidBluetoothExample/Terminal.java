package com.javacodegeeks.androidBluetoothExample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.javacodegeeks.R;

import java.util.ArrayList;
import java.util.List;


public class Terminal extends Fragment {


    Communication gru;
    Context thiscontext;
    private Button terminalSendButton;
    private EditText messageText;
    private TextView odp;
    private String TAG = "ttt";
    private RecyclerView mRecyclerView;
    private TextAdapter mTextAdapter;

    private List<String> messageList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thiscontext = container.getContext();
        return inflater.inflate(R.layout.terminal_view,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.messageListView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thiscontext));
        mTextAdapter = new TextAdapter();
        mRecyclerView.setAdapter(mTextAdapter);

        terminalSendButton = getView().findViewById(R.id.terminal_send);
        messageText = getView().findViewById(R.id.terminalTextView);

        terminalSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                BluetoothChat.sendMessage(message);
                Log.d(TAG, "onClick: " + BluetoothChat.termninalMessage);

                messageList.add("< " + message);
                messageList.add("> " + BluetoothChat.termninalMessage + "\n");
                mTextAdapter.setItems(messageList);
            }
        });

    }

}

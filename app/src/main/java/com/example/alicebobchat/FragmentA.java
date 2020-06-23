package com.example.alicebobchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alicebobchat.storage.MessageContainer;
import com.example.alicebobchat.storage.Storage;
import com.example.alicebobchat.storage.StorageImpl;
import com.example.alicebobchat.storage.Messages;

import java.util.ArrayList;

public class FragmentA extends Fragment {

    private EditText messageInput;
    public static String NOTIFICATION = "NOTIFICATION";
    public static String NOTIFICATION_DATA = "DATA";
    private static AMessageCatcherBroadcast aMessageCatcherBroadcast;
    private static AListArrayAdapter aListArrayAdapter;
    private static ArrayList<Messages> chat;
    private static Context context;
    private static ListView aListView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment_a, container, false);
        messageInput = view.findViewById(R.id.messageA);
        context = getContext();
        aListView = view.findViewById(R.id.aList);
        aListArrayAdapter = new AListArrayAdapter(context, 0, new ArrayList<Messages>());
        chat = new ArrayList<Messages>();
        view.findViewById(R.id.sendToB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messageInput.getText().toString().trim().equals("")) {
                    MessageContainer.addMessage(getContext(), messageInput.getText().toString().trim(), "B");
                    Intent intent = new Intent();
                    intent.setAction(NOTIFICATION);
                    intent.putExtra(NOTIFICATION_DATA, "Message");
                    getActivity().sendBroadcast(intent);
                    messageInput.setText("");
                    fillListView();
                }
            }
        });
        registerBroadCast();
        fillListView();
        return view;
    }

    private static void fillListView()
    {
        Storage storage = new StorageImpl();
        Object storageAsObject = storage.getObject(context, MessageContainer.STORAGE_KEY, MessageContainer.class);

        MessageContainer messageContainerStorage;
        if (storageAsObject != null) {
            messageContainerStorage = (MessageContainer) storageAsObject;
        } else {
            messageContainerStorage = new MessageContainer();
        }
        chat = messageContainerStorage.getMessages();
        aListArrayAdapter.clear();
        aListView.setAdapter(aListArrayAdapter);
        aListArrayAdapter.addAll(chat);
    }


    private void registerBroadCast()
    {
        aMessageCatcherBroadcast = new AMessageCatcherBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FragmentB.NOTIFICATION);
        getActivity().registerReceiver(aMessageCatcherBroadcast, filter);
    }


    public static class AMessageCatcherBroadcast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra(FragmentB.NOTIFICATION_DATA))
            {
                Storage storage = new StorageImpl();
                Object storageAsObject = storage
                        .getObject(context, MessageContainer.STORAGE_KEY, MessageContainer.class);

                MessageContainer messageContainerStorage;
                if (storageAsObject != null) {
                    messageContainerStorage = (MessageContainer) storageAsObject;
                } else {
                    messageContainerStorage = new MessageContainer();
                }
                fillListView();
            }
        }
    }

    public class AListArrayAdapter extends ArrayAdapter<Messages>
    {
        private Context context;

        public AListArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Messages> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_a, parent, false);
            final Messages messages = getItem(position);


            TextView message = view.findViewById(R.id.aMessage);
            message.setText(messages.getText());
            if (messages.getReceiver().equals("A")) {

                message.setGravity(Gravity.START);
                message.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
            else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.RIGHT;
                view.setLayoutParams(params);
                message.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            return view;
        }
    }
}

package com.example.alicebobchat.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class StorageImpl implements Storage {

    public void add(Context context, String key, Object obj)
    {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, new Gson().toJson(obj));
        editor.commit();
    }

    public Object getObject(Context context, String key, Class className)
    {
        SharedPreferences sharedPreferences = getInstance(context);
        String data = sharedPreferences.getString(key, null);
        return data == null ? null : new Gson().fromJson(data, className);
    }

    private SharedPreferences getInstance(Context context) {
        return context.getSharedPreferences("Chat_Container", Context.MODE_PRIVATE);
    }
}

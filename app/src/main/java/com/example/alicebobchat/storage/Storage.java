package com.example.alicebobchat.storage;

import android.content.Context;

public interface Storage {

    void add(Context context, String key, Object obj);

    Object getObject(Context context, String key, Class className);
}

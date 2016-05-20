package com.quinny898.xposed.alloallo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        /*
        URI testing pre-database editing

        Uri.Builder uri = new Uri.Builder();
        uri.authority("fireball://hello@chat_with");
        Intent i = new Intent();
        i.setAction("com.google.android.apps.fireball.START_CHAT");
        i.putExtra("extra_user_id", "0");
        i.putExtra("extra_user_type", "user");
        startActivity(i);*/
        //SMS testing, does nothing other than open the "send to" activity
        Uri uri = Uri.parse("smsto:" + "0");
        Intent intent = new Intent(Intent.ACTION_SEND, uri);
        intent.setComponent(ComponentName.unflattenFromString("com.google.android.apps.fireball/com.google.android.apps.fireball.ui.conversationlist.ShareIntentActivity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

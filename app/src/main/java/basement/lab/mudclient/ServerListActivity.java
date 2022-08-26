package basement.lab.mudclient;

import static basement.lab.mudclient.adapter.ServerInfoAdapter.REQUEST_EDIT_SERVER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import basement.lab.mudclient.adapter.ServerInfoAdapter;
import basement.lab.mudclient.bean.ServerInfo;

public class ServerListActivity extends AppCompatActivity implements
        android.view.View.OnClickListener {
    private ServerInfoAdapter adapter;

    public Handler listReflashHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            adapter.setReflash();
            return false;
        }
    });

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serverlist);

        setTitle(R.string.serverlist);
        adapter = new ServerInfoAdapter(ServerListActivity.this);
        if (adapter.getItemCount() == 0) {
            new AlertDialog.Builder(this).setIcon(R.drawable.smallicon)
                    .setTitle(R.string.app_name).setMessage(
                            R.string.noserverfound).setPositiveButton(
                            R.string.ok, (dialog, which) -> {
                                createDefaultServerList();
                                listReflashHandler.sendEmptyMessage(0);
                            }).setNegativeButton(R.string.no, null).create()
                    .show();
        }

        RecyclerView listview = findViewById(R.id.list_server_rv);
        Button newServer = findViewById(R.id.newServer);
        newServer.setOnClickListener(this);
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        adapter = new ServerInfoAdapter(ServerListActivity.this);
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public void onClick(View v) {
        int resId = v.getId();
        if (resId == R.id.newServer) {
            Intent i = new Intent(ServerListActivity.this,
                    ConnectionEditor.class);
            startActivityForResult(i, REQUEST_EDIT_SERVER);
        } else if (resId == R.id.cancel) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_EDIT_SERVER) {
            if (resultCode != RESULT_CANCELED) {
                listReflashHandler.sendEmptyMessage(0);
            }
        }
    }

    private void createDefaultServerList() {
        ArrayList<ServerInfo> list = new ArrayList<ServerInfo>();
        list.add(new ServerInfo("무검(십웅기)", "toox.co.kr", 8050, null, ""));
        list.add(new ServerInfo("십웅기검하천산", "27.102.207.232", 9999, null, ""));
        list.add(new ServerInfo("승풍파랑", "sppr.kr", 5050, null, ""));
        list.add(new ServerInfo("넓은마을 마법의대륙", "221.146.75.32", 4001, null, ""));
        SettingsManager.setServerList(this, list);
    }
}
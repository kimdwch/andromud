package basement.lab.mudclient;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import basement.lab.mudclient.bean.ServerInfo;
import basement.lab.mudclient.utils.ServerInfoAdapter;

public class ServerListActivity extends AppCompatActivity implements
        android.view.View.OnClickListener, OnItemLongClickListener {

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_EDIT_SERVER) {
            if (resultCode != RESULT_CANCELED) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public final static String EDIT_SERVER_POSITION = "edit_server_position";
    public final static int REQUEST_EDIT_SERVER = 1;
    private int contextSelect = 0;
    private ServerInfoAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serverlist);
        setTitle(R.string.serverlist);
        Button newServer = findViewById(R.id.newServer);
        newServer.setOnClickListener(this);
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        adapter = new ServerInfoAdapter(ServerListActivity.this);
		/*this.setListAdapter(adapter);
		getListView().setOnItemLongClickListener(this);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ServerInfoAdapter(this);
		/*this.setListAdapter(adapter);
		getListView().setOnItemLongClickListener(this);*/
        if (adapter.getCount() == 0) {
            new AlertDialog.Builder(this).setIcon(R.drawable.smallicon)
                    .setTitle(R.string.app_name).setMessage(
                            R.string.noserverfound).setPositiveButton(
                            R.string.ok, new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    createDefaultServerList();
                                    adapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton(R.string.no, null).create()
                    .show();
        }
    }

	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(this, MudTerminalActivity.class);
		i.putExtra(MudTerminalActivity.Server, SettingsManager.getServerList(
				this).get(position));
		startActivity(i);
		this.finish();
	}*/

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newServer:
                Intent i = new Intent(ServerListActivity.this,
                        ConnectionEditor.class);
                startActivityForResult(i, REQUEST_EDIT_SERVER);
                break;
            case R.id.cancel:
                finish();
                break;
        }


        public boolean onItemLongClick (AdapterView < ? > arg0, View view,
        int position, long id){
            contextSelect = position;
            AlertDialog alert = new AlertDialog.Builder(this).setIcon(R.drawable.smallicon)
                    .setTitle(R.string.editserver).setPositiveButton(
                            R.string.modify, new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    Intent i = new Intent(ServerListActivity.this,
                                            ConnectionEditor.class);
                                    i.putExtra(EDIT_SERVER_POSITION, contextSelect);
                                    startActivityForResult(i, REQUEST_EDIT_SERVER);
                                }
                            }).setNegativeButton(R.string.delete,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    ArrayList<ServerInfo> list = SettingsManager
                                            .getServerList(ServerListActivity.this);
                                    list.remove(contextSelect);
                                    SettingsManager.setServerList(
                                            ServerListActivity.this, list);
                                    adapter.notifyDataSetChanged();
                                }
                            }).create();
            alert.show();
            return true;
        }

        private void createDefaultServerList () {
            ArrayList<ServerInfo> list = new ArrayList<ServerInfo>();
            list.add(new ServerInfo("무검(십웅기)", "toox.co.kr", 8050, null, ""));
            list.add(new ServerInfo("십웅기검하천산", "27.102.207.232", 9999, null, ""));
            list.add(new ServerInfo("승풍파랑", "sppr.kr", 5050, null, ""));
            list.add(new ServerInfo("넓은마을 마법의대륙", "221.146.75.32", 4001, null, ""));
            SettingsManager.setServerList(this, list);
        }
    }

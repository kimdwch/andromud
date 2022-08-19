package basement.lab.mudclient.utils;

import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import basement.lab.mudclient.R;
import basement.lab.mudclient.SettingsManager;
import basement.lab.mudclient.bean.ServerInfo;

public class ServerInfoAdapter extends BaseAdapter {

	ArrayList<ServerInfo> servers;
	AppCompatActivity ctx;

	public ServerInfoAdapter(AppCompatActivity ctx) {
		servers = SettingsManager.getServerList(ctx);
		this.ctx = ctx;
	}

	public int getCount() {
		return servers.size();
	}

	@Override
	public void notifyDataSetChanged() {
		servers = SettingsManager.getServerList(ctx);
		super.notifyDataSetChanged();
	}

	public Object getItem(int position) {
		return servers.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(ctx);
		View server = inflater.inflate(R.layout.serverrow, null);
		TextView nickname, host;
		nickname = server.findViewById(R.id.nickname);
		host = server.findViewById(R.id.host);
		nickname.setText(servers.get(position).nickName);
		host.setText(servers.get(position).IP + ":"
				+ servers.get(position).Port);
		return server;
	}
}

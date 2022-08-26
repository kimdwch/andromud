package basement.lab.mudclient.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import basement.lab.mudclient.ConnectionEditor;
import basement.lab.mudclient.MudTerminalActivity;
import basement.lab.mudclient.R;
import basement.lab.mudclient.ServerListActivity;
import basement.lab.mudclient.SettingsManager;
import basement.lab.mudclient.bean.ServerInfo;
import basement.lab.mudclient.utils.Logger;

public class ServerInfoAdapter extends RecyclerView.Adapter<ServerInfoAdapter.ViewHolder> {
	public final static String EDIT_SERVER_POSITION = "edit_server_position";
	public final static int REQUEST_EDIT_SERVER = 1;

	ArrayList<ServerInfo> servers;
	Context context;

	public static class ViewHolder extends RecyclerView.ViewHolder{
		TextView nickname, host;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			Logger.d("ViewHolder");

			nickname = itemView.findViewById(R.id.nickname);
			host = itemView.findViewById(R.id.host);
			itemView.setOnClickListener(view -> {
				Intent i = new Intent(itemView.getContext(), MudTerminalActivity.class);
				i.putExtra(MudTerminalActivity.Server, SettingsManager.getServerList(itemView.getContext()).get(getAbsoluteAdapterPosition()));
				itemView.getContext().startActivity(i);
				((ServerListActivity)itemView.getContext()).finish();
			});
			itemView.setOnLongClickListener(view -> {
				AlertDialog alert = new AlertDialog.Builder(view.getContext()).setIcon(R.drawable.smallicon)
						.setTitle(R.string.editserver).setPositiveButton(
								R.string.modify, (dialog, whichButton) -> {
									Intent i = new Intent(view.getContext(),
											ConnectionEditor.class);
									i.putExtra(EDIT_SERVER_POSITION, getAbsoluteAdapterPosition());
									((ServerListActivity)view.getContext()).startActivityForResult(i, REQUEST_EDIT_SERVER);
								}).setNegativeButton(R.string.delete,
								(dialog, whichButton) -> {
									ArrayList<ServerInfo> list = SettingsManager
											.getServerList(view.getContext());
									list.remove(getAbsoluteAdapterPosition());
									SettingsManager.setServerList(
											(ServerListActivity)view.getContext(), list);
								}).create();
				alert.show();
				return false;
			});
		}
	}

	public ServerInfoAdapter(AppCompatActivity ctx) {
		servers = SettingsManager.getServerList(ctx);
		context = ctx;
	}

	public void setReflash() {
		if(context != null)
			servers = SettingsManager.getServerList(context);
		this.notifyDataSetChanged();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View server = inflater.inflate(R.layout.serverrow, parent,false);
		return new ViewHolder(server);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.nickname.setText(servers.get(position).nickName);
		holder.host.setText(String.format(Locale.getDefault(),"%s:%s",
				servers.get(position).IP ,servers.get(position).Port));

	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		return servers.size();
	}

}

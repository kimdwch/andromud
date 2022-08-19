package basement.lab.mudclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import basement.lab.mudclient.SendQueue;
import basement.lab.mudclient.utils.TerminalView;

public class SessionData implements Parcelable {
	public Thread connection;
	public SendQueue queue;
	public List<String> commandHistory;
	public ServerInfo server;
	public TerminalView terminal;
	public ScriptEngine aliases;

	public SessionData(Parcel in) {
		commandHistory = in.createStringArrayList();
	}

	public static final Creator<SessionData> CREATOR = new Creator<SessionData>() {
		@Override
		public SessionData createFromParcel(Parcel in) {
			return new SessionData(in);
		}

		@Override
		public SessionData[] newArray(int size) {
			return new SessionData[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeStringList(commandHistory);
	}
}

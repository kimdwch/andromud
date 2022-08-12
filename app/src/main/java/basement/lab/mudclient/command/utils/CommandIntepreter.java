package basement.lab.mudclient.command.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import android.util.Log;
import basement.lab.mudclient.bean.ScriptEngine;

public class CommandIntepreter {

	private final static String TAG = "mudclient.CommandIntepreter";

	private static void debug(String notice) {
		Log.d(TAG, notice);
	}

	public static void sendComplexCommand(String encoding, String cmd,
			OutputStream os) throws IOException {
		HashMap<String, String> aliases = ScriptEngine.aliases;
		String[] cmdGroup = cmd.split(";");
		for (String s : cmdGroup) {
			debug(s);
			String aliasCmd = aliases.get(s);
			if (aliasCmd != null) {
				String[] aCmds = aliasCmd.split(";");
				for (String aCmd : aCmds) {
					sendSimpleCommand(encoding, aCmd, os);
				}
			} else {
				sendSimpleCommand(encoding, s, os);
			}
		}
	}

	private static void sendSimpleCommand(String encoding, String cmd,
			OutputStream os) throws IOException {
		if (cmd == null) {
			return;
		}
		if (!cmd.startsWith("#")) {
			os.write((cmd + "\r\n").getBytes(encoding));
			os.flush();
		} else if (cmd.startsWith("#wa")) {
			try {
				int time = Integer.parseInt(cmd.substring(3).trim());
				Thread.sleep(time);
			} catch (NumberFormatException ignored) {
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			int firstSpace = cmd.indexOf(' ');
			if (firstSpace != -1) {
				try {
					int repeatTime = Integer.parseInt(cmd.substring(1,
							firstSpace));
					String cmdBody = cmd.substring(firstSpace).trim();
					for (int i = 0; i < repeatTime; i++) {
						sendSimpleCommand(encoding, cmdBody, os);
					}
				} catch (NumberFormatException ignored) {
				}
			}
		}
	}
}
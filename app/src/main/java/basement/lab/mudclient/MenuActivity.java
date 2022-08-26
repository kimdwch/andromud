package basement.lab.mudclient;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity implements OnClickListener {

	AlertDialog aboutDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*if (SettingsManager.isFullScreen(this)) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,a
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}*/

		setContentView(R.layout.menu);

		/*Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);*/

		Button start = findViewById(R.id.start);
		start.setOnClickListener(this);
		Button setting = findViewById(R.id.setting);
		setting.setOnClickListener(this);
		Button about = findViewById(R.id.about);
		about.setOnClickListener(this);
		Button help = findViewById(R.id.help);
		help.setOnClickListener(this);
		Button exit = findViewById(R.id.exit);
		exit.setOnClickListener(this);
		Button colors = findViewById(R.id.color);
		colors.setOnClickListener(this);
		aboutDialog = new AlertDialog.Builder(this).setIcon(
				R.drawable.smallicon).setTitle(R.string.about_title)
				.setMessage(R.string.about_body).setNegativeButton(
						R.string.cancel, null).create();
		StringBuilder ver = new StringBuilder(getString(R.string.versionName));
		ver.append(BuildConfig.VERSION_NAME);
		((TextView)findViewById(R.id.version)).setText(ver);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			Intent s = new Intent(this, ServerListActivity.class);
			startActivity(s);
			break;
		case R.id.setting:
			Intent i = new Intent(this, SettingsManager.class);
			this.startActivity(i);
			break;
		case R.id.color:
			Intent c = new Intent(this, ColorsActivity.class);
			this.startActivity(c);
			break;
		case R.id.about:
			aboutDialog.show();
			break;
		case R.id.exit:
			finish();
			break;
		case R.id.help:
			Intent h = new Intent(this, HelpActivity.class);
			startActivity(h);
			break;
		}
	}
}

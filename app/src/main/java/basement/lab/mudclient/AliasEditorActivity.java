package basement.lab.mudclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AliasEditorActivity extends AppCompatActivity implements OnClickListener {
	public final static int REQUEST_ADD_ALIAS = 100;
	public final static int RESULT_ADD = 101;
	public final static int REQUEST_EDIT_ALIAS = 200;
	public final static int RESULT_EDIT = 201;
	public final static int RESULT_DELETE = 202;

	public final static String REQUEST_TYPE = "requestType";
	public final static String TITLE = "aliasTitle";
	public final static String BODY = "aliasBody";

	private EditText name;
	private EditText body;

	private int requestCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.aliaseditor);
		setContentView(R.layout.aliaseditbox);
		Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(this);
		Button delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(this);
		name = (EditText) findViewById(R.id.aliasname);
		body = (EditText) findViewById(R.id.aliasbody);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent i = getIntent();
		requestCode = i.getIntExtra(REQUEST_TYPE, 0);
		switch (requestCode) {
		case REQUEST_ADD_ALIAS:
			break;
		case REQUEST_EDIT_ALIAS:
			name.setEnabled(false);
			name.setText(i.getStringExtra(TITLE));
			body.setText(i.getStringExtra(BODY));
			break;
		}
	}

	public void onClick(View v) {
		Intent i = new Intent();
		int resId = v.getId();

		if(resId == R.id.save)
		{
			String n = name.getText().toString();
			String b = body.getText().toString();
			if (n.length() == 0 || b.length() == 0) {
				Toast.makeText(this, R.string.editorerr, Toast.LENGTH_SHORT).show();
				return;
			}
			i.putExtra(TITLE, n);
			i.putExtra(BODY, b);
			if (requestCode == REQUEST_ADD_ALIAS)
				setResult(RESULT_ADD, i);
			else
				setResult(RESULT_EDIT, i);
			finish();
		}
		else if(resId == R.id.delete)
		{
			if (requestCode != REQUEST_ADD_ALIAS) {
				i.putExtra(TITLE, name.getText().toString());
				setResult(RESULT_DELETE, i);
			}
			finish();
		}
	}
}

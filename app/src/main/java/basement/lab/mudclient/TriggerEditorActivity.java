package basement.lab.mudclient;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import basement.lab.mudclient.bean.ScriptEngine;
import basement.lab.mudclient.bean.Trigger;

public class TriggerEditorActivity extends AppCompatActivity implements OnClickListener {

    public final static String TRIGGER_POSITION = "B.L.M.TRIGGER_POSITION";
    public final static String REQUEST_TYPE = "B.L.M.TRIGGER_EDIT_TYPE";

    public final static int REQUEST_MODIFY = 100;
    public final static int REQUEST_ADD = 200;

    public final static int RESULT_MODIFY = 101;
    public final static int RESULT_DELETE = 102;

    private int position;

    private int requestType;

    private EditText pattern, body;

    private ArrayList<String> screenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.trigereditor);
        setContentView(R.layout.triggereditbox);
        Button save = findViewById(R.id.save);
        save.setOnClickListener(this);
        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(this);
        Button choose = findViewById(R.id.triggerChoose);
        choose.setOnClickListener(this);
        pattern = findViewById(R.id.triggerPattern);
        body = findViewById(R.id.triggerBody);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestType = getIntent().getIntExtra(REQUEST_TYPE, REQUEST_ADD);
        position = getIntent().getIntExtra(TRIGGER_POSITION, -1);
        screenText = getIntent().getStringArrayListExtra(
                TriggerListActivity.SCREEN_TEXT);
        if (requestType == REQUEST_MODIFY && position >= 0
                && position < ScriptEngine.triggers.size()) {
            pattern.setText(ScriptEngine.triggers.get(position).pattern);
            body.setText(ScriptEngine.triggers.get(position).body);
        } else {
            requestType = REQUEST_ADD;
        }
    }

    public void onClick(View v) {
        int resId = v.getId();

        if (resId == R.id.save) {
            String p = pattern.getText().toString();
            String b = body.getText().toString();
            if (p.length() == 0 || b.length() == 0) {
                Toast.makeText(this, R.string.editorerr, Toast.LENGTH_SHORT).show();
                return;
            }
            if (requestType == REQUEST_ADD) {
                ScriptEngine.addTrigger(new Trigger(p, b));
                setResult(RESULT_OK);
                finish();
            } else {
                ScriptEngine.modifyTrigger(position, p, b);
                setResult(RESULT_MODIFY);
                finish();
            }
        } else if (resId == R.id.delete) {
            if (requestType == REQUEST_MODIFY) {
                ScriptEngine.removeTrigger(position);
                setResult(RESULT_DELETE);
            }
            finish();
        } else if (resId == R.id.triggerChoose) {
            new AlertDialog.Builder(this).setIcon(R.drawable.smallicon)
                    .setTitle(R.string.trigger_body).setAdapter(
                            new ScreenTextAdapter(this, screenText),
                            (dialog, which) -> pattern.setText(screenText.get(which))).create().show();
        }
    }
}

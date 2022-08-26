package basement.lab.mudclient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;

import basement.lab.mudclient.adapter.AliasAdapter;
import basement.lab.mudclient.bean.Alias;
import basement.lab.mudclient.bean.ScriptEngine;

public class AliasListActivity extends ListActivity implements OnClickListener,
        OnItemClickListener {
    private AliasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.aliaslist);
        setTitle(R.string.aliaseditor);
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        Button finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new AliasAdapter(this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void onClick(View v) {
        int resId = v.getId();
        if(resId == R.id.add)
        {
            Intent a = new Intent(this, AliasEditorActivity.class);
            a.putExtra(AliasEditorActivity.REQUEST_TYPE,
                    AliasEditorActivity.REQUEST_ADD_ALIAS);
            startActivityForResult(a, AliasEditorActivity.REQUEST_ADD_ALIAS);
        }
        else if(resId == R.id.finish)
        {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case AliasEditorActivity.REQUEST_ADD_ALIAS:
                    if (resultCode != RESULT_CANCELED) {
                        String key = data.getStringExtra(AliasEditorActivity.TITLE);
                        String body = data.getStringExtra(AliasEditorActivity.BODY);
                        ScriptEngine.addNewAlias(key, body);
                        ScriptEngine.commit();
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case AliasEditorActivity.REQUEST_EDIT_ALIAS:
                    if (resultCode != RESULT_CANCELED) {
                        if (resultCode == AliasEditorActivity.RESULT_DELETE) {
                            String key = data
                                    .getStringExtra(AliasEditorActivity.TITLE);
                            ScriptEngine.removeAlias(key);
                            ScriptEngine.commit();
                            adapter.notifyDataSetChanged();
                        } else {
                            String key = data
                                    .getStringExtra(AliasEditorActivity.TITLE);
                            String body = data
                                    .getStringExtra(AliasEditorActivity.BODY);
                            ScriptEngine.removeAlias(key);
                            ScriptEngine.addNewAlias(key, body);
                            ScriptEngine.commit();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, R.string.commitfileerror, Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                            long position) {
        Intent a = new Intent(this, AliasEditorActivity.class);
        a.putExtra(AliasEditorActivity.REQUEST_TYPE,
                AliasEditorActivity.REQUEST_EDIT_ALIAS);
        Alias alias = (Alias) adapter.getItem((int) position);
        a.putExtra(AliasEditorActivity.TITLE, alias.name);
        a.putExtra(AliasEditorActivity.BODY, alias.body);
        startActivityForResult(a, AliasEditorActivity.REQUEST_EDIT_ALIAS);
    }
}

package com.stream.wantsms;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText number;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText) findViewById(R.id.number);
        content = (EditText) findViewById(R.id.content);
    }


    public void click(View view) {

        String n = number.getText().toString();
        String c = content.getText().toString();
        if (n.equals("") || c.equals("")) {
            Toast.makeText(this, "联系人或内容为空", Toast.LENGTH_SHORT).show();
        } else {
            Uri uri = Uri.parse("content://sms/");
            ContentValues value = new ContentValues();
            value.put("address", n);
            switch (view.getId()) {
                case R.id.receive:
                    value.put("type", 1);
                    break;
                case R.id.send:
                    value.put("type", 2);
                    break;
            }
            value.put("body", c);
            uri = getContentResolver().insert(uri, value);
            if (uri == null) {
                Toast.makeText(this, "请到应用权限管理中赋予需要的权限", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void search(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 清除文本框
     *
     * @param view view
     */
    public void clear(View view) {
        content.setText("");
        number.setText("");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK)
                    number.setText(data.getStringExtra("contact"));
                break;
            default:
                break;
        }
    }


}

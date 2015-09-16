package com.stream.wantsms;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ContactActivity extends Activity implements OnItemClickListener{
	
	private ListView list;
	private ArrayAdapter<String> adapter;
	List<String> contactList = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_activity);
		
		list = (ListView)findViewById(R.id.contact_list);
		
		
		readContacts();

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	private void readContacts() {
		Cursor cursor = null;
		try{
			cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null, null, null, null);
			while(cursor.moveToNext()){
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				contactList.add(name+":"+number);
			}

		}catch(Exception e){
			Toast.makeText(this, "获取联系人列表失败，您禁止了权限", Toast.LENGTH_SHORT).show();
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String contact = contactList.get(position);
		String[] strs = contact.split(":");
		Intent intent = new Intent();
		intent.putExtra("contact", strs[1]);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	
}

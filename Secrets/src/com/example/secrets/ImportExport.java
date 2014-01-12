package com.example.secrets;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ImportExport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import_export);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.import_export, menu);
		return true;
	}

	public void importFunction(View v) {
		if (!isExternalStorageWritable()) {
			Toast.makeText(this, "Cannot import. Missing External Storage",
					Toast.LENGTH_LONG).show();
		} else {
			final Intent sender = getIntent();
			final String fileName = sender.getExtras().getString("FileName");
			final String resultedFileName = sender.getExtras().getString(
					"ResultedFile");
			String path = getFilesDir().getAbsolutePath() + "/" + fileName;
			File f = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + resultedFileName);
			String returnPath = f.getAbsolutePath();

			Log.d("Import path 1", path);
			Log.d("Import path 2", returnPath);
			
			CaesarCode.decryptFile(returnPath, path);
			Toast message = Toast.makeText(ImportExport.this,
					"Your file has been decrypted", Toast.LENGTH_SHORT);
			message.show();
		}
	}

	public void exportFunction(View v) {
		if (!isExternalStorageWritable()) {
			Toast.makeText(this, "Cannot export. Missing External Storage",
					Toast.LENGTH_LONG).show();
		} else {
			final Intent sender = getIntent();
			final String fileName = sender.getExtras().getString("FileName");
			final String resultedFileName = sender.getExtras().getString(
					"ResultedFile");
			String path = getFilesDir().getAbsolutePath() + "/" + fileName;
			File f = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + resultedFileName);
			String returnPath = f.getAbsolutePath();
			Log.d("Export path 1", path);
			Log.d("Export path 2", returnPath);
			
			try {
				CaesarCode.encryptFile(path, returnPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast message = Toast.makeText(ImportExport.this,
					"Your file has been encrypted", Toast.LENGTH_SHORT);
			message.show();
		}
	}

	public void IEBackFunction(View v) {
		Intent intent = new Intent(ImportExport.this, MainActivity.class);
		startActivity(intent);

	}

	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

}

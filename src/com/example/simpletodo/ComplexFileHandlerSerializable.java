package com.example.simpletodo;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.util.Log;

public class ComplexFileHandlerSerializable extends FileHandlerInterface {
	
	private static final String TAG="ComplexFileHandlerSerializable";

	String fileName = "todocomplex.txt";

	public ArrayList<TodoItem> parseItemsFromFile(File filesDir) {
		File todoFile = new File(filesDir, fileName);
		ArrayList<TodoItem> items = new ArrayList<TodoItem>();		
		FileInputStream fin;
		ObjectInputStream ois = null;
			try {
				fin = FileUtils.openInputStream(todoFile);
				ois = new ObjectInputStream(fin);
				// TODO How to serialize/deserialize an arraylist
//				ArrayList<TodoItem> readObject = (ArrayList<TodoItem>) ois.readObject();
//				items = readObject;
				while(true) {
					TodoItem item = (TodoItem) ois.readObject();
					items.add(item);
				}
			} catch (EOFException exc) {
				 try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch ( FileNotFoundException e) {
				Log.w(TAG , "File " + todoFile.getAbsolutePath() + " not found!");
				
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
	
		
		return items;
	}

	public void saveItemsToFile(File filesDir, List<TodoItem> items) {
		// ArrayList<String> itemStrings = new ArrayList<String>();
		try {
			File todoFile = new File(filesDir, fileName);
			FileOutputStream fout = FileUtils.openOutputStream(todoFile);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			// oos.writeObject(items);
			for (TodoItem item : items) {
				oos.writeObject(item);
			}
			oos.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

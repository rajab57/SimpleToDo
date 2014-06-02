package com.example.simpletodo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ComplexFileHandler extends FileHandlerInterface {

	String fileName = "todocomplex.txt";

	public ArrayList<TodoItem> parseItemsFromFile(File filesDir) {
		File todoFile = new File(filesDir, fileName);
		ArrayList<String> itemStrings = new ArrayList<String>();
		ArrayList<TodoItem> items = new ArrayList<TodoItem>();
		try {
			itemStrings = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String item : itemStrings) {
			String delims = "[,]";
			String[] tokens = item.split(delims);
			TodoItem todoItem = new TodoItem(tokens[0]);
			todoItem.setPriority(TodoItem.PriorityLevels.valueOf(tokens[1]));
			todoItem.setDueDate(tokens[2]);
			todoItem.setReminder(Long.parseLong(tokens[3]));
			items.add(todoItem);
		}

		return items;
	}

	public void saveItemsToFile(File filesDir, List<TodoItem> items) {
		ArrayList<String> itemStrings = new ArrayList<String>();
		for (TodoItem item : items) {
			itemStrings.add(item.toString());
		}
		File todoFile = new File(filesDir, fileName);
		try {
			FileUtils.writeLines(todoFile, itemStrings);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

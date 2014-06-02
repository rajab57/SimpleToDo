package com.example.simpletodo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class FileHandlerInterface {

	public abstract ArrayList<TodoItem> parseItemsFromFile(File filesDir);

	public abstract void saveItemsToFile(File filesDir, List<TodoItem> items);

}

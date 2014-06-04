
# SimpleTodo App

SimpleTodo App has mininimalist interface so you can get in and add tasks quickly. 
The idea is to keep it minimal and not complex and laborious.


## Functionality

1. Add a task.
2. Delete a task.
3. Add due date to a task.
4. Add a reminder/alarm to a task.
5. Set priority to a task.
6. Sort the tasks by due date, last added or priority.
7. Select an item with onItemLong Click. The following options becomes available on the ActionBar
	1. Add Due Date
	2. Delete the item
	3. Set Reminder
	4. Set Priority ( low, medium, and high )
8. Data is persisted in files.

## Resolved Issues/Features
1. Only one alarm is set for each task. The older alarms for the same task are cancelled.
2.  Multiple items on a single row in ListView. The right most item is only location it was clickable. Set right/left item to focusable=false, 
and clickable=false for the middle (text - task ) to be clickable.
3. Show previously set value for Alarm and Duedate on the Date/Time Picker
4. Sorting does not work correctly for next year.

## Pending Issues

2. Color of the dialogs are not consistent with the rest.
3. The alarm sets again when the orientation changes. Apparently the activity re creates when the orientation changes. For now restricted to
portrait orientation since it looks better in appearance. 
4. Implementation of the Detail view of the Task. ( duplicate of the existing way of seeing on the same activity ) 
5. Java Serializable of ArrayList throw ClassNotFound Exception
6. There is no way to cancel an alarm other than deleting the task.(workaround done )
7. List of alarms are not shown.
8. Persisting the alarms in the database. Is it necesssary ?
9. Performance Testing and Unit Testing.
10. Swipe issues - at the listview level access to position available but lose other events like onlongclick etc.
11. ** When the keyboard opens up the Sort button moves to above keyboard. I would prefer if they hidden instead.


## Wishlist
1. Swipe left to right to strike out to indicate completed task.
2. Swipe right to left to delete the task.
3. Change color based on due dates.
4. How to include datetime picker library ? https://github.com/rajab57/SimpleToDo.git
5. Too many images form 9 patch image generator. Explore alternatives.
6. Notify others UI objects when i am not focussed anymore.
7. Experiment with SQlite

## Explored Features

1. Swipes
2. Custom Adapter
3. 9 patch images
4. View Holder
5. Date Picker and Time Picker
6. Action Bar
7. Java Serializable
8. Custom Fonts
9. More understanding of Linear Layout vs Relative Layout ( Performance costs & Restrictions)
10. Difference between app.DialogFragment and v4.DialogFragment

## Demo
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/mainView.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/addItem.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/selectItem.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/setDueDate.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/setPriority.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/setReminder.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/playAlarm.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/addNewItem.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/afterNewItemAdded.png?raw=true" width="300"/>
<span> </span>
<img src="https://github.com/rajab57/SimpleToDo/raw/master/screenshots/sortedByPriority.png?raw=true" width="300"/>







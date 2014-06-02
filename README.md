SIMPLY TODO
===========

This is a minimalistic approach to Todo App for those who like postits and throw them away when they are done. 
I have attempted to keep the app very minimalistic without making it too complex to use and too cluttered for visualization.

Tested only on Nexus 5, and the images are supported only for xxhdpi screens.

Functionalities
===============

1. Add a task
2. Delete a task
3. Edit a task.
4. Set due date for the task
5. Set an alarm/reminder for the task
6. Set priority of the task
7. Sort the tasks based on high priority, last added, or due date.
8. Detail view of the task with edit features.

Usage
=====

OnLongClick of Item
-------------------
 See options to add information to the item on the action bar. OnLongClick again, the item is no longer selected
 and the actionbar disappaers.
 Action Bar items
 1. Add due date ( right most ). By default set to one year from the date added
 2. Remove an item
 3. Set an alarm ( reminder )
 4. Set Priority ( low, medium, high ) Default priority is low.
 
OnItemClick
------------
Opens the detail page of the items with options to edit the item.

Sort Buttons at the bottom
--------------------------
1. Sort By Last Added item ( Latest on the top )
2. Sort by priority ( Highest on the top )
3. Sorty by due date ( earliest on the top )


Resolved Issues
==============
1.Not sorting next year correctly.
2.The row can be accessed only from the right side. This issue needs to be resolved asap. - DONE (set focusbale and clickable to false on left/right 
item on the row )
3.Reminder action - DONE
4.Cancel alarm(also called reminder) if the task is completed before the set alarm time - DONE
5. Support only Portrait orientation DONE. set android:screenOrientation="potrait" for every activity in the Android-manifest.xml file


Pending Tasks
==============

2. Images are from free source and hence do not look alike.
3. When there are enough items to scroll, and you try to select the last item, there is an overlap with the sort button at the bottom.
6. Confusing when year is not shown ( on the right of the  item )
7. Check for past due date an display PastDue instead of the date.
8. TASK completed action - strike out task name
9. Alarm starts again when the orientation changes. Apparently the orientation changes restarts the activity. (anyway the orientation is fixed 
to portrait for now since that gives a better appearance). This issues needs to be investigated further.
10. Performance Testing and Unit Testing
11. The popup dialogs do not use the 9 patch images.


Wishlist
========

1. Color based on Due date.
2. Add Items using voice
3. Add category - Decided against it since I want to keep it to the minimalistic todo app
4. Share with email 
5. Strike item when completed.

Some topics explored in the process of building the app
=======================================================

1. Intent
2. Action Bar
3. Java Serializable
4. Setting up Alarm Mananger
6. Date Picker and Time Picker
7. Custom Dialog
8. Adapters and Custom Adapters
9. Alarm Managers
10. Used 9 patch generator. Is there alternatives ? What is the performance hit on having unused files from the generation ?


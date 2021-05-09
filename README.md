# MADShoppingList
UNI S6 MAD shopping list project

- Author: Eric ROMANG
- Professor: Dr. Jean Botev

# General Description

- Application in package `lu.uni.mad.madproject`

## Dependancies

- Libraries
    - Lifecycle-compiler v1.1.1
    - Lifecycle-extensions v1.1.1
    - Room-compiler v1.1.1
    - Room-runtime v1.1.1
    - Constraint-layout v1.1.3
    - Appcompact v7.27.1.1
    - Design 27.1.1
    
- Excluded
    - configurations { implementation.exclude group: 'org.jetbrains', module: 'annotations' }
    
## Code

- Item class: Entity class that represents a item in the database
- ItemDao interface: Data Access Object (DAO) for an item
- ItemRoomDatabase abstract class: Includes code to create the database.
    - Also initial prepopulate the database with predefined items names, descriptions and quantities
- ItemRepository class: Holds the implementation code for the methods that interact with the database
- ItemViewModel class: provides the interface between the UI and the data layer of the app represented by ItemRepository
- ItemListAdapter class: Adapter for the RecyclerView that displays a list of items.
- MainActivity class: Main class to start the application
- NewItemActivity class: To add a new item or to update it
- AboutActivity class: To display the about

## Usage 

- The app start the MainActivity displaying a pre-populated list of example items.
- Pre-populated items is done during initial database creation, only if no items are present in the database.
- MainActivity:
    - Display a list of items in a RecylerView
    - Display a FAB to add new items via the NewItemActivity
    - Allow users to delete an item by swiping it away
    - If an item is added, modified, or deleted, it is automatically updated
    - Display an AboutActivity via the option menu
    - Allow deletion of all items via the option menu

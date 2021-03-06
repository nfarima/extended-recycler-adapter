# Android Extended Recycler Adapter
[![Release](https://jitpack.io/v/nfarima/extended-recycler-adapter.svg)](https://jitpack.io/#nfarima/extended-recycler-adapter)

![Recycler Adapter](https://github.com/nfarima/extended-recycler-adapter/blob/master/demo.png)



A fast and flexible way to use the RecyclerView on android. Eliminates all verbose declarations of RecyclerAdapter and ViewHolder classes by taking full advantage of Java8 lambdas, which are now available as a backport on any API level

Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
    implementation 'com.github.nfarima:extended-recycler-adapter:1.2.4'
}
```

make sure you have enabled the Java8 features in the **android compileOptions** section of your app module build.gradle file
(as long as you don't use other Java8 feature than lambdas, you can enable it on any API level)
```
android {
..............
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

## 1 Examples

### 1.1 Quick and clean
```java
        List<String> names = Arrays.asList("Black Widow", "Hulk", "Thanos", "Maw", "Dr. Strange", "Dormamu");

        new ExtendedRecyclerAdapter<TextView, String>(recyclerView)
                .viewFactory(() -> new TextView(this))
                .data(() -> names)
                .bind(TextView::setText)
                .vertical();
```

### 1.2 Quick and clean, using a view resource id
```java
        List<String> names = Arrays.asList("Black Widow", "Hulk", "Thanos", "Maw", "Dr. Strange", "Dormamu");

        new ExtendedRecyclerAdapter<TextView, String>(recyclerView)
                .viewResource(android.R.layout.simple_list_item_1)
                .onClick(this::showName)
                .data(() -> names)
                .bind(TextView::setText)
                .vertical();
```

### 1.3 Advanced, still clean
```java
        new ExtendedRecyclerAdapter<UserView, User>(recyclerView)
                .viewResource(R.layout.user_list_item)
                .data(UsersRepository::getActiveUsers)
                .bind(UserView::fill)
                .onCheckedChange(R.id.userActiveCheckbox, this::setActive)
                .onClick(R.id.removeUser, this::showRemoveConfirmation)
                .onLongClick(R.id.contents, this::showOptionsDialog)
                .vertical();
```                


## 2 Builder methods:
### 2.1 Mandatory (will throw if any of these isn't assigned)

#### 2.1.a 
**viewFactory(ViewFactory<VIEW> viewFactory)**

    sets the method (functional interface) that takes no arguments and is responsible for creating and returning the view that will be used as an item. Alternatively, the viewResource(id) method can be used, it will create an inflate factory for you

#### 2.1.b
**bind(Bind<VIEW, MODEL> bind)**: 

    sets the method (functional interface) that takes two arguments, view and model and is responsible for filling the view with the contents of the model

#### 2.1.c
**data(DataSource< MODEL > dataSource)**: 

    assigns the List<MODEL> data set or provides the datasource method (functional interface) that returns a collection of items (of MODEL type) that will be used as a dataset for the RecyclerView

#### 2.1.d
**any of the terminal methods below**


### 2.2 Terminal (The adapter will not be attached until a terminal method is invoked)

These methods build and attach to the RecyclerView you passed in the constructor. The obtained instance can then be used to control the RecyclerView using the intermediary methods. Calling any of the mandatory or terminal methods after invoking a terminal method will throw an exception

**vertical()**

    Assigns a vertical LinearLayoutManager to the RecyclerView and builds this adapter

**horizontal()**

    Assigns a horizontal LinearLayoutManager to the RecyclerView and builds this adapter

**grid(int columnCount)**

    Assigns a GridLayoutManager with the specified column count and builds this adapter

**custom(RecyclerView.LayoutManager layoutManager)**

    Assigns the passed LayoutManager and builds this adapter


### 2.3 Intermediary methods

    onClick(int id, Click<MODEL> click)
    
    onLongClick(int id, LongClick<MODEL> longClick)
    
    onCheckedChange(int id, Checked<MODEL> checked)
    
    mutate(Mutator<VIEW, MODEL> mutator)  Allows you to make any custom mutations to the view on bind
    
    notifyDataSetChanged() forwards the call to the underlying adapter
    
    notifyItemRemoved(int position) forwards the call to the underlying adapter
    
    setItems(List<MODEL> items) allows you to change the dataset
    
    getAdapter() returns the underlying adapter

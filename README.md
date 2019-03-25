# recycler-adapter-builder

[![Release](https://jitpack.io/v/nfarima/recycler-adapter-builder.svg)](https://jitpack.io/#nfarima/recycler-adapter-builder)

A fast and flexible way to use the RecyclerView on android

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
    implementation 'com.github.nfarima:recycler-adapter-builder:1.0'
}
```


## 1 Examples

### 1.1 Quick and clean
```java
        List<String> names = Arrays.asList("Black Widow", "Hulk", "Thanos", "Maw", "Dr. Strange", "Dormamu");

        new RecyclerAdapterBuilder<TextView, String>(recyclerView)
                .viewFactory(() -> new TextView(this))
                .data(() -> names)
                .bind(TextView::setText)
                .vertical();
```

### 1.2 Quick and clean, using a view resource id
```java
        List<String> names = Arrays.asList("Black Widow", "Hulk", "Thanos", "Maw", "Dr. Strange", "Dormamu");

        new RecyclerAdapterBuilder<TextView, String>(recyclerView)
                .viewResource(android.R.layout.simple_list_item_1)
                .data(() -> names)
                .bind(TextView::setText)
                .vertical();
```

### 1.3 Advanced and clean
```java
        new RecyclerAdapterBuilder<UserView, User>(recyclerView)
                .viewResource(R.layout.user_list_item)
                .data(UsersRepository::getActiveUsers)
                .bind(UserView::fill)
                .onCheckedChange(R.id.userActiveCheckbox, this::setActive)
                .onClick(R.id.removeUser, this::showRemoveConfirmation)
                .onLongClick(R.id.contents, this::showOptionsDialog)
                .vertical();
```                


## 2 Builder methods:
### 2.1 Mandatory 
**viewFactory**

sets the method (functional interface) that takes no arguments and is responsible for creating and returning the view that will be used as an item. Alternatively, the viewResource(id) method can be use, it will create an inflate factory for you

**bind**: 

sets the method (functional interface) that takes two arguments, view and model and is responsible for filling the view with the contents of the model

**dataSource**: 

sets the method (functional interface) that returns a collection of items (of MODEL type) that will be used as a dataset for the RecyclerView

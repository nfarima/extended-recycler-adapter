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


## Examples

### 1. Quick and clean
```java
        List<String> names = Arrays.asList("Black Widow", "Hulk", "Thanos", "Maw", "Dr. Strange", "Dormamu");

        new RecyclerAdapterBuilder<TextView, String>(recyclerView)
                .viewFactory(() -> new TextView(this))
                .data(() -> names)
                .bind(TextView::setText)
                .vertical();
```

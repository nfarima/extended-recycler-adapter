package com.github.nfarima.extendedrecycleradapterexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.nfarima.extendedrecycleradapter.ExtendedRecyclerAdapter;
import com.github.nfarima.extendedrecycleradapter.lambda.Click;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private  void onClick(int position, String item) {
        System.out.println(position+item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> names = Arrays.asList("Black Widow", "Hulk", "Thanos", "Maw", "Dr. Strange", "Dormamu");

        RecyclerView recycler = findViewById(R.id.recycler);
        new ExtendedRecyclerAdapter<TextView, String>(recycler)
                .viewFactory(() -> new TextView(this))
                .onClick(this::onClick)
                .data(names)
                .bind(TextView::setText)
                .vertical();
    }

}

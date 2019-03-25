package com.github.nfarima.extendedrecycleradapter.lambda;

import android.view.View;

public interface Bind<V extends View, M> {
    void bind(V view, M model);
}

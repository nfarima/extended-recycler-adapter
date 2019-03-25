package com.github.nfarima.recycleradapterbuilder.lambda;

import android.view.View;

public interface Bind<V extends View, M> {
    void bind(V view, M model);
}

package com.github.nfarima.recycleradapterbuilder.lambda;

import android.view.View;

public interface Mutator<V extends View,M> {
    void mutate(V view, M model, int position);
}

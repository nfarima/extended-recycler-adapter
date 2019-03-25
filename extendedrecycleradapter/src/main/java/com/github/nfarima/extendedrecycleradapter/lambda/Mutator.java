package com.github.nfarima.extendedrecycleradapter.lambda;

import android.view.View;

public interface Mutator<V extends View,M> {
    void mutate(V view, M model, int position);
}

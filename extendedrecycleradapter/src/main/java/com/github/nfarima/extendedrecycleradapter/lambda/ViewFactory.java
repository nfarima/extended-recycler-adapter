package com.github.nfarima.recycleradapterbuilder.lambda;

import android.view.View;

public interface ViewFactory<V extends View> {
    V createView();
}

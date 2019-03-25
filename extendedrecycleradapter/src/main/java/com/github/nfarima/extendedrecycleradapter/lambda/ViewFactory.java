package com.github.nfarima.extendedrecycleradapter.lambda;

import android.view.View;

public interface ViewFactory<V extends View> {
    V createView();
}

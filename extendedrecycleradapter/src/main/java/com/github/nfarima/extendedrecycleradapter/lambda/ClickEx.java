package com.github.nfarima.extendedrecycleradapter.lambda;

import android.view.View;

public interface ClickEx<VIEW extends View, MODEL> {
    void onClick(VIEW view, MODEL model, int position);
}

package com.github.nfarima.extendedrecycleradapter.lambda;

public interface Checked<MODEL> {
    void onCheckedChange(int position, MODEL item, boolean isChecked);
}

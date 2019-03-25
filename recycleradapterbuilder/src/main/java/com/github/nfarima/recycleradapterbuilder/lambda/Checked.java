package com.github.nfarima.recycleradapterbuilder.lambda;

public interface Checked<MODEL> {
    void onCheckedChange(int position, MODEL item, boolean isChecked);
}

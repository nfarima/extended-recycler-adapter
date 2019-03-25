package com.github.nfarima.recycleradapterbuilder.lambda;

import java.util.List;

public interface DataSource<MODEL> {
    List<MODEL> getItems();
}

package com.github.nfarima.extendedrecycleradapter.lambda;

import java.util.List;

public interface DataSource<MODEL> {
    List<MODEL> getItems();
}

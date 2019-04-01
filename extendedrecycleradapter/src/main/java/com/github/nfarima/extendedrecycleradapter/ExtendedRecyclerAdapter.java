package com.github.nfarima.extendedrecycleradapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.github.nfarima.extendedrecycleradapter.lambda.Checked;
import com.github.nfarima.extendedrecycleradapter.lambda.ClickEx;
import com.github.nfarima.extendedrecycleradapter.lambda.Mutator;
import com.github.nfarima.extendedrecycleradapter.lambda.ViewFactory;
import com.github.nfarima.extendedrecycleradapter.lambda.Bind;
import com.github.nfarima.extendedrecycleradapter.lambda.Click;
import com.github.nfarima.extendedrecycleradapter.lambda.DataSource;
import com.github.nfarima.extendedrecycleradapter.lambda.LongClick;

import java.util.List;


public class ExtendedRecyclerAdapter<VIEW extends View, MODEL> {

    private boolean built = false;

    private Bind<VIEW, MODEL> bind = (view, model) -> {
        throw new IllegalStateException("You must implement this method");
    };
    private ViewFactory<VIEW> viewFactory = () -> {
        throw new IllegalStateException("You must implement this method");
    };
    private DataSource<MODEL> dataSource = () -> {
        throw new IllegalStateException("You must implement this method");

    };
    private Click<MODEL> click = (position, item) -> {

    };

    private ClickEx<VIEW, MODEL> clickEx = (view, model, position) -> {

    };

    private SparseArray<Checked<MODEL>> checkedChange = new SparseArray<Checked<MODEL>>();

    private SparseArray<Click<MODEL>> secondaryClick = new SparseArray<Click<MODEL>>();

    private SparseArray<LongClick<MODEL>> secondaryLongClick = new SparseArray<LongClick<MODEL>>();


    private LongClick<MODEL> longClick = (position, item) -> false;

    private Mutator<VIEW, MODEL> mutator = (view, model, position) -> {

    };
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    public ExtendedRecyclerAdapter<VIEW, MODEL> viewFactory(ViewFactory<VIEW> viewFactory) {
        validateChainedMethodCall("You can not change the view factory once assigned. See the docs");
        this.viewFactory = viewFactory;
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> viewResource(int layoutResourceId) {
        validateChainedMethodCall("You can not change the view factory once assigned. See the docs");
        this.viewFactory = () -> (VIEW) View.inflate(recyclerView.getContext(), layoutResourceId, null);
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> data(DataSource<MODEL> dataSource) {
        validateChainedMethodCall("You can not change the data source once assigned. See the docs");
        this.dataSource = dataSource;
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> data(List<MODEL> items) {
        validateChainedMethodCall("You can not change the data source once assigned. See the docs");
        this.dataSource = () -> items;
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> mutate(Mutator<VIEW, MODEL> mutator) {
        this.mutator = mutator;
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> onClick(Click<MODEL> click) {
        this.click = click;
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> onClick(int id, Click<MODEL> click) {
        this.secondaryClick.put(id, click);
        return this;
    }


    public ExtendedRecyclerAdapter<VIEW, MODEL> onCheckedChange(int id, Checked<MODEL> checked) {
        this.checkedChange.put(id, checked);
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> onLongClick(int id, LongClick<MODEL> longClick) {
        this.secondaryLongClick.put(id, longClick);
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> onLongClick(LongClick<MODEL> longClick) {
        this.longClick = longClick;
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> bind(Bind<VIEW, MODEL> bind) {
        validateChainedMethodCall("You can not change the binder once assigned. See the docs");
        this.bind = bind;
        return this;
    }

    private RecyclerView recyclerView;


    public ExtendedRecyclerAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> vertical() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        build();
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> custom(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        build();
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> horizontal() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        build();
        return this;
    }

    public ExtendedRecyclerAdapter<VIEW, MODEL> grid(int columnCount) {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), columnCount));
        build();
        return this;
    }

    private void validateChainedMethodCall(String message) {
        if (built) {
            throw new IllegalStateException(message);
        }

    }

    private List<MODEL> items;

    @SuppressWarnings("unchecked")
    private void build() {
        validateChainedMethodCall("You can not call a terminal method twice");

        built = true;

        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            {
                items = dataSource.getItems();
            }


            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(viewFactory.createView()) {

                    private final View.OnClickListener clickListener =
                            v -> click.onClick(getAdapterPosition(), items.get(getAdapterPosition()));
                    private final View.OnLongClickListener longClickListener =
                            v -> longClick.onLongClick(getAdapterPosition(), items.get(getAdapterPosition()));

                    {

                        itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                //todo investigate why sometimes click only works on parent. dont use descendantFocusability
//                                ((ViewGroup) itemView).getChildAt(0).setOnClickListener(clickListener);
//                                ((ViewGroup) itemView).getChildAt(0).setOnLongClickListener(longClickListener);
                                itemView.setOnClickListener(clickListener);
                                itemView.setOnLongClickListener(longClickListener);
                                for (int i = 0; i < checkedChange.size(); i++) {
                                    int id = checkedChange.keyAt(i);
                                    Checked<MODEL> checked = checkedChange.get(id);
                                    ((CompoundButton) itemView.findViewById(id))
                                            .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    int position = getAdapterPosition();
                                                    checked.onCheckedChange(position, items.get(position), isChecked);
                                                }
                                            });
                                }

                                for (int i = 0; i < secondaryClick.size(); i++) {
                                    int id = secondaryClick.keyAt(i);
                                    Click click = secondaryClick.get(id);
                                    itemView.findViewById(id).setOnClickListener(v -> {
                                        int position = getAdapterPosition();
                                        click.onClick(position, items.get(position));
                                    });
                                }

                                for (int i = 0; i < secondaryLongClick.size(); i++) {
                                    int id = secondaryLongClick.keyAt(i);
                                    LongClick click = secondaryLongClick.get(id);
                                    itemView.findViewById(id).setOnLongClickListener(v -> {
                                        int position = getAdapterPosition();
                                        return click.onLongClick(position, items.get(position));
                                    });
                                }
                            }
                        });

                    }

                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MODEL model = items.get(position);
                VIEW itemView = (VIEW) holder.itemView;

                bind.bind(itemView, model);
                mutator.mutate(itemView, model, position);
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public void setItems(List<MODEL> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<MODEL> getItems() {
        return items;
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void notifyItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return adapter;
    }
}

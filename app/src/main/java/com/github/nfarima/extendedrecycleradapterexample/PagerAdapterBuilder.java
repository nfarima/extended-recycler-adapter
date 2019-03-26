package com.github.nfarima.extendedrecycleradapterexample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PagerAdapterBuilder {

    private ViewPager viewPager;
    private ViewGroup childrenContainer;
    private List<String> titles;
    private int childCount;

    public static PagerAdapterBuilder from(ViewGroup childrenContainer) {
        return new PagerAdapterBuilder(childrenContainer);
    }

    private PagerAdapterBuilder(ViewGroup childrenContainer) {
        this.childrenContainer = childrenContainer;
    }

    public PagerAdapterBuilder into(ViewPager viewPager) {
        this.viewPager = viewPager;
        childCount = childrenContainer.getChildCount();
        final View[] views = new View[childCount];
        for (int i = 0; i < childCount; i++) {
            views[i] = childrenContainer.getChildAt(i);
        }

        childrenContainer.removeAllViews();

        titles = new ArrayList<>(childCount);
        viewPager.setOffscreenPageLimit(childCount);
        viewPager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = views[position];
                container.addView(view);
                return view;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }

            @Override
            public int getCount() {
                return childCount;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            }
        });
        return this;
    }

    public PagerAdapterBuilder withTabLayout(TabLayout tabLayout) {
        ensureOrder();
        if (tabLayout.getTabCount() != childCount) {
            throw new IllegalStateException("Make sure tab layout has tab count equal to viewpager item count");
        }

        titles = new ArrayList<>();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            CharSequence text = tabLayout.getTabAt(i).getText();
            titles.add(text == null ? "" : text.toString());
        }
        tabLayout.setupWithViewPager(viewPager);
        return this;
    }

    public PagerAdapterBuilder onPageSelected(Consumer<Integer> newIndexConsumer) {
        ensureOrder();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                newIndexConsumer.accept(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return this;
    }


    public PagerAdapterBuilder withListener(ViewPager.OnPageChangeListener listener) {
        ensureOrder();
        viewPager.addOnPageChangeListener(listener);
        return this;
    }

    private void ensureOrder() {
        if (viewPager == null) {
            throw new IllegalStateException("Invocation order: PagerAdapterBuilder.from(container).into(viewPager).with..");
        }
    }
}

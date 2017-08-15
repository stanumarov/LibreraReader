package com.foobnix.pdf.info.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.foobnix.android.utils.LOG;
import com.foobnix.pdf.info.R;
import com.foobnix.ui2.fragment.BookmarksFragment2;
import com.foobnix.ui2.fragment.BrowseFragment2;
import com.foobnix.ui2.fragment.OpdsFragment2;
import com.foobnix.ui2.fragment.PrefFragment2;
import com.foobnix.ui2.fragment.RecentFragment2;
import com.foobnix.ui2.fragment.SearchFragment2;
import com.foobnix.ui2.fragment.StarsFragment2;
import com.foobnix.ui2.fragment.UIFragment;

public enum UITab {

    SearchFragment(0, R.string.library, R.drawable.glyphicons_2_book_open, SearchFragment2.class, true), //
    OpdsFragment(1, OpdsFragment2.PAIR.first, OpdsFragment2.PAIR.second, OpdsFragment2.class, true), //
    BrowseFragment(2, BrowseFragment2.PAIR.first, BrowseFragment2.PAIR.second, BrowseFragment2.class, true), //
    RecentFragment(3, RecentFragment2.PAIR.first, RecentFragment2.PAIR.second, RecentFragment2.class, true), //
    StarsFragment(4, StarsFragment2.PAIR.first, StarsFragment2.PAIR.second, StarsFragment2.class, true), //
    BookmarksFragment(5, BookmarksFragment2.PAIR.first, BookmarksFragment2.PAIR.second, BookmarksFragment2.class, true), //
    PrefFragment(6, PrefFragment2.PAIR.first, PrefFragment2.PAIR.second, PrefFragment2.class, true); //

    private int index;
    private int name;
    private int icon;
    private Class<? extends UIFragment> clazz;
    private boolean isVisible;

    private UITab(int index, int name, int icon, Class<? extends UIFragment> clazz, boolean isVisible) {
        this.index = index;
        this.name = name;
        this.icon = icon;
        this.clazz = clazz;
        this.isVisible = isVisible;
    }

    public int getIndex() {
        return index;
    }

    public int getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public Class<? extends UIFragment> getClazz() {
        return clazz;
    }

    public static UITab getByIndex(int index) {
        for (UITab tab : values()) {
            if (tab.index == index) {
                return tab;
            }
        }
        return SearchFragment;
    }

    public static List<UITab> getOrdered(String input) {
        LOG.d("getOrdered", input);
        List<UITab> list = new ArrayList<UITab>();
        for (String pair : input.split(",")) {
            String[] tab = pair.split("#");
            int id = Integer.valueOf(tab[0]);
            boolean isVisible = tab[1].equals("1");
            UITab byIndex = getByIndex(id);
            byIndex.setVisible(isVisible);
            list.add(byIndex);
        }
        return list;
    }

    public static int getCurrentTabIndex(UITab tab) {
        List<UITab> ordered = getOrdered(AppState.get().tabsOrder);
        for (int i = 0; i < ordered.size(); i++) {
            if (ordered.get(i) == tab) {
                return i;
            }
        }
        return 0;
    }

    public static boolean isShowRecent() {
        return AppState.get().tabsOrder.contains("2#1");
    }

    public static boolean isShowPreferences() {
        return AppState.get().tabsOrder.contains("5#1");
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}

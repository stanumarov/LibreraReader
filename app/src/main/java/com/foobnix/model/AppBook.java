package com.foobnix.model;

import com.foobnix.android.utils.LOG;
import com.foobnix.android.utils.Objects;
import com.foobnix.sys.TempHolder;

import org.ebookdroid.core.PageIndex;
import org.ebookdroid.core.events.CurrentPageListener;

public class AppBook implements CurrentPageListener {
    @Objects.IgnoreCalculateHashCode
    public transient String path;
    public int z = 100;//z

    public boolean sp = false;//split pages
    public boolean cp = false; //crop pages
    public boolean dp = false; //double pages
    public boolean dc = false; //double pages cover
    public boolean l = true; //locked

    public float x; //offsetX
    public float y; //offsetY

    public int s = AppState.get().autoScrollSpeed; //speed

    public int d = 0;//delta
    public float p; //percent
    @Objects.IgnoreCalculateHashCode
    public long h;//hash


    public AppBook() {
    }

    public AppBook(final String path) {
        this.path = path;
    }

    public void updateFromAppState() {
        cp = AppState.get().isCrop;
        dp = AppState.get().isDouble;
        sp = AppState.get().isCut;
        dc = AppState.get().isDoubleCoverAlone;
        l = AppTemp.get().isLocked;
        d = TempHolder.get().pageDelta;
        s = AppState.get().autoScrollSpeed;
    }


    public void currentPageChanged(int page, int pages) {
        if (pages <= 0 || pages <= 0) {
            if (LOG.isEnable) {
                //throw new RuntimeException("Error!!! " + page + " : " + pages);
            }
            LOG.d("currentPageChanged ERROR!!!", page + " : " + pages);
            return;
        }
        this.p = (float) page / pages;
        LOG.d("currentPageChanged", page, pages, p);
    }

    public PageIndex getCurrentPage(int pages) {
        if (pages <= 0) {
            throw new RuntimeException("Error!!! " + pages);
        }
        if (this.p > 2) {//old import support
            LOG.d("AppBook-getCurrentPage old", p,  pages);

        }

        int p = (int) (pages * this.p);
        if (p > 0) {
            p = p - 1;
        }
        LOG.d("AppBook-getCurrentPage", p, this.p, pages);

        return new PageIndex(p, p);
    }

    public float getZoom() {
        return z / 100.0f;
    }

    public void setZoom(final float zoom) {
        this.z = Math.round(zoom * 100);
    }


    public static class Diff {

        private static final short D_SplitPages = 0x0001 << 1;
        private static final short D_AnimationType = 0x0001 << 3;
        private static final short D_CropPages = 0x0001 << 4;
        private static final short D_NightMode = 0x0001 << 5;

        private static final short D_Effects = D_NightMode;

        private short mask;
        private final boolean firstTime;

        public Diff(final AppBook olds, final AppBook news) {
            firstTime = olds == null;
            if (firstTime) {
                mask = (short) 0xFFFF;
            } else if (news != null) {
                if (olds.sp != news.sp) {
                    mask |= D_SplitPages;
                }
                if (olds.cp != news.cp) {
                    mask |= D_CropPages;
                }

            }
        }

        public boolean isFirstTime() {
            return firstTime;
        }

        public boolean isSplitPagesChanged() {
            return 0 != (mask & D_SplitPages);
        }

        public boolean isAnimationTypeChanged() {
            return 0 != (mask & D_AnimationType);
        }

        public boolean isCropPagesChanged() {
            return 0 != (mask & D_CropPages);
        }

        public boolean isEffectsChanged() {
            return 0 != (mask & (D_Effects));
        }
    }
}

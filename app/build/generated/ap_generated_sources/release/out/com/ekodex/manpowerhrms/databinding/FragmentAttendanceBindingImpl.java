package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAttendanceBindingImpl extends FragmentAttendanceBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cardView12, 1);
        sViewsWithIds.put(R.id.textView14, 2);
        sViewsWithIds.put(R.id.textView16, 3);
        sViewsWithIds.put(R.id.textView18, 4);
        sViewsWithIds.put(R.id.button, 5);
        sViewsWithIds.put(R.id.imageView, 6);
        sViewsWithIds.put(R.id.cardView13, 7);
        sViewsWithIds.put(R.id.imageView6, 8);
        sViewsWithIds.put(R.id.textView21, 9);
        sViewsWithIds.put(R.id.textView20, 10);
        sViewsWithIds.put(R.id.button3, 11);
        sViewsWithIds.put(R.id.textView15, 12);
        sViewsWithIds.put(R.id.textView19, 13);
        sViewsWithIds.put(R.id.attendace_history_list, 14);
        sViewsWithIds.put(R.id.not_available1, 15);
        sViewsWithIds.put(R.id.no_internet, 16);
        sViewsWithIds.put(R.id.progressBar2, 17);
        sViewsWithIds.put(R.id.textView77, 18);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentAttendanceBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private FragmentAttendanceBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[14]
            , (androidx.appcompat.widget.AppCompatButton) bindings[5]
            , (androidx.appcompat.widget.AppCompatButton) bindings[11]
            , (androidx.cardview.widget.CardView) bindings[1]
            , (androidx.cardview.widget.CardView) bindings[7]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[8]
            , (com.airbnb.lottie.LottieAnimationView) bindings[16]
            , (com.airbnb.lottie.LottieAnimationView) bindings[15]
            , (android.widget.ProgressBar) bindings[17]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[18]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}
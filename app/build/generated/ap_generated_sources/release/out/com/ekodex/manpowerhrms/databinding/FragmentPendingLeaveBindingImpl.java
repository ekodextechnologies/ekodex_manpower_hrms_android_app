package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentPendingLeaveBindingImpl extends FragmentPendingLeaveBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView78, 1);
        sViewsWithIds.put(R.id.pendingLeaveList, 2);
        sViewsWithIds.put(R.id.addLeave, 3);
        sViewsWithIds.put(R.id.not_available1, 4);
        sViewsWithIds.put(R.id.no_internet, 5);
        sViewsWithIds.put(R.id.progressBar2, 6);
        sViewsWithIds.put(R.id.button24, 7);
        sViewsWithIds.put(R.id.button25, 8);
        sViewsWithIds.put(R.id.button26, 9);
        sViewsWithIds.put(R.id.tl1, 10);
        sViewsWithIds.put(R.id.company, 11);
        sViewsWithIds.put(R.id.textView204, 12);
        sViewsWithIds.put(R.id.tl2, 13);
        sViewsWithIds.put(R.id.branch, 14);
        sViewsWithIds.put(R.id.textView205, 15);
        sViewsWithIds.put(R.id.addLeave2, 16);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentPendingLeaveBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private FragmentPendingLeaveBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[3]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[16]
            , (android.widget.AutoCompleteTextView) bindings[14]
            , (android.widget.Button) bindings[7]
            , (android.widget.Button) bindings[8]
            , (android.widget.Button) bindings[9]
            , (android.widget.AutoCompleteTextView) bindings[11]
            , (com.airbnb.lottie.LottieAnimationView) bindings[5]
            , (com.airbnb.lottie.LottieAnimationView) bindings[4]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (android.widget.ProgressBar) bindings[6]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[1]
            , (com.google.android.material.textfield.TextInputLayout) bindings[10]
            , (com.google.android.material.textfield.TextInputLayout) bindings[13]
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
package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentApprovedVouchersBindingImpl extends FragmentApprovedVouchersBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.not_available1, 1);
        sViewsWithIds.put(R.id.no_internet, 2);
        sViewsWithIds.put(R.id.approvedVouchersList, 3);
        sViewsWithIds.put(R.id.addLeave, 4);
        sViewsWithIds.put(R.id.textView472, 5);
        sViewsWithIds.put(R.id.textView473, 6);
        sViewsWithIds.put(R.id.textView474, 7);
        sViewsWithIds.put(R.id.textView475, 8);
        sViewsWithIds.put(R.id.addLeave3, 9);
        sViewsWithIds.put(R.id.addLeave6, 10);
        sViewsWithIds.put(R.id.addLeave5, 11);
        sViewsWithIds.put(R.id.addLeave4, 12);
        sViewsWithIds.put(R.id.progressBar2, 13);
        sViewsWithIds.put(R.id.textView77, 14);
        sViewsWithIds.put(R.id.button24, 15);
        sViewsWithIds.put(R.id.button25, 16);
        sViewsWithIds.put(R.id.button26, 17);
        sViewsWithIds.put(R.id.tl1, 18);
        sViewsWithIds.put(R.id.company, 19);
        sViewsWithIds.put(R.id.textView204, 20);
        sViewsWithIds.put(R.id.tl2, 21);
        sViewsWithIds.put(R.id.branch, 22);
        sViewsWithIds.put(R.id.textView205, 23);
        sViewsWithIds.put(R.id.addLeave2, 24);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentApprovedVouchersBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 25, sIncludes, sViewsWithIds));
    }
    private FragmentApprovedVouchersBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[4]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[24]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[9]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[12]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[11]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            , (android.widget.AutoCompleteTextView) bindings[22]
            , (android.widget.Button) bindings[15]
            , (android.widget.Button) bindings[16]
            , (android.widget.Button) bindings[17]
            , (android.widget.AutoCompleteTextView) bindings[19]
            , (com.airbnb.lottie.LottieAnimationView) bindings[2]
            , (com.airbnb.lottie.LottieAnimationView) bindings[1]
            , (android.widget.ProgressBar) bindings[13]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[14]
            , (com.google.android.material.textfield.TextInputLayout) bindings[18]
            , (com.google.android.material.textfield.TextInputLayout) bindings[21]
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
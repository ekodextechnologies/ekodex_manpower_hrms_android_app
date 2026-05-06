package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentVoucherSummaryBindingImpl extends FragmentVoucherSummaryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.button24, 1);
        sViewsWithIds.put(R.id.button25, 2);
        sViewsWithIds.put(R.id.button26, 3);
        sViewsWithIds.put(R.id.attendance_report_list, 4);
        sViewsWithIds.put(R.id.not_available1, 5);
        sViewsWithIds.put(R.id.textView310, 6);
        sViewsWithIds.put(R.id.textView312, 7);
        sViewsWithIds.put(R.id.textView323, 8);
        sViewsWithIds.put(R.id.textView398, 9);
        sViewsWithIds.put(R.id.textView467, 10);
        sViewsWithIds.put(R.id.textView311, 11);
        sViewsWithIds.put(R.id.download_report, 12);
        sViewsWithIds.put(R.id.progressBar, 13);
        sViewsWithIds.put(R.id.tl1, 14);
        sViewsWithIds.put(R.id.company, 15);
        sViewsWithIds.put(R.id.textView204, 16);
        sViewsWithIds.put(R.id.tl2, 17);
        sViewsWithIds.put(R.id.branch, 18);
        sViewsWithIds.put(R.id.tl3, 19);
        sViewsWithIds.put(R.id.gang, 20);
        sViewsWithIds.put(R.id.textView205, 21);
        sViewsWithIds.put(R.id.textView206, 22);
        sViewsWithIds.put(R.id.addLeave2, 23);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentVoucherSummaryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private FragmentVoucherSummaryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[23]
            , (androidx.recyclerview.widget.RecyclerView) bindings[4]
            , (android.widget.AutoCompleteTextView) bindings[18]
            , (android.widget.Button) bindings[1]
            , (android.widget.Button) bindings[2]
            , (android.widget.Button) bindings[3]
            , (android.widget.AutoCompleteTextView) bindings[15]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[12]
            , (android.widget.AutoCompleteTextView) bindings[20]
            , (com.airbnb.lottie.LottieAnimationView) bindings[5]
            , (android.widget.ProgressBar) bindings[13]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (com.google.android.material.textfield.TextInputLayout) bindings[14]
            , (com.google.android.material.textfield.TextInputLayout) bindings[17]
            , (com.google.android.material.textfield.TextInputLayout) bindings[19]
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
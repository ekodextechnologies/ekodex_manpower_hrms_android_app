package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentEditJobPostBindingImpl extends FragmentEditJobPostBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView92, 1);
        sViewsWithIds.put(R.id.textView97, 2);
        sViewsWithIds.put(R.id.textView103, 3);
        sViewsWithIds.put(R.id.textView100, 4);
        sViewsWithIds.put(R.id.textView121, 5);
        sViewsWithIds.put(R.id.textView105, 6);
        sViewsWithIds.put(R.id.textView107, 7);
        sViewsWithIds.put(R.id.textView109, 8);
        sViewsWithIds.put(R.id.textView115, 9);
        sViewsWithIds.put(R.id.textView94, 10);
        sViewsWithIds.put(R.id.textView101, 11);
        sViewsWithIds.put(R.id.textView104, 12);
        sViewsWithIds.put(R.id.textView102, 13);
        sViewsWithIds.put(R.id.textView123, 14);
        sViewsWithIds.put(R.id.textView106, 15);
        sViewsWithIds.put(R.id.textView108, 16);
        sViewsWithIds.put(R.id.textView110, 17);
        sViewsWithIds.put(R.id.textView112, 18);
        sViewsWithIds.put(R.id.editTextTextPersonName57, 19);
        sViewsWithIds.put(R.id.editTextTextPersonName59, 20);
        sViewsWithIds.put(R.id.editTextTextPersonName60, 21);
        sViewsWithIds.put(R.id.editTextTextPersonName63, 22);
        sViewsWithIds.put(R.id.tl1, 23);
        sViewsWithIds.put(R.id.job_category, 24);
        sViewsWithIds.put(R.id.emp_type, 25);
        sViewsWithIds.put(R.id.work_approach, 26);
        sViewsWithIds.put(R.id.experince, 27);
        sViewsWithIds.put(R.id.seekBar, 28);
        sViewsWithIds.put(R.id.textView114, 29);
        sViewsWithIds.put(R.id.button6, 30);
        sViewsWithIds.put(R.id.editTextTextPersonName70, 31);
        sViewsWithIds.put(R.id.editTextTextPersonName71, 32);
        sViewsWithIds.put(R.id.editTextTextPersonName72, 33);
        sViewsWithIds.put(R.id.textView72, 34);
        sViewsWithIds.put(R.id.textView117, 35);
        sViewsWithIds.put(R.id.textView119, 36);
        sViewsWithIds.put(R.id.textView113, 37);
        sViewsWithIds.put(R.id.textView118, 38);
        sViewsWithIds.put(R.id.textView120, 39);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentEditJobPostBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 40, sIncludes, sViewsWithIds));
    }
    private FragmentEditJobPostBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[30]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[21]
            , (android.widget.EditText) bindings[22]
            , (android.widget.EditText) bindings[31]
            , (android.widget.EditText) bindings[32]
            , (android.widget.EditText) bindings[33]
            , (com.google.android.material.chip.ChipGroup) bindings[25]
            , (com.google.android.material.chip.ChipGroup) bindings[27]
            , (android.widget.AutoCompleteTextView) bindings[24]
            , (android.widget.SeekBar) bindings[28]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[37]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[35]
            , (android.widget.TextView) bindings[38]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[39]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[2]
            , (com.google.android.material.textfield.TextInputLayout) bindings[23]
            , (com.google.android.material.chip.ChipGroup) bindings[26]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
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
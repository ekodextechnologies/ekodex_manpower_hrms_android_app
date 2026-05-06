package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAttendanceSupervisorBindingImpl extends FragmentAttendanceSupervisorBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView188, 1);
        sViewsWithIds.put(R.id.textView197, 2);
        sViewsWithIds.put(R.id.textView198, 3);
        sViewsWithIds.put(R.id.textView314, 4);
        sViewsWithIds.put(R.id.textView196, 5);
        sViewsWithIds.put(R.id.textView486, 6);
        sViewsWithIds.put(R.id.textView488, 7);
        sViewsWithIds.put(R.id.textView313, 8);
        sViewsWithIds.put(R.id.button20, 9);
        sViewsWithIds.put(R.id.button28, 10);
        sViewsWithIds.put(R.id.editTextTextPersonName77, 11);
        sViewsWithIds.put(R.id.button18, 12);
        sViewsWithIds.put(R.id.button19, 13);
        sViewsWithIds.put(R.id.button17, 14);
        sViewsWithIds.put(R.id.button27, 15);
        sViewsWithIds.put(R.id.tl1, 16);
        sViewsWithIds.put(R.id.emp_name, 17);
        sViewsWithIds.put(R.id.tl2, 18);
        sViewsWithIds.put(R.id.attendance_status, 19);
        sViewsWithIds.put(R.id.textView203, 20);
        sViewsWithIds.put(R.id.button24, 21);
        sViewsWithIds.put(R.id.multipleItemSelectionSpinner, 22);
        sViewsWithIds.put(R.id.progressBar4, 23);
        sViewsWithIds.put(R.id.imageView51, 24);
        sViewsWithIds.put(R.id.checkin_time_edit, 25);
        sViewsWithIds.put(R.id.checkout_time_edit, 26);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentAttendanceSupervisorBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private FragmentAttendanceSupervisorBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.AutoCompleteTextView) bindings[19]
            , (android.widget.Button) bindings[14]
            , (android.widget.Button) bindings[12]
            , (android.widget.Button) bindings[13]
            , (android.widget.Button) bindings[9]
            , (android.widget.Button) bindings[21]
            , (android.widget.Button) bindings[15]
            , (android.widget.Button) bindings[10]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[26]
            , (android.widget.EditText) bindings[11]
            , (android.widget.AutoCompleteTextView) bindings[17]
            , (android.widget.ImageView) bindings[24]
            , (com.androidbuts.multispinnerfilter.MultiSpinnerSearch) bindings[22]
            , (android.widget.ProgressBar) bindings[23]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (com.google.android.material.textfield.TextInputLayout) bindings[16]
            , (com.google.android.material.textfield.TextInputLayout) bindings[18]
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
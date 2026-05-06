package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAddLeaveBindingImpl extends FragmentAddLeaveBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tl1, 1);
        sViewsWithIds.put(R.id.leave_type, 2);
        sViewsWithIds.put(R.id.multipleItemSelectionSpinner, 3);
        sViewsWithIds.put(R.id.textView52, 4);
        sViewsWithIds.put(R.id.textView521, 5);
        sViewsWithIds.put(R.id.textView499, 6);
        sViewsWithIds.put(R.id.textView54, 7);
        sViewsWithIds.put(R.id.textView519, 8);
        sViewsWithIds.put(R.id.textView57, 9);
        sViewsWithIds.put(R.id.textView60, 10);
        sViewsWithIds.put(R.id.textView53, 11);
        sViewsWithIds.put(R.id.textView55, 12);
        sViewsWithIds.put(R.id.textView58, 13);
        sViewsWithIds.put(R.id.textView61, 14);
        sViewsWithIds.put(R.id.button4, 15);
        sViewsWithIds.put(R.id.button35, 16);
        sViewsWithIds.put(R.id.button5, 17);
        sViewsWithIds.put(R.id.button6, 18);
        sViewsWithIds.put(R.id.editTextTextPersonName56, 19);
        sViewsWithIds.put(R.id.editTextTextPersonName57, 20);
        sViewsWithIds.put(R.id.cardView19, 21);
        sViewsWithIds.put(R.id.textView500, 22);
        sViewsWithIds.put(R.id.textView502, 23);
        sViewsWithIds.put(R.id.textView501, 24);
        sViewsWithIds.put(R.id.cardView18, 25);
        sViewsWithIds.put(R.id.textView509, 26);
        sViewsWithIds.put(R.id.textView510, 27);
        sViewsWithIds.put(R.id.textView511, 28);
        sViewsWithIds.put(R.id.cardView23, 29);
        sViewsWithIds.put(R.id.textView512, 30);
        sViewsWithIds.put(R.id.textView513, 31);
        sViewsWithIds.put(R.id.textView514, 32);
        sViewsWithIds.put(R.id.cardView24, 33);
        sViewsWithIds.put(R.id.textView515, 34);
        sViewsWithIds.put(R.id.textView516, 35);
        sViewsWithIds.put(R.id.textView517, 36);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentAddLeaveBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 37, sIncludes, sViewsWithIds));
    }
    private FragmentAddLeaveBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[16]
            , (android.widget.Button) bindings[15]
            , (android.widget.Button) bindings[17]
            , (android.widget.Button) bindings[18]
            , (androidx.cardview.widget.CardView) bindings[25]
            , (androidx.cardview.widget.CardView) bindings[21]
            , (androidx.cardview.widget.CardView) bindings[29]
            , (androidx.cardview.widget.CardView) bindings[33]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[20]
            , (android.widget.AutoCompleteTextView) bindings[2]
            , (com.androidbuts.multispinnerfilter.SingleSpinnerSearch) bindings[3]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[32]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[35]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[14]
            , (com.google.android.material.textfield.TextInputLayout) bindings[1]
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
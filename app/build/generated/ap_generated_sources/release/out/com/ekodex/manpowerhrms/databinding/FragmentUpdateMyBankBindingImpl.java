package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentUpdateMyBankBindingImpl extends FragmentUpdateMyBankBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.button23, 1);
        sViewsWithIds.put(R.id.textView209, 2);
        sViewsWithIds.put(R.id.textView221, 3);
        sViewsWithIds.put(R.id.textView222, 4);
        sViewsWithIds.put(R.id.textView223, 5);
        sViewsWithIds.put(R.id.textView224, 6);
        sViewsWithIds.put(R.id.textView225, 7);
        sViewsWithIds.put(R.id.textView226, 8);
        sViewsWithIds.put(R.id.textView227, 9);
        sViewsWithIds.put(R.id.textView228, 10);
        sViewsWithIds.put(R.id.textView220, 11);
        sViewsWithIds.put(R.id.textView229, 12);
        sViewsWithIds.put(R.id.textView230, 13);
        sViewsWithIds.put(R.id.textView231, 14);
        sViewsWithIds.put(R.id.editTextTextPersonName81, 15);
        sViewsWithIds.put(R.id.editTextTextPersonName83, 16);
        sViewsWithIds.put(R.id.editTextTextPersonName84, 17);
        sViewsWithIds.put(R.id.editTextTextPersonName85, 18);
        sViewsWithIds.put(R.id.editTextTextPersonName86, 19);
        sViewsWithIds.put(R.id.editTextTextPersonName87, 20);
        sViewsWithIds.put(R.id.editTextTextPersonName88, 21);
        sViewsWithIds.put(R.id.editTextTextPersonName89, 22);
        sViewsWithIds.put(R.id.editTextTextPersonName90, 23);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentUpdateMyBankBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private FragmentUpdateMyBankBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[1]
            , (android.widget.EditText) bindings[15]
            , (android.widget.EditText) bindings[16]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[18]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[21]
            , (android.widget.EditText) bindings[22]
            , (android.widget.EditText) bindings[23]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[14]
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
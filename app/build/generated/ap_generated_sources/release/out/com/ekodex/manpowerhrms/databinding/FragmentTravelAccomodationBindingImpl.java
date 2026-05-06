package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentTravelAccomodationBindingImpl extends FragmentTravelAccomodationBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView160, 1);
        sViewsWithIds.put(R.id.textView178, 2);
        sViewsWithIds.put(R.id.textView179, 3);
        sViewsWithIds.put(R.id.textView180, 4);
        sViewsWithIds.put(R.id.textView181, 5);
        sViewsWithIds.put(R.id.textView182, 6);
        sViewsWithIds.put(R.id.textView183, 7);
        sViewsWithIds.put(R.id.textView184, 8);
        sViewsWithIds.put(R.id.textView185, 9);
        sViewsWithIds.put(R.id.textView187, 10);
        sViewsWithIds.put(R.id.textView186, 11);
        sViewsWithIds.put(R.id.textView170, 12);
        sViewsWithIds.put(R.id.textView171, 13);
        sViewsWithIds.put(R.id.textView172, 14);
        sViewsWithIds.put(R.id.textView173, 15);
        sViewsWithIds.put(R.id.textView174, 16);
        sViewsWithIds.put(R.id.textView175, 17);
        sViewsWithIds.put(R.id.textView177, 18);
        sViewsWithIds.put(R.id.textView176, 19);
        sViewsWithIds.put(R.id.textView161, 20);
        sViewsWithIds.put(R.id.button4, 21);
        sViewsWithIds.put(R.id.button5, 22);
        sViewsWithIds.put(R.id.view4, 23);
        sViewsWithIds.put(R.id.view5, 24);
        sViewsWithIds.put(R.id.textView111, 25);
        sViewsWithIds.put(R.id.textView116, 26);
        sViewsWithIds.put(R.id.textView169, 27);
        sViewsWithIds.put(R.id.editTextTextPersonName66, 28);
        sViewsWithIds.put(R.id.editTextTextPersonName68, 29);
        sViewsWithIds.put(R.id.editTextTextPersonName69, 30);
        sViewsWithIds.put(R.id.editTextTextPersonName73, 31);
        sViewsWithIds.put(R.id.editTextTextPersonName74, 32);
        sViewsWithIds.put(R.id.editTextTextPersonName76, 33);
        sViewsWithIds.put(R.id.editTextTextPersonName75, 34);
        sViewsWithIds.put(R.id.editTextTextPersonName57, 35);
        sViewsWithIds.put(R.id.button6, 36);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentTravelAccomodationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 37, sIncludes, sViewsWithIds));
    }
    private FragmentTravelAccomodationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[21]
            , (android.widget.Button) bindings[22]
            , (android.widget.Button) bindings[36]
            , (android.widget.EditText) bindings[35]
            , (android.widget.EditText) bindings[28]
            , (android.widget.EditText) bindings[29]
            , (android.widget.EditText) bindings[30]
            , (android.widget.EditText) bindings[31]
            , (android.widget.EditText) bindings[32]
            , (android.widget.EditText) bindings[34]
            , (android.widget.EditText) bindings[33]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[10]
            , (android.view.View) bindings[23]
            , (android.view.View) bindings[24]
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
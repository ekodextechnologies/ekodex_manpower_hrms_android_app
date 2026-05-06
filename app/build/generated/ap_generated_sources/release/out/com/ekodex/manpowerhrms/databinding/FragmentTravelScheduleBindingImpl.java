package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentTravelScheduleBindingImpl extends FragmentTravelScheduleBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.button16, 1);
        sViewsWithIds.put(R.id.textView134, 2);
        sViewsWithIds.put(R.id.textView162, 3);
        sViewsWithIds.put(R.id.textView163, 4);
        sViewsWithIds.put(R.id.textView164, 5);
        sViewsWithIds.put(R.id.textView165, 6);
        sViewsWithIds.put(R.id.textView167, 7);
        sViewsWithIds.put(R.id.textView158, 8);
        sViewsWithIds.put(R.id.textView159, 9);
        sViewsWithIds.put(R.id.textView160, 10);
        sViewsWithIds.put(R.id.textView161, 11);
        sViewsWithIds.put(R.id.textView166, 12);
        sViewsWithIds.put(R.id.textView168, 13);
        sViewsWithIds.put(R.id.groupSize, 14);
        sViewsWithIds.put(R.id.radioButton6, 15);
        sViewsWithIds.put(R.id.radioButton, 16);
        sViewsWithIds.put(R.id.editTextTextPersonName60, 17);
        sViewsWithIds.put(R.id.editTextTextPersonName66, 18);
        sViewsWithIds.put(R.id.editTextTextPersonName67, 19);
        sViewsWithIds.put(R.id.button4, 20);
        sViewsWithIds.put(R.id.button5, 21);
        sViewsWithIds.put(R.id.view4, 22);
        sViewsWithIds.put(R.id.view5, 23);
        sViewsWithIds.put(R.id.textView111, 24);
        sViewsWithIds.put(R.id.textView116, 25);
        sViewsWithIds.put(R.id.textView169, 26);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentTravelScheduleBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private FragmentTravelScheduleBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[1]
            , (android.widget.Button) bindings[20]
            , (android.widget.Button) bindings[21]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[18]
            , (android.widget.EditText) bindings[19]
            , (android.widget.RadioGroup) bindings[14]
            , (android.widget.RadioButton) bindings[16]
            , (android.widget.RadioButton) bindings[15]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[26]
            , (android.view.View) bindings[22]
            , (android.view.View) bindings[23]
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
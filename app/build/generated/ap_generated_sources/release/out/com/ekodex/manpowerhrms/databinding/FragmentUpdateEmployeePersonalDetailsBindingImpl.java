package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentUpdateEmployeePersonalDetailsBindingImpl extends FragmentUpdateEmployeePersonalDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView447, 1);
        sViewsWithIds.put(R.id.textView459, 2);
        sViewsWithIds.put(R.id.textView460, 3);
        sViewsWithIds.put(R.id.textView456, 4);
        sViewsWithIds.put(R.id.textView487, 5);
        sViewsWithIds.put(R.id.textView463, 6);
        sViewsWithIds.put(R.id.textView518, 7);
        sViewsWithIds.put(R.id.textView545, 8);
        sViewsWithIds.put(R.id.textView462, 9);
        sViewsWithIds.put(R.id.textView461, 10);
        sViewsWithIds.put(R.id.textView481, 11);
        sViewsWithIds.put(R.id.button23, 12);
        sViewsWithIds.put(R.id.editTextTextPersonName78, 13);
        sViewsWithIds.put(R.id.editTextTextPersonName91, 14);
        sViewsWithIds.put(R.id.editTextTextPersonName92, 15);
        sViewsWithIds.put(R.id.editTextTextPersonName93, 16);
        sViewsWithIds.put(R.id.editTextTextPersonName94, 17);
        sViewsWithIds.put(R.id.editTextTextPersonName95, 18);
        sViewsWithIds.put(R.id.editTextTextPersonName96, 19);
        sViewsWithIds.put(R.id.editTextTextPersonName118, 20);
        sViewsWithIds.put(R.id.editTextTextPersonName117, 21);
        sViewsWithIds.put(R.id.gender, 22);
        sViewsWithIds.put(R.id.male, 23);
        sViewsWithIds.put(R.id.female, 24);
        sViewsWithIds.put(R.id.tl1, 25);
        sViewsWithIds.put(R.id.emp_designation, 26);
        sViewsWithIds.put(R.id.button24, 27);
        sViewsWithIds.put(R.id.button31, 28);
        sViewsWithIds.put(R.id.textView235, 29);
        sViewsWithIds.put(R.id.textView424, 30);
        sViewsWithIds.put(R.id.editTextTextPersonName116, 31);
        sViewsWithIds.put(R.id.textView425, 32);
        sViewsWithIds.put(R.id.addDoc, 33);
        sViewsWithIds.put(R.id.addDoc2, 34);
        sViewsWithIds.put(R.id.textView208, 35);
        sViewsWithIds.put(R.id.textView339, 36);
        sViewsWithIds.put(R.id.aadharList, 37);
        sViewsWithIds.put(R.id.panList, 38);
        sViewsWithIds.put(R.id.editTextTextPersonName99, 39);
        sViewsWithIds.put(R.id.editTextTextPersonName100, 40);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentUpdateEmployeePersonalDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 41, sIncludes, sViewsWithIds));
    }
    private FragmentUpdateEmployeePersonalDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[37]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[33]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[34]
            , (android.widget.Button) bindings[12]
            , (android.widget.Button) bindings[27]
            , (android.widget.Button) bindings[28]
            , (android.widget.EditText) bindings[40]
            , (android.widget.EditText) bindings[31]
            , (android.widget.EditText) bindings[21]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[13]
            , (android.widget.EditText) bindings[14]
            , (android.widget.EditText) bindings[15]
            , (android.widget.EditText) bindings[16]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[18]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[39]
            , (android.widget.AutoCompleteTextView) bindings[26]
            , (android.widget.RadioButton) bindings[24]
            , (android.widget.RadioGroup) bindings[22]
            , (android.widget.RadioButton) bindings[23]
            , (androidx.recyclerview.widget.RecyclerView) bindings[38]
            , (android.widget.TextView) bindings[35]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[32]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (com.google.android.material.textfield.TextInputLayout) bindings[25]
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
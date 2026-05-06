package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentUpdateEmployeeDocumentBindingImpl extends FragmentUpdateEmployeeDocumentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView195, 1);
        sViewsWithIds.put(R.id.textView199, 2);
        sViewsWithIds.put(R.id.textView200, 3);
        sViewsWithIds.put(R.id.textView201, 4);
        sViewsWithIds.put(R.id.textView316, 5);
        sViewsWithIds.put(R.id.textView210, 6);
        sViewsWithIds.put(R.id.textView202, 7);
        sViewsWithIds.put(R.id.textView208, 8);
        sViewsWithIds.put(R.id.textView339, 9);
        sViewsWithIds.put(R.id.textView340, 10);
        sViewsWithIds.put(R.id.textView341, 11);
        sViewsWithIds.put(R.id.textView342, 12);
        sViewsWithIds.put(R.id.textView343, 13);
        sViewsWithIds.put(R.id.textView193, 14);
        sViewsWithIds.put(R.id.button21, 15);
        sViewsWithIds.put(R.id.editTextTextPersonName99, 16);
        sViewsWithIds.put(R.id.editTextTextPersonName100, 17);
        sViewsWithIds.put(R.id.editTextTextPersonName101, 18);
        sViewsWithIds.put(R.id.editTextTextPersonName102, 19);
        sViewsWithIds.put(R.id.editTextTextPersonName103, 20);
        sViewsWithIds.put(R.id.editTextTextPersonName104, 21);
        sViewsWithIds.put(R.id.editTextTextPersonName82, 22);
        sViewsWithIds.put(R.id.tl2, 23);
        sViewsWithIds.put(R.id.emp_doc_type, 24);
        sViewsWithIds.put(R.id.addDoc, 25);
        sViewsWithIds.put(R.id.addDoc2, 26);
        sViewsWithIds.put(R.id.addDoc3, 27);
        sViewsWithIds.put(R.id.addDoc4, 28);
        sViewsWithIds.put(R.id.addDoc5, 29);
        sViewsWithIds.put(R.id.addDoc6, 30);
        sViewsWithIds.put(R.id.aadharList, 31);
        sViewsWithIds.put(R.id.panList, 32);
        sViewsWithIds.put(R.id.uanList, 33);
        sViewsWithIds.put(R.id.pfList, 34);
        sViewsWithIds.put(R.id.esisList, 35);
        sViewsWithIds.put(R.id.passportList, 36);
        sViewsWithIds.put(R.id.progressBar5, 37);
        sViewsWithIds.put(R.id.button32, 38);
        sViewsWithIds.put(R.id.button33, 39);
        sViewsWithIds.put(R.id.button34, 40);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentUpdateEmployeeDocumentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 41, sIncludes, sViewsWithIds));
    }
    private FragmentUpdateEmployeeDocumentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[31]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[25]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[26]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[27]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[28]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[29]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[30]
            , (android.widget.Button) bindings[15]
            , (android.widget.Button) bindings[38]
            , (android.widget.Button) bindings[39]
            , (android.widget.Button) bindings[40]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[18]
            , (android.widget.EditText) bindings[19]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[21]
            , (android.widget.EditText) bindings[22]
            , (android.widget.EditText) bindings[16]
            , (android.widget.AutoCompleteTextView) bindings[24]
            , (androidx.recyclerview.widget.RecyclerView) bindings[35]
            , (androidx.recyclerview.widget.RecyclerView) bindings[32]
            , (androidx.recyclerview.widget.RecyclerView) bindings[36]
            , (androidx.recyclerview.widget.RecyclerView) bindings[34]
            , (android.widget.ProgressBar) bindings[37]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            , (com.google.android.material.textfield.TextInputLayout) bindings[23]
            , (androidx.recyclerview.widget.RecyclerView) bindings[33]
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
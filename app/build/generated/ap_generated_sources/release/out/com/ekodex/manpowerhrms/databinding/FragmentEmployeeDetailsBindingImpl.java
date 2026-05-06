package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentEmployeeDetailsBindingImpl extends FragmentEmployeeDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cardView15, 1);
        sViewsWithIds.put(R.id.textView211, 2);
        sViewsWithIds.put(R.id.textView298, 3);
        sViewsWithIds.put(R.id.textView212, 4);
        sViewsWithIds.put(R.id.textView213, 5);
        sViewsWithIds.put(R.id.textView214, 6);
        sViewsWithIds.put(R.id.textView215, 7);
        sViewsWithIds.put(R.id.textView299, 8);
        sViewsWithIds.put(R.id.imageView50, 9);
        sViewsWithIds.put(R.id.checkBox, 10);
        sViewsWithIds.put(R.id.docs_layout, 11);
        sViewsWithIds.put(R.id.doc_list, 12);
        sViewsWithIds.put(R.id.cardView16, 13);
        sViewsWithIds.put(R.id.textView216, 14);
        sViewsWithIds.put(R.id.textView217, 15);
        sViewsWithIds.put(R.id.textView218, 16);
        sViewsWithIds.put(R.id.textView219, 17);
        sViewsWithIds.put(R.id.imageView52, 18);
        sViewsWithIds.put(R.id.cardView17, 19);
        sViewsWithIds.put(R.id.textView301, 20);
        sViewsWithIds.put(R.id.textView302, 21);
        sViewsWithIds.put(R.id.textView303, 22);
        sViewsWithIds.put(R.id.textView304, 23);
        sViewsWithIds.put(R.id.textView305, 24);
        sViewsWithIds.put(R.id.textView306, 25);
        sViewsWithIds.put(R.id.imageView56, 26);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentEmployeeDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private FragmentEmployeeDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.cardview.widget.CardView) bindings[1]
            , (androidx.cardview.widget.CardView) bindings[13]
            , (androidx.cardview.widget.CardView) bindings[19]
            , (android.widget.CheckBox) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[12]
            , (androidx.cardview.widget.CardView) bindings[11]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[26]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[25]
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
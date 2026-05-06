package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentEditMyProfileBindingImpl extends FragmentEditMyProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.textView31, 1);
        sViewsWithIds.put(R.id.textView122, 2);
        sViewsWithIds.put(R.id.textView194, 3);
        sViewsWithIds.put(R.id.textView129, 4);
        sViewsWithIds.put(R.id.textView130, 5);
        sViewsWithIds.put(R.id.textView131, 6);
        sViewsWithIds.put(R.id.textView132, 7);
        sViewsWithIds.put(R.id.textView133, 8);
        sViewsWithIds.put(R.id.textView136, 9);
        sViewsWithIds.put(R.id.textView32, 10);
        sViewsWithIds.put(R.id.textView137, 11);
        sViewsWithIds.put(R.id.textView33, 12);
        sViewsWithIds.put(R.id.textView34, 13);
        sViewsWithIds.put(R.id.textView35, 14);
        sViewsWithIds.put(R.id.textView36, 15);
        sViewsWithIds.put(R.id.textView37, 16);
        sViewsWithIds.put(R.id.textView40, 17);
        sViewsWithIds.put(R.id.textView41, 18);
        sViewsWithIds.put(R.id.textView42, 19);
        sViewsWithIds.put(R.id.textView237, 20);
        sViewsWithIds.put(R.id.textView239, 21);
        sViewsWithIds.put(R.id.textView238, 22);
        sViewsWithIds.put(R.id.textView240, 23);
        sViewsWithIds.put(R.id.textView138, 24);
        sViewsWithIds.put(R.id.textView142, 25);
        sViewsWithIds.put(R.id.textView154, 26);
        sViewsWithIds.put(R.id.textView241, 27);
        sViewsWithIds.put(R.id.textView242, 28);
        sViewsWithIds.put(R.id.textView243, 29);
        sViewsWithIds.put(R.id.textView244, 30);
        sViewsWithIds.put(R.id.textView245, 31);
        sViewsWithIds.put(R.id.textView246, 32);
        sViewsWithIds.put(R.id.textView247, 33);
        sViewsWithIds.put(R.id.textView248, 34);
        sViewsWithIds.put(R.id.textView249, 35);
        sViewsWithIds.put(R.id.textView250, 36);
        sViewsWithIds.put(R.id.textView251, 37);
        sViewsWithIds.put(R.id.textView252, 38);
        sViewsWithIds.put(R.id.textView253, 39);
        sViewsWithIds.put(R.id.textView254, 40);
        sViewsWithIds.put(R.id.textView255, 41);
        sViewsWithIds.put(R.id.textView256, 42);
        sViewsWithIds.put(R.id.textView257, 43);
        sViewsWithIds.put(R.id.textView258, 44);
        sViewsWithIds.put(R.id.textView259, 45);
        sViewsWithIds.put(R.id.textView260, 46);
        sViewsWithIds.put(R.id.textView261, 47);
        sViewsWithIds.put(R.id.textView262, 48);
        sViewsWithIds.put(R.id.textView263, 49);
        sViewsWithIds.put(R.id.textView264, 50);
        sViewsWithIds.put(R.id.textView265, 51);
        sViewsWithIds.put(R.id.textView266, 52);
        sViewsWithIds.put(R.id.textView267, 53);
        sViewsWithIds.put(R.id.textView268, 54);
        sViewsWithIds.put(R.id.textView269, 55);
        sViewsWithIds.put(R.id.textView270, 56);
        sViewsWithIds.put(R.id.textView271, 57);
        sViewsWithIds.put(R.id.textView272, 58);
        sViewsWithIds.put(R.id.textView273, 59);
        sViewsWithIds.put(R.id.textView274, 60);
        sViewsWithIds.put(R.id.textView275, 61);
        sViewsWithIds.put(R.id.textView276, 62);
        sViewsWithIds.put(R.id.textView277, 63);
        sViewsWithIds.put(R.id.textView278, 64);
        sViewsWithIds.put(R.id.textView279, 65);
        sViewsWithIds.put(R.id.textView280, 66);
        sViewsWithIds.put(R.id.textView281, 67);
        sViewsWithIds.put(R.id.editTextTextPersonName, 68);
        sViewsWithIds.put(R.id.editTextTextPersonName14, 69);
        sViewsWithIds.put(R.id.editTextTextPersonName3, 70);
        sViewsWithIds.put(R.id.editTextTextPersonName13, 71);
        sViewsWithIds.put(R.id.editTextTextPersonName5, 72);
        sViewsWithIds.put(R.id.editTextTextPersonName55, 73);
        sViewsWithIds.put(R.id.editTextTextPersonName4, 74);
        sViewsWithIds.put(R.id.editTextTextPersonName6, 75);
        sViewsWithIds.put(R.id.editTextTextPersonName9, 76);
        sViewsWithIds.put(R.id.editTextTextPersonName10, 77);
        sViewsWithIds.put(R.id.editTextTextPersonName11, 78);
        sViewsWithIds.put(R.id.editTextTextPersonName12, 79);
        sViewsWithIds.put(R.id.editTextTextPersonName17, 80);
        sViewsWithIds.put(R.id.editTextTextPersonName18, 81);
        sViewsWithIds.put(R.id.editTextTextPersonName7, 82);
        sViewsWithIds.put(R.id.editTextTextPersonName8, 83);
        sViewsWithIds.put(R.id.editTextTextPersonName15, 84);
        sViewsWithIds.put(R.id.editTextTextPersonName16, 85);
        sViewsWithIds.put(R.id.editTextTextPersonName19, 86);
        sViewsWithIds.put(R.id.editTextTextPersonName20, 87);
        sViewsWithIds.put(R.id.editTextTextPersonName21, 88);
        sViewsWithIds.put(R.id.editTextTextPersonName22, 89);
        sViewsWithIds.put(R.id.editTextTextPersonName23, 90);
        sViewsWithIds.put(R.id.editTextTextPersonName24, 91);
        sViewsWithIds.put(R.id.editTextTextPersonName25, 92);
        sViewsWithIds.put(R.id.editTextTextPersonName26, 93);
        sViewsWithIds.put(R.id.editTextTextPersonName27, 94);
        sViewsWithIds.put(R.id.editTextTextPersonName28, 95);
        sViewsWithIds.put(R.id.editTextTextPersonName29, 96);
        sViewsWithIds.put(R.id.editTextTextPersonName30, 97);
        sViewsWithIds.put(R.id.editTextTextPersonName31, 98);
        sViewsWithIds.put(R.id.editTextTextPersonName32, 99);
        sViewsWithIds.put(R.id.editTextTextPersonName33, 100);
        sViewsWithIds.put(R.id.editTextTextPersonName34, 101);
        sViewsWithIds.put(R.id.editTextTextPersonName35, 102);
        sViewsWithIds.put(R.id.editTextTextPersonName36, 103);
        sViewsWithIds.put(R.id.editTextTextPersonName37, 104);
        sViewsWithIds.put(R.id.editTextTextPersonName38, 105);
        sViewsWithIds.put(R.id.editTextTextPersonName39, 106);
        sViewsWithIds.put(R.id.editTextTextPersonName40, 107);
        sViewsWithIds.put(R.id.editTextTextPersonName41, 108);
        sViewsWithIds.put(R.id.editTextTextPersonName42, 109);
        sViewsWithIds.put(R.id.editTextTextPersonName43, 110);
        sViewsWithIds.put(R.id.editTextTextPersonName44, 111);
        sViewsWithIds.put(R.id.editTextTextPersonName45, 112);
        sViewsWithIds.put(R.id.editTextTextPersonName46, 113);
        sViewsWithIds.put(R.id.editTextTextPersonName47, 114);
        sViewsWithIds.put(R.id.editTextTextPersonName48, 115);
        sViewsWithIds.put(R.id.editTextTextPersonName49, 116);
        sViewsWithIds.put(R.id.editTextTextPersonName50, 117);
        sViewsWithIds.put(R.id.editTextTextPersonName51, 118);
        sViewsWithIds.put(R.id.editTextTextPersonName52, 119);
        sViewsWithIds.put(R.id.editTextTextPersonName53, 120);
        sViewsWithIds.put(R.id.editTextTextPersonName54, 121);
        sViewsWithIds.put(R.id.images, 122);
        sViewsWithIds.put(R.id.button2, 123);
        sViewsWithIds.put(R.id.view2, 124);
        sViewsWithIds.put(R.id.view31, 125);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentEditMyProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 126, sIncludes, sViewsWithIds));
    }
    private FragmentEditMyProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatButton) bindings[123]
            , (android.widget.EditText) bindings[68]
            , (android.widget.EditText) bindings[77]
            , (android.widget.EditText) bindings[78]
            , (android.widget.EditText) bindings[79]
            , (android.widget.EditText) bindings[71]
            , (android.widget.EditText) bindings[69]
            , (android.widget.EditText) bindings[84]
            , (android.widget.EditText) bindings[85]
            , (android.widget.EditText) bindings[80]
            , (android.widget.EditText) bindings[81]
            , (android.widget.EditText) bindings[86]
            , (android.widget.EditText) bindings[87]
            , (android.widget.EditText) bindings[88]
            , (android.widget.EditText) bindings[89]
            , (android.widget.EditText) bindings[90]
            , (android.widget.EditText) bindings[91]
            , (android.widget.EditText) bindings[92]
            , (android.widget.EditText) bindings[93]
            , (android.widget.EditText) bindings[94]
            , (android.widget.EditText) bindings[95]
            , (android.widget.EditText) bindings[96]
            , (android.widget.EditText) bindings[70]
            , (android.widget.EditText) bindings[97]
            , (android.widget.EditText) bindings[98]
            , (android.widget.EditText) bindings[99]
            , (android.widget.EditText) bindings[100]
            , (android.widget.EditText) bindings[101]
            , (android.widget.EditText) bindings[102]
            , (android.widget.EditText) bindings[103]
            , (android.widget.EditText) bindings[104]
            , (android.widget.EditText) bindings[105]
            , (android.widget.EditText) bindings[106]
            , (android.widget.EditText) bindings[74]
            , (android.widget.EditText) bindings[107]
            , (android.widget.EditText) bindings[108]
            , (android.widget.EditText) bindings[109]
            , (android.widget.EditText) bindings[110]
            , (android.widget.EditText) bindings[111]
            , (android.widget.EditText) bindings[112]
            , (android.widget.EditText) bindings[113]
            , (android.widget.EditText) bindings[114]
            , (android.widget.EditText) bindings[115]
            , (android.widget.EditText) bindings[116]
            , (android.widget.EditText) bindings[72]
            , (android.widget.EditText) bindings[117]
            , (android.widget.EditText) bindings[118]
            , (android.widget.EditText) bindings[119]
            , (android.widget.EditText) bindings[120]
            , (android.widget.EditText) bindings[121]
            , (android.widget.EditText) bindings[73]
            , (android.widget.EditText) bindings[75]
            , (android.widget.EditText) bindings[82]
            , (android.widget.EditText) bindings[83]
            , (android.widget.EditText) bindings[76]
            , (android.widget.ImageView) bindings[122]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[32]
            , (android.widget.TextView) bindings[33]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[35]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[37]
            , (android.widget.TextView) bindings[38]
            , (android.widget.TextView) bindings[39]
            , (android.widget.TextView) bindings[40]
            , (android.widget.TextView) bindings[41]
            , (android.widget.TextView) bindings[42]
            , (android.widget.TextView) bindings[43]
            , (android.widget.TextView) bindings[44]
            , (android.widget.TextView) bindings[45]
            , (android.widget.TextView) bindings[46]
            , (android.widget.TextView) bindings[47]
            , (android.widget.TextView) bindings[48]
            , (android.widget.TextView) bindings[49]
            , (android.widget.TextView) bindings[50]
            , (android.widget.TextView) bindings[51]
            , (android.widget.TextView) bindings[52]
            , (android.widget.TextView) bindings[53]
            , (android.widget.TextView) bindings[54]
            , (android.widget.TextView) bindings[55]
            , (android.widget.TextView) bindings[56]
            , (android.widget.TextView) bindings[57]
            , (android.widget.TextView) bindings[58]
            , (android.widget.TextView) bindings[59]
            , (android.widget.TextView) bindings[60]
            , (android.widget.TextView) bindings[61]
            , (android.widget.TextView) bindings[62]
            , (android.widget.TextView) bindings[63]
            , (android.widget.TextView) bindings[64]
            , (android.widget.TextView) bindings[65]
            , (android.widget.TextView) bindings[66]
            , (android.widget.TextView) bindings[67]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[19]
            , (android.view.View) bindings[124]
            , (android.view.View) bindings[125]
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
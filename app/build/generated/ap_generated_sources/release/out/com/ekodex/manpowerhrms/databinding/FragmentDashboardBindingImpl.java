package com.ekodex.manpowerhrms.databinding;
import com.ekodex.manpowerhrms.R;
import com.ekodex.manpowerhrms.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentDashboardBindingImpl extends FragmentDashboardBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.dash_layout, 1);
        sViewsWithIds.put(R.id.button22, 2);
        sViewsWithIds.put(R.id.download_report, 3);
        sViewsWithIds.put(R.id.textView337, 4);
        sViewsWithIds.put(R.id.textView338, 5);
        sViewsWithIds.put(R.id.textView353, 6);
        sViewsWithIds.put(R.id.textView360, 7);
        sViewsWithIds.put(R.id.textView236, 8);
        sViewsWithIds.put(R.id.textView334, 9);
        sViewsWithIds.put(R.id.textView419, 10);
        sViewsWithIds.put(R.id.textView423, 11);
        sViewsWithIds.put(R.id.textView357, 12);
        sViewsWithIds.put(R.id.textView443, 13);
        sViewsWithIds.put(R.id.textView452, 14);
        sViewsWithIds.put(R.id.textView446, 15);
        sViewsWithIds.put(R.id.textView445, 16);
        sViewsWithIds.put(R.id.textView362, 17);
        sViewsWithIds.put(R.id.textView401, 18);
        sViewsWithIds.put(R.id.textView407, 19);
        sViewsWithIds.put(R.id.textView413, 20);
        sViewsWithIds.put(R.id.textView415, 21);
        sViewsWithIds.put(R.id.textView414, 22);
        sViewsWithIds.put(R.id.textView409, 23);
        sViewsWithIds.put(R.id.textView408, 24);
        sViewsWithIds.put(R.id.textView402, 25);
        sViewsWithIds.put(R.id.textView403, 26);
        sViewsWithIds.put(R.id.textView369, 27);
        sViewsWithIds.put(R.id.textView370, 28);
        sViewsWithIds.put(R.id.textView364, 29);
        sViewsWithIds.put(R.id.textView363, 30);
        sViewsWithIds.put(R.id.textView358, 31);
        sViewsWithIds.put(R.id.textView359, 32);
        sViewsWithIds.put(R.id.textView335, 33);
        sViewsWithIds.put(R.id.textView420, 34);
        sViewsWithIds.put(R.id.textView336, 35);
        sViewsWithIds.put(R.id.textView421, 36);
        sViewsWithIds.put(R.id.textView325, 37);
        sViewsWithIds.put(R.id.textView327, 38);
        sViewsWithIds.put(R.id.textView326, 39);
        sViewsWithIds.put(R.id.textView331, 40);
        sViewsWithIds.put(R.id.textView416, 41);
        sViewsWithIds.put(R.id.textView422, 42);
        sViewsWithIds.put(R.id.textView354, 43);
        sViewsWithIds.put(R.id.textView444, 44);
        sViewsWithIds.put(R.id.textView451, 45);
        sViewsWithIds.put(R.id.textView449, 46);
        sViewsWithIds.put(R.id.textView448, 47);
        sViewsWithIds.put(R.id.textView361, 48);
        sViewsWithIds.put(R.id.textView367, 49);
        sViewsWithIds.put(R.id.textView368, 50);
        sViewsWithIds.put(R.id.textView366, 51);
        sViewsWithIds.put(R.id.textView365, 52);
        sViewsWithIds.put(R.id.textView356, 53);
        sViewsWithIds.put(R.id.textView355, 54);
        sViewsWithIds.put(R.id.textView333, 55);
        sViewsWithIds.put(R.id.textView418, 56);
        sViewsWithIds.put(R.id.textView332, 57);
        sViewsWithIds.put(R.id.textView417, 58);
        sViewsWithIds.put(R.id.cardView3, 59);
        sViewsWithIds.put(R.id.imageView2, 60);
        sViewsWithIds.put(R.id.textView5, 61);
        sViewsWithIds.put(R.id.textView6, 62);
        sViewsWithIds.put(R.id.cardView, 63);
        sViewsWithIds.put(R.id.textView7, 64);
        sViewsWithIds.put(R.id.textView8, 65);
        sViewsWithIds.put(R.id.imageView3, 66);
        sViewsWithIds.put(R.id.cardView5, 67);
        sViewsWithIds.put(R.id.imageView4, 68);
        sViewsWithIds.put(R.id.textView9, 69);
        sViewsWithIds.put(R.id.textView10, 70);
        sViewsWithIds.put(R.id.cardView4, 71);
        sViewsWithIds.put(R.id.imageView5, 72);
        sViewsWithIds.put(R.id.textView11, 73);
        sViewsWithIds.put(R.id.textView12, 74);
        sViewsWithIds.put(R.id.cardView6, 75);
        sViewsWithIds.put(R.id.imageView22, 76);
        sViewsWithIds.put(R.id.textView282, 77);
        sViewsWithIds.put(R.id.textView283, 78);
        sViewsWithIds.put(R.id.cardView7, 79);
        sViewsWithIds.put(R.id.imageView23, 80);
        sViewsWithIds.put(R.id.textView284, 81);
        sViewsWithIds.put(R.id.textView285, 82);
        sViewsWithIds.put(R.id.cardView8, 83);
        sViewsWithIds.put(R.id.imageView24, 84);
        sViewsWithIds.put(R.id.textView286, 85);
        sViewsWithIds.put(R.id.textView287, 86);
        sViewsWithIds.put(R.id.cardView9, 87);
        sViewsWithIds.put(R.id.imageView25, 88);
        sViewsWithIds.put(R.id.textView288, 89);
        sViewsWithIds.put(R.id.textView289, 90);
        sViewsWithIds.put(R.id.cardView10, 91);
        sViewsWithIds.put(R.id.imageView26, 92);
        sViewsWithIds.put(R.id.textView290, 93);
        sViewsWithIds.put(R.id.textView291, 94);
        sViewsWithIds.put(R.id.cardView11, 95);
        sViewsWithIds.put(R.id.imageView27, 96);
        sViewsWithIds.put(R.id.textView292, 97);
        sViewsWithIds.put(R.id.textView293, 98);
        sViewsWithIds.put(R.id.cardView12, 99);
        sViewsWithIds.put(R.id.imageView28, 100);
        sViewsWithIds.put(R.id.textView294, 101);
        sViewsWithIds.put(R.id.textView295, 102);
        sViewsWithIds.put(R.id.cardView13, 103);
        sViewsWithIds.put(R.id.imageView29, 104);
        sViewsWithIds.put(R.id.textView296, 105);
        sViewsWithIds.put(R.id.textView297, 106);
        sViewsWithIds.put(R.id.cardview20, 107);
        sViewsWithIds.put(R.id.imageView41, 108);
        sViewsWithIds.put(R.id.textView65, 109);
        sViewsWithIds.put(R.id.textView66, 110);
        sViewsWithIds.put(R.id.button8, 111);
        sViewsWithIds.put(R.id.cardView14, 112);
        sViewsWithIds.put(R.id.textView69, 113);
        sViewsWithIds.put(R.id.textView68, 114);
        sViewsWithIds.put(R.id.imageView43, 115);
        sViewsWithIds.put(R.id.cardView21, 116);
        sViewsWithIds.put(R.id.textView317, 117);
        sViewsWithIds.put(R.id.textView318, 118);
        sViewsWithIds.put(R.id.cardView20, 119);
        sViewsWithIds.put(R.id.textView321, 120);
        sViewsWithIds.put(R.id.textView319, 121);
        sViewsWithIds.put(R.id.cardView22, 122);
        sViewsWithIds.put(R.id.textView322, 123);
        sViewsWithIds.put(R.id.textView320, 124);
        sViewsWithIds.put(R.id.view6, 125);
        sViewsWithIds.put(R.id.view7, 126);
        sViewsWithIds.put(R.id.view8, 127);
        sViewsWithIds.put(R.id.view9, 128);
        sViewsWithIds.put(R.id.textView328, 129);
        sViewsWithIds.put(R.id.textView330, 130);
        sViewsWithIds.put(R.id.textView329, 131);
        sViewsWithIds.put(R.id.calendarView, 132);
        sViewsWithIds.put(R.id.textView380, 133);
        sViewsWithIds.put(R.id.textView404, 134);
        sViewsWithIds.put(R.id.textView410, 135);
        sViewsWithIds.put(R.id.textView405, 136);
        sViewsWithIds.put(R.id.textView411, 137);
        sViewsWithIds.put(R.id.textView406, 138);
        sViewsWithIds.put(R.id.textView412, 139);
        sViewsWithIds.put(R.id.textView381, 140);
        sViewsWithIds.put(R.id.textView382, 141);
        sViewsWithIds.put(R.id.summaryTable, 142);
        sViewsWithIds.put(R.id.textView484, 143);
        sViewsWithIds.put(R.id.rvNames, 144);
        sViewsWithIds.put(R.id.emplist, 145);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentDashboardBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 146, sIncludes, sViewsWithIds));
    }
    private FragmentDashboardBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[2]
            , (android.widget.Button) bindings[111]
            , (android.widget.CalendarView) bindings[132]
            , (androidx.cardview.widget.CardView) bindings[63]
            , (androidx.cardview.widget.CardView) bindings[91]
            , (androidx.cardview.widget.CardView) bindings[95]
            , (androidx.cardview.widget.CardView) bindings[99]
            , (androidx.cardview.widget.CardView) bindings[103]
            , (androidx.cardview.widget.CardView) bindings[112]
            , (androidx.cardview.widget.CardView) bindings[119]
            , (androidx.cardview.widget.CardView) bindings[116]
            , (androidx.cardview.widget.CardView) bindings[122]
            , (androidx.cardview.widget.CardView) bindings[59]
            , (androidx.cardview.widget.CardView) bindings[71]
            , (androidx.cardview.widget.CardView) bindings[67]
            , (androidx.cardview.widget.CardView) bindings[75]
            , (androidx.cardview.widget.CardView) bindings[79]
            , (androidx.cardview.widget.CardView) bindings[83]
            , (androidx.cardview.widget.CardView) bindings[87]
            , (androidx.cardview.widget.CardView) bindings[107]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[3]
            , (androidx.recyclerview.widget.RecyclerView) bindings[145]
            , (android.widget.ImageView) bindings[60]
            , (android.widget.ImageView) bindings[76]
            , (android.widget.ImageView) bindings[80]
            , (android.widget.ImageView) bindings[84]
            , (android.widget.ImageView) bindings[88]
            , (android.widget.ImageView) bindings[92]
            , (android.widget.ImageView) bindings[96]
            , (android.widget.ImageView) bindings[100]
            , (android.widget.ImageView) bindings[104]
            , (android.widget.ImageView) bindings[66]
            , (android.widget.ImageView) bindings[68]
            , (android.widget.ImageView) bindings[108]
            , (android.widget.ImageView) bindings[115]
            , (android.widget.ImageView) bindings[72]
            , (androidx.recyclerview.widget.RecyclerView) bindings[144]
            , (android.widget.LinearLayout) bindings[142]
            , (android.widget.TextView) bindings[70]
            , (android.widget.TextView) bindings[73]
            , (android.widget.TextView) bindings[74]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[77]
            , (android.widget.TextView) bindings[78]
            , (android.widget.TextView) bindings[81]
            , (android.widget.TextView) bindings[82]
            , (android.widget.TextView) bindings[85]
            , (android.widget.TextView) bindings[86]
            , (android.widget.TextView) bindings[89]
            , (android.widget.TextView) bindings[90]
            , (android.widget.TextView) bindings[93]
            , (android.widget.TextView) bindings[94]
            , (android.widget.TextView) bindings[97]
            , (android.widget.TextView) bindings[98]
            , (android.widget.TextView) bindings[101]
            , (android.widget.TextView) bindings[102]
            , (android.widget.TextView) bindings[105]
            , (android.widget.TextView) bindings[106]
            , (android.widget.TextView) bindings[117]
            , (android.widget.TextView) bindings[118]
            , (android.widget.TextView) bindings[121]
            , (android.widget.TextView) bindings[124]
            , (android.widget.TextView) bindings[120]
            , (android.widget.TextView) bindings[123]
            , (android.widget.TextView) bindings[37]
            , (android.widget.TextView) bindings[39]
            , (android.widget.TextView) bindings[38]
            , (android.widget.TextView) bindings[129]
            , (android.widget.TextView) bindings[131]
            , (android.widget.TextView) bindings[130]
            , (android.widget.TextView) bindings[40]
            , (android.widget.TextView) bindings[57]
            , (android.widget.TextView) bindings[55]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[33]
            , (android.widget.TextView) bindings[35]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[43]
            , (android.widget.TextView) bindings[54]
            , (android.widget.TextView) bindings[53]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[32]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[48]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[52]
            , (android.widget.TextView) bindings[51]
            , (android.widget.TextView) bindings[49]
            , (android.widget.TextView) bindings[50]
            , (android.widget.TextView) bindings[27]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[133]
            , (android.widget.TextView) bindings[140]
            , (android.widget.TextView) bindings[141]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[26]
            , (android.widget.TextView) bindings[134]
            , (android.widget.TextView) bindings[136]
            , (android.widget.TextView) bindings[138]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[135]
            , (android.widget.TextView) bindings[137]
            , (android.widget.TextView) bindings[139]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[41]
            , (android.widget.TextView) bindings[58]
            , (android.widget.TextView) bindings[56]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[42]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[44]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[47]
            , (android.widget.TextView) bindings[46]
            , (android.widget.TextView) bindings[45]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[143]
            , (android.widget.TextView) bindings[61]
            , (android.widget.TextView) bindings[62]
            , (android.widget.TextView) bindings[109]
            , (android.widget.TextView) bindings[110]
            , (android.widget.TextView) bindings[114]
            , (android.widget.TextView) bindings[113]
            , (android.widget.TextView) bindings[64]
            , (android.widget.TextView) bindings[65]
            , (android.widget.TextView) bindings[69]
            , (android.view.View) bindings[125]
            , (android.view.View) bindings[126]
            , (android.view.View) bindings[127]
            , (android.view.View) bindings[128]
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
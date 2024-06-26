package org.example.diploma.database.pump


import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "pumps")
data class PumpEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val pumpId: Long?,

    //"Pump pulse duration, us="
    val tp: Double?,

    //"Pump power, W="
    var wp: Double?,

    //"Pump coupling optics efficiency="
    val hc: Double?,

    //"Pump reflectivity="
    val rp: Double?,
//    <com.google.android.material.textfield.TextInputLayout
//android:layout_width="300dp"
//android:layout_height="wrap_content"
//android:hint="@string/cavity_length_cm"
//app:endIconMode="clear_text">
//
//<com.google.android.material.textfield.TextInputEditText
//android:id="@+id/rp_input"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:text="@{viewModel.pumpData.rp != null ? String.valueOf(viewModel.pumpData.rp) : ``}" />
//</com.google.android.material.textfield.TextInputLayout>

    //"Pump wavelength, nm="
    val lp: Double?,

    //"Pump scheme type="(End|Side)
    @ColumnInfo(name = "scheme_id")
    val scheme: Int?,

    //"Pump type="(Pulsed pump|CW pump)
    @ColumnInfo(name = "ptype_id")
    val ptypeId: Int?,

    //"Pump form type="(Rectangular|Trapeze|Varying)
    @ColumnInfo(name = "pform_id")
    val pformId: Int?,

    //Для трапеции
    //"t1, us=   "

    val t1p: Double?,
    //"t2, us=   "
    val t2p: Double?,

    @ColumnInfo(name = "pform_text")
    val pformText: String?

    //Varying(pformt)

)

package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var mBotton = findViewById<FloatingActionButton>(R.id.btnCreate);
        mBotton.setOnClickListener(View.OnClickListener() {
            showBottomSheetDialog()
        })
    }
    fun showBottomSheetDialog() {
        var bottomSheetDialog = BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.fragment_bottom);
        var createAuto = bottomSheetDialog.findViewById<LinearLayout>(R.id.createAuto);
        var createManual = bottomSheetDialog.findViewById<LinearLayout>(R.id.createManual);

        if (createAuto != null) {
            createAuto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, "Create Auto", Toast.LENGTH_LONG).show();
            })
        }
        if (createManual != null) {
            createManual.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, "Create Manual", Toast.LENGTH_LONG).show();
            })
        }
        bottomSheetDialog.show();
    }

}
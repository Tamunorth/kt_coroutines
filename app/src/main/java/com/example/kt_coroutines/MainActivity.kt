package com.example.kt_coroutines

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var button: Button

    var customProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.center_btn);

        button.setOnClickListener {

            showProgressDialog();

            ///A kind of scope used to launch a suspend function
            lifecycleScope.launch {

                execute("Task Executed");

            }


        }

    }


    private suspend fun execute(result: String) {


        ///Moves an operation into a different thread and when it's complete move it back
        withContext(Dispatchers.IO) {
            for (i in 1..2000) {
                Log.e("delay", "" + i)
            }

            runOnUiThread {
                hideDialog()

                ///This is a foreground task and must be run on the UI thread not the IO thread
                Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()

            }
        }


    }

    private fun hideDialog() {

        if (customProgressDialog != null) {
            customProgressDialog?.dismiss();
            customProgressDialog = null
        }
    }

    private fun showProgressDialog() {


        customProgressDialog = Dialog(this@MainActivity)
        customProgressDialog?.setContentView(R.layout.custom_dialog);
        customProgressDialog?.show();

    }

}
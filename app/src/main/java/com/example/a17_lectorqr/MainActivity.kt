package com.example.a17_lectorqr

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a17_lectorqr.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private var activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)

        if (intentResult != null) {
            if (result.resultCode == Activity.RESULT_OK &&intentResult.contents == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, intentResult.contents, Toast.LENGTH_LONG).show()
            }
        }

    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.but.setOnClickListener { initScanner() }



    }

    private fun initScanner() {
        val integrator= IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("escaneo ")
        integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
      /*  integrator.initiateScan()*/
        activityResultLauncher.launch(integrator.createScanIntent())

    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result=IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result!=null){
            if(result.contents==null){
                Toast.makeText(this,"cancelado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"el valor es ${result.contents}",Toast.LENGTH_SHORT).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }*/
}
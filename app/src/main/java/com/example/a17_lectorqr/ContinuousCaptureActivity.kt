package com.example.a17_lectorqr

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.a17_lectorqr.databinding.ActivityContinuousCaptureBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory


class ContinuousCaptureActivity : AppCompatActivity() {
    private lateinit var binding:ActivityContinuousCaptureBinding
    private lateinit var beepManager: BeepManager
    private var lastText: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityContinuousCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formats: Collection<BarcodeFormat> = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        binding.barcodeScanner.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        binding.barcodeScanner.initializeFromIntent(intent)
        binding.barcodeScanner.decodeContinuous(callback)
        beepManager = BeepManager(this)
        binding.pausa.setOnClickListener { onPause()}
        binding.resumen.setOnClickListener { onResume()}
    }

    private val callback:BarcodeCallback= object:BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == lastText) {
                return
            }

            binding.barcodeScanner.setStatusText(result.text)
            beepManager.playBeepSoundAndVibrate()
            binding.barcodePreview.setImageBitmap(result.getBitmapWithResultPoints(Color.RED))
            /*binding.barcodeScanner.pause()*/
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }



    override fun onResume() {
        binding.barcodeScanner.resume()
        super.onResume()
    }

    override fun onPause() {
        binding.barcodeScanner.pause()
        super.onPause()

    }




    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return  binding.barcodeScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}
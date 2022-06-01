/*
    Ali Darwiche S00051281
    Bashayer Abdulkareem S00050555
    Zeinab Deris S00053357
 */
package com.example.guessme

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_fruits.*
import java.util.*
import kotlin.collections.ArrayList

private var score = 0
private var selection: Int = -1
private var imagesMap = Hashtable<String, Int>()
private var images = ArrayList<String>()
private var image = ""
private val request_code = 100
private var speech: SpeechRecognizer? = null
private var SpeechIntent: Intent? = null
private var mSensorManager: SensorManager? = null
private var mAccelerometer: Sensor? = null
private var mShakeDetector: ShakeDetector? = null

class Fruits : AppCompatActivity(), RecognitionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruits)

        imagesMap = Hashtable<String, Int>()
        images = ArrayList<String>()

        selection = intent.getIntExtra("category_index", 0)
        setUpImages()

        score = 0

        speech = SpeechRecognizer.createSpeechRecognizer(this)
        speech!!.setRecognitionListener(this)
        SpeechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        SpeechIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        SpeechIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        GuessingMic.setOnClickListener{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), request_code)
        }

        // ShakeDetector initialization
        // ShakeDetector initialization
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector!!.setOnShakeListener(object : ShakeDetector.OnShakeListener {
            override fun onShake(count: Int) {
                /*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                GuessingAnswer.text = image
                //CategoryResult.text = "Given Up"
                Toast.makeText(applicationContext, "Given Up", Toast.LENGTH_LONG).show()
                GNextButton.isClickable = true
            }
        })
    }

    fun onClick(view: View) {
        if(GNextButton.text.equals("START")) {
            GNextButton.text = "NEXT"
            GuessingScore.text = "Score: " + score
        }
        randomize()
        GuessingAnswer.text = "What Am I?"
        //FruitsResult.text = "Result"
        GNextButton.isClickable = false
    }

    fun setUpImages() {
        when(selection) {
            0 -> {
                GuessingImageView.setImageResource(R.drawable.animals)
                imagesMap.put("cat", R.drawable.cat)
                images.add("cat")
                imagesMap.put("cow", R.drawable.cat)
                images.add("cow")
                imagesMap.put("dog", R.drawable.dog)
                images.add("dog")
                imagesMap.put("duck", R.drawable.duck)
                images.add("duck")
                imagesMap.put("elephant", R.drawable.elephant)
                images.add("elephant")
                imagesMap.put("lion", R.drawable.lion)
                images.add("lion")
                imagesMap.put("lizard", R.drawable.lizard)
                images.add("lizard")
                imagesMap.put("monkey", R.drawable.monkey)
                images.add("monkey")
                imagesMap.put("panda", R.drawable.panda)
                images.add("panda")
                imagesMap.put("rabbit", R.drawable.rabbit)
                images.add("rabbit")
                imagesMap.put("snake", R.drawable.snake)
                images.add("snake")
                imagesMap.put("zebra", R.drawable.zebra)
                images.add("zebra")
            }
            1 -> {
                GuessingImageView.setImageResource(R.drawable.fruits)
                imagesMap.put("apple", R.drawable.apple)
                images.add("apple")
                imagesMap.put("banana", R.drawable.banana)
                images.add("banana")
                imagesMap.put("blueberry", R.drawable.blueberry)
                images.add("blueberry")
                imagesMap.put("coconut", R.drawable.coconut)
                images.add("coconut")
                imagesMap.put("grapes", R.drawable.grapes)
                images.add("grapes")
                imagesMap.put("kiwi", R.drawable.kiwi)
                images.add("kiwi")
                imagesMap.put("mango", R.drawable.mango)
                images.add("mango")
                imagesMap.put("orange", R.drawable.orange)
                images.add("orange")
                imagesMap.put("pineapple", R.drawable.pineapple)
                images.add("pineapple")
                imagesMap.put("strawberry", R.drawable.strawberry)
                images.add("strawberry")
                imagesMap.put("watermelon", R.drawable.watermelon)
                images.add("watermelon")
            }
            2 -> {
                GuessingImageView.setImageResource(R.drawable.num)
                imagesMap.put("8", R.drawable.eight)
                images.add("8")
                imagesMap.put("5", R.drawable.five)
                images.add("5")
                imagesMap.put("4", R.drawable.four)
                images.add("4")
                imagesMap.put("9", R.drawable.nine)
                images.add("9")
                imagesMap.put("1", R.drawable.one)
                images.add("1")
                imagesMap.put("7", R.drawable.seven)
                images.add("7")
                imagesMap.put("6", R.drawable.six)
                images.add("6")
                imagesMap.put("10", R.drawable.ten)
                images.add("10")
                imagesMap.put("2", R.drawable.two)
                images.add("2")
                imagesMap.put("3", R.drawable.three)
                images.add("3")
            }
        }
    }

    fun randomize() {
        val rand = java.util.Random()
        val index = rand.nextInt(images.size)
        image = images[index]

        GuessingImageView.setImageResource(imagesMap.get(image)!!)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            request_code ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speech!!.startListening(SpeechIntent)
                }
                else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager!!.registerListener(
            mShakeDetector,
            mAccelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onPause() {
        super.onPause()
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager!!.unregisterListener(mShakeDetector)
    }

    override fun onStop() {
        super.onStop()
        if (speech != null) {
            speech!!.destroy()
        }
    }

    override fun onReadyForSpeech(p0: Bundle?) {
    }

    override fun onBeginningOfSpeech() {
    }

    override fun onRmsChanged(p0: Float) {
    }

    override fun onBufferReceived(p0: ByteArray?) {
    }

    override fun onEndOfSpeech() {
        speech!!.stopListening()
    }

    override fun onError(p0: Int) {
        var message = ""
        when (p0) {
            SpeechRecognizer.ERROR_AUDIO -> message = "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> message = "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> message = "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> message = "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> message = "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> message = "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> message = "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> message = "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> message = "No speech input"
            else -> message = "Didn't understand, please try again."
        }
        GuessingAnswer.text = message
    }

    override fun onResults(p0: Bundle?) {
        val matches: ArrayList<String>? = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        GuessingAnswer.text = matches?.get(0)?.toLowerCase()
        if(matches?.get(0)?.toLowerCase().equals(image)) {
            score++
            GuessingScore.text = "Score: " + score
            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show()
            //FruitsResult.text = "Correct"
            GNextButton.isClickable = true
        }
        else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show()
            //FruitsResult.text = "Incorrect"
        }
    }

    override fun onPartialResults(p0: Bundle?) {
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
    }
}
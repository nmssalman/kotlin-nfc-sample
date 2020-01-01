package com.salman.nfcreader

import android.R.id.message
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcF
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_write_data.*


class WriteData : AppCompatActivity() {
    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(NfcF::class.java.name))
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_write_data)

        btnback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //nfc process start
        pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndef.addDataType("text/plain")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }
        intentFiltersArray = arrayOf(ndef)
        if (nfcAdapter == null) {
            val builder = AlertDialog.Builder(this@WriteData, R.style.MyAlertDialogStyle)
            builder.setMessage("This device doesn't support NFC.")
            builder.setPositiveButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(false)
            myDialog.show()
           // txttext.setText("THIS DEVICE DOESN'T SUPPORT NFC. PLEASE TRY WITH ANOTHER DEVICE!")
        } else if (!nfcAdapter!!.isEnabled) {
            val builder = AlertDialog.Builder(this@WriteData, R.style.MyAlertDialogStyle)
            builder.setTitle("NFC Disabled")
            builder.setMessage("Plesae Enable NFC")
           // txttext.setText("NFC IS NOT ENABLED. PLEASE ENABLE NFC IN SETTINGS->NFC")
            builder.setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
            builder.setNegativeButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(false)
            myDialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
try {

    if(!txtmachineid.text.toString().equals("") && !txtshopid.text.toString().equals("") ) {



        val shopid=txtshopid.text.toString()
        val machineid=txtmachineid.text.toString()

        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action
            || NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action
        ) {

            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return
            val ndef = Ndef.get(tag) ?: return

            if (ndef.isWritable) {

               var message = NdefMessage(
                    arrayOf(
                        NdefRecord.createTextRecord("en", shopid),
                        NdefRecord.createTextRecord("en", machineid)
//                        NdefRecord.createTextRecord("en", userid)

                    )
                )


                ndef.connect()
                ndef.writeNdefMessage(message)
                ndef.close()


                Toast.makeText(applicationContext, "Successfully Wroted!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    else
    {
        Toast.makeText(applicationContext, "Write on text box!", Toast.LENGTH_SHORT).show()
    }
}
catch (Ex:Exception)
{
    Toast.makeText(applicationContext, Ex.message, Toast.LENGTH_SHORT).show()
}




    }

    override fun onPause() {
        if (this.isFinishing) {
            nfcAdapter?.disableForegroundDispatch(this)
        }
        super.onPause()
    }
}

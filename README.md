# kotlin-nfc-sample
NFC NDEF Reader and Writer. there are two separate activities for reading and writing.

# Steps
1. Clone the Project
2. Open with Android Studio

# Description

1. There are two activities contain in the project.

Main Activity -: Reading Data
Write Data -: Write Data

2. There are no external dependancies required

# Manifest file should be like below

  ```
 <?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.salman.nfcreader">

    <uses-permission android:name="android.permission.NFC" />
    <uses-permission android:name="android.permission.VIBRATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".WriteData">
            <intent-filter>
                <action android:name="android.nfc.action.NDEF_DISCOVERED" />
                <category android:name="android.intent.category.DEFAULT" />
                <action android:name="android.nfc.action.TAG_DISCOVERED" />
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <data android:mimeType="text/plain" />
            </intent-filter>

            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.nfc.action.NDEF_DISCOVERED" />
                <category android:name="android.intent.category.DEFAULT" />
                <action android:name="android.nfc.action.TAG_DISCOVERED" />
                <action android:name="android.nfc.action.TECH_DISCOVERED" />
                <data android:mimeType="text/plain" />
            </intent-filter>

            <meta-data
                android:name="android.nfc.action.TECH_DISCOVERED"
                android:resource="@xml/nfc_tech_filter" />
        </activity>
    </application>

</manifest>

  ```

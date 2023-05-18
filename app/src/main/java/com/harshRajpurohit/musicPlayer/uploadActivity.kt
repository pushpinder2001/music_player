package com.harshRajpurohit.musicPlayer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.harshRajpurohit.musicPlayer.databinding.ActivitySettingsBinding
import java.util.UUID

class uploadActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding
    private lateinit var selectButton: Button
    private lateinit var uploadButton: Button

    private lateinit var storageReference: StorageReference
    private lateinit var audioFilePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "UPLOAD"

        selectButton = findViewById(R.id.select_button)
        uploadButton = findViewById(R.id.upload_button)

        selectButton.setOnClickListener {
            selectAudioFile()
        }

        uploadButton.setOnClickListener {
            uploadAudioFile()
        }

        storageReference = FirebaseStorage.getInstance().getReference("audios")
    }

    private fun selectAudioFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "audio/*"
        startActivityForResult(intent, 1)
    }

    private fun uploadAudioFile() {
        val fileName = UUID.randomUUID().toString()
        val ref = storageReference.child("$fileName.mp3")
        ref.putFile(audioFilePath)
            .addOnSuccessListener {
                Toast.makeText(this, "Audio file uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to upload audio file", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            audioFilePath = data.data!!
        }
    }
}

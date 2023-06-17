package com.example.pdfapplication


import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.parser.Line
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvPlace: TextView
    private lateinit var tvDateOfBirth: TextView
    private lateinit var tvOccupation: TextView
    private lateinit var tvSalary: TextView
    private lateinit var tvCompany: TextView
    private lateinit var btnConvertToPdf: Button
    private lateinit var layout:LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvName = findViewById(R.id.tvName)
        tvPlace = findViewById(R.id.tvPlace)
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth)
        tvOccupation = findViewById(R.id.tvOccupation)
        tvSalary = findViewById(R.id.tvSalary)
        tvCompany = findViewById(R.id.tvCompany)
        btnConvertToPdf = findViewById(R.id.btnConvertToPdf)
        layout= findViewById(R.id.print)

        // Set data in TextViews
        tvName.text = "John Doe"
        tvPlace.text = "City"
        tvDateOfBirth.text = "01/01/1990"
        tvOccupation.text = "Engineer"
        tvSalary.text = "$1000"
        tvCompany.text = "ABC Company"

        btnConvertToPdf.setOnClickListener {
            convertViewsToPdf()
        }
    }

    private fun convertViewsToPdf() {
        // Create a PDF file
        val pdfFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "bill.pdf"
        )

        try {
            // Convert TextViews to PDF
            val document = Document()
            PdfWriter.getInstance(document, FileOutputStream(pdfFile))
            document.open()

            val view = layout


                val bitmap = viewToBitmap(view)
                val image = Image.getInstance(bitmapToByteArray(bitmap))
                image.scaleToFit(PageSize.A4.width, PageSize.A4.height)
                document.add(image)

            document.close()

            Toast.makeText(
                this,
                "PDF downloaded successfully",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Failed to convert views to PDF",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}


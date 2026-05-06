package com.ekodex.manpowerhrms

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentUploadWorkOrderBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import org.json.JSONException
import org.json.JSONObject

class UploadWorkOrderFragment : Fragment() {

    lateinit var binding: FragmentUploadWorkOrderBinding
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_upload_work_order,
            container,
            false
        )

        // Select image from gallery
        binding.upload.setOnClickListener {
            //for image
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        // Extract text button
        binding.button2.setOnClickListener {
            if (selectedImageUri != null) {
                // extractTextFromImage(selectedImageUri!!)
                extractTextFromImage(selectedImageUri!!)
            } else {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    // Get image from gallery
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            selectedImageUri = result.data!!.data
            // Glide.with(requireContext()).load(selectedImageUri).into(binding.imageView)
        }
    }

    //most latest
    /*  private fun extractTextFromImage(uri: Uri) {
          try {
              val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
              val image = InputImage.fromFilePath(requireContext(), uri)

              recognizer.process(image)
                  .addOnSuccessListener { visionText ->
                      // 1) Collect tokens (elements) with bounding box info
                      data class Token(val text: String, val left: Int, val right: Int, val centerY: Int, val height: Int)

                      val tokens = mutableListOf<Token>()
                      for (block in visionText.textBlocks) {
                          for (line in block.lines) {
                              for (element in line.elements) {
                                  val box = element.boundingBox
                                  if (box != null) {
                                      val centerY = box.top + box.height() / 2
                                      tokens.add(Token(element.text, box.left, box.right, centerY, box.height()))
                                  } else {
                                      tokens.add(Token(element.text, 0, 0, 0, 0))
                                  }
                              }
                          }
                      }

                      // 2) Sort tokens by vertical position (centerY)
                      tokens.sortWith(compareBy({ it.centerY }, { it.left }))

                      // 3) Group tokens into lines by vertical proximity
                      val lines = mutableListOf<MutableList<Token>>()
                      var currentLine = mutableListOf<Token>()
                      var lastY = tokens.first().centerY
                      val avgTokenHeight = (tokens.map { it.height }.filter { it > 0 }.average().takeIf { !it.isNaN() } ?: 20.0)

                      // threshold: tokens with centerY within (height * 0.6) are considered same line
                      val yThreshold = (avgTokenHeight * 0.6).toInt().coerceAtLeast(8)

                      for (token in tokens) {
                          if (currentLine.isEmpty()) {
                              currentLine.add(token)
                              lastY = token.centerY
                          } else {
                              if (Math.abs(token.centerY - lastY) <= yThreshold) {
                                  currentLine.add(token)
                                  // update lastY to average to better handle slight slopes
                                  lastY = (lastY + token.centerY) / 2
                              } else {
                                  // finish current line and start new one
                                  lines.add(currentLine)
                                  currentLine = mutableListOf(token)
                                  lastY = token.centerY
                              }
                          }
                      }
                      if (currentLine.isNotEmpty()) lines.add(currentLine)

                      // 4) For each line: sort by left and join tokens. Add spaces based on horizontal gap.
                      val sb = StringBuilder()
                      for (lineTokens in lines) {
                          lineTokens.sortBy { it.left }
                          if (lineTokens.isEmpty()) continue

                          // Optional: decide when to insert space vs no space based on gap fraction of average height
                          val gapThreshold = (avgTokenHeight * 0.35) // tune this value to your documents

                          var prevRight = lineTokens.first().right
                          sb.append(lineTokens.first().text)

                          for (i in 1 until lineTokens.size) {
                              val tk = lineTokens[i]
                              val gap = (tk.left - prevRight).toFloat()
                              if (gap > gapThreshold) {
                                  sb.append(" ")
                              }
                              sb.append(tk.text)
                              prevRight = Math.max(prevRight, tk.right)
                          }
                          sb.append("\n")
                      }

                      // 5) Result (string)
                      val finalText = sb.toString()
                      insertTextInDb(finalText)
                      Log.i("OCR", finalText)
                  }
                  .addOnFailureListener { e ->
                      Log.i("OCR", e.message.toString())
                  }
          } catch (e: Exception) {
              Log.i("OCR", e.message.toString())
          }
      }*/

    private fun insertTextInDb(raw_txt: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_INSER_WORK_ORDER,
            Response.Listener { response ->
                try {
                    // 🔹 Log the complete JSON response as-is
                    Log.i("11111", "Response: $response")

                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }

                } catch (e: JSONException) {
                    
                    Log.e("11111", "JSON Parse Error: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                val errMsg = error.message ?: "Unknown network error"
                Log.e("WorkOrderResponse", "Volley Error: $errMsg")
                Toast.makeText(requireContext(), errMsg, Toast.LENGTH_SHORT).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["raw_text"] = raw_txt   // ✅ must match your PHP script parameter
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    // Extract text using ML Kit
    /*  private fun extractTextFromImage(uri: Uri)
      {
          try {
              val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
              val image = InputImage.fromFilePath(this, uri)

              recognizer.process(image)
                  .addOnSuccessListener { visionText ->
                      val linesList = mutableListOf<Pair<String, Int>>() // (text, y-coordinate)

                      for (block in visionText.textBlocks) {
                          for (line in block.lines) {
                              val yPos = line.boundingBox?.top ?: 0
                              linesList.add(Pair(line.text, yPos))
                          }
                      }

                      // Sort lines by their vertical position
                      linesList.sortBy { it.second }

                      val finalText = buildString {
                          for ((text, _) in linesList) {
                              append(text).append("\n")
                          }
                      }

                      binding.textResult.text = finalText
                      Log.i("11111", finalText)
                  }
                  .addOnFailureListener { e ->
                      binding.textResult.text = "Error: ${e.message}"
                  }

          } catch (e: Exception) {
              binding.textResult.text = "Failed to process image: ${e.message}"
          }
      }*/

    private fun extractTextFromImage(uri: Uri) {
        try {
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromFilePath(requireContext(), uri)

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    // --- Step 1: Collect tokens with bounding boxes ---
                    data class Token(val text: String, val left: Int, val right: Int, val centerY: Int, val height: Int)
                    val tokens = mutableListOf<Token>()
                    for (block in visionText.textBlocks) {
                        for (line in block.lines) {
                            for (element in line.elements) {
                                val box = element.boundingBox
                                val centerY = box?.top?.plus(box.height() / 2) ?: 0
                                tokens.add(Token(element.text, box?.left ?: 0, box?.right ?: 0, centerY, box?.height() ?: 0))
                            }
                        }
                    }

                    // --- Step 2: Sort tokens vertically and horizontally ---
                    tokens.sortWith(compareBy({ it.centerY }, { it.left }))

                    // --- Step 3: Group tokens into lines ---
                    val lines = mutableListOf<MutableList<Token>>()
                    var currentLine = mutableListOf<Token>()
                    var lastY = tokens.first().centerY
                    val avgTokenHeight = (tokens.map { it.height }.filter { it > 0 }.average().takeIf { !it.isNaN() } ?: 20.0)
                    val yThreshold = (avgTokenHeight * 0.6).toInt().coerceAtLeast(8)

                    for (token in tokens) {
                        if (currentLine.isEmpty()) {
                            currentLine.add(token)
                            lastY = token.centerY
                        } else {
                            if (Math.abs(token.centerY - lastY) <= yThreshold) {
                                currentLine.add(token)
                                lastY = (lastY + token.centerY) / 2
                            } else {
                                lines.add(currentLine)
                                currentLine = mutableListOf(token)
                                lastY = token.centerY
                            }
                        }
                    }
                    if (currentLine.isNotEmpty()) lines.add(currentLine)

                    // --- Step 4: Reconstruct lines as text (optional, mostly for debugging) ---
                    val reconstructedLines = lines.map { lineTokens ->
                        lineTokens.sortedBy { it.left }.joinToString(" ") { it.text }
                    }

                    // --- Step 5: Find table header ---
                    val tableHeaderKeywords = listOf("ContainerNo", "SealNo", "Weight", "PKGS", "EquipmentName")
                    val headerIndex = reconstructedLines.indexOfFirst { line ->
                        tableHeaderKeywords.any { keyword -> line.contains(keyword, ignoreCase = true) }
                    }

                    if (headerIndex == -1) {
                        Log.i("11122",reconstructedLines.joinToString("\n"))
                        return@addOnSuccessListener
                    }

                    val headerTokens = lines[headerIndex].sortedBy { it.left }
                    val headers = headerTokens.map { it.text }

                    // --- Step 6: Calculate column boundaries ---
                    data class Column(val name: String, val left: Int, val right: Int)
                    val columns = mutableListOf<Column>()
                    for (i in headers.indices) {
                        val left = headerTokens[i].left
                        val right = if (i < headerTokens.size - 1) headerTokens[i + 1].left else Int.MAX_VALUE
                        columns.add(Column(headers[i], left, right))
                    }

                    // --- Step 7: Parse rows ---
                    val tableRows = mutableListOf<Map<String, String>>()
                    for (i in (headerIndex + 1) until lines.size) {
                        val lineTokens = lines[i].sortedBy { it.left }
                        if (lineTokens.isEmpty()) continue

                        val rowMap = mutableMapOf<String, String>()
                        for (col in columns) {
                            val cellTokens = lineTokens.filter { it.left >= col.left && it.left < col.right }
                            rowMap[col.name] = cellTokens.joinToString(" ") { it.text }.ifEmpty { "" }
                        }
                        // Skip completely empty rows
                        if (rowMap.values.any { it.isNotEmpty() }) tableRows.add(rowMap)
                    }

                    // --- Step 8: Separate non-table lines ---
                    val nonTableLines = reconstructedLines.subList(0, headerIndex)

                    // --- Step 9: Build final output ---
                    val sb = StringBuilder()
                    sb.append("=== NON TABLE TEXT ===\n")
                    sb.append(nonTableLines.joinToString("\n"))
                    sb.append("\n\n=== TABLE DATA ===\n")
                    tableRows.forEachIndexed { idx, row ->
                        sb.append("${idx + 1}: ")
                        sb.append(headers.joinToString(" | ") { header -> row[header] ?: "" })
                        sb.append("\n")
                    }

                    Log.i("OCR_TABLE", sb.toString())
                }
                .addOnFailureListener { e ->
                    Log.i("11111",e.message.toString())
                }
        } catch (e: Exception) {
            Log.i("11111",e.message.toString())
        }
    }



}
package com.libopenmw.android.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.libopenmw.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val pickDirectoryButton: Button = binding.pickDirectoryButton
        val selectedDirectoryTextView: TextView = binding.selectedDirectoryTextView

        pickDirectoryButton.setOnClickListener {
            openDirectoryPicker()
        }

        return root
    }

    private val directoryPickerActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedDirectoryUri = result.data?.data
                selectedDirectoryUri?.let { uri ->
                    val selectedDirectoryPath = uri.path
                    val selectedDirectoryTextView: TextView = binding.selectedDirectoryTextView
                    selectedDirectoryTextView.text = selectedDirectoryPath
                    selectedDirectoryTextView.visibility = View.VISIBLE
                }
            }
        }

    private fun openDirectoryPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        directoryPickerActivityResult.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.trkkr.trkkrclean.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trkkr.trkkrclean.R
import com.trkkr.trkkrclean.databinding.MiniPoiModalBottomSheetBinding

class MiniPoiDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "MiniPoiDialogFragment"
    }
    private lateinit var binding: MiniPoiModalBottomSheetBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MiniPoiModalBottomSheetBinding.inflate(layoutInflater)

        return inflater.inflate(R.layout.mini_poi_modal_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miniPoiImage.setOnClickListener {
            Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
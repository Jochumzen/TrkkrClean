package com.trkkr.trkkrclean

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

        bottomSheetBehavior = BottomSheetBehavior.from(binding.miniPoiSheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle onslide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(context, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(context, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(context, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(context, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(context, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(context, "OTHER_STATE", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return inflater.inflate(R.layout.mini_poi_modal_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miniPoiImage.setOnClickListener {
            Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
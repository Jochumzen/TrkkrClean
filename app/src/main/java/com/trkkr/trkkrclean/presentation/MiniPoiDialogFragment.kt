package com.trkkr.trkkrclean.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trkkr.trkkrclean.R
import com.trkkr.trkkrclean.databinding.MiniPoiModalBottomSheetBinding
import java.net.URL
import java.net.URLEncoder

class MiniPoiDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "MiniPoiDialogFragment"
    }

    private val mapViewModel: MapViewModel by activityViewModels()

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapViewModel.miniPoi.observe(viewLifecycleOwner, {

            binding.miniPoiImage.setImageURI(Uri.parse(it.images?.get(0)))
            binding.poiInfoName.text = it.name
            binding.poiInfoTypeAndDistance.text = it.category + it.distance
            binding.poiInfoOpeningHours.text = "Open: " + it.open
        })

        binding.miniPoiImage.setOnClickListener {
            Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
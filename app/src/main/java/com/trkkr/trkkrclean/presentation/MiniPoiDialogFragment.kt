package com.trkkr.trkkrclean.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
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

    private var _binding: MiniPoiModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*_binding = MiniPoiModalBottomSheetBinding.inflate(inflater, container, false).apply {

            mapViewModel.miniPoi.observe(viewLifecycleOwner, {

                miniPoiImage.setImageURI(Uri.parse(it.images?.get(0)))
                poiInfoName.text = it.name
                poiInfoTypeAndDistance.text = it.category + it.distance
                poiInfoOpeningHours.text = "Open: " + it.open
            })

            miniPoiImage.setOnClickListener {
                Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show()
            }
        }*/

        Log.d("MyDebug", "running onCreateView in: $MiniPoiDialogFragment")

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
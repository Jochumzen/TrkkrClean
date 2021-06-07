package com.trkkr.trkkrclean.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mapbox.mapboxsdk.maps.Style.*
import com.trkkr.trkkrclean.R
import com.trkkr.trkkrclean.databinding.DialogMapStylesBinding
import com.trkkr.trkkrclean.presentation.MapViewModel
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("StaticFieldLeak")
private var _binding: DialogMapStylesBinding? = null
private val binding get() = _binding!!

@AndroidEntryPoint
class MapStyleDialogFragment : DialogFragment(R.layout.dialog_map_styles) {

    private val mapViewModel: MapViewModel by activityViewModels()

    private val defaultStyle = "mapbox://styles/mapbox/streets-v11"
    private val satelliteStyle = "mapbox://styles/mapbox/satellite-v9"
    private val trafficStyle = "mapbox://styles/mapbox/traffic-day-v2"
    private val walkingStyle = "mapbox://styles/mapbox/outdoors-v11"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMapStylesBinding.inflate(inflater, container, false).apply {
            mapboxStreetsStyleId.setOnClickListener { setWhiteColorOnButtons(); mapViewModel.updateMapBoxStyle(MAPBOX_STREETS) }
            satelliteStyleId.setOnClickListener { setWhiteColorOnButtons(); mapViewModel.updateMapBoxStyle(SATELLITE) }
            trafficLightStyleId.setOnClickListener { setWhiteColorOnButtons(); mapViewModel.updateMapBoxStyle(TRAFFIC_DAY) }
            outdoorsStyleId.setOnClickListener { setWhiteColorOnButtons(); mapViewModel.updateMapBoxStyle(OUTDOORS) }
        }

        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setWindow()

        // For checking which style is used initially
        when (mapViewModel.mapBoxStyle.value) {
            null -> setBlueColorOnButtonAndTv(binding.mapboxStreetsStyleId, binding.textMapLayoutDefault)
            defaultStyle -> setBlueColorOnButtonAndTv(binding.mapboxStreetsStyleId, binding.textMapLayoutDefault)
            satelliteStyle -> setBlueColorOnButtonAndTv(binding.satelliteStyleId, binding.textMapLayoutSatellite)
            trafficStyle -> setBlueColorOnButtonAndTv(binding.trafficLightStyleId, binding.textMapLayoutTraffic)
            walkingStyle -> setBlueColorOnButtonAndTv(binding.outdoorsStyleId, binding.textMapLayoutWalking)
        }

        // For checking when pressing another style button
        mapViewModel.mapBoxStyle.observe(viewLifecycleOwner, {
            when (it) {
                defaultStyle -> setBlueColorOnButtonAndTv(binding.mapboxStreetsStyleId, binding.textMapLayoutDefault)
                satelliteStyle -> setBlueColorOnButtonAndTv(binding.satelliteStyleId, binding.textMapLayoutSatellite)
                trafficStyle -> setBlueColorOnButtonAndTv(binding.trafficLightStyleId, binding.textMapLayoutTraffic)
                walkingStyle -> setBlueColorOnButtonAndTv(binding.outdoorsStyleId, binding.textMapLayoutWalking)

                else -> setBlueColorOnButtonAndTv(binding.mapboxStreetsStyleId, binding.textMapLayoutDefault)
            }
        })


    }

    private fun setWindow() {
        val window = dialog?.window
        window?.setGravity(Gravity.BOTTOM or Gravity.END)
        val layoutParams = window?.attributes
        layoutParams?.x = 150
        layoutParams?.y = 200
        window?.attributes = layoutParams
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setBlueColorOnButtonAndTv(usedView: ImageView?, usedTv: TextView?) {
        usedView?.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.button_border)
        usedTv?.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setWhiteColorOnButtons() {
        binding.mapboxStreetsStyleId.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.button_border_white)
        binding.textMapLayoutDefault.setTextColor(ContextCompat.getColor(requireContext(), R.color.originalTextColor))

        binding.satelliteStyleId.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.button_border_white)
        binding.textMapLayoutSatellite.setTextColor(ContextCompat.getColor(requireContext(), R.color.originalTextColor))

        binding.trafficLightStyleId.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.button_border_white)
        binding.textMapLayoutTraffic.setTextColor(ContextCompat.getColor(requireContext(), R.color.originalTextColor))

        binding.outdoorsStyleId.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.button_border_white)
        binding.textMapLayoutWalking.setTextColor(ContextCompat.getColor(requireContext(), R.color.originalTextColor))
    }

    companion object { const val TAG = "MapStyleDialog" }
}
package com.trkkr.trkkrclean

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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.Style.*
import com.trkkr.trkkrclean.databinding.DialogMapStylesBinding
import dagger.hilt.android.AndroidEntryPoint


private var _binding: DialogMapStylesBinding? = null
private val binding get() = _binding!!

@AndroidEntryPoint
class MapStyleDialogFragment : DialogFragment(R.layout.dialog_map_styles) {

    private val mapViewModel: MapViewModel by activityViewModels()

    private val defaultText: TextView? = null
    private  var satelliteText:TextView? = null
    private  var trafficText:TextView? = null
    private  var walkingText:TextView? = null

    private var defaultBtn:  ImageView? = null
    private var satelliteBtn: ImageView? = null
    private var trafficBtn: ImageView? = null
    private var walkingBtn: ImageView? = null

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
            satelliteStyleId.setOnClickListener {
                mapViewModel.updateMapBoxStyle(SATELLITE)
            }
        }

        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        defaultBtn = binding.mapboxStreetsStyleId
        satelliteBtn = binding.satelliteStyleId
        trafficBtn = binding.trafficLightStyleId
        walkingBtn = binding.outdoorsStyleId

        defaultBtn!!.setOnClickListener { mapViewModel.updateMapBoxStyle(MAPBOX_STREETS) }
        satelliteBtn!!.setOnClickListener { mapViewModel.updateMapBoxStyle(SATELLITE) }
        trafficBtn!!.setOnClickListener { mapViewModel.updateMapBoxStyle(TRAFFIC_DAY) }
        walkingBtn!!.setOnClickListener { mapViewModel.updateMapBoxStyle(OUTDOORS) }

        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setWindow()
        //val x = mapViewModel.mapBoxStyle.value

        // Change in map style
        mapViewModel.mapBoxStyle.observe(viewLifecycleOwner, {
            when {
                it.equals(defaultStyle) -> { // Default style

                // Set blue color
                setBlueColorOnButtonAndTv(defaultBtn, defaultText)

                // Set white color
                if (it != mapViewModel.mapStyleInUse) {
                    setWhiteColorOnButtonAndTv(mapViewModel.mapStyleInUse)
                }
                mapViewModel.mapStyleInUse = defaultStyle
                // SET MAP STYLE?


                } it.equals(satelliteStyle) -> { // Satellite style

                setBlueColorOnButtonAndTv(satelliteBtn, satelliteText)

                if (it != mapViewModel.mapStyleInUse) {
                    setWhiteColorOnButtonAndTv(mapViewModel.mapStyleInUse)
                }
                mapViewModel.mapStyleInUse = satelliteStyle

                } it.equals(trafficStyle) -> { // Traffic style

                setBlueColorOnButtonAndTv(trafficBtn, trafficText)

                if (it != mapViewModel.mapStyleInUse) {
                    setWhiteColorOnButtonAndTv(mapViewModel.mapStyleInUse)
                }
                mapViewModel.mapStyleInUse = trafficStyle

                } it.equals(walkingStyle) -> { // Walking style

                setBlueColorOnButtonAndTv(walkingBtn, walkingText)

                if (it != mapViewModel.mapStyleInUse) {
                    setWhiteColorOnButtonAndTv(mapViewModel.mapStyleInUse)
                }
                mapViewModel.mapStyleInUse = walkingStyle

                }
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
        usedView?.foreground = resources.getDrawable(R.drawable.button_border)
        usedTv?.setTextColor(resources.getColor(R.color.blue))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setWhiteColorOnButtonAndTv(mapStyleInUse: String?) {
        when (mapStyleInUse) {
            defaultStyle -> {
                defaultBtn!!.foreground = resources.getDrawable(R.drawable.button_border_white)
                defaultText!!.setTextColor(resources.getColor(R.color.originalTextColor))
            }
            satelliteStyle -> {
                satelliteBtn!!.foreground = resources.getDrawable(R.drawable.button_border_white)
                satelliteText!!.setTextColor(resources.getColor(R.color.originalTextColor))
            }
            trafficStyle -> {
                trafficBtn!!.foreground = resources.getDrawable(R.drawable.button_border_white)
                trafficText!!.setTextColor(resources.getColor(R.color.originalTextColor))
            }
            walkingStyle -> {
                walkingBtn!!.foreground = resources.getDrawable(R.drawable.button_border_white)
                walkingText!!.setTextColor(resources.getColor(R.color.originalTextColor))
            }
        }
    }

    companion object {
        const val TAG = "MapStyleDialog"

    }
}
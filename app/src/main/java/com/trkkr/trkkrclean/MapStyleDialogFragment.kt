package com.trkkr.trkkrclean

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
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
    ): View? {
        _binding = DialogMapStylesBinding.inflate(inflater, container, false).apply {
            satelliteStyleId.setOnClickListener {
                mapViewModel.updateMapBoxStyle(Style.SATELLITE)
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
        val x = mapViewModel.mapBoxStyle.value

    }


    private fun setWindow() {
        val window = dialog?.window
        window?.setGravity(Gravity.BOTTOM or Gravity.END)
        val layoutParams = window?.attributes
        layoutParams?.x = 150
        layoutParams?.y = 200
        window?.attributes = layoutParams
    }

    companion object {
        const val TAG = "MapStyleDialog"

    }
}
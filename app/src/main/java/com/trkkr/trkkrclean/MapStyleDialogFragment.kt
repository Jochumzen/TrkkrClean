package com.trkkr.trkkrclean

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mapbox.mapboxsdk.maps.Style
import com.trkkr.trkkrclean.databinding.DialogMapStylesBinding
import dagger.hilt.android.AndroidEntryPoint


private var _binding: DialogMapStylesBinding? = null
private val binding get() = _binding!!



@AndroidEntryPoint
class MapStyleDialogFragment : DialogFragment(R.layout.dialog_map_styles) {

    private val mapViewModel: MapViewModel by activityViewModels()

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
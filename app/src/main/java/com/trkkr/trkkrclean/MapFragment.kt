package com.trkkr.trkkrclean

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.trkkr.trkkrclean.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    @Inject
    lateinit var trkkrMapView: TrkkrMapView

    @Inject
    lateinit var trkkrLocation: TrkkrLocation

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var mapboxMap: MapboxMap? = null

    //private val mapViewModel: MapViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false).apply {
            mapView.onCreate(savedInstanceState)
            Log.d("MyDebug", "mapview: $mapView")
            trkkrMapView.setup(mapView) {
                Log.d("MyDebug", "mbm: $it")
                mapboxMap = it

                trkkrLocation.enableLocationComponent(
                    context = requireActivity(),
                    mapboxMap = mapboxMap
                )
            }
        }
        Log.d("MyDebug", "$trkkrLocation")
        return binding.root

    }

}

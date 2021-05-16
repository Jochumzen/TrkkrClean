package com.trkkr.trkkrclean

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    @Inject
    lateinit var trkkrMapView: TrkkrMapView

    @Inject
    lateinit var trkkrLocation: TrkkrLocation

    private val mapViewModel: MapViewModel by activityViewModels()

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var mapboxMap: MapboxMap? = null



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
                    mapboxMap = mapboxMap,
                    flyToLocation = savedInstanceState == null,
                    mapViewModel = mapViewModel
                )

            }
            setupViewObservers()

            mapSearchFab.setOnClickListener { view ->
                Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_searchFragment)
            }
        }

        Log.d("MyDebug", "vm: $mapViewModel")
        return binding.root

    }

    private fun FragmentMapBinding.setupViewObservers() {
        lifecycle.addObserver(trkkrMapView)
        this@MapFragment.mapViewModel.run {

            locationFlyer.observe(viewLifecycleOwner, Observer {
                trkkrMapView.flyToLocation(it, mapboxMap)
            })
        }
    }

}

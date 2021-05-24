package com.trkkr.trkkrclean

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    @Inject
    lateinit var trkkrMapView: TrkkrMapView

    @Inject
    lateinit var trkkrLocationComponent: TrkkrLocationComponent

    @Inject
    lateinit var trkkrLocation: TrkkrLocation

    private val mapViewModel: MapViewModel by activityViewModels()

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var mapboxMap: MapboxMap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false).apply {

            //mapView is a com.mapbox.mapboxsdk.maps.MapView in the fragment_map.xml layout file
            mapView.onCreate(savedInstanceState)
            Log.d("MyDebug", "mapView created in MapFragment : $mapView")

            //TrkkrMapView has a weak ref to the MapView.
            //setUp calls getMapAsync which creates a MapboxMap object. Then setStyle (async) is called on the MapboxMap object.
            //When all is done, the callback is implemented with the MabboxMap object which we save globally.
            trkkrMapView.setup(mapView) {
                mapboxMap = it
                Log.d("MyDebug", "TrkkrMapView.setup completed. We have the MapboxMap: $mapboxMap")

                //Enable location component (show current position). Enabled only with permsission.
                trkkrLocationComponent.enableLocationComponent(context = requireActivity(), mapboxMap = mapboxMap)

                val currentLocation = mapboxMap?.locationComponent?.lastKnownLocation
                Log.d("MyDebug", "Location: $currentLocation")

                if (currentLocation != null) {
                    trkkrMapView.flyToLocation(currentLocation, mapboxMap)
                }

            }
            setupViewObservers()

            mapSearchFab.setOnClickListener { view ->
                Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_searchFragment)
            }

            mapStyleFab.setOnClickListener {
                val mapStyleDialog = MapStyleDialogFragment().show(childFragmentManager, MapStyleDialogFragment.TAG)
            }
        }

        Log.d("MyDebug", "vm: $mapViewModel")

        // Set the map style
        mapViewModel.mapBoxStyle.observe(viewLifecycleOwner, {
            mapboxMap?.setStyle(it)
        })

        return binding.root

    }

    private fun setupViewObservers() {
        lifecycle.addObserver(trkkrMapView)

        this@MapFragment.mapViewModel.run {
            mapBoxStyle.observe(viewLifecycleOwner, Observer {
                val x = it
            })
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

}

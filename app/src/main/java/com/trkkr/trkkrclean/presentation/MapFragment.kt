package com.trkkr.trkkrclean.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.*
import com.trkkr.trkkrclean.databinding.FragmentMapBinding
import com.trkkr.trkkrclean.domain.MiniPoi
import com.trkkr.trkkrclean.utilities.TrkkrLocationComponent
import com.trkkr.trkkrclean.utilities.TrkkrMap
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    @Inject
    lateinit var trkkrMap: TrkkrMap

    @Inject
    lateinit var trkkrLocationComponent: TrkkrLocationComponent

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
            trkkrMap.setup(mapView) {
                mapboxMap = it
                Log.d("MyDebug", "TrkkrMapView.setup completed. We have the MapboxMap: $mapboxMap")

                //Enable location component (show current position). Requires location permission.
                trkkrLocationComponent.enableLocationComponent(context = requireActivity(), mapboxMap = mapboxMap) { locationComponentEnabled ->
                    Log.d("MyDebug", "LocationComponentEnabled: $locationComponentEnabled")

                    mapViewModel.setLocationComponentEnabled(locationComponentEnabled)

                    if(locationComponentEnabled) {
                        val currentLocation = mapboxMap?.locationComponent?.lastKnownLocation
                        Log.d("MyDebug", "MapFragment, currentLocation: $currentLocation")

                        if (currentLocation != null) {
                            trkkrMap.flyToLocation(currentLocation, mapboxMap)
                        }
                    }
                }

                setMapboxClickListener(mapboxMap)
            }
            setupViewObservers()

            mapSearchFab.setOnClickListener { view ->
                Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_searchFragment)
            }

            mapStyleFab.setOnClickListener {
                val mapStyleDialog = MapStyleDialogFragment().show(childFragmentManager,
                    MapStyleDialogFragment.TAG
                )
            }

            // https://medium.com/over-engineering/hands-on-with-material-components-for-android-bottom-sheet-970c5f0f1840

            val modalBottomSheet = MiniPoiDialogFragment()
            modalBottomSheet.show(childFragmentManager, MiniPoiDialogFragment.TAG)

            /*val modalBottomSheetBehavior = (modalBottomSheet.dialog as BottomSheetDialog).behavior
            modalBottomSheetBehavior.isDraggable = true
            modalBottomSheetBehavior.isHideable = true*/
            // modalBottomSheetBehavior.peekHeight // Set how high the modal sheet can be when it's just "mini poi"

            showMiniPoi.setOnClickListener {
                modalBottomSheet.also {
                    it.show(childFragmentManager, MiniPoiDialogFragment.TAG)
                    var miniPoi = MiniPoi(id = 1481338431, name = "Lunds Domkyrka", category = "Place of Worship", distance = "10m", open = true, images = listOf("https://www.google.com/url?sa=i&url=https%3A%2F%2Fsv.wikipedia.org%2Fwiki%2FLunds_domkyrka&psig=AOvVaw2KX0I4nmcJf-IaBNOJqVPz&ust=1623146586125000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPCwvoiihfECFQAAAAAdAAAAABAD"))
                    mapViewModel.updateMiniPoi(miniPoi)
                }
            }

            /*val miniPoiSheetView : ConstraintLayout = miniPoiSheet.miniPoiSheet
            bottomSheetBehavior = BottomSheetBehavior.from(miniPoiSheetView)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

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

            showMiniPoi.setOnClickListener {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                else
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            miniPoiSheet.miniPoiImage.setOnClickListener {
                Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show()
            }*/
        }

        Log.d("MyDebug", "vm: $mapViewModel")

        return binding.root

    }

    private fun setMapboxClickListener(
        mapboxMap: MapboxMap?
    ) {
        mapboxMap?.addOnMapClickListener { latLng ->

            Log.d("MyDebug", "Map clicked: $latLng")

            val screenPoint = mapboxMap.projection.toScreenLocation(latLng)

            //Assume that there can be at most 1 "poi-label" Feature
            val features = mapboxMap.queryRenderedFeatures(screenPoint, "poi-label")


            if (features.size > 0) {
                Log.d("MyDebug", "vm: $features[0]")

                val mapStateEvent : MapStateEvent.GetMiniPoiEvent =
                    MapStateEvent.GetMiniPoiEvent(latLng, features[0])

                mapViewModel.setStateEvent(mapStateEvent)
            } else {
                Log.d("MyDebug", "No poi-label features at this point")
                //Do nothing if there is no Feature at the position clicked
            }
            true
        }
    }

    private fun setupViewObservers() {
        lifecycle.addObserver(trkkrMap)

        this@MapFragment.mapViewModel.run {
            mapBoxStyle.observe(viewLifecycleOwner, Observer {
                mapboxMap?.setStyle(it)
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



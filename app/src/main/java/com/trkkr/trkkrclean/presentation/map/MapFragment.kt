package com.trkkr.trkkrclean.presentation.map

import PoiTabAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.*
import com.trkkr.trkkrclean.databinding.FragmentMapBinding
import com.trkkr.trkkrclean.presentation.search.SearchViewModel
import com.trkkr.trkkrclean.utilities.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    @Inject
    lateinit var trkkrMapView: TrkkrMapView

    @Inject
    lateinit var trkkrLocationComponent: TrkkrLocationComponent

    @Inject
    lateinit var mapper: Mapper

    private val mapViewModel: MapViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var mapboxMap: MapboxMap? = null

    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false).apply {

            //mapView is a com.mapbox.mapboxsdk.maps.MapView in the fragment_map.xml layout file
            mapView.onCreate(savedInstanceState)
            Log.d("MyDebug", "MapFragment(onCreateView). MapView created : $mapView")

            //trkkrMap.setup(mapView):  TrkkrMapView saves a weak ref to the MapView.
            //setUp() calls getMapAsync, which creates a MapboxMap object.
            //Then setStyle (async) is called on the MapboxMap object.
            //When all is done, the callback is implemented with the MabboxMap object, which we save globally.
            trkkrMapView.setup(mapView) { map ->

                //setup() is done. Save the MapboxMap object (which may be null)
                mapboxMap = map
                Log.d("MyDebug", "MapFragment(onCreateView). trkkrMap.setup completed and returned the MapboxMap")

                //Enable location component (to show current position map and request current location of user).
                //Requires location permission. Returns true if location component was enabled
                trkkrLocationComponent.enableLocationComponent(context = requireActivity(), mapboxMap = mapboxMap) { locationComponentEnabled ->

                    //EnableLocationComponent complete
                    Log.d("MyDebug", "MapFragment(onCreateView). LocationComponent was enabled?: $locationComponentEnabled")

                    //Self-explanatory
                    if(locationComponentEnabled) {
                        mapboxMap.flyToLastKnownLocation()
                    }
                }

                //If we click on "poi-label" Feature, we receive a MapStateEvent object, a GetPoiEvent (LatLng (click), Feature, LatLng? (user))
                mapboxMap.listenForPoiClick() { mapStateEvent ->

                    if (mapStateEvent != null) {
                        //Implements the GetPoiInteractor which picks up the Poi
                        mapViewModel.setStateEvent(mapStateEvent)

                    } else {

                        // User clicked map but not on a POI
                    }

                }

            }

            setupViewObservers()

            mapSearchFab.setOnClickListener { view ->
                searchViewModel.setSearchCameraPosition(mapboxMap?.cameraPosition)
                Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_searchFragment)
            }

            mapStyleFab.setOnClickListener {
                MapStyleDialogFragment().show(
                    childFragmentManager,
                    MapStyleDialogFragment.TAG
                )
            }

            showMiniPoi.setOnClickListener { behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED }

            // BOTTOM SHEET BEHAVIOR

            behavior = BottomSheetBehavior.from(miniPoiSheet)
            behavior.isFitToContents = false
            behavior.halfExpandedRatio = 0.3f

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // Handle onslide
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }
            })

            miniPoiSheet.setOnClickListener {
                when (behavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    BottomSheetBehavior.STATE_EXPANDED -> behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    else -> behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            // PageAdapter stuff
            val viewPager = poiViewpager
            viewPager.adapter = PoiTabAdapter(childFragmentManager)

            val tabLayout = tabLayout
            tabLayout.setupWithViewPager(viewPager)
        }

        Log.d("MyDebug", "MapFragment(onCreateView). Complete. We have the MapViewModel: $mapViewModel")

        return binding.root

    }


    private fun setupViewObservers() {
        lifecycle.addObserver(trkkrMapView)

        this@MapFragment.mapViewModel.run {

            //MapBoxStyle Live data
            mapBoxStyle.observe(viewLifecycleOwner, Observer {
                mapboxMap?.setStyle(it)
            })
        }
    }


    //Lifecycle shit
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    //Lifecycle shit
    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

}



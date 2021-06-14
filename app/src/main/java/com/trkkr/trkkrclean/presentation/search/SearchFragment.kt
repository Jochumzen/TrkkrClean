package com.trkkr.trkkrclean.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.R
import com.trkkr.trkkrclean.databinding.FragmentSearchBinding
import com.trkkr.trkkrclean.presentation.map.MapStateEvent
import com.trkkr.trkkrclean.utilities.flyToPosition
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var trkkrSearchMapView: TrkkrSearchMapView

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var mapboxMap: MapboxMap? = null

    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false).apply {

            //searchMapView is a com.mapbox.mapboxsdk.maps.MapView in the fragment_search.xml layout file
            searchMapView.onCreate(savedInstanceState)

            trkkrSearchMapView.setup(searchMapView) { map ->
                //setup() is done. Save the MapboxMap object (which may be null)
                mapboxMap = map

                searchViewModel.getCameraPosition()?.let {mapboxMap?.flyToPosition(it)}

                val searchStateEvent : SearchStateEvent.GetCachedSearchDataEvent =
                    SearchStateEvent.GetCachedSearchDataEvent()

                searchViewModel.setStateEvent(searchStateEvent)
            }

            setupViewObservers()
        }

        return binding.root

    }

    private fun setupViewObservers() {
        lifecycle.addObserver(trkkrSearchMapView)

    }
}
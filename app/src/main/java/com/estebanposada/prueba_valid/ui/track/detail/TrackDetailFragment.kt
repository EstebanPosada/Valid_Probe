package com.estebanposada.prueba_valid.ui.track.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.estebanposada.prueba_valid.App
import com.estebanposada.prueba_valid.R
import com.estebanposada.prueba_valid.databinding.FragmentTrackDetailBinding
import com.estebanposada.prueba_valid.ui.artist.MainViewModel
import com.estebanposada.prueba_valid.ui.track.TrackViewModel
import javax.inject.Inject


class TrackDetailFragment : Fragment() {

    private var _binding: FragmentTrackDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: TrackViewModel
    //private val args: TrackDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        (requireContext().applicationContext as App).appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = 5L
        viewModel.getTrackDetail(args)
        viewModel.detail.observe(viewLifecycleOwner, Observer { it ->
            it.let { track ->
                binding.apply {
                    name.text = track.name
                    listeners.text = String.format(getString(R.string.listeners), track.listeners)

                    url.text = track.url
                    streamable.text = track.streamable.text.toString()
                }

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.estebanposada.prueba_valid.ui.artist.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.estebanposada.prueba_valid.App
import com.estebanposada.prueba_valid.R
import com.estebanposada.prueba_valid.databinding.FragmentArtistDetailBinding
import com.estebanposada.prueba_valid.ui.artist.MainViewModel
import com.estebanposada.prueba_valid.ui.track.TrackActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject


class ArtistDetailFragment : Fragment() {

    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: MainViewModel
    private val args: ArtistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        (requireContext().applicationContext as App).appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.id
        viewModel.getArtistDetail(args.id)
        viewModel.detail.observe(viewLifecycleOwner, Observer { it ->
            it.let { artist ->
                binding.apply {
                    val imageViews = listOf(imgSmall, imgMedium, imgLarge, imgExtra, imgMega)
                    name.text = artist.name
                    listeners.text = String.format(getString(R.string.listeners), artist.listeners)
                    artist.image.forEachIndexed { index, image ->
                        Picasso.get().load(image.text).fit().into(imageViews[index])
                    }
                    url.text = artist.url
                    streamable.text = artist.streamable
                }

            }
        })
        binding.goTracks.setOnClickListener {
            startActivity(Intent(requireContext(), TrackActivity::class.java))
            requireActivity().finish()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.estebanposada.prueba_valid.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.estebanposada.prueba_valid.App
import com.estebanposada.prueba_valid.databinding.FragmentArtistDetailBinding
import com.estebanposada.prueba_valid.ui.main.MainViewModel
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
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            it.let { artist ->
                binding.apply {
                    name.text = artist.name
                    listeners.text = artist.name
                }

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
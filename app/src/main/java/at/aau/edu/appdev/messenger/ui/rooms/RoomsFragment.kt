package at.aau.edu.appdev.messenger.ui.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.databinding.FragmentRoomBinding
import at.aau.edu.appdev.messenger.persistence.UserRepository

class RoomsFragment : Fragment() {

    private val viewModel by viewModels<RoomsViewModel> {
        viewModelFactory {
            initializer {
                RoomsViewModel(
                    Client.getInstance(
                        context = requireContext(),
                        user = UserRepository(requireContext()).getUser()
                    )
                )
            }
        }
    }

    private var binding: FragmentRoomBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRoomBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        viewModel.startDiscovery()
    }

    override fun onDestroyView() {
        binding = null
        viewModel.stopDiscovery()
        super.onDestroyView()
    }

    private fun setupUi() {
        val binding = this.binding ?: return
        val navController = findNavController()

        val adapter = RoomsAdapter { connection ->
            // TODO: Not sure if we want to pass connection here or not. If not we can remove click handler from adapter
            navController.navigate(R.id.chat_client)
        }

        binding.recycler.adapter = adapter

        binding.addRoomButton.setOnClickListener {
            navController.navigate(R.id.chat_server)
        }

        viewModel.rooms?.observe(viewLifecycleOwner) { connections ->
            adapter.submitList(connections)
        }

        binding.animation.playAnimation()

        viewModel.jokes.observe(viewLifecycleOwner) { joke ->
            binding.joke.text = joke
        }

        binding.jokeContainer.background = AppCompatResources.getDrawable(
            requireContext(), UserRepository(requireContext()).getUser()?.color?.primary
                ?: return
        )
    }
}
package at.aau.edu.appdev.messenger.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import at.aau.edu.appdev.messenger.Environment
import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.databinding.FragmentChatBinding

class ChatClientFragment : Fragment() {

    private val viewModel by viewModels<ChatClientViewModel> {
        viewModelFactory {
            initializer {
                ChatClientViewModel(Environment.getInstance(requireContext()).client)
            }
        }
    }

    private var binding: FragmentChatBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentChatBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setupUi() {

    }
}
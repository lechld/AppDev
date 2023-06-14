package at.aau.edu.appdev.messenger.ui.chat.server

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.databinding.FragmentChatBinding
import at.aau.edu.appdev.messenger.persistence.UserRepository
import at.aau.edu.appdev.messenger.ui.chat.ChatFragmentDelegate
import at.aau.edu.appdev.messenger.ui.chat.MessageAdapter

class ChatServerFragment : Fragment() {

    private val viewModel by viewModels<ChatServerViewModel> {
        viewModelFactory {
            initializer {
                val userRepo = UserRepository(requireContext())
                ChatServerViewModel(
                    server = Server.getInstance(requireContext(), userRepo.getUser()),
                    userRepository = userRepo,
                )
            }
        }
    }

    private var binding: FragmentChatBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startBroadcasting()
    }

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

    override fun onDestroy() {
        viewModel.stopBroadcasting()
        super.onDestroy()
    }

    private fun setupUi() {
        val binding = this.binding ?: return
        val delegate = ChatFragmentDelegate(binding)

        delegate.setup(viewLifecycleOwner, viewModel)
    }
}
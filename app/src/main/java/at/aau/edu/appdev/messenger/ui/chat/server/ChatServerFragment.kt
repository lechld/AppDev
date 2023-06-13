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
import at.aau.edu.appdev.messenger.Environment
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.databinding.FragmentChatBinding
import at.aau.edu.appdev.messenger.ui.chat.MessageAdapter

class ChatServerFragment : Fragment() {

    private val viewModel by viewModels<ChatServerViewModel> {
        viewModelFactory {
            initializer {
                val environment = Environment.getInstance(requireContext())
                ChatServerViewModel(
                    server = environment.server,
                    userRepository = environment.userRepository
                )
            }
        }
    }

    private var binding: FragmentChatBinding? = null

    private val closeIcon by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.close)
    }

    private val brushIcon by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.brush)
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

    private fun setupUi() {
        val binding = this.binding ?: return
        val adapter = MessageAdapter(this.context ?: return)

        binding.recycler.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages) {
                binding.recycler.smoothScrollToPosition(messages.size)
            }
        }

        binding.textInputLayout.setEndIconOnClickListener {
            viewModel.sendMessage(
                binding.drawingView.getBitmap(),
                binding.textInput.text?.toString()
            )

            binding.textInput.text?.clear()
            disableDrawingMode()
        }

        binding.textInputLayout.setStartIconOnClickListener {
            toggleDrawingMode()
        }
    }

    private fun toggleDrawingMode() {
        val binding = this.binding ?: return
        val enabled = binding.drawingView.isVisible

        if (enabled) {
            disableDrawingMode()
        } else {
            enableDrawingMode()
        }
    }

    private fun enableDrawingMode() {
        val binding = this.binding ?: return

        binding.textInputLayout.startIconDrawable = closeIcon
        binding.drawingView.isVisible = true
        binding.recycler.isEnabled = false
        binding.recycler.alpha = 0.7f
    }

    private fun disableDrawingMode() {
        val binding = this.binding ?: return

        binding.textInputLayout.startIconDrawable = brushIcon
        binding.drawingView.isVisible = false
        binding.drawingView.reset()
        binding.recycler.isEnabled = true
        binding.recycler.alpha = 1f
    }
}
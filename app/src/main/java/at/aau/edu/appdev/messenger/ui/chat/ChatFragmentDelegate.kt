package at.aau.edu.appdev.messenger.ui.chat

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.databinding.FragmentChatBinding

class ChatFragmentDelegate(
    private val binding: FragmentChatBinding,
) {
    private val closeIcon by lazy {
        ContextCompat.getDrawable(binding.root.context, R.drawable.close)
    }

    private val brushIcon by lazy {
        ContextCompat.getDrawable(binding.root.context, R.drawable.brush)
    }

    fun setup(viewLifecycleOwner: LifecycleOwner, viewModel: ChatFragmentViewModel) {
        val adapter = MessageAdapter()

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
        val enabled = binding.drawingView.isVisible

        if (enabled) {
            disableDrawingMode()
        } else {
            enableDrawingMode()
        }
    }

    private fun enableDrawingMode() {
        binding.textInputLayout.startIconDrawable = closeIcon
        binding.drawingView.isVisible = true
        binding.recycler.isEnabled = false
        binding.recycler.alpha = 0.7f
    }

    private fun disableDrawingMode() {
        binding.textInputLayout.startIconDrawable = brushIcon
        binding.drawingView.isVisible = false
        binding.drawingView.reset()
        binding.recycler.isEnabled = true
        binding.recycler.alpha = 1f
    }
}
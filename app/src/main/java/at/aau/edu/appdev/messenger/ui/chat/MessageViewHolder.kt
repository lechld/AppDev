package at.aau.edu.appdev.messenger.ui.chat

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import at.aau.edu.appdev.messenger.databinding.ItemDrawingBinding
import at.aau.edu.appdev.messenger.databinding.ItemMessageBinding
import at.aau.edu.appdev.messenger.model.Message
import java.time.format.DateTimeFormatter

private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")

sealed class MessageViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class TextViewHolder(
        private val binding: ItemMessageBinding,
    ) : MessageViewHolder(binding) {

        fun bind(item: Message.Text) {
            binding.time.text = TIME_FORMATTER.format(item.time)
            binding.header.text = item.sender.name
            binding.content.text = item.content
        }
    }

    class DrawingViewHolder(
        private val binding: ItemDrawingBinding,
    ) : MessageViewHolder(binding) {

        fun bind(item: Message.Drawing) {
            binding.time.text = TIME_FORMATTER.format(item.time)
            binding.drawnImage.setImageBitmap(item.bitmap) // TODO: Maybe we should upscale that again at some point
            binding.header.text = item.sender.name
            binding.content.text = item.text
            binding.content.isVisible = item.text.isNotEmpty()
        }
    }
}
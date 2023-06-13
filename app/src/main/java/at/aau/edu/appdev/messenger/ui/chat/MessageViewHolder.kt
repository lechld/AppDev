package at.aau.edu.appdev.messenger.ui.chat

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import at.aau.edu.appdev.messenger.databinding.ItemMessageBinding
import at.aau.edu.appdev.messenger.model.Message

sealed class MessageViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class TextViewHolder(
        private val binding: ItemMessageBinding,
    ) : MessageViewHolder(binding) {

        fun bind(item: Message.Text) {
            binding.time.text = item.time.toLocalTime().toString()
            binding.header.text = item.sender.name
            binding.content.text = item.content
        }
    }
}
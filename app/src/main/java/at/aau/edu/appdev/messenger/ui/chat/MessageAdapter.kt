package at.aau.edu.appdev.messenger.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.databinding.ItemDrawingBinding
import at.aau.edu.appdev.messenger.databinding.ItemMessageBinding
import at.aau.edu.appdev.messenger.model.Message

class MessageAdapter : ListAdapter<Message, MessageViewHolder>(Differ()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Message.Text -> {
                R.layout.item_message
            }
            is Message.Drawing -> {
                R.layout.item_drawing
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_message -> {
                val binding = ItemMessageBinding.inflate(inflater, parent, false)

                MessageViewHolder.TextViewHolder(binding)
            }

            R.layout.item_drawing -> {
                val binding = ItemDrawingBinding.inflate(inflater, parent, false)

                MessageViewHolder.DrawingViewHolder(binding)
            }

            else -> throw IllegalStateException("invalid view type!")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is MessageViewHolder.TextViewHolder -> {
                holder.bind(item as Message.Text)
            }

            is MessageViewHolder.DrawingViewHolder -> {
                holder.bind(item as Message.Drawing)
            }
        }
    }

    class Differ : DiffUtil.ItemCallback<Message>() {

        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
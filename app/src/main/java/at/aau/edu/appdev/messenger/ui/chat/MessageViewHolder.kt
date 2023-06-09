package at.aau.edu.appdev.messenger.ui.chat

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import at.aau.edu.appdev.messenger.databinding.ItemDrawingBinding
import at.aau.edu.appdev.messenger.databinding.ItemMessageBinding
import at.aau.edu.appdev.messenger.model.Message
import java.time.format.DateTimeFormatter

private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
private const val BITMAP_UPSCALE_FACTOR = 3

sealed class MessageViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    protected val context: Context = binding.root.context

    class TextViewHolder(
        private val binding: ItemMessageBinding,
    ) : MessageViewHolder(binding) {

        fun bind(item: Message.Text) {
            binding.time.text = TIME_FORMATTER.format(item.time)
            binding.header.text = item.sender.name
            binding.content.text = item.content
            binding.header.setTextColor(context.getColor(item.sender.color.primary))
            binding.headerContainer.background =
                AppCompatResources.getDrawable(context, item.sender.color.secondary)
            binding.headerContainerContainer.background =
                AppCompatResources.getDrawable(context, item.sender.color.primary)
            binding.contentContainerContainer.background =
                AppCompatResources.getDrawable(context, item.sender.color.primary)
        }
    }

    class DrawingViewHolder(
        private val binding: ItemDrawingBinding,
    ) : MessageViewHolder(binding) {

        fun bind(item: Message.Drawing) {
            binding.time.text = TIME_FORMATTER.format(item.time)
            binding.drawnImage.setImageBitmap(
                Bitmap.createScaledBitmap(
                    item.bitmap,
                    item.bitmap.width * BITMAP_UPSCALE_FACTOR,
                    item.bitmap.height * BITMAP_UPSCALE_FACTOR,
                    false
                )
            )
            binding.header.text = item.sender.name
            binding.content.text = item.text
            binding.content.isVisible = item.text.isNotEmpty()
            binding.header.setTextColor(context.getColor(item.sender.color.primary))
            binding.headerContainer.background =
                AppCompatResources.getDrawable(context, item.sender.color.secondary)
            binding.headerContainerContainer.background =
                AppCompatResources.getDrawable(context, item.sender.color.primary)
            binding.contentContainerContainer.background =
                AppCompatResources.getDrawable(context, item.sender.color.primary)
        }
    }
}
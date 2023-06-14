package at.aau.edu.appdev.messenger.ui.rooms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import at.aau.edu.appdev.messenger.api.model.Connection
import at.aau.edu.appdev.messenger.databinding.ItemRoomBinding
import at.aau.edu.appdev.messenger.persistence.UserRepository

class RoomsAdapter(
    private val onClick: (Connection) -> Unit,
) : ListAdapter<Connection, RoomsAdapter.RoomViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomBinding.inflate(inflater, parent, false)

        return RoomViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onClick(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val connection = getItem(position)

        holder.bind(connection)
    }

    inner class RoomViewHolder(
        private val binding: ItemRoomBinding
    ) : ViewHolder(binding.root) {
        fun bind(connection: Connection) {
            binding.content.text = connection.user.name
            binding.contentContainerContainer.background = AppCompatResources.getDrawable(
                binding.root.context, UserRepository(binding.root.context).getUser()?.color?.primary
                    ?: return
            )
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Connection>() {
        override fun areItemsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem.endpointId == newItem.endpointId
        }

        override fun areContentsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem.equals(newItem)
        }
    }
}
package at.aau.edu.appdev.messenger.ui.user

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.databinding.ListItemColorBinding
import at.aau.edu.appdev.messenger.model.UserColor

class ColorAdapter(
    private val context: Context,
    layoutRes: Int = R.layout.list_item_color,
    private val userColors: List<UserColor> = UserColor.values().toList()
) : ArrayAdapter<UserColor>(context, layoutRes, userColors) {

    override fun getCount(): Int {
        return userColors.size
    }

    override fun getItem(position: Int): UserColor {
        return userColors[position]
    }

    override fun getItemId(position: Int): Long {
        return userColors[position].description.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView != null) {
            ListItemColorBinding.bind(convertView)
        } else {
            val inflater = (context as Activity).layoutInflater

            ListItemColorBinding.inflate(inflater, parent, false)
        }

        val userColor = getItem(position)

        binding.colorIndicator.setBackgroundColor(context.getColor(userColor.primary))
        binding.title.text = context.getString(userColor.description)

        return binding.root
    }
}
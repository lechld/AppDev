package at.aau.edu.appdev.messenger.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import at.aau.edu.appdev.messenger.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {

    private var binding: FragmentEntryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEntryBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
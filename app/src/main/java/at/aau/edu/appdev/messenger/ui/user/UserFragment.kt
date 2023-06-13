package at.aau.edu.appdev.messenger.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import at.aau.edu.appdev.messenger.Environment
import at.aau.edu.appdev.messenger.databinding.FragmentUserBinding
import at.aau.edu.appdev.messenger.model.UserColor

class UserFragment : Fragment() {

    private val viewModel by viewModels<UserViewModel> {
        viewModelFactory {
            initializer {
                UserViewModel(Environment.getInstance(requireContext()).userRepository)
            }
        }
    }

    private var binding: FragmentUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserBinding.inflate(inflater, container, false)
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
        val context = this.context ?: return

        binding.nameInput.doAfterTextChanged { text ->
            binding.saveButton.isEnabled = !text.isNullOrEmpty()
        }

        binding.colorInput.setAdapter(ColorAdapter(context))

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.nameInput.setText(user?.name ?: "")
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val color = UserColor.valueOf(binding.colorInput.text.toString())

            viewModel.update(name, color)

            findNavController().popBackStack()
        }
    }
}
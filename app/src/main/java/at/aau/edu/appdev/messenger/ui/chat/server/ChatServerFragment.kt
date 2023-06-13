package at.aau.edu.appdev.messenger.ui.chat.server

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.databinding.FragmentChatBinding
import at.aau.edu.appdev.messenger.model.Message
import at.aau.edu.appdev.messenger.ui.chat.MessageAdapter
import at.aau.edu.appdev.messenger.user.User
import at.aau.edu.appdev.messenger.user.UserColor
import at.aau.edu.appdev.messenger.user.UserRepository
import java.time.OffsetDateTime

class ChatServerFragment : Fragment() {

    private val viewModel by viewModels<ChatServerViewModel> {
        viewModelFactory {
            initializer {
                ChatServerViewModel(Server.getInstance(requireContext()))
            }
        }
    }

    private var binding: FragmentChatBinding? = null

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
        val context = this.context ?: return
        val user = UserRepository(context).getUser() ?: return

        val adapter = MessageAdapter()

        binding.recycler.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
        }

        adapter.submitList(getDummyData(user))

        binding.textInputLayout.setEndIconOnClickListener {
            // TODO: Send content
        }
        binding.textInputLayout.setStartIconOnClickListener {
            // TODO: Show user options
        }
    }

    private fun getDummyData(user1: User): List<Message> {
        val user2 = User("Emily", "user2", UserColor.VIOLET)

        return listOf(
            Message.Text(
                user1,
                OffsetDateTime.parse("2023-06-13T10:00:00Z"),
                "1",
                "Hello"
            ),
            Message.Text(
                user2,
                OffsetDateTime.parse("2023-06-13T10:02:00Z"),
                "2",
                "Hi John, how are you?"
            ),
            Message.Text(
                user1,
                OffsetDateTime.parse("2023-06-13T10:05:00Z"),
                "3",
                "I'm good, thanks! How about you?"
            ),
            Message.Text(
                user2,
                OffsetDateTime.parse("2023-06-13T10:07:00Z"),
                "4",
                "I'm doing well too."
            ),
            Message.Text(
                user1,
                OffsetDateTime.parse("2023-06-13T10:10:00Z"),
                "5",
                "That's great to hear!"
            )
        )
    }
}
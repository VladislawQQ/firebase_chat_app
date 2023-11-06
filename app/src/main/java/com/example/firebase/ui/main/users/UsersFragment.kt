package com.example.firebase.ui.main.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.FragmentUsersBinding
import com.example.firebase.ui.adapters.users.UsersAdapter
import com.example.firebase.ui.base.BaseFragment
import kotlinx.coroutines.launch

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel : UsersViewModel by viewModels()

    private val usersAdapter by lazy {
        UsersAdapter(userActionListener = object :
            UserActionListener {
                override fun onUserClick() {
                    startUserProfileFragment()
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRecycleView()
        observeViewModel()
    }

    private fun startUserProfileFragment() {
        val direction = UsersFragmentDirections
            .actionUsersFragmentToChatFragment()

        navController.navigate(direction)
    }

    private fun bindRecycleView() {
        val recyclerLayoutManager = LinearLayoutManager(activity)

        with(binding.rvUsers) {
            layoutManager = recyclerLayoutManager
            adapter = usersAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userStateFlow.collect { messages ->
                    usersAdapter.submitList(messages)
                }
            }
        }
    }

}
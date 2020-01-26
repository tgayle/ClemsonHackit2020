package com.tno.cuhackit2020.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tno.cuhackit2020.databinding.RecentIssuesFragmentBinding
import com.tno.cuhackit2020.model.RecentIssuesWrapper

class RecentIssuesFragment : Fragment() {

    private val vm by activityViewModels<RecentIssuesViewModel>()
    private lateinit var binding: RecentIssuesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecentIssuesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecentIssuesAdapter()
        binding.issuesList.adapter = adapter
        binding.issuesList.layoutManager = LinearLayoutManager(context)

        vm.recents.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        adapter.onLikeButtonClick = { liked, item ->
            vm.likePost(item, liked)
        }

        adapter.onThingSelected = {
            val args = RecentIssuesFragmentDirections.actionRecentIssuesToViewContent(
                it is RecentIssuesWrapper.RecentIssue,
                if (it is RecentIssuesWrapper.RecentIssue) {
                    it.issue.issueid
                } else {
                    (it as RecentIssuesWrapper.RecentSuggestion).suggestion.suggestionsid
                }
            )

            findNavController().navigate(args)
        }

    }

}

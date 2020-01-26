package com.tno.cuhackit2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tno.cuhackit2020.recent.RecentIssuesAdapter
import com.tno.cuhackit2020.recent.RecentIssuesViewModel
import kotlinx.android.synthetic.main.fragment_view_content.*

/**
 * A simple [Fragment] subclass.
 */
class ViewContentFragment : Fragment() {

    private val vm by activityViewModels<RecentIssuesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_content, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadIssue(navArgs<ViewContentFragmentArgs>().value.itemId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecentIssuesAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)

        refresh.setOnRefreshListener {
            vm.loadIssue(navArgs<ViewContentFragmentArgs>().value.itemId)
        }

        adapter.onLikeButtonClick = { liked, item ->
            vm.likePost(item, liked)
        }

        vm.issueInfo.observe(viewLifecycleOwner, Observer {
            refresh.isRefreshing = it.isNullOrEmpty()
            adapter.submitList(it)
        })

    }

}

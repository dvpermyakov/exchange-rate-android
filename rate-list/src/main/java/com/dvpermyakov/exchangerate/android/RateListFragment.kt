package com.dvpermyakov.exchangerate.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvpermyakov.exchangerate.R
import com.dvpermyakov.exchangerate.presentation.RateListViewModel
import com.dvpermyakov.exchangerate.presentation.RateListViewModelFactory
import kotlinx.android.synthetic.main.fragment_rate_list.*

class RateListFragment : Fragment() {

    private val viewModel: RateListViewModel by lazy {
        ViewModelProvider(this, RateListViewModelFactory()).get(RateListViewModel::class.java)
    }

    private val adapter by lazy {
        RateListAdapter(object : RateListAdapter.Listener {
            override fun onRateItemClick(rateId: String, value: String) {
                viewModel.onRateItemClick(rateId, value)
            }

            override fun onValueChange(value: String) {
                viewModel.onValueChange(value)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rate_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.rateListStateLiveData.observe(viewLifecycleOwner, Observer { viewState ->
            adapter.submitList(viewState.items)
        })

        viewModel.progressBarLiveData.observe(viewLifecycleOwner, Observer { isProgressState ->
            if (isProgressState) {
                progressBarView.visibility = View.VISIBLE
            } else {
                progressBarView.visibility = View.GONE
            }
        })

        viewModel.scrollToFirstPositionLiveData.observe(
            viewLifecycleOwner,
            Observer { forceScroll ->
                if (forceScroll) {
                    recyclerView.smoothScrollToPosition(0)
                }
            })
    }

    companion object {
        fun newInstance() = RateListFragment()
    }
}

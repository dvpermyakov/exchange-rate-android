package com.dvpermyakov.exchangerate.android

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
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

            override fun onFirstItemChange() {
                recyclerView.scrollToPosition(0)
                recyclerView.postDelayed({
                    recyclerView.getChildAt(0).run {
                        val focusableView = findViewById<EditText>(R.id.focusableEditTextView)
                        focusableView.setSelection(focusableView.text.length)
                        focusableView.requestFocus()
                        (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
                            showSoftInput(focusableView, InputMethodManager.SHOW_IMPLICIT)
                        }
                    }
                }, 100)
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
        recyclerView.itemAnimator = null
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_DRAGGING) {
                    (recyclerView.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
                        hideSoftInputFromWindow(view.windowToken, 0)
                    }
                }
            }
        })

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
    }

    companion object {
        fun newInstance() = RateListFragment()
    }
}

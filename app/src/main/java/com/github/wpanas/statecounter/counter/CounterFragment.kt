package com.github.wpanas.statecounter.counter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.github.wpanas.statecounter.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class CounterFragment : DaggerFragment() {

    @Inject
    lateinit var counterViewModel: CounterViewModel

    private var listener: CounterFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CounterFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CounterFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_counter, container, false)
        val counter = view?.findViewById<TextView>(R.id.counter)

        initCounterChangerListeners(view)

        counterViewModel.counter.observe(this, Observer {
            counter?.text = it.toString()
        })

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initCounterChangerListeners(view: View) {
        view.findViewById<Button>(R.id.minus_button).setOnClickListener {
            listener?.decrementState(it)
        }
        view.findViewById<Button>(R.id.plus_button).setOnClickListener {
            listener?.incrementState(it)
        }
    }

    interface CounterFragmentInteractionListener {
        fun incrementState(view: View)
        fun decrementState(view: View)
    }
}
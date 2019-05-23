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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context !is CounterChanger) {
            throw RuntimeException("Activity doesn't implement CounterChanger")
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

    private fun initCounterChangerListeners(view: View) {
        val counterChanger: CounterChanger = context as CounterChanger

        view.findViewById<Button>(R.id.minus_button).setOnClickListener(counterChanger::decrementState)
        view.findViewById<Button>(R.id.plus_button).setOnClickListener(counterChanger::incrementState)
    }
}

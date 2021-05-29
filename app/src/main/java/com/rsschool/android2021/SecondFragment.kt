package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null

    private lateinit var sender: OnOutputSender

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /*
        * Check if the host activity implements OnOutputSender interface.
        * */
        if (context is OnOutputSender) {
            sender = context
        } else {
            throw RuntimeException("Something wrong with sending output from SecondFragment: not a suitable context.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        * This callback implementation allows callback added via the #addCallback method
        * to handle the Back button event and return generated result to the first screen.
        * */
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            sender.sendOutput(result?.text.toString().toInt())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result?.text = generate(min, max).toString()

        backButton?.setOnClickListener {
            sender.sendOutput(result?.text.toString().toInt())
        }
    }

    /**
     * Generate a random integer from given range.
     * @param min   value that serves as a start point of the random generated range
     * @param max   value that serves as an end point of the random generated range
     * @return      random generated value in the range from min to max.
     */
    private fun generate(min: Int, max: Int) = (min..max).random()

    companion object {

        /**
         * This annotation tells the compiler to generate an additional static method.
         * Also it allows to leave out multiple companion references in multiple places of the app.
         * That annotation required only when method called from Java code.
         */
        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}
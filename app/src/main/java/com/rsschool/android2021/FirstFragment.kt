package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var minValue: EditText
    private lateinit var maxValue: EditText

    private lateinit var sender: OnInputSender

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnInputSender) {
            sender = context
        } else {
            throw RuntimeException("Something wrong with sending input from FirstFragment: not a suitable context.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val minInput = minValue.text.toString()
            val maxInput = maxValue.text.toString()

            runIfValid(minInput, maxInput)
        }
    }

    private fun runIfValid(firstInput: String, secondInput: String) {
        if (firstInput.isBlank() || secondInput.isBlank()) {
            Toast.makeText(
                this.context,
                "Empty fields are not allowed!\nPlease, try again.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val min = firstInput.toIntOrNull() ?: INVALID_INPUT
            val max = secondInput.toIntOrNull() ?: INVALID_INPUT

            if ((min == INVALID_INPUT || min > Int.MAX_VALUE) || (max == INVALID_INPUT || max > Int.MAX_VALUE)) {
                Toast.makeText(
                    this.context,
                    "Invalid input data: out of maximum value of the integer.\nPlease, try again.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (min > max) {
                    Toast.makeText(
                        this.context,
                        "Min must be less than Max.\nPlease, try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    sender.sendInput(min, max)
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        private const val INVALID_INPUT = -1
    }
}
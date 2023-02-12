package com.uxapp.oktava.ui.myViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.models.StepModel
import com.uxapp.oktava.ui.session.SessionActivity
import com.uxapp.oktava.utils.dpToPx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepFragment(
    private val stepModel: StepModel
) : Fragment() {

    private val sessionViewModel by lazy {
        (requireActivity() as SessionActivity).sessionViewModel
    }

    private lateinit var step: TextView
    private lateinit var name: TextView
    private lateinit var todosContainer: LinearLayout
    private lateinit var adviceContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        view.apply {
            adviceContainer = findViewById(R.id.advicesContainer)
            name = findViewById(R.id.stepNameTextView)
            step = findViewById(R.id.stepNumberTextView)
            todosContainer = findViewById(R.id.todosContainer)
        }
        initBaseStepInfo()
        initTodos()
        initAdvices()
    }

    private fun initBaseStepInfo() {
        name.text = stepModel.stepName
        step.text = "Шаг ${stepModel.stepCount}"
    }

    private fun initTodos() {
        lifecycleScope.launch(Dispatchers.Main) {
            val checkedTodos = sessionViewModel.getCheckedTodos()
            stepModel.todos.forEach {
                val checkBox = CheckBox(requireContext())
                checkBox.isChecked = checkedTodos?.contains(it) ?: false
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    sessionViewModel.checkTodo(it, isChecked)
                }
                val listItem = ListItem(requireContext()).apply {
                    setLeadView(checkBox)
                    setText(it)
                    setBottomSpace(true)
                }
                todosContainer.addView(listItem)
            }
        }

    }

    private fun initAdvices() {
        stepModel.advices.forEach {
            val starView = ImageView(requireContext()).apply {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.updateMargins(top = dpToPx(10f))
                layoutParams = params
                setBackgroundResource(R.drawable.ic_baseline_star_24)
            }
            val listItem = ListItem(requireContext()).apply {
                setLeadView(starView)
                setText(it)
                setBottomSpace(true)
            }
            adviceContainer.addView(listItem)
        }
    }
}
package com.uxapp.oktava.ui.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.models.StepModel
import com.uxapp.oktava.ui.myViews.StepFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionStepsFragment : Fragment() {

    private val sessionViewModel by lazy {
        (requireActivity() as SessionActivity).sessionViewModel
    }

    private lateinit var step1: Button
    private lateinit var step2: Button
    private lateinit var step3: Button
    private lateinit var step4: Button
    private lateinit var step5: Button
    private lateinit var step6: Button
    private lateinit var step7: Button
    private val listOfButtons by lazy {
        listOf(step1, step2, step3, step4, step5, step6, step7)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(
            R.layout.fragment_steps_in_sesssion,
            container,
            false
        )
        setupViews(fragmentView)
        return fragmentView
    }

    private fun setupViews(view: View) {
        view.apply {
            step1 = findViewById(R.id.buttonStep1)
            step2 = findViewById(R.id.buttonStep2)
            step3 = findViewById(R.id.buttonStep3)
            step4 = findViewById(R.id.buttonStep4)
            step5 = findViewById(R.id.buttonStep5)
            step6 = findViewById(R.id.buttonStep6)
            step7 = findViewById(R.id.buttonStep7)
        }
        setupButtons()
        setupSongCardInfo()
    }

    private fun setupButtons() {
        listOfButtons.forEach { button ->
            button.setOnClickListener {
                setStepChosen((it as Button).text.toString().toInt())
            }
        }
    }

    private fun setStepChosen(stepNumber: Int) {
        listOfButtons.forEach {
            if (it.text == stepNumber.toString()) {
                it.alpha = 1f
            } else {
                it.alpha = 0.5f
            }
        }
        childFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(
                R.id.stepFragmentContainer,
                StepFragment(StepModel.get(stepNumber))
            )
            .commit()
        sessionViewModel.setStepChosen(stepNumber)
    }

    private fun setupSongCardInfo() {
        lifecycleScope.launch(Dispatchers.Main) {
            val chosenSongId = sessionViewModel.songsChooseProcess.getChosenSongId()
            val songSessionModel =
                sessionViewModel.getSessionModelOfSong(chosenSongId) ?: return@launch
            val step = if(songSessionModel.step != 0) songSessionModel.step else 1
            setStepChosen(step)
        }
    }
}
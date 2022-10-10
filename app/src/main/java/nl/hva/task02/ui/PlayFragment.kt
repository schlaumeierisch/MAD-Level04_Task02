package nl.hva.task02.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.task02.R
import nl.hva.task02.databinding.FragmentPlayBinding
import nl.hva.task02.model.Move
import nl.hva.task02.model.Result
import nl.hva.task02.repository.ResultRepository
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!

    private lateinit var resultRepository: ResultRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private var alreadyPlayed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultRepository = ResultRepository(requireContext())
        alreadyPlayed = false

        initView()

        binding.ibtnRock.setOnClickListener {
            generateResult(Move.ROCK)
        }
        binding.ibtnPaper.setOnClickListener {
            generateResult(Move.PAPER)
        }
        binding.ibtnScissors.setOnClickListener {
            generateResult(Move.SCISSORS)
        }
    }

    private fun initView() {
        mainScope.launch {
            val numberOfWins = withContext(Dispatchers.IO) {
                resultRepository.getNumberOfWins()
            }
            val numberOfLosses = withContext(Dispatchers.IO) {
                resultRepository.getNumberOfLosses()
            }
            val numberOfDraws = withContext(Dispatchers.IO) {
                resultRepository.getNumberOfDraws()
            }

            binding.tvWinStatistic.text = getString(R.string.win_counter, numberOfWins)
            binding.tvLossStatistic.text = getString(R.string.loss_counter, numberOfLosses)
            binding.tvDrawStatistic.text = getString(R.string.draw_counter, numberOfDraws)
        }
    }

    private fun generateResult(playerMove: Move) {
        val computerMove = listOf(Move.ROCK, Move.PAPER, Move.SCISSORS).random()

        val result = if (playerMove == computerMove) {
            getString(R.string.draw)
        } else if (
            (playerMove == Move.ROCK && computerMove == Move.SCISSORS) ||
            (playerMove == Move.PAPER && computerMove == Move.ROCK) ||
            (playerMove == Move.SCISSORS && computerMove == Move.PAPER)
        ) {
            getString(R.string.win)
        } else {
            getString(R.string.loss)
        }

        // update TextView & ImageViews
        binding.tvCurrentResult.text = result
        binding.ivCurrentComputerMove.setImageResource(
            resources.getIdentifier(
                computerMove.toString().lowercase(), "drawable",
                requireContext().packageName
            )
        )
        binding.ivCurrentPlayerMove.setImageResource(
            resources.getIdentifier(
                playerMove.toString().lowercase(), "drawable",
                requireContext().packageName
            )
        )

        // change visibility to VISIBLE after first time playing
        if (!alreadyPlayed) {
            changeVisibility()
            alreadyPlayed = true
        }

        saveResultToDatabase(result, playerMove, computerMove)

        initView()
    }

    private fun changeVisibility() {
        // TextViews
        binding.tvCurrentResult.visibility = View.VISIBLE
        binding.tvVersus.visibility = View.VISIBLE
        binding.tvComputer.visibility = View.VISIBLE
        binding.tvYou.visibility = View.VISIBLE

        // ImageViews
        binding.ivCurrentComputerMove.visibility = View.VISIBLE
        binding.ivCurrentPlayerMove.visibility = View.VISIBLE
    }

    private fun saveResultToDatabase(result: String, playerMove: Move, computerMove: Move) {
        mainScope.launch {
            val resultToBeAdded = Result(
                date = Date(),
                computerMove = computerMove,
                playerMove = playerMove,
                result = result
            )

            withContext(Dispatchers.IO) {
                resultRepository.addResult(resultToBeAdded)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
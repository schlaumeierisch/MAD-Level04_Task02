package nl.hva.task02.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.task02.databinding.FragmentHistoryBinding
import nl.hva.task02.model.Result
import nl.hva.task02.repository.ResultRepository

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    private lateinit var resultRepository: ResultRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val results = arrayListOf<Result>()
    private val historyAdapter = HistoryAdapter(results)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultRepository = ResultRepository(requireContext())
        getHistoryFromDatabase()

        initRv()

        binding.fabDeleteAllResults.setOnClickListener {
            deleteAllResults()
        }
    }

    private fun deleteAllResults() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                resultRepository.deleteAllResults()
            }

            getHistoryFromDatabase()
        }
    }

    private fun initRv() {
        binding.rvResults.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = historyAdapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getHistoryFromDatabase() {
        mainScope.launch {
            val history = withContext(Dispatchers.IO) {
                resultRepository.getAllResults()
            }
            results.clear()
            results.addAll(history)
            historyAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
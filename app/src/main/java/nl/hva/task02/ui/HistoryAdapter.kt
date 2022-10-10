package nl.hva.task02.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.hva.task02.R
import nl.hva.task02.databinding.ItemResultBinding
import nl.hva.task02.model.Move
import nl.hva.task02.model.Result

class HistoryAdapter(private val history: List<Result>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemResultBinding.bind(itemView)

        fun databind(result: Result) {
            binding.tvResult.text = result.result
            binding.tvDate.text = result.date.toString()

            when (result.computerMove) {
                Move.ROCK -> binding.ivComputerMove.setImageResource(R.drawable.rock)
                Move.PAPER -> binding.ivComputerMove.setImageResource(R.drawable.paper)
                Move.SCISSORS -> binding.ivComputerMove.setImageResource(R.drawable.scissors)
            }

            when (result.playerMove) {
                Move.ROCK -> binding.ivPlayerMove.setImageResource(R.drawable.rock)
                Move.PAPER -> binding.ivPlayerMove.setImageResource(R.drawable.paper)
                Move.SCISSORS -> binding.ivPlayerMove.setImageResource(R.drawable.scissors)
            }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return history.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(history[position])
    }
}
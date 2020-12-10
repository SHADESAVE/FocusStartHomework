package com.example.focusstarthomework.loans.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.focusstarthomework.R

class LoansListAdapter(
    private val loanClickListener: (Loan) -> Unit
) : RecyclerView.Adapter<LoansListViewHolder>() {

    private var loansList = mutableListOf<Loan>()

    fun setLoansList(loansList: List<Loan>) {
        this.loansList.clear()
        this.loansList.addAll(loansList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoansListViewHolder {
        val holder = LoansListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.loans_list_item, parent, false)
        )
        holder.itemView.setOnClickListener {
            loanClickListener(loansList[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount() = loansList.size

    override fun onBindViewHolder(holder: LoansListViewHolder, position: Int) {
        holder.bind(loansList[position])
    }
}
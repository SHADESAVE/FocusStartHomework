package com.example.focusstarthomework.loans.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.loans_list_item.view.*

class LoansListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(loan: Loan) {
        itemView.loan_id.text = loan.id.toString()
        itemView.state.text = loan.state
        itemView.state.setTextColor(loan.stateColor)
    }
}
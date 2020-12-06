package com.example.focusstarthomework.loanslist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.loans_list_item.view.*

class LoansListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(loan: LoanView) {
        itemView.loan_id.text = loan.id.toString()
        itemView.state.apply {
            text = loan.state
            setTextColor(loan.stateColor)
        }
    }
}
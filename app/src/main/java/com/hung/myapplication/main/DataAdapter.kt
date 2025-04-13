package com.hung.myapplication.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hung.myapplication.data.MainStockDataModel
import com.hung.myapplication.databinding.ItemMainStockDataBinding

class DataAdapter(
    private var dataList: List<MainStockDataModel>,
    private val onItemClick: (MainStockDataModel) -> Unit
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(val binding: ItemMainStockDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MainStockDataModel?) {
            binding.tvCode.text = data?.Code
            binding.tvName.text = data?.stockDayAvgAllItem?.Name
            binding.tvOpeningPrice.text = data?.stockDayAllItem?.OpeningPrice
            binding.tvClosingPrice.text = data?.stockDayAllItem?.ClosingPrice
            //比較收盤價vs月平均價
            setTextColor(binding.tvClosingPrice, data?.stockDayAllItem?.ClosingPrice, data?.stockDayAvgAllItem?.MonthlyAveragePrice)
            binding.tvHighestPrice.text = data?.stockDayAllItem?.HighestPrice
            binding.tvLowestPrice.text = data?.stockDayAllItem?.LowestPrice
            binding.tvChange.text = data?.stockDayAllItem?.Change
            //比較漲跌價差,只判斷正負
            setTextColor(binding.tvChange, data?.stockDayAllItem?.Change, "")
            binding.tvMonthlyAveragePrice.text = data?.stockDayAvgAllItem?.MonthlyAveragePrice
            binding.tvTransaction.text = data?.stockDayAllItem?.Transaction
            binding.tvTradeVolume.text = data?.stockDayAllItem?.TradeVolume
            binding.tvTradeValue.text = data?.stockDayAllItem?.TradeValue
        }

        /**
         * 設定文字顏色,高於比較價用紅色,低於用綠色.
         * @param textView 要設定顏色得TextView
         * @param priceVar 目前價位
         * @param compareVar 比較價
         */
        private fun setTextColor(textView: TextView, priceVar: String?, compareVar: String?) {
            val price = priceVar?.toDoubleOrNull() ?: 0.0
            val compare = compareVar?.toDoubleOrNull() ?: 0.0
            if (price > compare){
                textView.setTextColor(android.graphics.Color.RED)
            }else if (price < compare){
                textView.setTextColor(android.graphics.Color.GREEN)
            }else{
                textView.setTextColor(android.graphics.Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemMainStockDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    override fun getItemCount() = dataList.size

    fun updateData(newDataList: List<MainStockDataModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

}
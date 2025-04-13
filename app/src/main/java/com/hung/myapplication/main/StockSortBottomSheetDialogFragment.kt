package com.hung.myapplication.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hung.myapplication.R

class StockSortBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val option1Button: Button = view.findViewById(R.id.descButton)
        val option2Button: Button = view.findViewById(R.id.ascButton)

        option1Button.setOnClickListener {
            Toast.makeText(context, "選擇了選項一Desc", Toast.LENGTH_SHORT).show()
            mainViewModel.sortDataByDesc()
            dismiss() // 關閉 Bottom Sheet
        }

        option2Button.setOnClickListener {
            Toast.makeText(context, "選擇了選項二Asc", Toast.LENGTH_SHORT).show()
            mainViewModel.sortDataByAsc()
            dismiss() // 關閉 Bottom Sheet
        }
    }

}
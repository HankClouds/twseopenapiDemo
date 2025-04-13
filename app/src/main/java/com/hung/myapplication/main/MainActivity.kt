package com.hung.myapplication.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hung.myapplication.R
import com.hung.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DataAdapter(emptyList()) { stockItem ->
            AlertDialog.Builder(this)
                .setTitle("${stockItem.Code} ${stockItem.stockDayAvgAllItem?.Name} 個股資訊")
                .setMessage(
                            "本益比: ${stockItem.bwibbuAllDataItem?.PEratio ?: "-"}\n" +
                            "殖利率: ${stockItem.bwibbuAllDataItem?.DividendYield ?: "-"}\n" +
                            "股價淨值比: ${stockItem.bwibbuAllDataItem?.PBratio ?: "-"}\n")
                .setPositiveButton("確定") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            mainViewModel.data.collectLatest {
                adapter.updateData(it)
            }
        }

        lifecycleScope.launch {
            mainViewModel.error.collectLatest {
                if (it.isNullOrEmpty()) {
                    return@collectLatest
                }
                Toast.makeText(this@MainActivity, "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            mainViewModel.isLoading.collectLatest {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
//            mainViewModel.uiEvent.emit(MainViewModel.UiEvent.LoadData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_options -> {
                //顯示bottomSheet
                val bottomSheetFragment = StockSortBottomSheetDialogFragment()
                bottomSheetFragment.show(supportFragmentManager, StockSortBottomSheetDialogFragment().tag)
            }
        }
        return true
    }

}
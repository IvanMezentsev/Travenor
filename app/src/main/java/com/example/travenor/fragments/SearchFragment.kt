package com.example.travenor.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travenor.R
import com.example.travenor.adapter.PlaceAdapter
import com.example.travenor.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        setupUI(view)
        setupObservers()

        return view
    }

    private fun setupUI(view: View) {
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        val tvCancel = view.findViewById<TextView>(R.id.tvCancel)
        val ivMic = view.findViewById<ImageView>(R.id.ivMic)
        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val rvPlaces = view.findViewById<RecyclerView>(R.id.rvPlaces)

        // 1. Налаштування списку (Grid - 2 стовпці)
        placeAdapter = PlaceAdapter()
        rvPlaces.layoutManager = GridLayoutManager(context, 2)
        rvPlaces.adapter = placeAdapter

        // 2. Кнопка Cancel -> Перехід на Home
        tvCancel.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        // 3. Кнопка Назад (Стрілка) -> Повернутися назад
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 4. Мікрофон -> Toast
        ivMic.setOnClickListener {
            Toast.makeText(context, "We are currently working on this feature", Toast.LENGTH_SHORT).show()
        }

        // 5. Пошук при вводі тексту
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupObservers() {
        // Слухаємо зміни у StateFlow
        lifecycleScope.launch {
            viewModel.places.collect { places ->
                placeAdapter.submitList(places)
            }
        }
    }
}
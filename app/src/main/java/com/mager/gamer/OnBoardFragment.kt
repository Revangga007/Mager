package com.mager.gamer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mager.gamer.databinding.FragmentOnBoardBinding
import com.mager.gamer.ui.onBoard.ViewPagerAdapter

private const val ARG_PARAM1 = "param1"

class OnBoardFragment : Fragment() {
    private var param1: String? = null
    private var _binding: FragmentOnBoardBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            OnBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (param1) {
            "2" -> {
                binding.txtDesc.text =
                    "Bagikan pengalaman, tips dan trick & game dengan gamers lainnya"
                binding.img1.setBackgroundResource(R.drawable.ic_3)
            }
            "1" -> {
                binding.txtDesc.text = "Temukan teman mabar kamu disini"
                binding.img1.setBackgroundResource(R.drawable.ic_2)
            }
            else -> {
                binding.txtDesc.text = "Ayo gabung bersama ribuan \n Komunitas Gamers di Indonesia!"
                binding.img1.setBackgroundResource(R.drawable.ic_2)
            }
        }
    }
}
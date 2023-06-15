package com.example.gbsb.community.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbsb.R
import com.example.gbsb.community.CommentViewModel
import com.example.gbsb.community.CommunityActivity
import com.example.gbsb.community.comment.CommentFragment
import com.example.gbsb.databinding.ActivityCommunityBinding
import com.example.gbsb.databinding.FragmentBoardBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BoardFragment : Fragment() {
    var binding: FragmentBoardBinding?=null
    lateinit var adapter: BoardAdapter
    val model: CommentViewModel by activityViewModels()
    private lateinit var communitydb: DatabaseReference
    lateinit var activityBinding: ActivityCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        activityBinding = (activity as? CommunityActivity)!!.binding
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()
        initContent()
        initBtn()
    }

    private fun initSpinner() {
        val spinnerList = resources.getStringArray(R.array.category_arrays)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerList)
        binding!!.boardSpinner.adapter=spinnerAdapter
        binding!!.boardSpinner.setSelection(0)
        binding!!.boardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                findFromDB()
                activityBinding.communityTitle.text=binding!!.boardSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun initBtn() {
        binding!!.apply {
            boardAdd.setOnClickListener {
                val checkUser = FirebaseAuth.getInstance().currentUser
                if(checkUser?.isAnonymous==true){ // 익명 사용자일 경우
                    Toast.makeText(context, "익명 로그인의 경우 해당 기능을 이용할 수 없습니다.", Toast.LENGTH_LONG).show()
                }else{
                    val fragment = requireActivity().supportFragmentManager.beginTransaction()
                    fragment.addToBackStack(null)
                    val boardAddFragment = BoardAddFragment()
                    fragment.replace(R.id.contentLayout, boardAddFragment)
                    fragment.commit()
                }
            }
        }
    }

    private fun initContent() {
        communitydb= Firebase.database.getReference("Community")
        communitydb.get().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val snapshot = task.result
                if(snapshot.exists()){
                    settingFromDB()
                }
            }else{
                Toast.makeText(activity, "게시판 DB 조회 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun settingFromDB() {
        val query = communitydb.orderByChild("name").limitToLast(100)
        val option = FirebaseRecyclerOptions.Builder<Board>()
            .setQuery(query, Board::class.java)
            .build()
        adapter= BoardAdapter(option)
        adapter.itemClickListener = object:BoardAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val boardId = adapter.getItem(position).boardid
                model.setBoardId(boardId)
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val commentFragment = CommentFragment()
                fragment.replace(R.id.contentLayout, commentFragment)
                fragment.commit()
            }

        }
        binding!!.listView.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.listView.adapter=adapter
        adapter.startListening()
    }

    private fun findFromDB() {
        val selectedCategory = binding!!.boardSpinner.selectedItem.toString()
        if(selectedCategory=="전체"){ // 모든 쿼리 찾기
            settingFromDB()
        }else {
            var query: Query = communitydb.orderByChild("category").equalTo(selectedCategory)
            val option = FirebaseRecyclerOptions.Builder<Board>()
                .setQuery(query, Board::class.java)
                .build()
            adapter = BoardAdapter(option)
            adapter.itemClickListener = object : BoardAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    binding.apply {
                        val boardId = adapter.getItem(position).boardid
                        model.setBoardId(boardId)
                        val fragment = requireActivity().supportFragmentManager.beginTransaction()
                        fragment.addToBackStack(null)
                        val commentFragment = CommentFragment()
                        fragment.replace(R.id.contentLayout, commentFragment)
                        fragment.commit()
                    }
                }
            }

            if (adapter != null) {
                binding!!.listView.adapter = adapter
                adapter.startListening()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
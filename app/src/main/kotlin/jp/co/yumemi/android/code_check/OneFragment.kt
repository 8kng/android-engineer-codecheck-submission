/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.IOException

class OneFragment: Fragment(R.layout.fragment_one){

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentOneBinding.bind(view)
        val viewModel = OneViewModel(requireContext())

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter= CustomAdapter(object : CustomAdapter.OnItemClickListener{
            override fun itemClick(item: item){
                gotoRepositoryFragment(item)
            }
        })

        //入力された文字の検索処理
        viewBinding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {  //キーボードの検索ボタンを押したときの処理
                    editText.text.toString().let {

                        if (editText.text.isNotEmpty()) {  //editTextに文字が入力されている場合
                            try {
                                viewModel.searchResults(it).apply {
                                    adapter.submitList(this)
                                }
                            } catch (e: IOException) {  //オフライン時の時の例外処理
                                print(e)
                                Toast.makeText(activity,"オフラインになっています", Toast.LENGTH_SHORT).show()
                            }
                        } else {  //editTextに文字が入力されている場合
                            Toast.makeText(activity,"入力されていないです", Toast.LENGTH_SHORT).show()
                        }
                    }

                    //キーボードを隠す
                    val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        //リサイクルビューの設定
        viewBinding.recyclerView.also{
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    fun gotoRepositoryFragment(item : item)
    {
        val action = OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}

val diff_util = object: DiffUtil.ItemCallback<item>(){
    override fun areItemsTheSame(oldItem: item, newItem: item): Boolean
    {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem : item, newItem : item): Boolean
    {
        return oldItem == newItem
    }

}

class CustomAdapter(private val itemClickListener : OnItemClickListener) :
    ListAdapter<item, CustomAdapter.ViewHolder>(diff_util){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener{
    	fun itemClick(item: item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
    	val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
    	return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
    	val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            item.name

    	holder.itemView.setOnClickListener{
     		itemClickListener.itemClick(item)
    	}
    }
}
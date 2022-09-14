/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding
import jp.co.yumemi.android.code_check.TwoFragmentArgs as TwoFragmentArgs1

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs1 by navArgs()

    private var binding: FragmentTwoBinding? = null
    private val rootView get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentTwoBinding.bind(view)

        //リサイクルビューから選択したリポジトリの情報をlayoutに反映する
        val pickItem = args.item

        rootView.ownerIconView.load(pickItem.ownerIconUrl)                        //アイコン表示
        rootView.nameView.text = pickItem.name                                    //制作者名表示
        rootView.languageView.text = pickItem.language                            //利用言語表示
        rootView.starsView.text = "${pickItem.stargazersCount} stars"             //star数表示
        rootView.watchersView.text = "${pickItem.watchersCount} watchers"         //watcher数表示
        rootView.forksView.text = "${pickItem.forksCount} forks"                  //fork数表示
        rootView.openIssuesView.text = "${pickItem.openIssuesCount} open issues"  //open issue数表示
    }
}

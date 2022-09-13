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

        var item = args.item

        rootView.ownerIconView.load(item.ownerIconUrl);
        rootView.nameView.text = item.name;
        rootView.languageView.text = item.language;
        rootView.starsView.text = "${item.stargazersCount} stars";
        rootView.watchersView.text = "${item.watchersCount} watchers";
        rootView.forksView.text = "${item.forksCount} forks";
        rootView.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}

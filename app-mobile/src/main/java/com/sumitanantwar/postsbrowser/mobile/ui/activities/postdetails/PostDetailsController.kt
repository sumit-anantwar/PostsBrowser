package com.sumitanantwar.postsbrowser.mobile.ui.activities.postdetails

import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.sumitanantwar.mvp.MvpController
import com.sumitanantwar.postsbrowser.mobile.R
import javax.inject.Inject

class PostDetailsController @Inject constructor()
    : MvpController<PostDetailsContract.View, PostDetailsContract.Presenter>(),
    PostDetailsContract.View {

    //======= ButterKnife Binders =======

    @BindView(R.id.text_post_id)
    lateinit var testPostId: TextView

    @BindView(R.id.text_post_title)
    lateinit var testPostTitle: TextView

    @BindView(R.id.text_post_body)
    lateinit var testPostBody: TextView



    //======= Overrides =======
    override lateinit var presenter: PostDetailsPresenter
    override val layoutId: Int = R.layout.post_details_controller


    override fun onViewBound(view: View) {

        presenter = PostDetailsPresenter(this)

    }
}
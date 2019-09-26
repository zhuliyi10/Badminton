package com.leory.badminton.video.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.leory.badminton.video.R
import com.leory.badminton.video.di.component.DaggerVideoComponent
import com.leory.badminton.video.mvp.contract.VideoListContract
import com.leory.badminton.video.mvp.model.bean.VideoBean
import com.leory.badminton.video.mvp.presenter.VideoPresenter
import com.leory.badminton.video.mvp.ui.adapter.VideoAdapter
import com.leory.commonlib.base.BaseFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.utils.ToastUtils
import com.leory.commonlib.widget.morePop.MorePopBean
import com.leory.interactions.RouterHub
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.fragment_video_main.*

/**
 * Describe : 视频首页
 * Author : zhuly
 * Date : 2019-05-22
 */
@Route(path = RouterHub.VIDEO_VIDEOMAINFRAGMENT)
class VideoMainFragment : BaseFragment<VideoPresenter>(), VideoListContract.View {


    private val morePopBeans: List<MorePopBean> by lazy {
        listOf(MorePopBean("全部"),
                MorePopBean("少于1分钟"),
                MorePopBean("大于1分钟"),
                MorePopBean("少于10分钟"),
                MorePopBean("大于10分钟"),
                MorePopBean("少于30分钟"),
                MorePopBean("大于30分钟"))
    }


    private var videoAdapter: VideoAdapter? = null

    override fun startLoadMore() {

    }

    override fun endLoadMore() {
        refreshLayout!!.finishLoadMore()
    }

    override fun showVideoList(data: List<VideoBean>, refresh: Boolean) {
        if (refresh) {
            videoAdapter!!.setNewData(data)
        } else {
            videoAdapter!!.addData(data)
        }
    }

    override fun setupActivityComponent(component: IComponent): IComponent {
        DaggerVideoComponent.builder()
                .view(this)
                .appComponent(component as AppComponent)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }


    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_video_main, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar.addMorePopView(morePopBeans) { pos, name ->
            presenter!!.setSelectPos(pos)
            showMessage(name)
        }
        toolbar.morePopView!!.setSelectStateChange(true)
        rcv.layoutManager = LinearLayoutManager(context)
        videoAdapter = VideoAdapter(ArrayList())
        rcv.adapter = videoAdapter
        refreshLayout.setOnRefreshListener { presenter!!.requestData(true) }
        refreshLayout.setOnLoadMoreListener { presenter!!.requestData(false) }
        refreshLayout.autoRefresh()

        rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    val linearManager = layoutManager as LinearLayoutManager?
                    //获取最后一个可见view的位置
                    val lastItemPosition = linearManager!!.findLastVisibleItemPosition()
                    //获取第一个可见view的位置
                    val firstItemPosition = linearManager.findFirstVisibleItemPosition()
                    //大于0说明有播放
                    if (GSYVideoManager.instance().playPosition >= 0) {
                        //当前播放的位置
                        val position = GSYVideoManager.instance().playPosition
                        //对应的播放列表TAG
                        if (GSYVideoManager.instance().playTag == VideoAdapter.TAG && (position < firstItemPosition || position > lastItemPosition)) {
                            if (GSYVideoManager.isFullState(activity)) {
                                return
                            }
                            //如果滑出去了上面和下面就是否，和今日头条一样
                            GSYVideoManager.releaseAllVideos()
                            videoAdapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
        refreshLayout!!.finishRefresh()
    }

    override fun showMessage(message: String) {
        ToastUtils.showShort(message)
    }


    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun onBackPressed(): Boolean {
        return if (GSYVideoManager.backFromWindowFull(activity)) {
            true//表示处理了
        } else {
            false
        }
    }
}

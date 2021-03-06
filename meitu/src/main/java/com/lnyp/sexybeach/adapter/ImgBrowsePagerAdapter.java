package com.lnyp.sexybeach.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lnyp.sexybeach.R;
import com.lnyp.sexybeach.common.Const;
import com.lnyp.sexybeach.entry.ListEntity;
import com.lnyp.sexybeach.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImgBrowsePagerAdapter extends PagerAdapter {

    List<ListEntity> imgs;

    List<View> views;

    Activity mContext;

    private DisplayImageOptions mListItemOptions;

    public ImgBrowsePagerAdapter(Activity context, List<ListEntity> imgs) {

        this.mContext = context;
        this.imgs = imgs;

        this.views = new ArrayList<View>();

        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        mListItemOptions = new DisplayImageOptions.Builder()
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.default_empty_bg)
                .showImageOnLoading(R.drawable.default_empty_bg)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.default_empty_bg)
                // 加载图片时会在内存、磁盘中加载缓存
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() { // 获得size
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String imgUrl = Const.BASE_IMG_URL2 + imgs.get(position).getSrc();

        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.img_browse, null);
        final PhotoView img = (PhotoView) view.findViewById(R.id.photoViewImg);

        img.setTag(imgUrl);
        ImageLoaderUtil.getInstance().displayListItemImage(imgUrl, img, mListItemOptions);

        img.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {

                mContext.finish();
            }
        });
        img.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                mContext.finish();
            }
        });

        ((ViewPager) container).addView(view);

        return view;
    }
}